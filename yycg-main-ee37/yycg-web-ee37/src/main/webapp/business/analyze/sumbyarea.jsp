<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html> 
<head>
<title>按区域统计</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>

<script type="text/javascript">
//查询方法
function query(){
		//根据查询条件进行统计，执行form提交
		document.businesslistForm.submit();
}
//导出excel的方法
function exportexcel(){
	
	//获得 时间选择框的时间
	 var start = $("#startDate").val();
	 var end = $("#endDate").val();
	 
	 //向自定义表单中添加表单项 
	$("#startDateForExcel").val(start);
	$("#endDateForExcel").val(end);

	
	//通过ajax提交表单 exprotexcel_callback为回调函数
	jquerySubByFId("exprotexcelForm",exprotexcel_callback);

}
function exprotexcel_callback(data ,exprotexcel_callback){
	
	message_alert(data ,exprotexcel_message_callback);
}

function exprotexcel_message_callback(){
	
	
}

$(function(){

	$('#startDate').datebox({
	    onSelect: function(date){
	       var startDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	       $('#startDate').val(startDate);
	    }
	});

	$('#endDate').datebox({
	    onSelect: function(date){
	       var endDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	       $('#endDate').val(endDate);
	    }
	});	
	

})


</script>

</HEAD>
<BODY>
	<!-- 图表的表单  -->
    <form id="businesslistForm" name="businesslistForm" method="post" action="${baseurl }/analyze/sumbyarea.action">
			<TABLE  class="table_search">
				<TBODY>
					
					<TR>
					   <TD class="left">统计开始时间：</TD>
						<td >
						 <INPUT id="startDate"  
							name="yybusinessQueryCustom.startDate"
							value ="${yybusinessQueryCustom.startDate}"
							class="easyui-datebox"  data-options="sharedCalendar:'#cc'" editable="false"/>
						</td>
					   <TD class="left">统计截止时间：</td>
					    <td>
					     <INPUT id="endDate" 
							name="yybusinessQueryCustom.endDate"
							value ="${yybusinessQueryCustom.endDate}"
							 class="easyui-datebox "  data-options="sharedCalendar:'#cc'" editable="false"/>
						<a id="btn" href="#" onclick="query()" class="easyui-linkbutton" iconCls='icon-search'>统计</a>	
						 </TD>
						 <TD> 
						 	<a id="btnexport" href="#" onclick="exportexcel()" class="easyui-linkbutton" iconCls='icon-search'>导出Excel</a>
						 </Td>
					</tr>
				</TBODY>
			</TABLE>
			
			<div id="cc" class="easyui-calendar"></div>
	   
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<!-- jfreechart图形 -->
						<img src="${baseurl}/jfreechart?filename=${jfreechart_filename }"  border=0 />
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		 </form>
		 
	<!-- 	 导出excel表单 -->
		 <form id="exprotexcelForm" action = "${baseurl }/analyze/export_excel.action" method="post" >
		 	<input id="startDateForExcel" type ='hidden' name = 'yybusinessQueryCustom.startDate' value =''/>
		 	<input id="endDateForExcel" type ='hidden' name = 'yybusinessQueryCustom.endDate' value =''/>
		 </form>
	<!-- 	 导出excel表单 --> 

</BODY>
</HTML>

