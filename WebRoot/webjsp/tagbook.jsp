<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'tag.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="webjsp/styles.css">
<s:head/>
<script type="text/javascript" language="javascript">


function transferForm(me)
{
		tf=document.forms[0];
		tf.action=me;
		tf.submit()
}




</script>
	</head>

	<body>
	<s:form name="ckform" action="TagShowAction" method="post" theme="simple">
	分类：<s:select name="cateSel" id="cateSel" list="cateList" onchange="transferForm('TagShowAction!showTagBook')" />
	标签：<s:select name="tagSel" id="tagSel" list="tagselList" onchange="transferForm('TagShowAction!tagSelect')" />
	标签：<s:textfield name="tagsearch"/>
	<input type="button" value="搜索" onclick="transferForm('TagShowAction!searchTag')"/>
	包名：<s:textfield name="booksearch"/>
	<input type="button" value="搜索" onclick="transferForm('TagShowAction!searchBook')"/>
	<br/>
	</s:form>
	
	<table id="customers">
	<tr>
	<th>ID</th>
	<th>书名</th>
	<th>ISBN</th>
	<th>类别</th>
	<th>标签</th>
	</tr>
<s:iterator value="booklist" status="st">
	<tr>
	<td><s:property value="id"/></td>
	<td><s:property value="bookName" /></td>
	<td><s:property value="isbn" /></td>
	<td><s:property value="summary" /></td>
	<td><s:property value="downloadNum" /></td>
	</tr>
</s:iterator>
</table>

	
	
	
	
	
	
	
	
	
	
	
	
<!--  
<table id="customers"> 
	<tr> 
	<s:iterator value="taglist" status="st"> 
	    <td>
	    	<s:property value="tagName"/>
	    </td> 
		<s:if test="#st.modulus(4)==0"> 
		<s:if test="#st.last"> 
	</tr> 
		</s:if>	
	    <s:else> 
	    	</tr><tr> 
		</s:else> 
		</s:if> 
    </s:iterator> 
</table>-->
	</body>
</html>
