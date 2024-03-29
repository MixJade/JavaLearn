package com.example.mysecurity.service;

public interface RoleConst {
    // 赋予角色时强制要求前面加上ROLE_
    // 验证时不需要，验证角色时，会自动在前面加ROLE_
    String USER="ROLE_USER",
            ADMIN="ROLE_ADMIN",
            ADMIN2="ROLE_ADMIN2";
}
