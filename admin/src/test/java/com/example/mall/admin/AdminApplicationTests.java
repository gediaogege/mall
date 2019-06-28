package com.example.mall.admin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.mall.entity.admin.UserAdmin;
import com.example.mall.service.admin.UserAdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTests {
	@Autowired
		private UserAdminService userAdminService;
	@Test
	public void contextLoads() {
        List<UserAdmin> userAdmins = userAdminService.selectList(new EntityWrapper<>());
        System.out.println(userAdmins);
    }

}
