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

	//定义列
	 var colArr = [[
	             {field:"wssmc",title:"卫生室名称",width:200,
	            	 formatter:function(value,row,index){
	            		 if (row.dwWss) {
	            			 return row.dwWss.mc;
	            		 }
	         	 }},
	             {field:"id",title:"采购单编号",width:120},
	             {field:"mc",title:"采购单名称",width:260},
	             {field:"cjsj",title:"建单时间",width:120,
	            	 formatter:function(value,row,index){
							if (value){
								var date = new Date(value);
								return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
							} 
						}
	             },
	             {field:"cgdztmc",title:"采购单状态",
	            	 formatter:function(value,row,index){
	            		 if (row.sysDictInfoByzt) {
	         				return row.sysDictInfoByzt.info;
	         				
	            		 }
	         	 }},
	             {field:"opt3",title:"受理",width:80,
	         		formatter:function(value, row, index){
	         			var optStr = "去受理";
	         			try {
	         				if (row.sysDictInfoByzt.dictcode==5) {
		         				optStr = "查看详情";
		         			}
	         			} catch (e) {}
	         			
	         		return "<a href='javascript:void(0)' onclick='yycgdedit("+row.id+")'>"+optStr+"</a>";
	         	}},
	                ]]; 

	 $(function(){
	 	
	 	var options={
	 		
	 		title:"医药采购单列表",
	 		columns:colArr,
	 		url:"${baseurl }/cgd/disposelist_result.action",
	 		pagination:true,
	 		rownumbers:true,
	 		idFiled:"id"
	 	};
	 	
	 	$("#yycgdlist").datagrid(options);
	 });
	 
	 
	 function yycgdquery() {
			var formdata = $("#yycgdqueryForm").serializeJson();//将form中的input数据取出来
			$('#yycgdlist').datagrid('load', formdata);
		}
	 
	 
	//受理
	 function yycgdedit(id) {
	 	//以选项卡方式打开编辑页面
	 	var title = id+"采购单受理";
	 	//调用父页面函数
	 	parent.opentabwindow(title,"${baseurl}/cgd/dispose.action?yycgdMxCustom.yycgd.id="+id);
	 }

</script>
</HEAD>
<BODY>
    <form id="yycgdqueryForm" name="yycgdqueryForm" method="post" >
			<TABLE  class="table_search">
				<TBODY>
					
					<TR> 
					<TD class="left">采购单编号：</td>
						<td><INPUT type="text"  name="yycgdQueryCustom.id" /></TD>
					<TD class="left">采购单名称：</TD>
						<td ><INPUT type="text" name="yycgdQueryCustom.mc" /></td>
						
					 <TD class="left">采购单状态：</TD>
						<td >
							<select name="yycgdQueryCustom.sysDictInfoByzt.id" style="width:150px">
							<option value="">全部</option>
								<c:forEach items="${yycgdZtList}" var="dictinfo">
									<option value="${dictinfo.id}">${dictinfo.info}</option>
								</c:forEach>
							</select>
						</td> 
					<td ><a id="btn" href="#" onclick="yycgdquery()" class="easyui-linkbutton" iconCls='icon-search'>查询</a>	</td>
							<input type="submit" style="display:none" onclick="yycgdquery();return false" />
						
						
						
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
		 </form>


</BODY>
</HTML> 