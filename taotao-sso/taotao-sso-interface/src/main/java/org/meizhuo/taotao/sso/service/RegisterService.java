package org.meizhuo.taotao.sso.service;

import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.pojo.TbUser;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.sso.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/21 10:48
 * @UpdateUser:
 * @UpdateDate: 2018/8/21 10:48
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface RegisterService {
    E3Result checkData(String param, int type);
    E3Result register(TbUser user);


}
