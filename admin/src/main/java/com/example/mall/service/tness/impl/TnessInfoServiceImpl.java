package com.example.mall.service.tness.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mall.entity.tness.TnessInfo;
import com.example.mall.mapper.tness.TnessInfoMapper;
import com.example.mall.rvo.TnessQueryVo;
import com.example.mall.rvo.TnessResultVo;
import com.example.mall.service.tness.TnessInfoService;
import com.example.mall.util.JUUIDUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmt
 * @since 2019-07-22
 */
@Service
public class TnessInfoServiceImpl extends ServiceImpl<TnessInfoMapper, TnessInfo> implements TnessInfoService {

    @Override
    public boolean saveData(TnessInfo tnessInfo) {
        tnessInfo.setCreateTime(new Date());
        tnessInfo.setId(JUUIDUtil.createUuid());
        tnessInfo.setRemark(DateUtil.formatDate(tnessInfo.getDurationTime()));
        return this.insert(tnessInfo);
    }

    @Override
    public List<TnessResultVo> getTnessDataByDate(TnessQueryVo qo) {
        return this.baseMapper.getTnessDataByDate(qo);
    }
}
