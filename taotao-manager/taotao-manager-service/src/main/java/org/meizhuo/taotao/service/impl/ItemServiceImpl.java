package org.meizhuo.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.meizhuo.taotao.common.pojo.EasyUIDataGridResult;
import org.meizhuo.taotao.mapper.TbItemMapper;
import org.meizhuo.taotao.pojo.TbItem;
import org.meizhuo.taotao.pojo.TbItemExample;
import org.meizhuo.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
