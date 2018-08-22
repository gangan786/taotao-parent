package org.meizhuo.taotao.service.impl;

import org.meizhuo.taotao.common.jedis.JedisClient;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.common.utils.JsonUtils;
import org.meizhuo.taotao.mapper.TbUserMapper;
import org.meizhuo.taotao.pojo.TbUser;
import org.meizhuo.taotao.pojo.TbUserExample;
import org.meizhuo.taotao.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/21 14:01
 * @UpdateUser:
 * @UpdateDate: 2018/8/21 14:01
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {


        // 1、判断用户和密码是否正确
        //根据用户名查询用户信息
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return E3Result.build(400, "用户名或密码错误");
        }
        //取用户信息
        TbUser user = list.get(0);
        //判断密码是否正确
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            // 2、如果不正确，返回登录失败
            return E3Result.build(400, "用户名或密码错误");
        }
        // 3、如果正确生成token。
        String token = UUID.randomUUID().toString();
        // 4、把用户信息写入redis，key：token value：用户信息
        user.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
        // 5、设置Session的过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        // 6、把token返回

        return E3Result.ok(token);
    }
}
