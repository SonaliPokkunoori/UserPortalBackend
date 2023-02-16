package com.example.UserProducer;

import com.example.UserProducer.dto.LoginDTO;
import com.example.UserProducer.entities.User;
import com.example.UserProducer.repository.UserRepository;
import com.example.UserProducer.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserProducerApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void testAddUser(){
		User user=new User();
		user.setUserName("SONALI");
		user.setPassword("sonadev");
		user.setEmail("sonalipokkunoori@gmail.com");
		user.setIsvalid(false);
		user.setMaritalStatus("married");
		user.setPhno("6303036364");
		userService.addUserDetails(user);
	}

	@Test
	public void testgetUserDetails(){
		userService.getUserDetailsByName("SONALI");
	}

	@Test
    public void testgetUserDetailsById(){
//	    User user = userService.getUserDetailsByName("SONALI");
//	    String userId= user.getUserId();
	    userService.getUserDetails("63ee112c2d18692bdfb32d10");
    }

    @Test
    public void testDeleteByUserId(){
	    userService.deleteUserById("63ee112c2d18692bdfb32d10");
    }

    @Test
    public void testLoginStatus(){
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setUserName("SONALI");
        loginDTO.setPassword("sonadev");
	    userService.loginStatus(loginDTO);
    }

    @Test
    public void testLoginStatusFalse(){
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setUserName("SONALI");
        loginDTO.setPassword("sonalidev");
        userService.loginStatus(loginDTO);
    }

    @Test
    public void testLogoutStatus(){
	    userService.logoutStatus("63ee112c2d18692bdfb32d10");
    }

}
