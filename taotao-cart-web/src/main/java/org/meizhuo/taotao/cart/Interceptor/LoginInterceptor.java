package org.meizhuo.taotao.cart.Interceptor;

import org.apache.commons.lang3.StringUtils;
import org.meizhuo.taotao.common.utils.CookieUtils;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.pojo.TbUser;
import org.meizhuo.taotao.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.cart.Interceptor
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/23 13:46
 * @UpdateUser:
 * @UpdateDate: 2018/8/23 13:46
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    /**
     * 判断请求用户是否登录，如果登录就往request中添加一个
     * user标志位，并将用户数据写往request中，这样往下的controller中就可以根据该标志位判断用户是否登录
     * 从而实现两种不同的购物车存储方案
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // 前处理，执行handler之前执行此方法。
        //返回true，放行	false：拦截
        //1.从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        //2.如果没有token，未登录状态，直接放行
        if (StringUtils.isBlank(token)) {
            return true;
        }
        //3.取到token，需要调用sso系统的服务，根据token取用户信息
        E3Result e3Result = tokenService.getUserByToken(token);
        //4.没有取到用户信息。登录过期，直接放行。
        if (e3Result.getStatus() != 200) {
            return true;
        }
        //5.取到用户信息。登录状态。
        TbUser user = (TbUser) e3Result.getData();
        //6.把用户信息放到request中。只需要在Controller中判断request中是否包含user信息。放行
        httpServletRequest.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
