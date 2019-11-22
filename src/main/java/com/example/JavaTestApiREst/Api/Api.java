package com.example.JavaTestApiREst.Api;

import com.example.JavaTestApiREst.Controllers.UserController;
import com.example.JavaTestApiREst.Models.UserModel;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@CrossOrigin
@RequestMapping("api/v1/users/")
@RestController
public class Api {
    private UserController userController;
    private Gson gson;

    public Api() {
        userController = new UserController();
        gson = new Gson();
    }

    @RequestMapping("/actives")
    @GetMapping
    public ArrayList<UserModel> ActivesUsers(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return userController.ActivesUsers();
    }

    @RequestMapping("/cities")
    @PostMapping
    public ArrayList<UserModel> citiesByLetter(@RequestBody String message,
                                               HttpServletResponse response) {
        ArrayList<UserModel> users = userController.citiesWithLetter(message);
        if(users == null){
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return null;
        }
        return users;
    }

    @RequestMapping("/creationDate")
    @GetMapping
    public ArrayList<UserModel> creationDate(@RequestBody String message,
                                                 HttpServletResponse response) {
        ArrayList<UserModel> users = userController.listByCreationDate(message);
        if(users == null){
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return null;
        }
        return users;
    }

    @RequestMapping("/addUser")
    @PostMapping
    public String addUser(@RequestBody String data,
                          HttpServletResponse response){
        String res = userController.addUser(data);
        if(res.equals("User registered"))
            response.setStatus(HttpServletResponse.SC_OK);
        if(res.equals("Problem with names or city or actives param"))
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        if(res.equals("email not valid"))
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if(res.equals("invalid bday format"))
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        return res;
    }
}
