package com.wcb.service;


import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    public Map<String, String> login(String username, String password, String enCode, HttpSession session);
}
