<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>jQuery EasyUI</title>
<LINK rel="stylesheet" type="text/css" href="${baseurl}/js/easyui/styles/default.css">
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
	<script>
	/*列描述
	formatter 单元格格式化函数,需要return数据。
		参数1：value，通过field指定名称，从data数据获得值
		参数2：row，表示当前行数，{k:v,k:v,...}
		参数3：index，表示当前行索引
	*/
	var cgData = [
				     {'cgId':'01','cgName':'未审核'},
				     {'cgId':'02','cgName':'已审核'},
				     {'cgId':'03','cgName':'无法供货'},
				    ]
	
	var cgstatusOptions = {
					valueField:'cgId',
					textField:'cgName',
					method:'get',
					data: cgData,
					//required:true,
					formatter:function(row){
						return row.cgName;
					}
			};
	
	var colArr = [[
	               {field:'productname',title:'产品名称',width:100},    
	               {field:'unitcost',title:'产品价格',width:100,editor : 'numberbox'},    
	               {field:'status',title:'产品状态',width:100,editor: {type:'checkbox',options:{on:'P',off:''}} },    
	               {field:'del',title:'删除',width:100,formatter:function(value,row,index){
	            	   //<a onclick="deluser('FI-SW-01')">删除</a>
	            	   return "<a onclick=\"deluser('"+row.productid+"')\">删除</a>";
	               }},  
	               {field:'cgName',title:'采购状态',width:100,editor: {type:'combobox',options:cgstatusOptions} ,
	            	   formatter:function(value,row,index){
	            		   
	            		   $(cgData).each(function(){
	            			   if(this.cgId == row.cgName){
	            				   row.cgId = this.cgId;
	            				   row.cgName = this.cgName;
	            				   return this.cgName;
	            			   }
	            		   });
	            		   
	            		   return row.cgName;
	            	   }
	               }
	               ,  
	               {field:'cgId',title:'',hidden:true
	               }
	               ]];
	//冻结列
	var frozenColArr = [[
			               {field:'productid',title:'产品编号',width:100} 
			               
			            ]];
	
	//工具条，一维数组，多个按钮使用逗号分隔
	var toolArr = [
	               {
	            	iconCls: 'icon-add',
	            	text:'添加用户',
	           		handler: function(){alert('添加按钮')}
	               },
	               {iconCls: 'icon-remove',
		           	handler: function(){alert('删除按钮')}}
	               ];
	//删除用户
	function deluser(id){
		alert(id);
	}
	
	$(function(){
		var options = {
			url:'datagrid_data.json',		//加载内容
			columns: colArr,					//列描述
			frozenColumns:frozenColArr,			//冻结列描述
			striped:true,						//是否显示斑马线效果。
			idField:'productid',				//指明哪一个字段是标识字段。
			pagination:true,					//显示分页条
			rownumbers:true,					//显示行号
			title:'用户详情',					//标题
			toolbar: toolArr,					//工具条
			pageList:[10,20,25,30],				//设置分页选择列表
			pageSize:10,						//设置分页页面显示大小
			queryParams: {id:'abc'},			//额外请求参数
			onClickRow: editcgl,
			onLoadSuccess:function(){
				$(document).click(function(event){
					if($(event.target).closest(".datagrid-btable").length == 0){
						endEditing();
					}
				});
			}
		};
		
		$("#dg").datagrid(options);
	});
	
	//datagrid 点击事件，修改采购量
	var editIndex;		//之前编辑的索引
	function editcgl(index){
		//当前编辑和之前编辑，不是同一个
		if (editIndex != index){
			if (endEditing()){
				$('#dg').datagrid('selectRow', index)		//选中当前行
						.datagrid('beginEdit', index);		//开始编辑当前行
				editIndex = index;					//记录当前编辑的行，下一次使用。
			} else {
				$('#dg').datagrid('selectRow', editIndex);
			}
		}
	}
	
	//结束之前编辑，返回值表示是否需要编辑
	function endEditing(){
		//第一次点击，没有之前
		if (editIndex == undefined){return true}
		//对当前行的数据进行校验
		if ($('#dg').datagrid('validateRow', editIndex)){
			//结束编辑
			$('#dg').datagrid('unselectRow', editIndex).datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	
		function resize(){
			$('#dg').datagrid('resize', {
				width:700,
				height:400
			});
		}
		function getSelected(){
			var selected = $('#dg').datagrid('getSelected');
			if (selected){
				alert(selected.productid+":"+selected.productname+":"+selected.unitcost+":"+selected.itemid);
			}
		}
		function getSelections(){
			var ids = [];
			var rows = $('#dg').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].productname);
			}
			alert(ids.join(':'));
		}
		function clearSelections(){
			$('#dg').datagrid('clearSelections');
		}
		function selectRow(){
			$('#dg').datagrid('selectRow',2);
		}
		function selectRecord(){
			$('#dg').datagrid('selectRecord','002');
		}
		function unselectRow(){
			$('#dg').datagrid('unselectRow',2);
		}
		function mergeCells(){
			$('#dg').datagrid('mergeCells',{
				index:2,
				field:'addr',
				rowspan:2,
				colspan:2
			});
		}
		
		function getChanges(){
			var ids = [];
			var rows = $('#dg').datagrid('getChanges');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].productname);
			}
			alert(ids.join(':'));
		}
	</script>
</head>
<body>
	<h1>DataGrid</h1>
	<div style="margin-bottom:10px;">
		<a href="#" onclick="resize()">resize</a>
		<a href="#" onclick="getSelected()">getSelected</a>
		<a href="#" onclick="getSelections()">getSelections</a>
		<a href="#" onclick="clearSelections()">clearSelections</a>
		<a href="#" onclick="selectRow()">selectRow</a>
		<a href="#" onclick="selectRecord()">selectRecord</a>
		<a href="#" onclick="unselectRow()">unselectRow</a>
		<a href="#" onclick="getChanges()">getChanges</a>
	</div>
	
	<table id="dg"></table>
	
</body>
</html>