package org.meizhuo.taotao.common.pojo;

import java.io.Serializable;

/**
 * @ProjectName: taotao-parent
 * @Package: org.meizhuo.taotao.common.pojo
 * @ClassName: ${TYPE_NAME}
 * @Description:
 * @Author: Gangan
 * @CreateDate: 2018/7/27 10:16
 * @UpdateUser:
 * @UpdateDate: 2018/7/27 10:16
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class EasyUITreeNode implements Serializable {

    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
