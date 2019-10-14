package com.wcb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

//文章编辑图片上传
@RestController
@RequestMapping("kindeditor")
public class Kindeditor {
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile img, HttpSession session, HttpServletRequest request) throws IOException {
        String realPath = session.getServletContext().getRealPath("/img");
        String s = new Date().getTime() + "_" + img.getOriginalFilename();
        File file = new File(realPath, s);
        img.transferTo(file);
        Map<String, Object> map = new HashMap<>();
        map.put("error", 0);
        //获取协议
        String scheme = request.getScheme();
        //获取ip地址
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取端口号
        int port = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        String url = scheme + "://" + hostAddress + ":" + port + contextPath + "/img/" + s;

        map.put("url", url);
        return map;
    }

    @RequestMapping("allImages")
    public Map<String, Object> allimage(HttpSession session, HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        String[] all = file.list();

        List<Map<String, Object>> list = new ArrayList<>();
        for (String s : all) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("is_dir", false);
            hashMap.put("has_file", false);
            hashMap.put("dir_path", "");
            hashMap.put("is_photo", true);
            //获取文件大小
            File file1 = new File(s);
            long length = file1.length();
            hashMap.put("filesize", length);

            //文件后缀
            String substring = s.substring(s.indexOf(".") + 1);
            hashMap.put("filetype", substring);
            hashMap.put("filename", s);

            if (s.contains("_")) {
                String s1 = s.split("_")[0];
                Long aLong = Long.valueOf(s1);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(aLong);
                hashMap.put("datetime", format);
            } else {
                hashMap.put("datetime", new Date());
            }
            list.add(hashMap);
        }

        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        String sch = request.getScheme();
        int port = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        String url = sch + "://" + hostAddress + ":" + port + contextPath + "/img/";
        map.put("current_url", url);
        map.put("total_count", list.size());
        map.put("file_list", list);
        return map;
    }
}
