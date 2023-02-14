package com.example.UserProducer.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class User {

    @Id
    public String userId;
    public String password;
    public String userName;
    public String email;
    public String phno;
    public String maritalStatus;
    public Date dob;

}
