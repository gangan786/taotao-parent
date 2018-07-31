package org.meizhuo.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.meizhuo.taotao.common.pojo.EasyUIDataGridResult;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.common.utils.IDUtils;
import org.meizhuo.taotao.mapper.TbItemDescMapper;
import org.meizhuo.taotao.mapper.TbItemMapper;
import org.meizhuo.taotao.pojo.TbItem;
import org.meizhuo.taotao.pojo.TbItemDesc;
import org.meizhuo.taotao.pojo.TbItemExample;
import org.meizhuo.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/24 17:13
 * @UpdateUser:
 * @UpdateDate: 2018/7/24 17:13
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper mapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        if (page == null) page = 1;
        if (rows == null) rows = 30;
        PageHelper.startPage(page, rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> items = mapper.selectByExample(example);
        PageInfo<TbItem> info = new PageInfo<>(items);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int) info.getTotal());
        result.setRows(info.getList());
        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
        //生成商品ID
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //1-正常 2-下架 3-刪除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //补全item属性
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        //插入数据
        mapper.insert(item);
        tbItemDescMapper.insert(tbItemDesc);
        return E3Result.ok();
    }
}
