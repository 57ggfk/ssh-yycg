<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/common/tag.jsp"%>
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
	<head>
		<!--ico图标-->
		<link rel="shortcut icon" href="ico/papapa.png">
		<script src="js/codemirror.js"></script>
		<script src="js/javascript.js"></script>
		<meta charset="utf-8">
		<title>统计</title>
	</head>

	<body>
		<!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
		<!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
		
			<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="head"></div>
		
				<div id="main" style="height:500px;border:1px solid #ccc;padding:10px;">
			
			</div>

		<!--Step:2 Import echarts.js-->
		<!--Step:2 引入echarts.js-->
		<script src="js/echarts.js"></script>
		<!--加载主题-->
		<script src="js/zhuti/macarons.js"></script>

		<script type="text/javascript">
		
		
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
				
		function query(){
			
    
			// Step:3 conifg ECharts's path, link to echarts.js from current page.
			// Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
			require.config({
				paths: {
					echarts: './js'
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
					initData();//加载数据
					// --- 地图 ---
					/* 读取数据时加载效果 */
					//myChart.showLoading();
					//initData();//加载数据
					
				}
			);
		}
		
		query();
			
			/* 加载数据ajax */
			function initData(){

				
				$.ajax({
			         type : "post",
			         async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
			         url : "${baseurl}/analyze/AjaxSumbyarea.action",    //请求发送到TestServlet处
			         data : $('#businesslistForm').serialize(),
			         dataType : "json",        //返回数据形式为json
			         success : function(result) {
			             //请求成功时执行该函数内容，result即为服务器返回的json对象 
			             //清空画布，防止缓存---------------------------------
			         	myChart.clear();
			             
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
<!-- -------------------------日期查询部分---------------------------------------- -->		
<script type="text/javascript">


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
</script>
<form id="businesslistForm"  >
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
						<a id="btn" href="#" onclick="initData()" class="easyui-linkbutton" iconCls='icon-search'>统计</a>	
						 </TD>
						 <TD> 
						 	<a id="btnexport" href="#" onclick="exportexcel()" class="easyui-linkbutton" iconCls='icon-search'>导出Excel</a>
						 </Td>
					</tr>
				</TBODY>
			</TABLE>
			
			<div id="cc" class="easyui-calendar"></div>
	   
		 </form>
		 
	<!-- 	 导出excel表单 -->
		 <form id="exprotexcelForm" action = "${baseurl }/analyze/export_excel.action" method="post" >
		 	<input id="startDateForExcel" type ='hidden' name = 'yybusinessQueryCustom.startDate' value =''/>
		 	<input id="endDateForExcel" type ='hidden' name = 'yybusinessQueryCustom.endDate' value =''/>
		 </form>
	<!-- 	 导出excel表单 --> 
	
		
</body>

</html>