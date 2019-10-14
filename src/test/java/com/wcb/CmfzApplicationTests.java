package com.wcb;

import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class CmfzApplicationTests {
/*
    @Test
    public void contextLoads() {

        //创建文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个表
        HSSFSheet sheet = workbook.createSheet();

        //创建一行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("卫大大");




        try {
            workbook.write(new FileOutputStream(new File("D:/a.xls")));
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void name() {
        //创建文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个表
        HSSFSheet sheet = workbook.createSheet();

        HSSFRow row = sheet.createRow(0);

        String[] str={"id","姓名","生日"};
        for (int i = 0; i < str.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(str[i]);
        }

        List<User> list = new ArrayList<>();
        User user1 = new User("1","卫",new Date());
        User user2 = new User("2","晨",new Date());
        User user3 = new User("3","彪",new Date());
        list.add(user1);
        list.add(user2);
        list.add(user3);

        for (int i = 0; i < list.size(); i++) {
            HSSFRow row1 = sheet.createRow(i+1);
            HSSFCell cell = row1.createCell(0);
            cell.setCellValue(list.get(i).getId());

        }


    }*/

    @Test
    public void name() {

        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-d2595c81a10248f59ec9fbb788f8b780");
        goEasy.publish("wcb", "Hello, GoEasy!");

    }
}
