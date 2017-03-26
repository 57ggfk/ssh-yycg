/**
 * 主题修改
 * 王吉@黑马程序员就业班37期
 * 
 * 引入样式themeCssId
 * 修改样式方法changeTheme
 * 修改样式themesId
 */
var themeName =  "theme";		//在cookie中保存主题名的name名，即主题变量名
var themeClass = "changeTheme"; //自动绑定修改主题事件到此class上
var themeCss = "themeCssId";
var themesUrl = $("#themeCssId")[0].href;
$(function(){

	//给class=变量themeClass的select元素绑定修改主题事件
	$("."+themeClass).changeTheme();
	themesUrl = $("#themeCssId")[0].href;
	mytheme = getCookie(themeName);
	changeTheme(mytheme);
});

//$(对象).changeTheme();
$.fn.changeTheme = function(){
	//this 代表jquery选择器被选中的对象，是jquery对象
	$this = this;
	var themesUrl = $("#themeCssId")[0].href;
	//初始化主题列表
	var themes = {"default":"默认主题",
				  "bootstrap":"布特斯穿破",
	              "material":"棉花糖",
	              "ui-pepper-grinder":"胡椒粉",
	              "gray":"星空灰",
	              "black":"梦幻黑",
	              "ui-dark-hive":"黑暗蜂巢",
	              "ui-sunny":"夕阳晚霞",
	              "ui-cupertino":"蓝瘦香菇",
	              "metro-blue":"美俏天空蓝",
	              "metro-gray":"美俏都市灰 ",
	              "metro-green":"美俏典雅绿 ",
	              "metro-orange":"美俏动感橙 ",
	              "metro-red":"美俏樱桃红",
	              "metro":"美俏象牙白"};
	for (var key in themes) {
		$("<option></option>").val(key).text(themes[key]).appendTo($this);
	}
	
	//读取Cookie看有没有保存主题变量，如果有设置select的默认值
	mytheme = getCookie(themeName);
	if (mytheme!="") {
		//console.log($this[0].id);
		//按class修改，变量themeClass的值是class的值
		//var $curOption = $("."+themeClass+" option[value='"+mytheme+"']");
		//$curOption.prop("selected",true);
		//按id修改，this[0].id是id
		//$curOption = $("#"+this[0].id+" option[value='"+mytheme+"']");
		//$curOption.prop("selected",true);
		//按select的子元素修改
		$this.children("[value='"+mytheme+"']").prop("selected",true);;
	}
	
	$this.bind("change",function(event) {
		changeTheme(this.value);
		saveTheme(this.value);
		//location.reload();
		//刷新所有框架
		$("iframe").each(function(idx, ele){
			ele.contentWindow.location.reload();
		});
		$("frame").each(function(idx, ele){
			ele.contentWindow.location.reload();
		});
	});
};

//调用changeTheme(主题名);
function changeTheme(theme){
	if (typeof theme=="string" && theme!="") {
		var href = themesUrl.replace("default",theme);
		$("#themeCssId")[0].href = href;
	}
}

//保存主题
function saveTheme(theme) {
	// save Theme
	if (typeof theme=="string" && theme!="" ){
		setCookie(themeName,theme,100);
	}
}

function setCookie(c_name,value,expiredays)
{
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}

function getCookie(c_name)
{
	if (document.cookie.length>0)
	  {
	  c_start=document.cookie.indexOf(c_name + "=")
	  if (c_start!=-1)
	    { 
	    c_start=c_start + c_name.length+1 
	    c_end=document.cookie.indexOf(";",c_start)
	    if (c_end==-1) c_end=document.cookie.length
	    return unescape(document.cookie.substring(c_start,c_end))
	    } 
	  }
	return ""
}
