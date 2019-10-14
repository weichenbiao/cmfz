package com.wcb.controller;

import com.wcb.entity.Audio;
import com.wcb.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

//音频
@RestController
@RequestMapping("audio")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows, String aid) {
        Map<String, Object> map = audioService.queryByPage(page, rows, aid);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Audio audio, String[] id, String oper, String aid) {
        String edit = audioService.edit(audio, oper, id, aid);
        return edit;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile audio, String id, HttpSession session) throws Exception {
        audioService.upload(audio, id, session);
    }


    @RequestMapping("/download")
    public void download(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        audioService.download(filename, request, response);
    }


}
