package com.example.UserProducer.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    public String userName;
    public String password;
    public String email;
    public String phno;
    public String maritalStatus;
    public Date dob;

}
