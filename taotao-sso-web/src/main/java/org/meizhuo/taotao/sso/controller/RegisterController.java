package org.meizhuo.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.sso.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/20 21:34
 * @UpdateUser:
 * @UpdateDate: 2018/8/20 21:34
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class RegisterController {
    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }
}
