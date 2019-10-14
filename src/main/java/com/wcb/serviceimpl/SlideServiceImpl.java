package com.wcb.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.wcb.entity.Slide;
import com.wcb.mapper.SlideMapper;
import com.wcb.service.SlideService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideMapper slideMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Slide> queryAll() {
        return slideMapper.queryAll();
    }

    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Integer count = slideMapper.count();
        Integer start = (page - 1) * rows;
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        List<Slide> slides = slideMapper.queryByPage(start, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", slides);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @Override
    public String edit(Slide slide, String oper, String[] id) {
        if ("add".equals(oper)) {
            String s = UUID.randomUUID().toString().replace("-", "");
            slide.setId(s);
            slideMapper.add(slide);
            return s;
        } else if ("del".equals(oper)) {
            slideMapper.delete(id);
            return null;
        } else {
            slideMapper.update(slide);
            return null;
        }
    }

    @Override
    public void upload(MultipartFile imgpath, String id, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = imgpath.getOriginalFilename();
        String newName = new Date().getTime() + "_" + originalFilename;
        try {
            imgpath.transferTo(new File(realPath, newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
//修改图片路径
        slideMapper.updatePath(newName, id);

    }

    @Override
    public void out(HttpServletResponse response) {
        List<Slide> slides = slideMapper.queryAll();

        for (Slide slide : slides) {
            slide.setImgpath("E:\\ideaworkspace\\后期项目\\cmfz\\src\\main\\webapp\\img\\" + slide.getImgpath());
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图信息", "轮播图"),
                Slide.class, slides);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("content-disposition", "attachment");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
