<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/common/tag.jsp"%>
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
	<head>
		<!--ico图标-->
		<link rel="shortcut icon" href="ico/papapa.png">
		<script src="${baseurl}/business/analyze/js/echarts.js"></script>
		<!--加载主题-->
		<script src="${baseurl}/business/analyze/js/zhuti/macarons.js"></script>
		<script src="${baseurl}/business/analyze/js/zhuti/yizu.js"></script>
		<%-- <script src="${baseurl}/business/analyze//js/javascript.js"></script> --%>
		<script src="${baseurl}/business/analyze/js/codemirror.js"></script>
		<meta charset="utf-8">
		<title>统计</title>
	</head>

	<body>
		<!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
		<!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
		
			<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="head"></div>
		
				<div id="main" style="height:500px;border:1px solid #ccc;padding:10px;">
			
			</div>



		<script type="text/javascript">
		
				
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
					myChart = ec.init(document.getElementById('main'), 'yizu');
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
				
				
				
				$.ajax({
			         type : "post",
			         async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
			         url : "${baseurl}/analyze/MonthBar.action",    //请求发送到TestServlet处
			         data : {},
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
			query();
		</script>
		
</body>

</html>