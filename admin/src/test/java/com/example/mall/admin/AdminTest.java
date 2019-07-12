package com.example.mall.admin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.mall.comment.ComemtSatues;
import com.example.mall.entity.admin.PermissionInfo;
import com.example.mall.entity.admin.UserAdmin;
import com.example.mall.entity.admin.UserAdminExtend;
import com.example.mall.pvo.PermissionQueryVo;
import com.example.mall.service.admin.UserAdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {
    @Autowired
    private UserAdminService userAdminService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void getSysRoles() {
        List<Map<String, String>> list = userAdminService.getSysRoles();
        System.out.println(list);
    }

    @Test
    public void getUserRoles() {
        List<String> list = userAdminService.getUserRoles("1");
    }

    @Test
    public void test1() {
        List<UserAdmin> userAdmins = userAdminService.selectList(new EntityWrapper<UserAdmin>());
        List<UserAdminExtend> userAdminExtends = userAdminService.userAdminExtend(userAdmins);
        System.out.println(userAdminExtends);
    }

    @Test
    public void test2() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void delRole() {
        userAdminService.delRole("8608e53df0d84adc8a71ff3e0bc6f283");
    }

    @Test
    public void permissionPage() {
        PermissionQueryVo vo = new PermissionQueryVo();
        vo.setStatus(ComemtSatues.NORMAL.getStatus());
        vo.setPermissionName("微信管理");
        Page<PermissionInfo> permissionInfoPage = userAdminService.permissionPage(vo);
        System.out.println(permissionInfoPage);
    }
}
