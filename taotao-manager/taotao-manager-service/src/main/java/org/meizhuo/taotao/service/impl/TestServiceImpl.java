package org.meizhuo.taotao.service.impl;

import org.meizhuo.taotao.mapper.TestMapper;
import org.meizhuo.taotao.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.service.impl
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/24 0:05
 * @UpdateUser:
 * @UpdateDate: 2018/7/24 0:05
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;
    @Override
    public String queryNow() {

        return testMapper.queryNow();
    }
}
