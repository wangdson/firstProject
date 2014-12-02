<%@ page language="java" import="java.util.*,com.dxs.Util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	if(!CheckUser.ck_class())
	{
		response.sendRedirect("webjsp/mylogin.jsp");
	}
%>
	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'booksView.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="webjsp/styles.css">
<s:head/>
<sx:head/>

  </head>
  
  <body>
    <h3></h3>
<table id="customers">
	<tr>
	<th>ID</th>
	<th>包名</th>
	<th>上架时间</th>
	<th>上架 / 推荐</th>
	<th>推荐状态</th>
	<th>版本</th>
	<th>备注</th>
	</tr>
<s:iterator value="pglist" status="st">
	<tr>
	<td><font size="2"><s:property value="id"/></font></td>
	<td><font size="2"><s:property value="bagName"/></font></td>
	<td><font size="2"><s:property value="uploadTime"/></font></td>
	<td><font size="2"><s:property value="state"/> / <a href="#"><s:property value="state_rec"/></a></font></td>
	<td><font size="2"><s:property value="state_rec"/></font></td>
	<td><font size="2"><s:property value="version"/></font></td>
	<td><font size="2"><s:property value="summary"/></font></td>
	</tr>
</s:iterator>
</table>
  </body>
</html>
