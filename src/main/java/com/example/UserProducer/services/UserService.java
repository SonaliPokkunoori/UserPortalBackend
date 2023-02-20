package com.example.UserProducer.services;

import com.example.UserProducer.dto.LoginDTO;
import com.example.UserProducer.dto.StatusDTO;
import com.example.UserProducer.dto.UserDTO;
import com.example.UserProducer.entities.User;
import com.example.UserProducer.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    UserRepository userRepository;

    @CachePut(value = "userCache")
    public User addUserDetails(Object userDTO) {
        kafkaTemplate.send("userProducerTopic", userDTO);
        System.out.println("Data sent to kafka");

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User user1 = getUserDetailsByName(user.getUserName());
        boolean checkUserName = userRepository.existsByUserName(user.getUserName());
        if (checkUserName) {
            System.out.println("Username already exists!");
            return new User();
        }
        userRepository.save(user);
        System.out.println("Data sent to Mongo");
        return user;
    }

    //    @Cacheable(value = "userCache")
    public User getUserDetails(String userId) {
        Optional<User> user = userRepository.findById(userId);
        User user1 = new User();
        if (user.isPresent()) {
            user1 = user.get();
        }
        return user1;
    }

    public User getUserDetailsByName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        User user1 = new User();
        if (user.isPresent()) {
            user1 = user.get();
        }
        return user1;
    }

    @CacheEvict(value = "userCache")
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
        System.out.println("Successfully Deleted!");
    }

    public StatusDTO loginStatus(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByUserName(loginDTO.getUserName());
        StatusDTO statusDTO = new StatusDTO();
        if (user.isPresent()) {
            User user1 = user.get();
            if (user1.getPassword().equals(loginDTO.getPassword())) {
                statusDTO.setIsvalid(true);
                statusDTO.setUserId(user1.userId);
                user1.setIsvalid(true);
                userRepository.save(user1);
                return statusDTO;
            } else {
                user1.setIsvalid(false);
                statusDTO.setIsvalid(false);
            }
            userRepository.save(user1);
        } else {
            System.out.println("No User");
            return new StatusDTO();
        }
//        statusDTO.setIsvalid(false);
        return statusDTO;
    }

    public Boolean logoutStatus(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
//        else {
//            System.out.println("No user");
//            return null;
//        }
        System.out.println(user);
        if (user.getIsvalid() == true) {
            user.setIsvalid(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @CacheEvict(value = "userCache")
    public User editProfile(User user) {
        Optional<User> user1 = userRepository.findById(user.getUserId());
        User user2 = new User();
        if (user1.isPresent()) {
            user2 = user1.get();
        } else {
            System.out.println("No user");
            return new User();
        }
//        user2.setUserId(user.getUserId());
//        user2.setUserName(user.getUserName());
//        user2.setPassword(user.getPassword());
//        user2.setPhno(user2.getPhno());
//        user2.setMaritalStatus(user2.getMaritalStatus());
//        user2.setEmail(user.getEmail());
//        user2.setDob(user.getDob());
        BeanUtils.copyProperties(user, user2);
        userRepository.save(user2);
        System.out.println("Data Updated!!");
        return user2;
    }

    public User forgotPassword(String userId, String password) {
        Optional<User> user = userRepository.findById(userId);
        User user1 = new User();
        if (user.isPresent()) {
            user1 = user.get();
        } else {
            System.out.println("No User");
            return new User();
        }
        user1.setPassword(password);
        System.out.println("Updated Password!!");
        userRepository.save(user1);
        return user1;
    }
}

