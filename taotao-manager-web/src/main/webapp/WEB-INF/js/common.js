Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

var TT = TAOTAO = {
    // 编辑器参数
    kingEditorParams: {
        //指定上传文件参数名称
        filePostName: "uploadFile", //相当于form的input type="file" name="uploadFile"
        //指定上传文件请求的url。
        uploadJson: '/pic/upload',
        //上传类型，分别为image、flash、media、file
        dir: "image"
    },
    // 格式化时间
    formatDateTime: function (val, row) {
        var now = new Date(val);
        return now.format("yyyy-MM-dd hh:mm:ss");
    },
    // 格式化连接
    formatUrl: function (val, row) {
        if (val) {
            return "<a href='" + val + "' target='_blank'>查看</a>";
        }
        return "";
    },
    // 格式化价格
    formatPrice: function (val, row) {
        return (val / 1000).toFixed(2);
    },
    // 格式化商品的状态
    formatItemStatus: function formatStatus(val, row) {
        if (val == 1) {
            return '正常';
        } else if (val == 2) {
            return '<span style="color:red;">下架</span>';
        } else {
            return '未知';
        }
    },

    init: function (data) {
        // 初始化图片上传组件
        this.initPicUpload(data);
        // 初始化选择类目组件
        this.initItemCat(data);
    },
    // 初始化图片上传组件
    initPicUpload: function (data) {
        //获取图片的标签数组遍历
        $(".picFileUpload").each(function (i, e) {
            //转成query的对象
            var _ele = $(e);
            //找兄弟节点("div.pics")  删除掉
            _ele.siblings("div.pics").remove();
            //在a标签后插入内容<div class="pics"><ul></ul></div>
            _ele.after('<div class="pics"><ul></ul></div>');

            //给“上传图片按钮”绑定click事件  当点击商品图片的按钮时触发
            $(e).click(function () {
                //$(this) 就是a标签  找到这个标签所在的form表单。就相当于使用$("#itemAddForm")
                var form = $(this).parentsUntil("form").parent("form");
                //打开图片上传窗口
                KindEditor.editor(TT.kingEditorParams).loadPlugin('multiimage', function () {
                    var editor = this;
                    editor.plugin.multiImageDialog({
                        //当点击“全部插入”的按钮的时候触发
                        clickFn: function (urlList) {
                            var imgArray = [];//定义一个数组
                            //遍历
                            KindEditor.each(urlList, function (i, data) {
                                //将数据添加到数组中
                                //data.url：就是返回回来的图片的地址
                                imgArray.push(data.url);
                                // 回显图片
                                //form.find(".pics ul") 获取元素对象 class为.pics 下的ul的标签对象：<div class="pics"><ul></ul></div>
                                form.find(".pics ul").append("<li><a href='" + data.url + "' target='_blank'><img src='" + data.url + "' width='80' height='50' /></a></li>");
                            });
                            //从表单中获取image标签所在的元素
                            //imgArray.join(",")  将数组进行转成字符串 而且是以逗号分隔：比如：a.jpg,b.jpg,c.jpg
                            //将图片的地址 设置到image标签中
                            form.find("[name=image]").val(imgArray.join(","));
                            //隐藏窗口
                            editor.hideDialog();
                        }
                    });
                });
            });
        });
    },

    // 初始化选择类目组件
    initItemCat: function (data) {
        //$(".selectItemCat") 类选择器 ，执行遍历
        $(".selectItemCat").each(function (i, e) {
            //将元素变成jquery的对象
            var _ele = $(e);
            if (data && data.cid) {
                _ele.after("<span style='margin-left:10px;'>" + data.cid + "</span>");
            } else {
                //在jquery对象的元素后插入内容
                _ele.after("<span style='margin-left:10px;'></span>");
            }
            //添加对元素的selectItemCat 所在的元素绑定点击事件
            _ele.unbind('click').click(function () {
                //创建一个div标签 在div中添加了ul标签  <div><ul></ul></div>
                $("<div>").css({padding: "5px"}).html("<ul>")
                //创建一个窗口 打开
                    .window({
                        width: '500',
                        height: "450",
                        modal: true,
                        closed: true,
                        iconCls: 'icon-save',
                        title: '选择类目',
                        //在窗口被打开的时候触发以下的逻辑
                        onOpen: function () {
                            //获取当前的窗口
                            var _win = this;
                            //在当前的窗口中的ul标签下创建一课树
                            $("ul", _win).tree({
                                //异步发送请求 从服务端获取数据的URL
                                url: '/item/cat/list',
                                animate: true,
                                onClick: function (node) {
                                    if ($(this).tree("isLeaf", node.target)) {
                                        // 填写到cid中
                                        _ele.parent().find("[name=cid]").val(node.id);
                                        // 将文本值显示，并设置标签(上边追加的<span)的cid属性为节点的id
                                        _ele.next().text(node.text).attr("cid", node.id);

                                        $(_win).window('close');
                                        if (data && data.fun) {
                                            data.fun.call(this, node);
                                        }
                                    }
                                }
                            });
                        },
                        onClose: function () {
                            $(this).window("destroy");
                        }
                    }).window('open');
            });
        });
    },

    createEditor: function (select) {
        return KindEditor.create(select, TT.kingEditorParams);
    },

    /**
     * 创建一个窗口，关闭窗口后销毁该窗口对象。<br/>
     *
     * 默认：<br/>
     * width : 80% <br/>
     * height : 80% <br/>
     * title : (空字符串) <br/>
     *
     * 参数：<br/>
     * width : <br/>
     * height : <br/>
     * title : <br/>
     * url : 必填参数 <br/>
     * onLoad : function 加载完窗口内容后执行<br/>
     *
     *
     */
    createWindow: function (params) {
        $("<div>").css({padding: "5px"}).window({
            width: params.width ? params.width : "80%",
            height: params.height ? params.height : "80%",
            modal: true,
            title: params.title ? params.title : " ",
            href: params.url,
            onClose: function () {
                $(this).window("destroy");
            },
            onLoad: function () {
                if (params.onLoad) {
                    params.onLoad.call(this);
                }
            }
        }).window("open");
    },

    closeCurrentWindow: function () {
        $(".panel-tool-close").click();
    },

    changeItemParam: function (node, formId) {
        $.getJSON("/item/param/query/itemcatid/" + node.id, function (data) {
            if (data.status == 200 && data.data) {
                $("#" + formId + " .params").show();
                var paramData = JSON.parse(data.data.paramData);
                var html = "<ul>";
                for (var i in paramData) {
                    var pd = paramData[i];
                    html += "<li><table>";
                    html += "<tr><td colspan=\"2\" class=\"group\">" + pd.group + "</td></tr>";

                    for (var j in pd.params) {
                        var ps = pd.params[j];
                        html += "<tr><td class=\"param\"><span>" + ps + "</span>: </td><td><input autocomplete=\"off\" type=\"text\"/></td></tr>";
                    }

                    html += "</li></table>";
                }
                html += "</ul>";
                $("#" + formId + " .params td").eq(1).html(html);
            } else {
                $("#" + formId + " .params").hide();
                $("#" + formId + " .params td").eq(1).empty();
            }
        });
    },
    getSelectionsIds: function (select) {
        var list = $(select);
        var sels = list.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].id);
        }
        ids = ids.join(",");
        return ids;
    },

    /**
     * 初始化单图片上传组件 <br/>
     * 选择器为：.onePicUpload <br/>
     * 上传完成后会设置input内容以及在input后面追加<img>
     */
    initOnePicUpload: function () {
        $(".onePicUpload").click(function () {
            var _self = $(this);
            KindEditor.editor(TT.kingEditorParams).loadPlugin('image', function () {
                this.plugin.imageDialog({
                    showRemote: false,
                    clickFn: function (url, title, width, height, border, align) {
                        var input = _self.siblings("input");
                        input.parent().find("img").remove();
                        input.val(url);
                        input.after("<a href='" + url + "' target='_blank'><img src='" + url + "' width='80' height='50'/></a>");
                        this.hideDialog();
                    }
                });
            });
        });
    }
};
