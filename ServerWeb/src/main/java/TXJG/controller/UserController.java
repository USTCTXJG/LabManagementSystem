package TXJG.controller;
import TXJG.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import  TXJG.bean.User;
import  TXJG.mapper.UserMapper;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/getAllUser")
    @ResponseBody
    private List<User> getAllUser() {
        List<User> users =  userService.getAllUser();

        return users;

    }

}
