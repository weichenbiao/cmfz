package com.wcb.mapper;

import com.wcb.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    public Admin queryByUsername(@Param("username") String username);
}
