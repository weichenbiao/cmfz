package com.wcb.serviceimpl;

import com.wcb.entity.Audio;
import com.wcb.mapper.AlbumMapper;
import com.wcb.mapper.AudioMapper;
import com.wcb.service.AudioService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
public class AudioServiceImpl implements AudioService {

    @Autowired
    private AudioMapper audioMapper;
    @Autowired
    private AlbumMapper albumMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows, String aid) {
        Integer star = (page - 1) * rows;
        Integer integer = audioMapper.queryCount(aid);
        Map<String, Object> hashMap = new HashMap<>();
        List<Audio> audio = audioMapper.queryByPage(star, rows, aid);
        Integer total = integer % rows == 0 ? integer / rows : integer / rows + 1;
        hashMap.put("records", integer);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", audio);
        return hashMap;
    }

    @Override
    public String edit(Audio audio, String oper, String[] id, String aid) {

        if ("add".equals(oper)) {
            String replace = UUID.randomUUID().toString().replace("-", "");
            audio.setId(replace);
            audio.setAudioId(aid);
            audio.setPrintTime(new Date());
            audioMapper.add(audio);
            Integer integer = albumMapper.queryByCount(aid);
            albumMapper.updateCount(aid, integer + 1);
            return replace;
        }
        if ("del".equals(oper)) {
            audioMapper.delete(id);
            Integer integer = albumMapper.queryByCount(aid);
            albumMapper.updateCount(aid, integer - 1);
        }
        if ("edit".equals(oper)) {
            audioMapper.update(audio);
        }
        return null;
    }

    @Override
    public void upload(MultipartFile audio, String id, HttpSession session) throws Exception {
        String name = audio.getOriginalFilename();
        String newName = new Date().getTime() + "_" + name;

        String audioPath = session.getServletContext().getRealPath("/audio");
        audio.transferTo(new File(audioPath, newName));

        audioMapper.updateAudio1(id, newName);
        //获取文件位置
        String ss = "/audio/" + newName;
        String realPath = session.getServletContext().getRealPath(ss);
        File file = new File(realPath);
        //获取文件大小  单位是字节 byte
        long length = file.length();
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = AudioFileIO.read(file);

        AudioHeader header = read.getAudioHeader();
        int trackLength = header.getTrackLength();
        //获取分钟数
        Integer m = trackLength / 60;
        //获取秒秒数
        Integer s = trackLength % 60;
        String timelong = m + "分" + s + "秒";
        //将文件大小强转成double类型
        double size1 = (double) length;
        //获取文件大小 单位是MB
        double ll = size1 / 1024 / 1024;
        //取double小数点后两位  四舍五入
        BigDecimal bg = new BigDecimal(ll).setScale(2, RoundingMode.UP);
        String size = bg + "MB";
        audioMapper.updateAudio(id, size, timelong);
    }

    @Override
    public void download(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取目标文件夹的路径
        String realPath = request.getSession().getServletContext().getRealPath("/audio");
        //读入
        FileInputStream fis = new FileInputStream(new File(realPath, filename));

        //写出
        ServletOutputStream os = response.getOutputStream();
        //设置响应头
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        //拷贝
        IOUtils.copy(fis, os);
        //关流
        IOUtils.closeQuietly(fis);
        IOUtils.closeQuietly(os);

    }
}
