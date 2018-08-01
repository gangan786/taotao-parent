package org.meizhuo.taotao.content.service.impl;

import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.content.service.ContentService;
import org.meizhuo.taotao.mapper.TbContentMapper;
import org.meizhuo.taotao.pojo.TbContent;
import org.meizhuo.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.content.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/1 17:40
 * @UpdateUser:
 * @UpdateDate: 2018/8/1 17:40
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public E3Result addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        tbContentMapper.insert(content);
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        return tbContentMapper.selectByExampleWithBLOBs(example);
    }
}
