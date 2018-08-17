package org.meizhuo.taotao.search.controller;

import org.meizhuo.taotao.common.pojo.SearchResult;
import org.meizhuo.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.search.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/4 22:23
 * @UpdateUser:
 * @UpdateDate: 2018/8/4 22:23
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String searchItemList(String keyword,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 Model model) throws Exception {
        keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
        SearchResult search = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",search.getTotalPages());
        model.addAttribute("page",page);
        model.addAttribute("recourdCount",search.getRecordCount());
        model.addAttribute("itemList",search.getItemList());

        //返回逻辑视图
        return "search";

    }

}
