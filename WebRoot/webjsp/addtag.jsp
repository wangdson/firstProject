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

function cateSelect(me)
{
	var cateSel=document.getElementById("cateSel");
	var cateDiv=document.getElementById("cateDiv");
	if(cateSel.value!="1"){
		cateDiv.style.display="none";
	}else{
		cateDiv.style.display="block";		
	}
}
function transferForm(me)
{
		tf=document.forms[0];
		tf.action=me;
		tf.submit();
}
function selectleftList(me){  
    var left = document.forms[0].leftSideRecords;  
    for(var i=0;i<left.length;i++)
    {
    	left[i].selected = true;
    }
    tf=document.forms[0];
	tf.action=me;
	tf.submit();      
} 
function selectleftList_bo(me){  
    var left = document.forms[0].leftSideRecords_bo;  
    for(var i=0;i<left.length;i++)
    {
    	left[i].selected = true;
    }
    tf=document.forms[0];
	tf.action=me;
	tf.submit();      
}





</script>
	</head>

	<body>
	<s:form name="ckform" action="TagAction" method="post" theme="simple">
	类别：<s:select name="cateSel" id="cateSel" list="cateList" headerKey="1" headerValue="--添加新类别--" onchange="cateSelect('TagAction')" />
	<input type="button" value="删除类别" onclick="transferForm('TagAction!delcate')" />
	
	<div id="cateDiv">
	类别名：<s:textfield name="cateName" />
	<input type="button" value="添加类别" onclick="transferForm('TagAction!addcate')" />
	</div>
	<br/>
	
	标签：<s:textfield name="tagNamein"/>
	<input type="button" value="添加输入标签" onclick="transferForm('TagAction!addtag')" />
	
	
	
	<input type="button" value="删除勾选标签" onclick="transferForm('TagAction!deltag')" />
	<table id="customers"> 
	<tr> 
	<s:iterator value="taglist" status="st"> 
	    <td>
	    	<input type="checkbox" name="ck_tag" value="<s:property value="tagId"/>"/>
	    	<s:property value="tagName"/>
	    </td> 
		<s:if test="#st.modulus(6)==0"> 
		<s:if test="#st.last"> 
	</tr> 
		</s:if>	
	    <s:else> 
	    	</tr><tr> 
		</s:else> 
		</s:if> 
    </s:iterator> 
</table>
<br/>
图片包：<br/>
	<table id="customers"> 
	<tr> 
	<s:iterator value="paperbaglist" status="sp"> 
	    <td>
	    	<input type="checkbox" name="ck_pg" value="<s:property value="Id"/>"/>
	    	<s:property value="Id"/>
	    	<a href="PaperEdit?id=<s:property value="Id"/>">
	    	<s:property value="bagName"/></a>
	    </td> 
		<s:if test="#sp.modulus(4)==0"> 
		<s:if test="#sp.last"> 
	</tr> 
		</s:if>	
	    <s:else> 
	    	</tr><tr> 
		</s:else> 
		</s:if> 
    </s:iterator> 
</table>
	<br/>
<input type="button" value="确认分配" onclick="transferForm('TagAction!distribution')" />
	
	</s:form>

	</body>
</html>
