package org.meizhuo.taotao.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.meizhuo.taotao.common.pojo.SearchItem;
import org.meizhuo.taotao.common.pojo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.search
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/4 21:30
 * @UpdateUser:
 * @UpdateDate: 2018/8/4 21:30
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    /**
     * 根据条件查询索引库
     *
     * @param solrQuery
     * @return
     */
    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList documentList = queryResponse.getResults();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(documentList.getNumFound());
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        ArrayList<SearchItem> searchResults = new ArrayList<>();
        for (SolrDocument entries : documentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) entries.get("id"));
            searchItem.setCategory_name((String) entries.get("item_category-name"));
            searchItem.setImage((String) entries.get("item_image"));
            searchItem.setPrice((Long) entries.get("item_price"));
            searchItem.setSell_point((String) entries.get("item_sell_point"));
            //取高亮
            String title = "";
            List<String> titleList = highlighting.get(entries.get("id")).get("item_title");
            if (titleList != null && titleList.size() > 0) {
                title = titleList.get(0);
            } else {
                title = (String) entries.get("item_title");
            }
            searchItem.setTitle(title);
            searchResults.add(searchItem);
        }
        searchResult.setItemList(searchResults);
        return searchResult;
    }
}
