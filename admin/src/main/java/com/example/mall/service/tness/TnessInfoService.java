package com.example.mall.service.tness;

import com.baomidou.mybatisplus.service.IService;
import com.example.mall.entity.tness.TnessInfo;
import com.example.mall.rvo.TnessQueryVo;
import com.example.mall.rvo.TnessResultVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qmt
 * @since 2019-07-22
 */
public interface TnessInfoService extends IService<TnessInfo> {
    /**
     * 保存数据
     * @param tnessInfo
     * @return
     */
    boolean saveData(TnessInfo tnessInfo);
    List<TnessResultVo> getTnessDataByDate(TnessQueryVo qo);
}
