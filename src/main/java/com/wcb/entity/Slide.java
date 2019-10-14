package com.wcb.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//轮播图
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Slide {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "状态", replace = {"显示_显示", "不显示_不显示"})
    private String status;
    @Excel(name = "描述", width = 25, height = 15)
    private String intr;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "日期", format = "yyyy-MM-dd", width = 20)
    private Date creat_time;
    @Excel(name = "图片", type = 2)
    private String imgpath;
}
