package org.meizhuo.taotao.service.impl;

import org.meizhuo.taotao.common.pojo.EasyUITreeNode;
import org.meizhuo.taotao.mapper.TbItemCatMapper;
import org.meizhuo.taotao.pojo.TbItemCat;
import org.meizhuo.taotao.pojo.TbItemCatExample;
import org.meizhuo.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/27 10:26
 * @UpdateUser:
 * @UpdateDate: 2018/7/27 10:26
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    TbItemCatMapper itemCatMapper;

    /**
     * 返回列表项
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> nodes = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCats) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");
            node.setText(tbItemCat.getName());
            nodes.add(node);
        }
        return nodes;
    }
}
