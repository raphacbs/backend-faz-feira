package com.coelho.fazfeira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    HttpServletRequest request;
    public UUID getLoggedUserId() {
        Map<String, String> map = (Map<String, String>) request.getAttribute("claims");
        return UUID.fromString(map.get("sub"));
    }
}
