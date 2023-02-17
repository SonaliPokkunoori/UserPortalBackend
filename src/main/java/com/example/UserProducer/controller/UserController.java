package com.example.UserProducer.controller;

import com.example.UserProducer.dto.LoginDTO;
import com.example.UserProducer.dto.StatusDTO;
import com.example.UserProducer.dto.UserDTO;
import com.example.UserProducer.entities.User;
import com.example.UserProducer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafkaUser")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUserDetails")
    public ResponseEntity<User> addUserDetails(@RequestBody UserDTO userDTO){
        User user=userService.addUserDetails(userDTO);
        System.out.println("Hello~");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/getUserDetails/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable("userId") String userId){
        User user=userService.getUserDetails(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/getUserDetailsByName/{userName}")
    public ResponseEntity<User> getUserDetailsByName(@PathVariable("userName") String userName){
        User user=userService.getUserDetailsByName(userName);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>("Data deleted!",HttpStatus.OK);
    }

    @PostMapping("/getLoginStatus")
    public ResponseEntity<StatusDTO> loginStatus(@RequestBody LoginDTO loginDTO){
        StatusDTO loginStatus=userService.loginStatus(loginDTO);
        return new ResponseEntity<>(loginStatus,HttpStatus.OK);
    }

    @PostMapping("/getLogoutStatus/{userId}")
    public ResponseEntity<Boolean> logoutStatus(@PathVariable("userId") String userId){
        Boolean logoutStatus = userService.logoutStatus(userId);
        return new ResponseEntity<>(logoutStatus,HttpStatus.OK);
    }

    @PostMapping("/editProfile")
    public ResponseEntity<User> editProfile(@RequestBody User user){
        User user1 = userService.editProfile(user);
        return new ResponseEntity<>(user1,HttpStatus.OK);
    }
}
