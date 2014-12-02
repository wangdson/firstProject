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
		tf=document.forms[0];
		tf.action=me;
		tf.submit();
	}else{
		cateDiv.style.display="block";		
	}
}
function transferForm(me)
{
		tf=document.forms[0];
		tf.action=me;
		tf.submit()
}
function selectleftList(me){  
    var left = document.forms[0].leftSideRecords;  
    for(var i=0;i<left.length;i++)
    {
    	left[i].selected = true;
    }
    tf=document.forms[0];
	tf.action=me;
	tf.submit()      
} 
function selectleftList_bo(me){  
    var left = document.forms[0].leftSideRecords_bo;  
    for(var i=0;i<left.length;i++)
    {
    	left[i].selected = true;
    }
    tf=document.forms[0];
	tf.action=me;
	tf.submit()      
}





</script>
	</head>

	<body>
	<s:form name="ckform" action="TagShowAction" method="post" theme="simple">
	分类：<s:select name="cateSel" id="cateSel" list="cateList"
	 headerKey="1" headerValue="--添加新类别--" onchange="cateSelect('TagShowAction')" />
	 
	<input type="button" value="删除类别" onclick="transferForm('TagCateAction!DELCate')" />
	<div id="cateDiv" style="display:none; float:inherit">
	类别名：<s:textfield name="cateName" />
	<input type="button" value="添加类别" onclick="transferForm('TagCateAction!AddCate')" />
	</div>
	<br/>
	<s:optiontransferselect name="leftSideRecords" leftTitle="类别所属标签" rightTitle="标签库"  
	list="leftList" cssStyle="width:150px" doubleCssStyle="width:150px" doubleName="rightSideRecords" doubleList="rightList"/>
	<input type="button" value="提交标签" onclick="selectleftList('TagCateAction!AddTag')" />
	
	<s:optiontransferselect name="leftSideRecords_bo" leftTitle="类别所属书目" rightTitle="未分类书目"  
	list="leftList_bo" cssStyle="width:150px" doubleCssStyle="width:150px" doubleName="rightSideRecords_bo" doubleList="rightList_bo"/>
	<input type="button" value="提交书目" onclick="selectleftList_bo('PaperbagCateAction!AddBook')" />

	
	</s:form>

	
	
	
	
	
	
	
	
	
	
	
	
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
