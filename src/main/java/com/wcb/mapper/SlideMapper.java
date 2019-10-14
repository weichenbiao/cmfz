package com.wcb.mapper;

import com.wcb.entity.Slide;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlideMapper {

    public List<Slide> queryAll();

    public List<Slide> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    public Integer count();

    public void add(Slide slide);

    public void update(Slide slide);

    public void updatePath(@Param("name") String name, @Param("id") String id);

    public void delete(String[] id);

}
