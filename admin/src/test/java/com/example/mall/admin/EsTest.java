package com.example.mall.admin;

import com.example.mall.pvo.PermissionQueryVo;
import com.example.mall.search.EsPermissionInfo;
import com.example.mall.service.search.EsPermissionInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
    @Autowired
    private EsPermissionInfoService esPermissionInfoService;

    @Test
    public void importAll() {
        esPermissionInfoService.importData();
    }

    @Test
    public void search() {
        PermissionQueryVo qo = new PermissionQueryVo();
        qo.setPermissionName("微信管理");
        qo.setStatus(1);
        Page<EsPermissionInfo> search = esPermissionInfoService.search(qo);
        int totalPages = search.getTotalPages();
        List<EsPermissionInfo> content = search.getContent();
        System.out.println(content);
        System.out.println(totalPages);
        for (EsPermissionInfo esPermissionInfo : search) {

            System.out.println(esPermissionInfo);
        }

    }
}
