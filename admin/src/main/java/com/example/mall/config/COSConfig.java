package com.example.mall.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component("COSConfig")
public class COSConfig {
    @Value("${cos.appid}")
    private int APPID;

    @Value("${cos.secretid}")
    private String SCRETID;

    @Value("${cos.secretkey}")
    private String SCRETKEY;

    @Value("${cos.buckertname}")
    private String BUCKET_NAME;

    @Value("${cos.prefix}")
    private String PERFIX;
}
