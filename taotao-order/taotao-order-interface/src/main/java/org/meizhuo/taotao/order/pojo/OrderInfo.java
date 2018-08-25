package org.meizhuo.taotao.order.pojo;

import org.meizhuo.taotao.pojo.TbOrder;
import org.meizhuo.taotao.pojo.TbOrderItem;
import org.meizhuo.taotao.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.order.pojo
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/24 15:43
 * @UpdateUser:
 * @UpdateDate: 2018/8/24 15:43
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class OrderInfo extends TbOrder implements Serializable {


    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;
    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }
    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
