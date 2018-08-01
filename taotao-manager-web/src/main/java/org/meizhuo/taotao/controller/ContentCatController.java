package org.meizhuo.taotao.controller;

import org.meizhuo.taotao.common.pojo.EasyUITreeNode;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/1 15:30
 * @UpdateUser:
 * @UpdateDate: 2018/8/1 15:30
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class ContentCatController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(
            @RequestParam(name = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCatList(parentId);
    }

    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public E3Result createContentCategory(Long parentId ,String name){
        return contentCategoryService.addContentCategory(parentId,name);
    }

}
