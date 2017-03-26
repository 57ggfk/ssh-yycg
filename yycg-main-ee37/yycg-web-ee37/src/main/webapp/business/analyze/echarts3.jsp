<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<%@ include file="/common/common_css.jsp"%>
<%@ include file="/common/common_js.jsp"%>
<title>ECharts</title>
</head>

<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript" src="echarts/dist/echarts.js"></script>
    <script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main'));
    option = {
    	    title : {
    	        text: '市级医药采购系统统计',
    	        subtext: '纯属虚构'
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['总金额','采购单统计']//需要统计的字段集合
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            magicType : {show: true, type: ['line', 'bar']},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    
    	    xAxis : [//数据的集合(data就是每个镇的名称)
    	        {
    	            type : 'category',
    	            data : ['AA镇','BB镇','CC镇','DD镇','EE镇','FF镇']
    	        }
    	    ],
    	    
    	    yAxis : [
    	        {
    	            type : 'value'
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'总金额',
    	            type:'bar',
    	            data:[1500.0, 2100.0, 3521.4, 520.36, 1820.6, 1300.5],
    	            markPoint : {
    	                data : [
    	                    {type : 'max', name: '最大值'},
    	                    {type : 'min', name: '最小值'}
    	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name: '平均值'}
    	                ]
    	            }
    	        },
    	        {
    	            name:'采购单统计',
    	            type:'bar',
    	            data:[59, 63, 78, 20, 43, 52],
    	            markPoint : {
    	                data : [
    	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
    	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
    	                ]
    	            },
    	            markLine : {
    	                data : [
    	                    {type : 'average', name : '平均值'}
    	                ]
    	            }
    	        }
    	    ]
    	};
    myChart.setOption(option)
    
    </script>
</body>
</html>