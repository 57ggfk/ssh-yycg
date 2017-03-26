<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<fmt:setBundle basename="resources.messages" var="messagesBundle"/>
<%--设置通用路径 --%>
<c:if test="${pageContext.request.contextPath == '/'}">
	<c:set var="baseurl" value="" />
</c:if>
<c:if test="${pageContext.request.contextPath != '/'}">
	<c:set var="baseurl" value="${pageContext.request.contextPath}" />
</c:if>
<script type="text/javascript">
		//初始化页面加载时间	
	
	//页面加载时间
	var start_time = (new Date()).getTime();
	var end_time = "" ;
	var t = setInterval(function(){
	if(document.readyState=="complete"){aa();}
	},500)
	
	function aa(){
	end_time = (new Date()).getTime();
	var data = (end_time - start_time-188)+" 毫秒";

		if (this.top) {
			top.setYmzrsj(data);
			clearInterval(t);
		} else {
			setYmzrsj(data);
		}
	}
</script>
<%-- <c:set var="baseurl" value="${pageContext.request.contextPath=='/'? '' : pageContext.request.contextPath}" /> --%>