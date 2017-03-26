<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
<script type="text/javascript">

var columns=[[
{	
	field : 'id',
	checkbox:true
},
{
	field : 'bm',
	title : '流水号',
	width : 50,
	formatter: function(value,row,index){
		if (row.ypxx) {
			return row.ypxx.bm;
		}
	}
	
},{
	field : 'mc',
	title : '通用名',
	width : 100,
	formatter: function(value,row,index){
		return row.ypxx.mc;
	}
},{
	field : 'jx',
	title : '剂型',
	width : 70,
	formatter: function(value,row,index){
		return row.ypxx.jx;
	}
},{
	field : 'gg',
	title : '规格',
	width : 70,
	formatter: function(value,row,index){
		return row.ypxx.gg;
	}
},{
	field : 'zhxs',
	title : '转换系数',
	width : 80,
	formatter: function(value,row,index){
		return row.ypxx.zhxs;
	}
},{
	field : 'zbjg',
	title : '中标价',
	width : 50
},{
	field : 'sysDictInfoCgzt',
	title : '采购状态',
	width : 80,
	formatter: function(value,row,index){
		if (value) {
			return value.info;
		}
	}
},{
	field : 'cgl',
	title : '采购量',
	width : 50,
	editor:{
		"type":"numberbox",
		"options":{
			precision:0, //精度0
			required:true //必填
		}
	} //editor:"numberbox"		//简化写法
}
,{
	field : 'cgje',
	title : '采购金额',
	width : 80,
	formatter:function(value,row,index) {
		if (value) {
			return "￥"+value.toFixed(2);
		}
	}
}
     
     ]];
     
//编辑
var editIndex = undefined;
//结束编辑状态
function endEditing(){
	//从来没有编辑                                                      
	if (editIndex == undefined){return true}
	//校验当前编辑行
	if ($('#yycgdmxlist').datagrid('validateRow', editIndex)){
		//将当前编辑行，结束编辑
		$('#yycgdmxlist').datagrid('endEdit', editIndex);
		//将当前编辑行设置没有数据
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}


//声明toolbar并初始化
var toolbar = [];
//声明点击事件空方法什么都不做
function onClickRow(index){}
</script>
<!-- 指定状态才有的方法start : 状态校验：未提交01001 和 审核未通过01004 修改采购单，其它不可以 -->
<c:if test="${yycgdCustom.sysDictInfoByzt.id=='01001' || yycgdCustom.sysDictInfoByzt.id=='01004'}">
<script type="text/javascript">
//重新定义点击行事件
function onClickRow(index){
	//之前编辑行，当前点击是否同一样
	if (editIndex != index){
		if (endEditing()){
			//开始编辑
			$('#yycgdmxlist').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			//并记录编辑行
			editIndex = index;
		} else {
			$('#yycgdmxlist').datagrid('selectRow', editIndex);
		}
	}
}

toolbar=[
{
	id : 'yycgdmxaddshow',
	text : '采购药品添加',
	iconCls : 'icon-add',
	handler : yycgdmxaddshow
	}
	,{
		id : 'yycgdmxdelete',
		text : '采购药品删除',
		iconCls : 'icon-remove',
		handler : yycgdmxdelete
	}
	,{
		id : 'yycgdmxsave',
		text : '保存采购量',
		iconCls : 'icon-save',
		handler : yycgdmxcglsave
	}  
    ];
//添加药品弹窗
function yycgdmxaddshow(){
	//打一个模态窗口 ,传入采购单id
	var sendUrl = "${baseurl}/cgd/addyycgdmx.action?yycgdCustom.id=${yycgdCustom.id}";
	//参数：1、窗口标题、2、宽、3：高、4、url
	createmodalwindow("添加采购药品信息", 980, 400, sendUrl);
}
//删除药品
function yycgdmxdelete(){
	endEditing();
	_confirm('您确定要删除所选药品吗?',function(){
		var rows = $("#yycgdmxlist").datagrid('getSelections'); //获取选中的行，json数组
		if (rows.length<1) {
			alert_warn("请选择要删除的药品");
			return false;
		}
		//先清除之前的
		$(".yycgdmxdel").remove();
		//遍历ids生成input
		$.each(rows, function(){
			$("<input type='hidden' name='yycgdmxIds' class='yycgdmxdel'></input>").val(this.id).appendTo($("#yycgdmxdelForm"));
		}); 
		jquerySubByFId('yycgdmxdelForm', yycgdmxsave_callback);
	});
}
//保存采购量
function yycgdmxcglsave(){
	//先结束编辑
	endEditing();
	_confirm('您确定要更改所选药品的采购量吗?',function(){
		var rows = $('#yycgdmxlist').datagrid('getChanges','updated');  //获取修改的行，json数组
		if (rows.length<1) {
			alert_warn('请选择要更改的药品!');
			return false;
		}
		$(".yycgdmxcgl").remove();
		
		//生成input
		$.each(rows,function(){
			//明细ids yycgdmxIds 采购量 yycgdmxCgls
			
			$("<input type='hidden' name='yycgdmxIds' class='yycgdmxcgl'></input>").val(this.id).appendTo($("#yycgdmxcglForm"));
			$("<input type='hidden' name='yycgdmxCgls' class='yycgdmxcgl'></input>").val(this.cgl).appendTo($("#yycgdmxcglForm"));
		});
		
		//ajaxform提交
		//要提交的参数
		jquerySubByFId('yycgdmxcglForm', yycgdmxsave_callback, null, "json");
		
	});
}

function yycgdmxsave_callback(data){
	
	_alert(data);//此代码没有阻断效应
	//重新 查询 采购药品列表，刷新 
	yycgdmxquery();
}
</script>
</c:if><!-- 指定状态才有的方法end -->

<script type="text/javascript">
$(function (){
	
	//加载datagrid
	$('#yycgdmxlist').datagrid({
		title : '采购单列表',
		striped : true,
		url : '${baseurl}/cgd/yycgdmx_result.action',//返回json
		queryParams:{//只在初始加载datagrid时使用
			'yycgdMxQueryCustom.yycgd.id':'${yycgdCustom.id}'
		},
		idField : 'id',//rows中每一行唯一标识的字段
		columns : columns,
		pagination : true,
		rownumbers : true,
		toolbar : toolbar,
		loadMsg : "小二正忙，请稍后...",
		pageSize : 5,
		pageList : [ 5, 10, 20, 50 ],//每页显示下拉框值
		onBeforeLoad : function(index, field, value) {//在加载datagrid之前执行unselectAll(取消所有选中的行)
			$('#yycgdmxlist').datagrid('unselectAll');
		},
		onClickRow:onClickRow,
		
	});
});
//保存采购单
function yycgdsave(){
	_confirm('确定要保存吗？',function(){

		$("#yycgdform").attr("action","${baseurl }/cgd/edit_submit.action");
		//ajaxForm提交
		jquerySubByFId('yycgdform',yycgdsave_callback,null,"json");
	
	});
}
//保存成功后回调
function yycgdsave_callback(result){
	message_alert(result,function(){
		//成功后刷新页面
		location.reload();
	});
}
//查询采购药品明细
function yycgdmxquery(){
	var formdata = $("#yycgdmxForm").serializeJson();
	//这里使用load方法请求上边加载datagrid中定义的url  url : '${baseurl}/cgd/yycgdmx_result.action'，不会使用上边的queryParams参数
	$('#yycgdmxlist').datagrid('reload', formdata);
	
}
//提交采购单
function yycgdsubmit(){
	//检查采购量是否保存
	endEditing(); //先结束编辑
	//有bug，endEding运行需要时间导致datagrid('getChanges','updated')获取的是endEditing之前的结果。加确认框屏蔽此问题
	_confirm('您确定要提交采购单吗?',function(){
		var rows = $('#yycgdmxlist').datagrid('getChanges','updated');  //获取修改的行，json数组
		if (rows.length>0) {
			alert_warn("有未保存的采购量，请先保存");
			return false;
		}
		$("#yycgdform").attr("action","${baseurl }/cgd/submit.action");
		jquerySubByFId('yycgdform',yycgdsubmit_callback,null,"json");
	});
}
//提交成功后回调
function yycgdsubmit_callback(data){
	message_alert(data,function(){
		location.reload();
	});
}
</script>
<title>编辑采购单</title>
</head>
<body>
<!-- 采购单基本信息 -->
<form id="yycgdform" action="${baseurl }/cgd/edit_submit.action" method="post">
<!-- 采购单id -->
<input type="hidden" name="yycgdCustom.id" value="${yycgdCustom.id }"/>
<!-- 任务id -->
<%-- <input type="hidden" name="taskId" value="${taskId}"/> --%>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="70%" bgColor=#c4d8ed align=center>
	<TBODY>
		<TR>
				<TD background="${baseurl }/images/r_0.gif" width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;编辑采购单</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							<TR>
								<TD height=30 width="15%" align=right >采购单号：</TD>
								<TD class=category width="35%"> 
								${yycgdCustom.id }
								</TD>
								<TD height=30 width="15%" align=right >采购单名称：</TD>
								<TD class=category width="35%">
								${yycgdCustom.mc }
								</TD>
							</TR>
							<TR>
							   <TD height=30 width="15%" align=right >建单时间：</TD>
								<TD class=category width="35%">
									<fmt:formatDate value="${yycgdCustom.cjsj}" pattern="yyyy-MM-dd"/>
								</TD>
								<TD height=30 width="15%" align=right >提交时间：</TD>
								<TD class=category width="35%">
								<fmt:formatDate value="${yycgdCustom.tjsj}" pattern="yyyy-MM-dd"/>
								</TD>
								
							</TR>
								<TR>
								<TD height=30 width="15%" align=right>联系人：</TD>
								<TD class=category width="35%">
								<input type="text" name="yycgdCustom.lxr" id="yycgd.lxr" value="${yycgdCustom.lxr}"  style="width:260px" />
								</TD>
								<TD height=30 width="15%" align=right >联系电话：</TD>
								<TD class=category width="35%">
								<input type="text" name="yycgdCustom.lxdh" id="yycgd.lxdh" value="${yycgdCustom.lxdh}"  style="width:260px" />
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>采购单状态：</TD>
								<TD class=category width="35%">
								${yycgdCustom.sysDictInfoByzt.info}
								</TD>
								<TD height=30 width="15%" align=right>备注：</TD>
								<TD colspan=3>
									<textarea rows="2" cols="30" name="yycgdCustom.bz">${yycgdCustom.bz}</textarea>
								</TD>
							</TR>
							
							<TR>
								<TD height=30 width="15%" align=right>审核时间：</TD>
								<TD class=category width="35%">
								<fmt:formatDate value="${yycgdCustom.shsj}" pattern="yyyy-MM-dd"/>
								</TD>
								<TD height=30 width="15%" align=right >审核意见：</TD>
								<TD class=category width="35%">
								${yycgdCustom.shyj}
								</TD>
							</TR>
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a  href="#" onclick="yycgdsave()" class="easyui-linkbutton" iconCls='icon-save'>保存</a>
							    <a  href="#" onclick="yycgdsubmit()" class="easyui-linkbutton" iconCls='icon-ok'>提交</a>
							  </td>
							</tr>
						</TBODY>
					</TABLE>
				</TD>
		    </TR>
	</TBODY>
</TABLE>

</form>
<!-- 条件查询表单 -->
<form id="yycgdmxForm" action="${baseurl}/cgd/yycgdmx_result.action" method="post">
<!-- 采购单id -->
<input type="hidden" name="yycgdMxQueryCustom.yycgd.id" value="${yycgdCustom.id }"/>

<!-- 采购单明细 -->
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background="${baseurl }/images/r_0.gif" width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;采购药品列表</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
<TABLE  class="table_search">
				<TBODY>
					<TR>
						
						<TD class="left">通用名：</td>
						<td><INPUT type="text"  name="yycgdMxQueryCustom.ypxx.mc" /></TD>
						<TD class="left">剂型：</TD>
						<td ><INPUT type="text" name="yycgdMxQueryCustom.ypxx.jx" /></td>
						<TD class="left">规格：</TD>
						<td ><INPUT type="text" name="yycgdMxQueryCustom.ypxx.gg" /></td>
						<TD class="left">转换系数：</TD>
						<td ><INPUT type="text" name="yycgdMxQueryCustom.ypxx.zhxs" /></td>
					</TR>
					<TR>
						<TD class="left">流水号：</TD>
						<td ><INPUT type="text" name="yycgdMxQueryCustom.ypxx.bm" /></td>
						<TD class="left">生产企业：</TD>
						<td ><INPUT type="text" name="yycgdMxQueryCustom.ypxx.scqymc" /></td>
						<TD class="left">商品名称：</TD>
						<td ><INPUT type="text" name="yycgdMxQueryCustom.ypxx.spmc" /></td>
						 <td class="left">价格范围：</td>
				  		<td>
				      		<INPUT id="zbjglower" name="zbjglower" style="width:70px"/>
							至
							<INPUT id="zbjgupper" name="zbjgupper" style="width:70px"/>
							
				 		 </td>
					</tr>
					<tr>
					  
						<TD class="left">药品类别：</TD>
						<td >
							<!-- 药品类别从数据字典中取id作为页面传入action的value -->
							<select id="yycgdMxQueryCustom.lb" name="yycgdMxQueryCustom.ypxx.lb" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${yplbList}" var="value">
									<option value="${value.id}">${value.info}</option>
								</c:forEach>
							</select>
						</td>
						<TD class="left">交易状态：</TD>
						<td >
						    <!-- 交易状态从数据字典中取出dictcode作为页面传入action的value -->
							<select id="yycgdMxQueryCustom.ypxx.jyzt" name="yycgdMxQueryCustom.ypxx.jyzt" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${jyztList}" var="value">
									<option value="${value.dictcode}">${value.info}</option>
								</c:forEach>
							</select>
							
						</td>
						<TD class="left">采购状态：</TD>
						<td >
						   
							<select id="yycgdMxQueryCustom.cgzt" name="yycgdMxQueryCustom.cgzt" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${cgztList}" var="value">
									<option value="${value.dictcode}">${value.info}</option>
								</c:forEach>
							</select>
							
						</td>
				 		<TD class="left">供货状态：</TD>
						<td >
						   
							<select id="gysypmlCustom.control" name="gysypmlCustom.control" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${ghztList}" var="value">
									<option value="${value.dictcode}">${value.info}</option>
								</c:forEach>
							</select>
							
						</td>

				  		<td colspan=2>
				  		<input type="submit" style="display:none" onclick="yycgdmxquery();return false" />
						<a id="btn" href="javascript:void(0)" onclick="yycgdmxquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>
				  		</td>
						
					</TR>
					
				</TBODY>
			</TABLE>
			 <!-- datagrid -->
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
					<!-- 采购单明细列表 -->
						<table id="yycgdmxlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
</form>
<%--修改采购量 start --%>
<form id="yycgdmxcglForm" action="${baseurl}/cgd/yycgdmxcgl_submit.action" method="post" style="display:none">
	<!-- 采购单id -->
	<input type="text" name="yycgdCustom.id" value="${yycgdCustom.id }"/>
	
	<%-- 明细id，采购量 
		<input type="text" name="yycgdmxIds" value="mx001"/>
		<input type="text" name="yycgdmxCgls" value="100"/>
		
		<input type="text" name="yycgdmxIds" value="mx002"/>
		<input type="text" name="yycgdmxCgls" value="99"/>
	
	--%>
</form>
<%--修改采购量 end --%>

<%--删除明细药品项 start --%>
<form id="yycgdmxdelForm" action="${baseurl}/cgd/yycgdmxdel_submit.action" method="post" style="display:none">
	<!-- 采购单id -->
	<input type="text" name="yycgdCustom.id" value="${yycgdCustom.id }"/>
	
	<%-- 明细id 
		<input type="text" name="yycgdmxIds" value="mx001"/>
	--%>
</form>
<%--删除明细药品项 end --%>

</body>
</html>