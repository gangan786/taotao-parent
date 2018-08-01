package org.meizhuo.taotao.content.service.impl;

import org.meizhuo.taotao.common.pojo.EasyUIDataGridResult;
import org.meizhuo.taotao.common.pojo.EasyUITreeNode;
import org.meizhuo.taotao.common.utils.E3Result;
import org.meizhuo.taotao.content.service.ContentCategoryService;
import org.meizhuo.taotao.mapper.TbContentCategoryMapper;
import org.meizhuo.taotao.pojo.TbContentCategory;
import org.meizhuo.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.content.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/8/1 15:05
 * @UpdateUser:
 * @UpdateDate: 2018/8/1 15:05
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categories = contentCategoryMapper.selectByExample(example);
        ArrayList<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory category : categories) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent() ? "closed" : "open");
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public E3Result addContentCategory(long parentid, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        //添加属性
        tbContentCategory.setParentId(parentid);
        tbContentCategory.setName(name);
        tbContentCategory.setStatus(1);//1正常 2删除
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);//新添加的节点一定是叶子节点
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        //插入数据库
        contentCategoryMapper.insert(tbContentCategory);
        //判断父节点的isparent属性，如果不是TRUE改为TRUE
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentid);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            //更新到数据库中
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        return E3Result.ok(tbContentCategory);
    }
}
