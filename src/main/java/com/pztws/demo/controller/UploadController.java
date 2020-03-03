package com.pztws.demo.controller;

import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication2;
import com.pztws.demo.utils.annotation.UserLoginToken;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class UploadController {
    // ...生成上传凭证，然后准备上传 从yml配置文件取出
  //  @Value("${qiniu.accessKey}")
    String accessKey="JERx5-LwqlfZE86BkCZRPpFf1-pbD7tXXdVUOblQ";

    //@Value("${qiniu.secretKey}")
    String secretKey="-hiFroVuqvfxZvAJKzJj6kJSqqXBZt2hxVbbG0pU";

  //  @Value("${qiniu.bucket}")
    String bucket="pztws";

  //  @Value("${qiniu.domain}")
    String domain="http://pic.stadc.cn/";

    // 获取上传凭证
    @GetMapping("/uptoken")
    @UserLoginToken
    @LevelAuthentication2
    public Result getUptoken() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Result result = new Result();
        result.setData(upToken);
        result.setStatus(true);
        return result;
    }

    /**
     * StringMap putPolicy = new StringMap();
     * putPolicy.put("returnBody", 在此可自定义传回的数据
     * "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
     * long expireSeconds = 3600;
     * String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
     * System.out.println(upToken);
     *
     *
     *
     */

}
