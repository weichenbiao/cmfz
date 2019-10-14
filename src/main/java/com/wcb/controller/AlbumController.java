package com.wcb.controller;

import com.wcb.entity.Album;
import com.wcb.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

//专辑
@RestController
@RequestMapping("album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = albumService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Album album, String[] id, String oper) {
        String edit = albumService.edit(album, oper, id);
        return edit;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile photo, String id, HttpSession session) {
        albumService.upload(photo, id, session);
    }
}
