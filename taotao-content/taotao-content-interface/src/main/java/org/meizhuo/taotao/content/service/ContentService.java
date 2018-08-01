package org.meizhuo.taotao.content.service;

import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.pojo.TbContent;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.content.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/1 17:41
 * @UpdateUser:
 * @UpdateDate: 2018/8/1 17:41
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface ContentService {
    E3Result addContent(TbContent content);
    List<TbContent> getContentListByCid(long cid);
}
