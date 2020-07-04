package com.tideseng.codeblocks.service.impl;

import com.tideseng.codeblocks.annotation.InvokeInfo;
import com.tideseng.codeblocks.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @InvokeInfo("根据用户ID获取用户名")
    @Override
    public String getName(String id) {
        return String.format("章佳欢-%s", id);
    }

}

