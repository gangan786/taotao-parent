import org.junit.Test;
import org.meizhuo.taotao.pojo.TbItem;
import org.meizhuo.taotao.pojo.TbItemDesc;
import org.meizhuo.taotao.service.ItemService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ProjectName: taotao-parent
 * @Package: PACKAGE_NAME
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/18 23:15
 * @UpdateUser:
 * @UpdateDate: 2018/8/18 23:15
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class RedisTest {
    @Test
    public void testRedis(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        ItemService itemService = applicationContext.getBean(ItemService.class);
        TbItemDesc itemDescById = itemService.getItemDescById(153457492530171l);
        TbItem itemById = itemService.getItemById(153457492530171l);
    }
}
