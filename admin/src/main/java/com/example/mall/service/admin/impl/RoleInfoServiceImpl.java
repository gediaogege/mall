package com.example.mall.service.admin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mall.entity.admin.RoleInfo;
import com.example.mall.entity.admin.RoleInfoExtend;
import com.example.mall.mapper.admin.RoleInfoMapper;
import com.example.mall.service.admin.PermissionInfoService;
import com.example.mall.service.admin.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmt
 * @since 2019-07-01
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements RoleInfoService {
}
