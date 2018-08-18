package org.meizhuo.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.meizhuo.taotao.common.jedis.JedisClient;
import org.meizhuo.taotao.common.pojo.EasyUIDataGridResult;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.common.utils.IDUtils;
import org.meizhuo.taotao.common.utils.JsonUtils;
import org.meizhuo.taotao.mapper.TbItemDescMapper;
import org.meizhuo.taotao.mapper.TbItemMapper;
import org.meizhuo.taotao.pojo.TbItem;
import org.meizhuo.taotao.pojo.TbItemDesc;
import org.meizhuo.taotao.pojo.TbItemExample;
import org.meizhuo.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/24 17:13
 * @UpdateUser:
 * @UpdateDate: 2018/7/24 17:13
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper mapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;

    @Override
    public TbItem getItemById(long itemId) {

        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存中没有，查询数据库
        //根据主键查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andIdEqualTo(itemId);
        //执行查询
        List<TbItem> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            //把结果添加到缓存
            try {
                jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(list.get(0)));
                //设置过期时间
                jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {

        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        //把结果添加到缓存
        try {
            jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
            //设置过期时间
            jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        if (page == null) page = 1;
        if (rows == null) rows = 30;
        PageHelper.startPage(page, rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> items = mapper.selectByExample(example);
        PageInfo<TbItem> info = new PageInfo<>(items);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int) info.getTotal());
        result.setRows(info.getList());
        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
        //生成商品ID
        final long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //1-正常 2-下架 3-刪除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //补全item属性
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        //插入数据
        mapper.insert(item);
        tbItemDescMapper.insert(tbItemDesc);
        //发送消息通知搜索模块将更新的商品同步到索引库
        jmsTemplate.send(topicDestination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });
        return E3Result.ok();
    }
}
