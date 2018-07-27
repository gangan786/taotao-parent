package org.meizhuo.taotao.service;

import org.meizhuo.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/27 10:24
 * @UpdateUser:
 * @UpdateDate: 2018/7/27 10:24
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
