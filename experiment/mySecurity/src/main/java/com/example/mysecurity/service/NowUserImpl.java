package com.example.mysecurity.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public final class NowUserImpl implements INowUser {
    @Override
    public Object getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
