package org.meizhuo.taotao.controller;

import org.meizhuo.taotao.common.pojo.SearchItem;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/4 20:06
 * @UpdateUser:
 * @UpdateDate: 2018/8/4 20:06
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    private E3Result importSearchItem(){
        return searchItemService.importAllItems();
    }
}
