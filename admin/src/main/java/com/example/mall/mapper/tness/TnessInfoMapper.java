package com.example.mall.mapper.tness;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.mall.entity.tness.TnessInfo;
import com.example.mall.rvo.TnessQueryVo;
import com.example.mall.rvo.TnessResultVo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qmt
 * @since 2019-07-22
 */
public interface TnessInfoMapper extends BaseMapper<TnessInfo> {
    List<TnessResultVo> getTnessDataByDate(TnessQueryVo qo);
}
