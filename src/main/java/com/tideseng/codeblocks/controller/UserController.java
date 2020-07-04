package com.tideseng.codeblocks.controller;

import com.tideseng.codeblocks.annotation.InvokeInfo;
import com.tideseng.codeblocks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @InvokeInfo("获取用户名")
    @RequestMapping("/findName")
    public String getUserName(@RequestParam String id){
        return this.userService.getName(id);
    }

}
