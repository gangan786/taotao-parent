package org.meizhuo.taotao.controller;

import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.common.utils.FastDFSClientUtil;
import org.meizhuo.taotao.common.utils.JsonUtils;
import org.meizhuo.taotao.pojo.TbItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/31 10:43
 * @UpdateUser:
 * @UpdateDate: 2018/7/31 10:43
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVICE_URL;

    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile) {
        try {
            FastDFSClientUtil clientUtil = new FastDFSClientUtil("classpath:conf/client.conf");
            String filename = uploadFile.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            String url = clientUtil.uploadFile(uploadFile.getBytes(), extName);
            url = IMAGE_SERVICE_URL + url;
            HashMap map = new HashMap<>();
            map.put("error",0);
            map.put("url",url);
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            HashMap map = new HashMap<>();
            map.put("error",0);
            map.put("message","图片上传失败");
            return JsonUtils.objectToJson(map);
        }
    }



}
