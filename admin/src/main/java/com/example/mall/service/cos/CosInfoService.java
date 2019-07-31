package com.example.mall.service.cos;

import com.baomidou.mybatisplus.service.IService;
import com.example.mall.entity.cos.CosInfo;

import java.io.File;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qmt
 * @since 2019-07-19
 */
public interface CosInfoService extends IService<CosInfo> {
    /**
     * 储存 backetName 和 key
     *
     * @param bucketName
     * @param key
     * @return
     */
    boolean saveBucketNameAndKey(String bucketName, String key, String imgUrl);

    /**
     * cos上传文件
     *
     * @param file
     * @return
     */
    String uploadFile(File file);

    /**
     * 删除上传到cos上的文件
     */
    void delCosFile(String url);
}
