package com.example.shopproject.ControllerTest;

import com.example.UserTest.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "/test")
    public UserDto test(){
        UserDto userDto=new UserDto();
        userDto.setName("Joo");
        userDto.setAge(23);

        return userDto;
    }
}
