<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<html> 
<head>
<title>药品统计</title>
<!--ico图标-->
<link rel="shortcut icon" href="ico/papapa.png">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>

<script src="${baseurl}/business/analyze/js/echarts.js"></script>
<!--加载主题-->
<script src="${baseurl}/business/analyze/js/zhuti/macarons.js"></script>
<%-- <script src="${baseurl}/business/analyze//js/javascript.js"></script> --%>
<script src="${baseurl}/business/analyze/js/codemirror.js"></script>


<!-- --------------------------- -->
		<script type="text/javascript">
			function query(){
    
			// Step:3 conifg ECharts's path, link to echarts.js from current page.
			// Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
			require.config({
				paths: {
					echarts: '/business/analyze/js'
				}
			});

			// Step:4 require echarts and use it in the callback.
			// Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
			require(
				[
					'echarts',
					'echarts/chart/bar',
					'echarts/chart/line'
				],
				function(ec) {
					//--- 声明一个折柱 ---
					myChart = ec.init(document.getElementById('main'), 'macarons');
					//initData();//加载数据
					// --- 地图 ---
					/* 读取数据时加载效果 */
					myChart.showLoading();
					initData();//加载数据
					
				}
			);
		}
		
			/* 加载数据ajax */
			function initData(){
				
				//表单参数序列化
				var params = $("#drugyzreaqueryForm").serializeJson();
				//刷新datagride
				$("#drugyzrealist").datagrid('reload',params);
				
				$.ajax({
			         type : "post",
			         async : false,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
			         url : "${baseurl}/analyze/ajaxDrugyzrea_bar.action",    //请求发送到TestServlet处
			         data : params,
			         dataType : "json",        //返回数据形式为json
			         success : function(result) {
			             //请求成功时执行该函数内容，result即为服务器返回的json对象 
			             //清空画布，防止缓存---------------------------------
			         	myChart.clear();
			             
			           // myChart.setOption({});//初始化数据
			            myChart.setOption(result);
			           
			            myChart.hideLoading();
			          //如果不清空,图形数据将不会更改
			      		
			            
			         
			        },
			         error : function(errorMsg) {
			             //请求失败时执行该函数
			         alert("图表请求数据失败!");
			         }
			    })
			}
			
		</script>


<script type="text/javascript">
	//定义列
	var colArr = [[
	            {field:"id",title:"流水号",width:120,formatter:function(value,row,index){
	            	if (row) {
		            	return row.ypxx.bm;
	            	}
	            }},
	            {field:"mc",title:"通用名",width:120,formatter:function(value,row,index){
	            	if (row) {
		            	return row.ypxx.mc;
					}
	            }},
	            {field:"cgl",title:"采购量",width:120},
	            {field:"cgje",title:"采购金额",width:120,formatter:function(value,row,index){
	            	var val = row.cgje;
	        		if(val!=null)
	                   return val.toFixed(2);

	            }} 	
	              ]]; 
	               
	//加载datagrod
	$(function(){
		
		$("#drugyzrealist").datagrid({
			url:"${baseurl }/analyze/list_drugyzrea.action",
			title:"交易明细列表",
			columns:colArr,
			fitColumns:true,
			//fixed:true,
			pagination:true,
			rownumbers:true,
			idFiled:"ypxx.bm",
			//striped:true,
		});
		
	});
	
	//显示datagrod
	function drugyzreaquery() {
		//jquerySubByFId('yycgdqueryForm',yycgdquery_callback);
		
		//表单参数序列化
		var params = $("#drugyzreaqueryForm").serializeJson();
		//刷新datagride
		$("#drugyzrealist").datagrid('reload',params);
		
	}
	drugyzreaquery();
	
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
	
	//从datagrid切换为图表
	function tubiao2datagrid(){
		//隐藏显示图表按钮
		$("#btntu2data").css("display","none")
		//显示datagrid按钮
		$("#data2btntu").css("display","block")
		//判断当前状态
			//显示图表隐藏datagrid
			$("#tubiao").css("display","block")
			query();
			$("#datagrid").css("display","none")
			//修改按钮值
			//$("#btntu2data").prop("iconCls","icon-database");
		
	}
	
	//从图表切换为datagrid
	function datagrid2tubiao(){
		$("#data2btntu").css("display","none")
		$("#btntu2data").css("display","block")
		drugyzreaquery();
		$("#datagrid").css("display","block")
		$("#tubiao").css("display","none")
	}
</script>
		
</HEAD>
<BODY>
 <div id="tubiao" style="display: none;">
		<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="head"></div>
				<div id="main" style="height:80%;border:1px solid #ccc;padding:10px;">
			</div>
	</div>

    <form id="drugyzreaqueryForm" name="businesslistForm" method="post" action="${baseurl }/analyze/drugyarea.action">
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
						<a id="btn" href="javascript:void(0)" onclick="initData()" class="easyui-linkbutton" iconCls='icon-search'>统计</a>	
						 </TD>
	
						 <TD> 
						 	<a id="btntu2data" href="javascript:void(0)" onclick="tubiao2datagrid()" class="easyui-linkbutton" iconCls='icon-search'>显示图表</a>
						 	<a id="data2btntu" href="javascript:void(0)" onclick="datagrid2tubiao()" class="easyui-linkbutton" iconCls='icon-search' style="display: none">显示详情</a>
						 </Td>
						 
					</tr>
				</TBODY>
			</TABLE>
			
			<div id="cc" class="easyui-calendar"></div>

		 </form>
		 
		 <div id="datagrid" style="display: block;">
		 	 <TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="drugyzrealist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		 </div>
		
		 
	<!-- 	 导出excel表单 -->
		 <form id="exprotexcelForm" action = "${baseurl }/analyze/export_excel.action" method="post" >
		 	<input id="startDateForExcel" type ='hidden' name = 'yybusinessQueryCustom.startDate' value =''/>
		 	<input id="endDateForExcel" type ='hidden' name = 'yybusinessQueryCustom.endDate' value =''/>
		 </form>
	<!-- 	 导出excel表单 --> 

</BODY>
</HTML>

