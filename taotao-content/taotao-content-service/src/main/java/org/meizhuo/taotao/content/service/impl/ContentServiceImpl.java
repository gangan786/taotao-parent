package org.meizhuo.taotao.content.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.meizhuo.taotao.common.jedis.JedisClient;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.common.utils.JsonUtils;
import org.meizhuo.taotao.content.service.ContentService;
import org.meizhuo.taotao.mapper.TbContentMapper;
import org.meizhuo.taotao.pojo.TbContent;
import org.meizhuo.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public E3Result addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        tbContentMapper.insert(content);
        //缓存同步，删除缓存中对应的数据
        jedisClient.hdel(CONTENT_LIST,content.getCategoryId().toString());
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(long cid) {

        //查询缓存
        try {
            String json = jedisClient.hget(CONTENT_LIST, cid + "");
            if (StringUtils.isNotBlank(json)) {
                return JsonUtils.jsonToList(json, TbContent.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果没有就查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        //将结果添加到缓存
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        try {
            jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
