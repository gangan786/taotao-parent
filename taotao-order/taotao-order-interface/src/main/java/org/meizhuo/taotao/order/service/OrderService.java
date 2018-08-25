package org.meizhuo.taotao.order.service;

import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.order.pojo.OrderInfo;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.order.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/24 15:53
 * @UpdateUser:
 * @UpdateDate: 2018/8/24 15:53
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface OrderService {
    E3Result createOrder(OrderInfo orderInfo);
}
