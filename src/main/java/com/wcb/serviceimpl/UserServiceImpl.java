package com.wcb.serviceimpl;

import com.wcb.entity.Maps;
import com.wcb.mapper.UserMapper;
import com.wcb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Maps> queryMap() {
        List<Maps> list = userMapper.queryMap();
        return list;
    }

    @Override
    public Map<String, List> findDate() {
        List<Maps> date = userMapper.findDate();
        List<Integer> listCount = new ArrayList<>();
        List<String> listDate = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for (Maps maps : date) {
            listCount.add(maps.getValue());
            listDate.add(format.format(maps.getTime()));
        }
        Map<String, List> hashMap = new HashMap<>();
        hashMap.put("aa", listCount);
        hashMap.put("bb", listDate);
        return hashMap;
    }
}
