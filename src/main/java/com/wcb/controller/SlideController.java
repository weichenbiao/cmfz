package com.wcb.controller;

import com.wcb.entity.Slide;
import com.wcb.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

//轮播图
@RestController
@RequestMapping("slide")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @RequestMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = slideService.queryByPage(page, rows);
        return map;
    }


    @RequestMapping("edit")
    public String edit(Slide slide, String oper, String[] id) {
        String edit = slideService.edit(slide, oper, id);
        return edit;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile imgpath, String id, HttpSession session) {
        slideService.upload(imgpath, id, session);
    }

    //导出excel表
    @RequestMapping("import")
    public void importExcel(HttpServletResponse response) {
        slideService.out(response);
    }


}
