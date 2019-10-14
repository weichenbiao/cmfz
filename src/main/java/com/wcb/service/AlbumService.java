package com.wcb.service;

import com.wcb.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AlbumService {
    public Map<String, Object> queryByPage(Integer page, Integer rows);

    public String edit(Album album, String oper, String[] id);

    public void upload(MultipartFile imgPath, String id, HttpSession session);
}
