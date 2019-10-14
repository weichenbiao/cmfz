package com.wcb.service;

import com.wcb.entity.Slide;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface SlideService {

    public List<Slide> queryAll();

    public Map<String, Object> queryByPage(Integer page, Integer rows);

    public String edit(Slide slide, String oper, String[] id);

    public void upload(MultipartFile imgPath, String id, HttpSession session);

    public void out(HttpServletResponse response);

}
