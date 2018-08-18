package org.meizhuo.taotao.item.controller;

import org.meizhuo.taotao.item.pojo.Item;
import org.meizhuo.taotao.pojo.TbItem;
import org.meizhuo.taotao.pojo.TbItemDesc;
import org.meizhuo.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.item.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/18 17:40
 * @UpdateUser:
 * @UpdateDate: 2018/8/18 17:40
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        TbItem itemById = itemService.getItemById(itemId);
        Item item = new Item(itemById);
        TbItemDesc itemDesc= itemService.getItemDescById(itemId);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }
}
