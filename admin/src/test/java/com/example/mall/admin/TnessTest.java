package com.example.mall.admin;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.mall.rvo.TnessQueryVo;
import com.example.mall.rvo.TnessResultVo;
import com.example.mall.service.tness.TnessInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TnessTest {
    @Autowired
    private TnessInfoService tnessInfoService;

    @Test
    public void test() {
        List<TnessResultVo> tnessDataByDate = tnessInfoService.getTnessDataByDate(new TnessQueryVo());
        System.out.println(tnessDataByDate);
    }

    @Test
    public void test1() {
        String formatDate = DateUtil.formatDate(DateUtil.lastWeek());
        System.out.println(formatDate);
    }
}
