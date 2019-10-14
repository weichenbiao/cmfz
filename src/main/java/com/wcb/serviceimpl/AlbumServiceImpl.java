package com.wcb.serviceimpl;

import com.wcb.entity.Album;
import com.wcb.mapper.AlbumMapper;
import com.wcb.mapper.AudioMapper;
import com.wcb.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    AudioMapper audioMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        Integer records = albumMapper.count();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Album> list = albumMapper.queryByPage(start, rows);
        map.put("rows", list);
        map.put("total", total);
        map.put("records", records);
        map.put("page", page);
        return map;
    }

    @Override
    public String edit(Album album, String oper, String[] id) {
        if ("add".equals(oper)) {
            String ids = UUID.randomUUID().toString().replace("-", "");
            album.setId(ids).setUpTime(new Date()).setCount(0);
            albumMapper.add(album);
            return ids;
        }
        if ("edit".equals(oper)) {
            albumMapper.update(album);
        }
        if ("del".equals(oper)) {
            audioMapper.deleteAudio(id);
            albumMapper.delete(id);
        }
        return null;
    }

    @Override
    public void upload(MultipartFile photo, String id, HttpSession session) {
        String originalFilename = photo.getOriginalFilename();
        String newName = new Date().getTime() + "_" + originalFilename;
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            photo.transferTo(new File(realPath, newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        albumMapper.updatePhoto(id, newName);
    }
}
