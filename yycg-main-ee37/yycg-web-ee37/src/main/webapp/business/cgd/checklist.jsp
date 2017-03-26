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


//打开一个模型审核采购单窗口
function check(id) {
	var sendUrl = "${baseurl}/cgd/check.action?yycgdCustom.id=" + id;
	createmodalwindow("采购单审核", 800, 300, sendUrl);
}

//查看采购单明细
function yycgdview(id) {

	//打一个模态窗口 ,传入采购单id
	var sendUrl = "${baseurl}/cgd/viewyycgdmx.action?yycgdCustom.id="+id;
	//参数：1、窗口标题、2、宽、3：高、4、url
	createmodalwindow("查看采购药品信息", 980, 400, sendUrl);
}


//格式化时间
function formatDate(value, row, index) {
	if (value) {
		var date = new Date(value);
		return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+
				date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	}
	return "暂无";
}
//格式化单位名称显示
function formatMc(value, row, index) {
	if (value) {
		return value.mc;
	}
	return "";
}

//格式化显示采购单状态
function formatZt(value, row, index) {
	if (value) {
		return value.info;
	}
}
//定义列
var colArr = [[
            {field:"id",title:"采购单编号",width:260},
            {field:"mc",title:"采购单名称",width:260},
            {field:"tjsj",title:"提交时间",width:260,formatter:formatDate},
            {field:"shsj",title:"审核时间",width:260,formatter:formatDate},
            {field:"dwGys",title:"供应商名称",width:260,formatter:formatMc},
            {field:"sysDictInfoByzt",title:"采购单状态",width:200,formatter:formatZt},
            {field:"bz",title:"备注",width:260},
            {field:"opt2",title:"查看",width:260,
            	formatter:function(value,row,index){
            		return "<a href='javascript:void(0)' onclick='yycgdview("+row.id+")'>查看</a>";
            	}
            },
            {field:"opt3",title:"审核",width:260,
            	formatter:function(value,row,index){
            		if("01003"==(row.sysDictInfoByzt.id)){
            			return "已审核";
            		}else{
            			return "<a href='javascript:void(0)' onclick='check("+row.id+")'>审核</a>";
            		}
            	}
            },
               ]];
               

//刷新datagrid查询采购单
function yycgdquery() {
	var formdata = $("#yycgdqueryForm").serializeJson();//将form中的input数据取出来
	console.log(formdata);
	$('#yycgdlist').datagrid('reload', formdata);
	return false;
}
	

	
$(function(){
	
	$("#yycgdlist").datagrid({
		url:"${baseurl }/cgd/checklist_result.action",
		title:"采购单列表",
		columns:colArr,
		fitColumns:true,
		//fixed:true,
		pagination:true,
		rownumbers:true,
		idFiled:"id",
		striped:true,
	});
	
});	

	
	
</script>
</HEAD>
<BODY>

    <form id="yycgdqueryForm" name="yycgdqueryForm" method="post" action="${baseurl}/cgd/checklist_result.action" >
			
			<TABLE  class="table_search">
				<TBODY>
					
					<TR> 
					<TD class="left">采购单编号：</td>
						<td><INPUT type="text"  name="yycgdQueryCustom.id" /></TD>
					<TD class="left">采购单名称：</TD>
						<td ><INPUT type="text" name="yycgdQueryCustom.mc" /></td>
					  <TD class="left">采购单状态：</TD>
						<td >
							<select id="yycgdQueryCustom.zt" name="yycgdQueryCustom.sysDictInfoByzt.id" style="width:150px">
							<option value="">全部</option>
								<c:forEach items="${yycgdZtList}" var="dictinfo">
									<option value="${dictinfo.id}">${dictinfo.info}</option>
								</c:forEach>
							</select>
							<a id="btn" href="javascript:void(0)" onclick="yycgdquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>	
							<input type="submit" style="display:none" onclick="yycgdquery();return false" />
						</td>
						
					</tr>
					
				</TBODY>
			</TABLE>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="yycgdlist"></table>
					</TD>
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


</BODY>
</HTML>

