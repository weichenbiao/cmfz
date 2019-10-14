package com.wcb.serviceimpl;

import com.wcb.entity.Admin;
import com.wcb.mapper.AdminMapper;
import com.wcb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, String> login(String username, String password, String enCode, HttpSession session) {
        Map<String, String> map = new HashMap<>();
        String verCode = (String) session.getAttribute("verCode");
        Admin admin = adminMapper.queryByUsername(username);
        session.setAttribute("admin", admin);
        if (admin == null) {
            String msg = "用户名不存在";
            map.put("msg", msg);
        } else if (!admin.getPassword().equals(password)) {
            String msg = "密码不正确";
            map.put("msg", msg);
        } else if (!verCode.equals(enCode)) {
            String msg = "验证码不正确";
            map.put("msg", msg);
        }
        return map;
    }
}
