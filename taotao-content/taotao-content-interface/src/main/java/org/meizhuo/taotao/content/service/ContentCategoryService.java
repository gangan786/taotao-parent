package org.meizhuo.taotao.content.service;

import org.meizhuo.taotao.common.pojo.EasyUIDataGridResult;
import org.meizhuo.taotao.common.pojo.EasyUITreeNode;
import org.meizhuo.taotao.common.utils.E3Result;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.content.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/1 15:02
 * @UpdateUser:
 * @UpdateDate: 2018/8/1 15:02
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(long parentId);
    E3Result addContentCategory(long parent,String name);
}
