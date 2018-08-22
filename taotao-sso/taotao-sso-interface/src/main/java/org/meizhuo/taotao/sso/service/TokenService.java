package org.meizhuo.taotao.sso.service;

import org.meizhuo.taotao.common.utils.E3Result;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.sso.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/21 17:03
 * @UpdateUser:
 * @UpdateDate: 2018/8/21 17:03
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface TokenService {
    E3Result getUserByToken(String token);
}
