package com.example.mall.rvo;

import lombok.Data;

@Data
public class TnessResultVo {
    public Double totalDuration;//总运动时长
    private Double totalRange;//总运动距离
    private Double totalCalorie;//总卡路里
    private String dateTime;//日期
    private int tnessType;//运动类型


}
