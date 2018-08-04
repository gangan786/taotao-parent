<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">  </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
//文档加载后处理以下的逻辑
$(function(){
	//在#contentCategory 所在的标签中创建一颗树
	$("#contentCategory").tree({
		url : '/content/category/list',
		animate: true,
		method : "GET",
		//右击鼠标触发
		onContextMenu: function(e,node){
			//关闭原来的鼠标的默认事件
            e.preventDefault();
			//选中 右击鼠标的节点
            $(this).tree('select',node.target);
			//展示菜单栏
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,//在鼠标的位置显示
                top: e.pageY//在鼠标的位置显示
            });
        },
        //在选中的节点被编辑之后触发
        onAfterEdit : function(node){
        	//获取树本身
        	var _tree = $(this);
        	
        	//表示的是新增的节点  新增
        	if(node.id == 0){
        		// 新增节点
        		//parentId:node.parentId,name:node.text  
        		//parentId：就是新增节点的父节点的Id
        		//name：新增节点的文本
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				//更新节点
        				_tree.tree("update",{
            				target : node.target,//更新哪一个节点
            				id : data.data.id//更新新增节点的id 
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		$.post("/content/category/update",{id:node.id,name:node.text});
        	}
        }
	});
});
//处理点击菜单的事件
function menuHandler(item){
	//获取树
	var tree = $("#contentCategory");
	//获取被选中的节点 就是右击鼠标时的所在的节点
	var node = tree.tree("getSelected");
	
	//判断选择的是添加还是重命名还是删除
	
	//  ==     1==1   true  1=="1"  true;
	//  ===   1===1   true  1==="1" false
	//点击“添加” 
	if(item.name === "add"){
		//在被点击的节点下追加一个子节点
		tree.tree('append', {
            parent: (node?node.target:null), //被添加的子节点的父
            //
            data: [{
                text: '新建分类123',//节点的内容
                id : 0,//节点的id
                parentId : node.id//新建的节点的父节点的id
            }]
        }); 
		//找到id为0的节点  添加的节点
		var _node = tree.tree('find',0);
		//选中id为0的节点 添加的节点 开启编辑
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){//如果是true 表示要执行以下的逻辑
				$.post("/content/category/delete/",{id:node.id},function(){
					//后台删除成功后，删除前端的节点
					tree.tree("remove",node.target);
				});	
			}
		});
	}
}
</script>