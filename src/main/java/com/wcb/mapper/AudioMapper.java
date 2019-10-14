package com.wcb.mapper;

import com.wcb.entity.Audio;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AudioMapper {
    public List<Audio> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows, @Param("aid") String aid);

    public Integer queryCount(String aid);

    public void updateAudio(@Param("id") String id, @Param("size") String size, @Param("timelong") String timelong);

    public void updateAudio1(@Param("id") String id, @Param("audio") String audio);

    public void add(Audio audio);

    public void update(Audio audio);

    public void delete(String[] id);

    public void deleteAudio(String[] id);

}
