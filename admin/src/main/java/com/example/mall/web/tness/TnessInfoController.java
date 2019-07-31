package com.example.mall.web.tness;


import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.example.mall.comment.CommentResult;
import com.example.mall.entity.tness.TnessInfo;
import com.example.mall.pvo.TnessParamVo;
import com.example.mall.rvo.TnessQueryVo;
import com.example.mall.rvo.TnessResultVo;
import com.example.mall.service.tness.TnessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qmt
 * @since 2019-07-22
 */
@Controller
@RequestMapping("mall/tness")
public class TnessInfoController {
    @Autowired
    private TnessInfoService tnessInfoService;

    @RequestMapping("/saveData")
    @ResponseBody
    public CommentResult saveData(@RequestBody TnessInfo tnessInfo) {
        tnessInfoService.saveData(tnessInfo);
        return CommentResult.success();
    }

    @RequestMapping("/getTnessDataByDate")
    @ResponseBody
    public CommentResult getTnessDataByDate(@RequestBody TnessParamVo vo) {
        Map<String, Object> map = new HashMap<>();
        List<TnessResultVo> tnessDataByDate = tnessInfoService.getTnessDataByDate(this.getQueryVo(vo.getQueryDataType()));
        List<Map<String, Object>> pieRows = this.getPieRows(tnessDataByDate);
        List<Map<String, Object>> lineRows = this.getlineRows(tnessDataByDate);
        map.put("pieRows", pieRows);
        map.put("lineRows", lineRows);
        return CommentResult.success(map);
    }

    private List<Map<String, Object>> getPieRows(List<TnessResultVo> resultVos) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        for (TnessResultVo resultVo : resultVos) {
            map = new HashMap<>();
            map.put("日期", resultVo.getDateTime());
            map.put("燃烧卡路里", resultVo.getTotalCalorie());
            list.add(map);
        }
        return list;
    }

    private List<Map<String, Object>> getlineRows(List<TnessResultVo> resultVos) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        for (TnessResultVo resultVo : resultVos) {
            map = new HashMap<>();
            map.put("日期", resultVo.getDateTime());
            map.put("运动距离", resultVo.getTotalRange());
            map.put("运动时长", resultVo.getTotalDuration());
            map.put("燃烧卡路里", resultVo.getTotalCalorie());
            list.add(map);
        }
        return list;
    }

    private TnessQueryVo getQueryVo(String queryType) {
        TnessQueryVo vo = new TnessQueryVo();
        vo.setEndDate(new Date());
        if ("week".equals(queryType)) {
            vo.setStartDate(DateUtil.lastWeek());
        }
        if ("month".equals(queryType)) {
            vo.setStartDate(DateUtil.lastMonth());
        }
        return vo;
    }
}
