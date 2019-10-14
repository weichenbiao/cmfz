package com.wcb.controller;

import com.wcb.entity.Maps;
import com.wcb.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//用户
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("queryMap")
    public List<Maps> queryMap() {
        List<Maps> maps = userService.queryMap();
        return maps;
    }

    @RequestMapping("queryDate")
    public Map<String, List> queryDate() {
        return userService.findDate();
    }

    @RequestMapping("go")
    public void goeasy(String inp) {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-d2595c81a10248f59ec9fbb788f8b780");
        goEasy.publish("wcb", inp);
    }

}
