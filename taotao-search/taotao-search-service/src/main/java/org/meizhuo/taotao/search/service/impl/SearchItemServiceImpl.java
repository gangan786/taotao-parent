package org.meizhuo.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.meizhuo.taotao.common.pojo.SearchItem;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.search.mapper.ItemMapper;
import org.meizhuo.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.search.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/4 16:24
 * @UpdateUser:
 * @UpdateDate: 2018/8/4 16:24
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public E3Result importAllItems() {
        try {
            List<SearchItem> itemList = itemMapper.getItemList();
            for (SearchItem searchItem : itemList) {
                SolrInputDocument inputDocument = new SolrInputDocument();
                inputDocument.addField("id",searchItem.getId());
                inputDocument.addField("item_title",searchItem.getTitle());
                inputDocument.addField("item_sell_point",searchItem.getSell_point());
                inputDocument.addField("item_price",searchItem.getPrice());
                inputDocument.addField("item_image",searchItem.getImage());
                inputDocument.addField("item_category_name",searchItem.getCategory_name());

                solrServer.add(inputDocument);
            }
            solrServer.commit();
            return E3Result.ok();
        }catch (Exception e){
            e.printStackTrace();
            return E3Result.build(500,"导入数据失败");
        }
    }
}
