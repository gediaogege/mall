package com.example.mall.admin;

import cn.hutool.core.io.FileTypeUtil;
import com.example.mall.service.cos.CosInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@SpringBootTest
@RunWith(SpringRunner.class)
public class COSTest {
    @Autowired
    private CosInfoService cosInfoService;

    @Test
    public void test1() {
        String uploadFile = cosInfoService.uploadFile(new File("D://timg.jpg"));
        System.out.println(uploadFile);
    }

    @Test
    public void test2() {
        String type = FileTypeUtil.getType(new File("D://timg.jpg"));
        System.out.println(type);
    }

    @Test
    public void test3() {
        cosInfoService.delCosFile("http://gediao-1257281765.cos.ap-guangzhou.myqcloud.com/mall-admin2019-07-19d041e4db106b4bee85fde30620ad1683jpg");
    }
}
