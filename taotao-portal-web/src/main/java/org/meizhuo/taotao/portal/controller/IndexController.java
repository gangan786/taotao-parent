package org.meizhuo.taotao.portal.controller;

import org.meizhuo.taotao.content.service.ContentService;
import org.meizhuo.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.portal.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/1 10:08
 * @UpdateUser:
 * @UpdateDate: 2018/8/1 10:08
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class IndexController {

    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_ID;
    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> ad1List= contentService.getContentListByCid(CONTENT_ID);
        model.addAttribute("ad1List",ad1List);
        return "index";
    }

}
