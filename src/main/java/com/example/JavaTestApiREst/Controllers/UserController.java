package com.example.JavaTestApiREst.Controllers;

import com.example.JavaTestApiREst.Helper.Helper;
import com.example.JavaTestApiREst.Models.UserModel;
import com.example.JavaTestApiREst.Repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserController {

    private UserRepository userRepository;
    private Gson gson;

    public UserController() {
        userRepository = new UserRepository("/home/sergio/Dropbox/JavaTestApiREst/src/main/java/com/example/JavaTestApiREst/test.json");
        gson = new Gson();
    }

    public ArrayList<UserModel> ActivesUsers() {
        return userRepository.listActiveUsers();
    }

    public ArrayList<UserModel> citiesWithLetter(String message){
        try {
            JsonElement jelem = gson.fromJson(message, JsonElement.class);
            String letter = jelem.getAsJsonObject().get("letter").getAsString();
            if(Helper.containsNumbers(letter)){
                return null;
            }
            if(letter.length() > 1){
                return null;
            }
            return userRepository.listCityByLetter(letter);
        }catch (Exception e){
            System.out.println("Cities With Users" + e);
        }
        return null;
    }

    public ArrayList<UserModel> listByCreationDate(String data){
        try {
            JsonElement jelem = gson.fromJson(data, JsonElement.class);
            String option = jelem.getAsJsonObject().get("option").getAsString();
            if (!Helper.trueOrFalse(option)) {
                return null;
            }
            boolean opt = Boolean.parseBoolean(option);
            return userRepository.listByCreationDate(opt);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public String addUser(String data) {
        JsonElement jelem = gson.fromJson(data, JsonElement.class);
        ArrayList<String> userData = getUserData(jelem);
        LocalDateTime date = LocalDateTime.now();

        String name = getName(userData.get(0));
        String surname = getSurname(userData.get(1));
        String actives = getActives(userData.get(2));
        String email = getEmail(userData.get(3));
        String city = getCity(userData.get(4));
        String bday = getBday(userData.get(5));

        if(name.equals("") || surname.equals("") ||
                actives.equals("") || city.equals(""))
            return "Problem with names or city or actives param";
        if(email.equals(""))
            return "email not valid";
        if(bday.equals(""))
            return "invalid bday format";
        userRepository.addUser(name,surname,
               Boolean.parseBoolean(actives), email, city,bday, date);
        return "User registered";
    }


    private ArrayList<String> getUserData(JsonElement jelem) {
        ArrayList<String> userData = new ArrayList<>();

        try {
            userData.add(jelem.getAsJsonObject().get("name").getAsString());
            userData.add(jelem.getAsJsonObject().get("surname").getAsString());
            userData.add(jelem.getAsJsonObject().get("active").getAsString());
            userData.add(jelem.getAsJsonObject().get("email").getAsString());
            userData.add(jelem.getAsJsonObject().get("city").getAsString());
            userData.add(jelem.getAsJsonObject().get("bday").getAsString());
            return userData;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private String getName(String name){
        if (Helper.containsNumbers(name))
            return "";
        if (Helper.tooShort(name))
            return "";
        return name;
    }

    private String getSurname(String name) {
        if (Helper.containsNumbers(name))
            return "";
        if (Helper.tooShort(name))
            return "";
        return name;
    }

    private String getEmail(String email) {
        if (!Helper.isEmail(email))
            return "";
        return email;
    }

    private String getActives(String active) {
        if (!Helper.trueOrFalse(active))
            return "";
        return active;
    }

    private String getCity(String city) {
        if (Helper.tooShort(city))
            return "";
        return city;


    }

    private String getBday(String bday) {
        if (!Helper.checkFormat(bday))
            return "";
        return bday;
    }




}
