package com.wcb.service;

import com.wcb.entity.Audio;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AudioService {
    public Map<String, Object> queryByPage(Integer page, Integer rows, String aid);

    public String edit(Audio audio, String oper, String[] id, String aid);

    public void upload(MultipartFile audio, String id, HttpSession session) throws Exception;

    public void download(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception;


}
