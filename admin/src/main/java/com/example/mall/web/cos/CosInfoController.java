package com.example.mall.web.cos;


import com.example.mall.comment.CommentResult;
import com.example.mall.pvo.UploadParamVo;
import com.example.mall.service.cos.CosInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qmt
 * @since 2019-07-19
 */
@Controller
@RequestMapping("mall/cos")
public class CosInfoController {
    @Autowired
    private CosInfoService cosInfoService;

    @ResponseBody
    @RequestMapping("/upload")
    public CommentResult upload(@RequestParam("file") MultipartFile file) throws Exception {
        String uploadFile = cosInfoService.uploadFile(multipartFileToFile(file));
        return CommentResult.success(uploadFile);
    }

    public File multipartFileToFile(@RequestParam MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;

    }

    public void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
