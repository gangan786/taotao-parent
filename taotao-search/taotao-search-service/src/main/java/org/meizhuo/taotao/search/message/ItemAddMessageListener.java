package org.meizhuo.taotao.search.message;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.meizhuo.taotao.common.pojo.SearchItem;
import org.meizhuo.taotao.search.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.search.message
 * @ClassName: ${TYPE_NAME}
 * @Description:监听商品添加消息，接受消息后将对应商品消息同步到索引库
 * @Author: Gangan
 * @CreateDate: 2018/8/18 10:55
 * @UpdateUser:
 * @UpdateDate: 2018/8/18 10:55
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class ItemAddMessageListener implements MessageListener  {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage= (TextMessage) message;
            String text=textMessage.getText();
            Long itemId=new Long(text);
           Thread.sleep(1000);
            SearchItem searchItem = itemMapper.getItemById(itemId);
            //创建一个文档对象
            SolrInputDocument document = new SolrInputDocument();
            //向文档对象中添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            //把文档写入索引库
            solrServer.add(document);
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
