package org.meizhuo.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.sso.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/21 17:12
 * @UpdateUser:
 * @UpdateDate: 2018/8/21 17:12
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class TokenController {



    @Autowired
    private TokenService tokenService;

    /*@RequestMapping(value="/user/token/{token}",
            produces=MediaType.APPLICATION_JSON_UTF8_VALUE"application/json;charset=utf-8")
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        E3Result result = tokenService.getUserByToken(token);
        //响应结果之前，判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            //把结果封装成一个js语句响应
            return callback + "(" + JsonUtils.objectToJson(result)  + ");";
        }
        return JsonUtils.objectToJson(result);
    }*/
    @RequestMapping(value="/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        E3Result result = tokenService.getUserByToken(token);
        //响应结果之前，判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            //把结果封装成一个js语句响应
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }

}
