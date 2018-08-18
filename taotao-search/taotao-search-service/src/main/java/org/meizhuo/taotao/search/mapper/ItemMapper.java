package org.meizhuo.taotao.search.mapper;

import org.meizhuo.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.search.mapper
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/4 14:27
 * @UpdateUser:
 * @UpdateDate: 2018/8/4 14:27
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ItemMapper {
    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
