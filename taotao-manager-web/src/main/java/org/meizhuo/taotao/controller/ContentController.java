package org.meizhuo.taotao.controller;

import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.content.service.ContentService;
import org.meizhuo.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.controller
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/1 17:47
 * @UpdateUser:
 * @UpdateDate: 2018/8/1 17:47
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent content){
        return contentService.addContent(content);
    }
}
