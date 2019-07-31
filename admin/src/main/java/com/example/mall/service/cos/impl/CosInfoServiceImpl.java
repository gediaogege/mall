package com.example.mall.service.cos.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.mall.comment.ComemtSatues;
import com.example.mall.config.COSConfig;
import com.example.mall.entity.cos.CosInfo;
import com.example.mall.mapper.cos.CosInfoMapper;
import com.example.mall.service.cos.CosInfoService;
import com.example.mall.util.JUUIDUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmt
 * @since 2019-07-19
 */
@Service
@Transactional
@Slf4j
public class CosInfoServiceImpl extends ServiceImpl<CosInfoMapper, CosInfo> implements CosInfoService, InitializingBean {
    @Autowired
    private COSConfig cosConfig;
    private COSClient cosClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        COSCredentials cred = new BasicCOSCredentials(cosConfig.getSCRETID(), cosConfig.getSCRETKEY());
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        this.cosClient = new COSClient(cred, clientConfig);
    }

    @Override
    public boolean saveBucketNameAndKey(String bucketName, String key, String imgUrl) {
        CosInfo cosInfo = new CosInfo();
        cosInfo.setId(JUUIDUtil.createUuid());
        cosInfo.setBacketName(bucketName);
        cosInfo.setKey(key);
        cosInfo.setUrl(imgUrl);
        cosInfo.setStatus(ComemtSatues.NORMAL.getStatus());
        return this.insert(cosInfo);

    }

    @Override
    public String uploadFile(File file) {
        String resultUrl = "";
        try {
            String uploadKey = getUploadKey()+"."+getFileType(file);
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosConfig.getBUCKET_NAME(), uploadKey, file);
            cosClient.putObject(putObjectRequest);
            String generatePresignedUrl = cosClient.generatePresignedUrl(new GeneratePresignedUrlRequest(cosConfig.getBUCKET_NAME(), uploadKey)).toString();
            resultUrl = generatePresignedUrl.substring(0, generatePresignedUrl.indexOf("?"));
            cosClient.shutdown();
            this.saveBucketNameAndKey(cosConfig.getBUCKET_NAME(), uploadKey, resultUrl);
            log.info("上传图片成功，URL：{}", resultUrl);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
            log.error("上传图片出错：{}", serverException.getMessage(), serverException);
        }
        return resultUrl;
    }

    @Override
    public void delCosFile(String url) {
        CosInfo cosInfo = this.selectOne(new EntityWrapper<CosInfo>().eq("url", url));
        if (cosInfo != null) {
            try {
                cosClient.deleteObject(cosInfo.getBacketName(), cosInfo.getKey());
                this.deleteById(cosInfo.getId());
                log.info("删除图片成功,URL:{}", url);
            } catch (Exception e) {
                log.error("删除图片失败: {}", e.getMessage(), e);
            }
        }
    }

    private String getUploadKey() {
        return cosConfig.getPERFIX() + DateUtil.formatDate(new Date()) + JUUIDUtil.createUuid();
    }

    private String getFileType(File file) {
        return FileTypeUtil.getType(file);
    }


}
