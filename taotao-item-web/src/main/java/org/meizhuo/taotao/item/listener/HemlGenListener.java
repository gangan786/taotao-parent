package org.meizhuo.taotao.item.listener;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.meizhuo.taotao.item.pojo.Item;
import org.meizhuo.taotao.pojo.TbItem;
import org.meizhuo.taotao.pojo.TbItemDesc;
import org.meizhuo.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.item.listener
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/20 16:15
 * @UpdateUser:
 * @UpdateDate: 2018/8/20 16:15
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class HemlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {

        try {
            //创建一个模板，参考jsp
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品id查询商品信息，商品基本信息和商品描述。
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            //取商品描述
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //创建一个数据集，把商品数据封装
            Map data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            //加载模板对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //创建一个输出流，指定输出的目录及文件名。
            Writer out = new FileWriter(HTML_GEN_PATH + itemId + ".html");
            //生成静态页面。
            template.process(data, out);
            //关闭流
            out.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
