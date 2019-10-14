package com.wcb.mapper;

import com.wcb.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    public List<Album> queryByPage(@Param("start") Integer sart, @Param("row") Integer row);

    public Integer count();

    public void add(Album album);

    public void delete(String[] id);

    public void updatePhoto(@Param("id") String id, @Param("photo") String photo);

    public void update(Album album);

    public void updateCount(@Param("id") String id, @Param("count") Integer count);

    public Integer queryByCount(String id);

}
