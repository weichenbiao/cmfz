package com.wcb.service;

import com.wcb.entity.Maps;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<Maps> queryMap();

    public Map<String, List> findDate();
}
