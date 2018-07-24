package org.meizhuo.taotao.controller;

import org.meizhuo.taotao.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/24 8:37
 * @UpdateUser:
 * @UpdateDate: 2018/7/24 8:37
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test/queryNow")
    @ResponseBody
    public String queryNow(){

        return testService.queryNow();
    }
}
