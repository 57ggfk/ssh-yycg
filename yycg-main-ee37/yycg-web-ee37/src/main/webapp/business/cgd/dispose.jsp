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
	//修改药品状态数据
	var comboboxdata = [
                	{"dictId":"01101","dictInfo":"未受理"},
                	{"dictId":"01102","dictInfo":"已发货"},
                	{"dictId":"01103","dictInfo":"无法供货"}
                ];
	
	// 普通列
	var colArr = [ [ {
		field : '',
		title : '',
		//checkbox:true
	},{
		field : 'bm',
		title : '流水号',
		width : 100,
		formatter: function(value,row,index){
			if(row.ypxx){
				return row.ypxx.bm;
			}
			return "";
		}
		
	},{
		field : 'mc',
		title : '通用名',
		width : 100,
		formatter: function(value,row,index){
			if(row.ypxx){
				return row.ypxx.mc;
			}
			return "";
		}
	},{
		field : 'jx',
		title : '剂型',
		width : 70,
		formatter: function(value,row,index){
			if(row.ypxx){
				return row.ypxx.jx;
			}
			return "";
		}
	},{
		field : 'gg',
		title : '规格',
		width : 70,
		formatter: function(value,row,index){
			if(row.ypxx){
				return row.ypxx.gg;
			}
			return "";
		}
	},{
		field : 'zhxs',
		title : '转换系数',
		width : 100,
		formatter: function(value,row,index){
			if(row.ypxx){
				return row.ypxx.zhxs;
			}
			return "";
		}
	},{
		field : 'zbjg',
		title : '中标价',
		width : 50
	},{
		field : 'jyztmc',
		title : '交易状态',
		width : 100,
		formatter: function(value,row,index){
			if(row.ypxx.sysDictInfoByJyzt){
				return row.ypxx.sysDictInfoByJyzt.info;
			}
			return "";
		}
	},{
		field : 'jyjg',
		title : '交易价',
		width : 50
	},{
		field : 'cgl',
		title : '采购量',
		width : 50
	},{
		field : 'cgje',
		title : '采购金额',
		width : 100
	},{
		field : 'cgztmc',
		title : '受理状态',
		width : 100,
		formatter:function(value,row,index){
			if(row.sysDictInfoCgzt){
				return row.sysDictInfoCgzt.info;
			}
			return "";
		}
	},{
		field : 'opt1',
		title : '修改状态',
		width : 100,
		formatter: function(value,row,index){
			//显示时处理
 		    $.each(comboboxdata,function(){
 			   if(this.dictId == value){
 				   //alert(this.dictInfo);
 				   //将获得数据添加row
 				   row.dictInfo = this.dictInfo;
 			   }
 		   });
 		   return row.dictInfo;
		},
		editor:{
			type:'combobox',
			options:{
				valueField:'dictId',
				textField:'dictInfo',
				panelHeight:'80',
				method:'get',
				data:comboboxdata,
				required:true
			}
		}
	}]];
	// 定义工具条
	var toolbar = [];
	// 定义编辑行
	var onClickRow = function(){};
	</script>
	
	<%-- 指定状态才有的方法start : 状态校验：审核通过01003 才能受理采购单，其它不可以 --%>
	<c:if test="${yycgdCustom.sysDictInfoByzt.id=='01003'}">
							
	<script type="text/javascript">
	// 工具条
	toolbar = [{
		id : 'disposesubmit',
		text : '保存药品发货状态',
		iconCls : 'icon-save',
		handler : sendsubmit
	}];
	
	// 保存药品发货状态回调
// 	function sendsubmit_callback(data){
// 		message_alert(data);
// 	}

	// 保存药品发货状态
	function sendsubmit(){
		// 结束编辑
		endEditing();
		// 获取所有的修改数据
		var updateRow = $("#yycgdmxlist").datagrid("getChanges","updated");
		if(updateRow.length==0){
			alert_warn("没有需要保存的采购量");
			return;
		}
		//* 清空上次数据
		$("#yycgdmxztFrom input[name='yycgdmxIds']").remove();
		$("#yycgdmxztFrom input[name='yycgdmxztIds']").remove();
		
		// 将改变数据保存到数据库
		$.each(updateRow,function(){
			$("#yycgdmxztFrom").append("<input type='text' name='yycgdmxIds' value='"+this.id+"'/>");
			$("#yycgdmxztFrom").append("<input type='text' name='yycgdmxztIds' value='"+this.opt1+"'/>");
		});								
		// ajax提交表单
		jquerySubByFId("yycgdmxztFrom",saveyycgdmxfhzt_callback);
	}
	// 保存药品发货状态回调
	function saveyycgdmxfhzt_callback(data){
		message_alert(data,saveyycgdmxfhzt_message_callback);
	}
	function saveyycgdmxfhzt_message_callback (){
		 yycgdmxquery();
	}
	
	// 编辑药品状态
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#yycgdmxlist').datagrid('validateRow', editIndex)){
			$('#yycgdmxlist').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				$('#yycgdmxlist').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#yycgdmxlist').datagrid('selectRow', editIndex);
			}
		}
	}
	
	
	// 完成受理的方法
	function disposesubmit(){
		_confirm('您确认要完成受理吗?',function(){
			//* 结束所有的编辑
			endEditing();
			//校验，如果采购量有修改，不允许提交
			var changeRow = $("#yycgdmxlist").datagrid('getChanges','updated');	
			if(changeRow.length > 0) {
				alert_warn("有未保存采购状态，请先保存");
				return;
			}
			// 提交
			//由form被多个功能公用，每次操作设置form的url
			$("#yycgdsaveForm").attr("action","${baseurl}/cgd/dispose_submit.action");
			jquerySubByFId('yycgdsaveForm', disposesubmit_callback, null);
		});
	}
	// 完成受理的回调函数
	function disposesubmit_callback(data) {
		message_alert(data,disposesubmit_message_callback);
	}
	// 受理完成后关闭窗口
	function disposesubmit_message_callback() {
		parent.$('#tabs').tabs('close','${yycgdCustom.id}采购单受理');
	}
	</script>
	
	</c:if>
	
	<script type="text/javascript">
	//页面加载完成，绘制datagried数据表格，
	$(function() {
		 // 基本数据
		 var options = {
			title : '采购药品列表',
			showFooter:true,//是否显示总计行
			striped : true,
			url : '${baseurl}/cgd/yycgdmx_result.action',//这里后边带了一个参数，所以form中不需要此参数yycgdid
			 queryParams:{//url的参数，初始加载datagrid时使用的参数
				'yycgdMxQueryCustom.yycgd.id':'${yycgdCustom.id}'   //yycgdid是参数名称，如果参数名称中间有点，将参数用单引号括起来
			}, 
			idField : 'id',//采购药品明细id
			columns : colArr,
			pagination : true,
			rownumbers : true,
			showFooter:true,//显示总计
			toolbar : toolbar,
			loadMsg:"",
			pageSize : 5,
			pageList : [ 5,15, 30, 50, 100 ],//每页显示下拉框值
	 		onBeforeLoad : function(index, field, value) {//在加载datagrid之前执行unselectAll(取消所有选中的行)
				$('#yycgdlist').datagrid('unselectAll');
			},
			onClickRow: onClickRow
		 };
		 
		$('#yycgdmxlist').datagrid(options);
	}); 
	
	// 条件查询购单明细列表的方法
	function yycgdmxquery(){
		var params = $("#yycgdmxForm").serializeJson();
		$("#yycgdmxlist").datagrid("load",params);
	}

</script>
</HEAD>
<BODY>
<!-- 采购单主信息保存form -->
<form id="yycgdsaveForm" name="yycgdsaveForm" action="${baseurl}/cgd/disposesubmit.action" method="post">
<input type="hidden" name="yycgdMxQueryCustom.yycgd.id" value="${yycgdCustom.id}"/>
<input type="hidden" name="taskid" value="${taskid }"/>
<TABLE border=0 cellSpacing=0 cellPadding=0 width="70%" bgColor=#c4d8ed align=center>
		<TBODY>
			<TR>
				<TD width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;药品采购单</TD>
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
								<TD height=30 width="15%" align=right>采购单编号：</TD>
								<TD class=category width="35%">
								${yycgdCustom.id}
								</TD>
								<TD height=30 width="15%" align=right >采购单名称：</TD>
								<TD class=category width="35%">
								${yycgdCustom.mc}
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
								${yycgdCustom.lxr}
								</TD>
								<TD height=30 width="15%" align=right >联系电话：</TD>
								<TD class=category width="35%">
								${yycgdCustom.lxdh}
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right>采购单状态：</TD>
								<TD class=category width="35%">
								${yycgdCustom.sysDictInfoByzt.info}
								</TD>
								<TD height=30 width="15%" align=right>备注：</TD>
								<TD colspan=3>
									${yycgdCustom.bz}
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
							
							<!-- 指定状态才有的方法start : 状态校验：审核通过01003 才能受理采购单，其它不可以 -->
							<c:if test="${yycgdCustom.sysDictInfoByzt.id=='01003'}">
							<tr>
							  <td colspan=4 align=center class=category>
								<a  href="#" onclick="disposesubmit()" class="easyui-linkbutton" iconCls='icon-save'>完成受理</a>
							  </td>
							</tr>
							</c:if>
							
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

<!-- 采购单明细信息 -->
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background="${baseurl}/images/r_0.gif" width="100%">
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
	
	<!-- 采购单明细form -->
	<form id="yycgdmxForm" name="yycgdmxForm" method="post">
	<input type="hidden" name="indexs" id="indexs" />
	<input type="hidden" name="yycgdMxQueryCustom.yycgd.id" value="${yycgdCustom.id}"/>
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
				      		<INPUT id="zbjglower" name="yycgdMxQueryCustom.zbjglower" style="width:70px"/>
							至
							<INPUT id="zbjgupper" name="yycgdMxQueryCustom.zbjgupper" style="width:70px"/>
							
				 		 </td>
					</tr>
					<tr>
					  
						<TD class="left">药品类别：</TD>
						<td >
							<!-- 药品类别从数据字典中取id作为页面传入action的value -->
							<select id="ypxxCustom.lb" name="yycgdMxQueryCustom.ypxx.sysDictInfoByLb.id" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${yplbList}" var="value">
									<option value="${value.id}">${value.info}</option>
								</c:forEach>
							</select>
						</td>
						<TD class="left">交易状态：</TD>
						<td >
						    <!-- 交易状态从数据字典中取出dictcode作为页面传入action的value -->
							<select id="ypxxCustom.jyzt" name="yycgdMxQueryCustom.ypxx.sysDictInfoByJyzt.id" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${jyztList}" var="value">
									<option value="${value.id}">${value.info}</option>
								</c:forEach>
							</select>
							
						</td>
						<TD class="left">采购状态：</TD>
						<td >
						   
							<select id="yycgdCustom.cgzt" name="yycgdMxQueryCustom.sysDictInfoCgzt.id" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${cgztList}" var="value">
									<option value="${value.id}">${value.info}</option>
								</c:forEach>
							</select>
							
						</td>
				 		<TD class="left">供货状态：</TD>
						<td >
						   
							<select id="gysypmlCustom.control" name="gysypmlCustom.control" style="width:150px">
								<option value="">全部</option>
								<c:forEach items="${ghztList}" var="value">
									<option value="${value.id}">${value.info}</option>
								</c:forEach>
							</select>
							
						</td>

				  		<td colspan=2>
				  		<input type="submit" style="display:none" onclick="yycgdmxquery();return false" />
						<a id="btn" href="#" onclick="yycgdmxquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>
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

<%-- 保存药品状态表单  start--%>
<form id="yycgdmxztFrom" action="${baseurl }/cgd/saveyycgdmxfhzt.action" style="display: none" >
	<input type="text" name="yycgdCustom.id" value='${yycgdCustom.id }'/>
</form>
<%-- 保存药品状态表单  end--%>

</BODY>
</HTML> 