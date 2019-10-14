package com.wcb.mapper;

import com.wcb.entity.Maps;

import java.util.List;

public interface UserMapper {
    public List<Maps> queryMap();

    public List<Maps> findDate();
}
