﻿var pathName = window.location.pathname.substring(1); 
var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/')); 
var BaseUrl= window.location.protocol + '//' + window.location.host + '/'+ (webName ? webName + '/' : ''); 

var _menus;

//菜单加载失败次数
var menus = 0;



$(function() {
	//加载菜单
	$.ajax({
		url : BaseUrl+'menu.action',//请求动态数据，根据用户角色查询用户权限范围内的菜单
		//url : BaseUrl+'menu.json',// 请求的是一个静态数据
		type : 'POST',
		dataType : 'json',
		success : function(data) {// data就是json数据
			_menus = data;
			//alert(data.menus[0].menuname);
			initMenu(_menus);
		},
		error : function(msg) {
			//加载失败加载本地菜单
			$.ajax({
				//url : 'menu.jso',//请求动态数据，根据用户角色查询用户权限范围内的菜单
				url : BaseUrl+'menu.json',// 请求的是一个静态数据
				type : 'POST',
				dataType : 'json',
				success : function(data) {// data就是json数据
					_menus = data;
					//alert(data.menus[0].menuname);
					initMenu(_menus);
				},
				error : function(msg) {
					//本地菜单加载失败,刷新页面
					if (menu>1) {
						//菜单加载失败超过两次,跳转到子项目
						window.location = 'http://192.168.122.90:8080';
					}
					location.reload();
					menus = menus+1;
				}
			});
			
			
		}
	});
	//加载用户信息
	/*
	$.ajax({
		url : BaseUrl+'userinfo.action',// 请求的是一个静态数据
		type : 'POST',
		dataType : 'json',
		success : function(data) {// data就是json数据
			//alert(data.username);
			$('#userinfo').html(data.username);
		}
	});
	*/
	//加载系统信息
	/*
	$.ajax({
		url : BaseUrl+'sysinfo.action',// 请求的是一个静态数据
		type : 'POST',
		dataType : 'json',
		success : function(data) {// data就是json数据
			$('#version_number').html(data.version_number);
			$('#version_date').html(data.version_date);
		}
	});
	*/

	// tabClose();
	// tabCloseEven();

	$('#tabs').tabs('add', {
		title : '欢迎使用',
		content : createFrame(BaseUrl+'helloworld.jsp')
	}).tabs({
		onSelect : function(title) {
			var currTab = $('#tabs').tabs('getTab', title);
			var iframe = $(currTab.panel('options').content);

			var src = iframe.attr('src');
			if (src)
				$('#tabs').tabs('update', {
					tab : currTab,
					options : {
						content : createFrame(src)
					}
				});

		}
	});

	// 修改密码
	$('#modifypwd').click(menuclick);

});

// 退出系统方法
function logout() {
	_confirm('您确定要退出本系统吗?', function() {
		location.href = BaseUrl+'auth/logout.action';
	})
}

// 帮助
function showhelp() {
	window.open(BaseUrl+'help/help.html', '帮助文档');
}

// 打开标签窗口
var opentabwindow = function(tabTitle, url) {
	addTab(tabTitle, url, 'icon icon-null');
};

// 关闭标签窗口
var closetab = function(tabTitle) {
	//通过标题查看选项卡是否存在
	if ($('#tabs').tabs('exists', tabTitle)) {
		_confirm("您确定要关闭该页面吗", function() {
			$('#tabs').tabs('close', tabTitle);
		})
		/*var r = confirm("您确定要关闭该页面吗");
		if (r == true) {
			$('#tabs').tabs('close', tabTitle)
		}*/
	} 
}

// 菜单单击事件
var menuclick = function() {
	// var tabTitle = $(this).children('.nav').text();
	// 获取二级菜单的title
	var tabTitle = $(this).attr("title");
	// 获取二级菜单的url,rel是获取json数据时拼接成的html的一部分
	var url = $(this).attr("rel");		//路径
	var menuid = $(this).attr("ref");	//id
	var icon = 'icon ' + $(this).attr("icon");
	addTab(tabTitle, url, icon);
	$('.easyui-accordion li div').removeClass("selected");
	$(this).parent().addClass("selected");
};
// 初始化左侧
function initMenu(menus_var) {
//	console.log("BaseUrl: "+BaseUrl);
//	console.log("webName: "+webName);
	$("#nav").accordion({
		animate : false
	});
	var menuArray = menus_var.menus;
	$.each(menuArray, function(i, n) {
		var menulist = '';
		menulist += '<ul>';
//		console.log(n.menuname);
//		console.log(n.menus[0].menuname);
		$.each(n.menus, function(j, o) {
			//如果/开头添加项目名称，否则原路径返回
			//console.log(o);
			//ie不支持startsWith
			url = o? (o.url.substr(0,1)=="/" ? BaseUrl + o.url.substr(1) :  o.url) : '';
			menulist += '<li><div><a title="' + o.menuname + '" ref="'
					+ o.menuid + '" href="#" rel="' + url + '" icon="'
					+ o.icon + '"  ><span class="icon ' + o.icon
					+ '" >&nbsp;</span><span class="nav">' + o.menuname
					+ '</span></a></div></li> ';
		});
		menulist += '</ul>';

		$('#nav').accordion('add', {
			title : n.menuname,
			content : menulist,
			iconCls : 'icon ' + n.icon
			
		});

	});
	
	//选中第一个菜单
	if(menus_var.menus.length > 0){
		try {
			var sIndex = 0;
		
			if (sIndex>menus_var.menus.length) {
				sIndex = menus_var.menus.length;
			}
			$("#nav").accordion("select",menus_var.menus[sIndex].menuname);
		} catch (e) {
			
		}
		
	}
	

	$('.easyui-accordion li a').click(menuclick).hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});

}

function addTab(subtitle, url, icon) {
	//通过标题查看选项卡是否存在
	if (!$('#tabs').tabs('exists', subtitle)) {
		//不存在，添加
		$('#tabs').tabs('add', {
			title : subtitle,
			content : createFrame(url),
			closable : true,
			icon : icon
		});
	} else {
		//存在，选中
		$('#tabs').tabs('select', subtitle);
	}
}

function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url
			+ '" style="width:100%;height:100%;"></iframe>';
	return s;
}









