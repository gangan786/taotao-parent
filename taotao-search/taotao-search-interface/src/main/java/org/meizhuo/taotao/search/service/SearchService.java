package org.meizhuo.taotao.search.service;

import org.meizhuo.taotao.common.pojo.SearchResult;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.search.service
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/4 22:00
 * @UpdateUser:
 * @UpdateDate: 2018/8/4 22:00
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface SearchService {
    SearchResult search(String keyWord,int page,int rows) throws Exception;
}
