package org.meizhuo.taotao.cart.service;

import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.pojo.TbItem;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.cart.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/23 14:18
 * @UpdateUser:
 * @UpdateDate: 2018/8/23 14:18
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface CartService {
    E3Result addCart(long userId, long itemId, int num);
    E3Result mergeCart(long userId, List<TbItem> itemList);
    List<TbItem> getCartList(long userId);
    E3Result updateCartNum(long userId, long itemId, int num);
    E3Result deleteCartItem(long userId, long itemId);
}
