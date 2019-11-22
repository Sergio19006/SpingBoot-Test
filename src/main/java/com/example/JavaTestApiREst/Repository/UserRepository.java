package com.example.JavaTestApiREst.Repository;

import com.example.JavaTestApiREst.Models.UserModel;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import com.google.gson.Gson;

public class UserRepository {
    private ArrayList<UserModel> users = new ArrayList<>();
    private String file;
    private Gson gson = new Gson();


    public UserRepository(String file) {
        this.file = file;
        String contentFile = readFile();
        UserModel[] users = gson.fromJson(contentFile, UserModel[].class);
        this.users.addAll(Arrays.asList(users));
    }

    private String readFile(){

        try {
            FileReader reader = new FileReader(this.file);
            StringBuilder contentFile = new StringBuilder();

            //Read all content file to a string to create the array of users
            int ch;
            while ((ch = reader.read()) != -1) {
                char readCharacter = (char) ch;
                contentFile.append(readCharacter);
            }
            reader.close();
            return contentFile.toString();
        } catch (Exception e){
            System.out.println(e);
        }
        return "";

    }

    public ArrayList<UserModel> listActiveUsers() {
        ArrayList<UserModel> activesUsers = new ArrayList<>();
        for (UserModel user : users) {
            if (user.active)
                activesUsers.add(user);
        }
        return activesUsers;
    }

    public ArrayList<UserModel> listCityByLetter(String character) {
        ArrayList<UserModel> usersByCity = new ArrayList<>();
        for (UserModel user : users) {
            //Here check the first letter of the city and if there are igual show the user
            if (user.city.indexOf(character) == 0)
                usersByCity.add(user);
        }
        return usersByCity;
    }

    public ArrayList<UserModel> listByCreationDate(boolean ascending) {
        ArrayList<UserModel> usersByCreationDates = new ArrayList<>();
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        for (UserModel user : users) {
            LocalDateTime date = LocalDateTime.parse(user.creationDate);
            dates.add(date);
        }
        if (ascending)
            Collections.sort(dates);
        else
            dates.sort(Collections.reverseOrder());

        for (LocalDateTime date : dates)
            for (UserModel user : users){
                LocalDateTime dateUser = LocalDateTime.parse(user.creationDate);
                if (date.compareTo(dateUser) == 0)
                    usersByCreationDates.add(user);
            }
        return usersByCreationDates;
    }

    public void addUser(String name, String surname, boolean active, String email, String city, String bday, LocalDateTime date) {
        UserModel newUser = createUser(name, surname, active, email, city, bday, date);

        StringBuilder dataToWrite = new StringBuilder("[");

        this.users.add(newUser);

        try {
            //Here i overwrite all the users
            for (UserModel user : this.users)
                dataToWrite.append(gson.toJson(user)).append(",").append("\n");

            //Here i eliminate the last ',' to avoid problems in the read.
            dataToWrite = new StringBuilder(dataToWrite.substring(0, dataToWrite.length() - 2));
            dataToWrite.append("]");

            FileWriter writer = new FileWriter(this.file);
            writer.write(dataToWrite.toString());
            writer.close();
        }catch (Exception e){
            System.out.println("error" + e);
        }
    }

    private UserModel createUser(String name, String surname, boolean active, String email, String city, String bday,LocalDateTime d) {
        String date = d.toString();
        return new UserModel(name, surname, active, email, city, bday, date);
    }
}
