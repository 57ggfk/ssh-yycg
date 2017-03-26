<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ include file="/common/tag.jsp"%>
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>

<script type="text/javascript">

var columns = [ [ 
{
	field : 'yymc',
	title : '医院名称',
	width : 100,
	formatter: function(value,row,index){
		if(row.dwWss!=null){
			return row.dwWss.mc;
		}
		return null;
	}
	
},
 {
	field : 'cgdh',
	title : '采购单号',
	width : 100,
	formatter: function(value,row,index){
		if(row.yycgd!=null){
			return row.yycgd.id;
		}
		return null;

	}
	
},

{
	field : 'bm',
	title : '流水号',
	width : 100,
	formatter: function(value,row,index){
		if(row.ypxx!=null){
			return row.ypxx.bm;
		}
		return null;	
	}
	
},



{
	field : 'ghs',
	title : '供货商',
	width : 100,
	formatter: function(value,row,index){
	
		if(row.dwGys!=null){
			return row.dwGys.mc;
		}
		return null;	

	}
	
},
{
	field : 'mc',
	title : '通用名',
	width : 100,
	formatter: function(value,row,index){
		
		if(row.ypxx!=null){
			return row.ypxx.mc;
		}
		return null;	
	
	}
},
{
	field : 'jx',
	title : '剂型',
	width : 100,
	formatter: function(value,row,index){
		
		if(row.ypxx!=null){
			return row.ypxx.jx;
		}
		return null;	

	}
},{
	field : 'gg',
	title : '规格',
	width : 70,
	formatter: function(value,row,index){
		
		
		if(row.ypxx!=null){
			return row.ypxx.gg;
		}
		return null;

	}
},{
	field : 'zhxs',
	title : '转换系数',
	width : 70,
	formatter: function(value,row,index){
		
		if(row.ypxx!=null){
			return row.ypxx.zhxs;
		}
		return null;
		
	}
},
{
	field : 'jyjg',
	title : '交易价格',
	width : 70,
	formatter: function(value,row,index){	
		var monery = row.jyjg;
		 if( monery!=null){
			 return monery.toFixed(2);
		 }

	}
},
{
	field : 'cgl',
	title : '采购量',
	width : 50,
	formatter: function(value,row,index){
		return row.cgl;
	}
},
{
	field : 'jyjge',
	title : '采购金额',
	width : 70,
	formatter: function(value,row,index){
		
		var val = row.cgje;
		if(val!=null)
            return val.toFixed(2);
	
	}
},
{
	field : 'cgzt',
	title : '状态',
	width : 70,
	formatter: function(value,row,index){
		
		if(row.sysDictInfoByCgzt!=null){
			return row.sysDictInfoByCgzt.info;
		}
		return null;
		
	}
},
{
	field : 'cgsj',
	title : '采购时间',
	width : 70,
	formatter: function(value,row,index){
		
		if(row.yycgd!=null){
			var formatterDate = new Date(row.yycgd.cjsj);  
           return formatterDate.toLocaleString();  
		}
		return null;

	},
}

]];
 	
 	function initGrid(){
 		$('#bussinessDetailFormList').datagrid({
			title : '采购药品列表',
			
			striped : true,
			url : '${baseurl}/bussinessdetail/bussinessDetail.action',//这里后边带了一个参数，所以form中不需要此参数yycgdid
			idField : 'id',//采购药品明细id
			columns : columns,
			pagination : true,
			rownumbers : true,
			showFooter:true,//是否显示总计行
			
			} );
 		}
	
 		$(function() {
 			initGrid();
 		
 		});
	
 		function bussinessDetailQuery() {
 			var formdata = $("#bussinessDetailForm").serializeJson();//将form中的input数据取出
 			$('#bussinessDetailFormList').datagrid('load', formdata);
 		}
 		 
</script>
</HEAD>
<BODY>

<!-- 采购单明细信息 -->
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
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
	<form id="bussinessDetailForm" name="bussinessDetailForm" method="post" ">
		<TABLE  class="table_search">
			<TBODY>
				<TR>
					<TD class="left">年份：</td>
					<td>
						<select name ="yybusinessQueryCustom.year"  class="easyui-combobox" panelHeight="200px"  style="width:100px ">
							<option value="">-请选择-</option>
						<c:forEach begin="2010" end="2030" var="i">
							
							<option value="${i}">${i}年</option>
						</c:forEach>
	
						</select>
					</TD>
					<TD class="left">医院名称：</TD>
					<td ><INPUT type="text" name="yybusinessQueryCustom.dwWss.mc" /></td>
					<TD class="left">供货商：</TD>
					<td ><INPUT type="text" name="yybusinessQueryCustom.dwGys.mc" style ="width:300px"/></td>
					<TD class="left">采购单号：</TD>
					<td ><INPUT type="text" name="yybusinessQueryCustom.yycgd.id" /></td>
				</TR>
				<TR>
					<TD class="left">流水号：</TD>
					<td ><INPUT type="text" name="yybusinessQueryCustom.ypxx.bm" /></td>
					<TD class="left">通用名：</TD>
					<td ><INPUT type="text" name="yybusinessQueryCustom.ypxx.spmc" /></td>
					 <td class="left">采购时间：</td>
			  	<td>
   	
   	 			<%-- 	<INPUT id="startDate"  
							name="yybusinessQueryCustom.startDate"
							value ="${yybusinessQueryCustom.startDate}"
							class="easyui-datebox"  data-options="sharedCalendar:'#cc'" editable="false"/> --%>
					<input class="easyui-datebox" name="yybusinessQueryCustom.startDate"     
       			 data-options="showSeconds:false"  editable="false" style="width:150px">  
				至
				<input class="easyui-datebox" name="yybusinessQueryCustom.endDate"     
       			 data-options="showSeconds:false" editable="false" style="width:150px ">  
					
			 	</td>
			 		 <TD class="left">采购状态：</TD>
					<td >
				<!-- 	panelHeight:'auto 自动适应面板高度 -->
						<select  name="yybusinessQueryCustom.sysDictInfoByCgzt.id"  class="easyui-combobox" panelHeight="100px"  style="width:100px "  >
							<option value="">-请选择-</option>
							<c:forEach items="${ypmxzt}" var="value">
								<option value="${value.id}">${value.info}</option>
							</c:forEach>
						</select>
						<a id="btn" href="javascript:void(0)" onclick="bussinessDetailQuery()" class="easyui-linkbutton c4" style="margin:5px 0 ;width:60px" iconCls='icon-search'>查询</a>
					</td>

				</tr>				
			</TBODY>
		</TABLE>
   
   <!-- datagrid -->
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
		<TBODY>
			<TR>
				<TD>
				<!-- 采购单明细列表 -->
					<table id="bussinessDetailFormList"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
	</form>



</BODY>
</HTML>

