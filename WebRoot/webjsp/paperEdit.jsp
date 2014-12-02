<%@ page language="java" import="java.util.*,java.io.*,com.dxs.Util.*,com.dxs.Entity.*,java.text.SimpleDateFormat,com.dxs.Service.Impl.*,com.dxs.Service.Intf.*,com.opensymphony.xwork2.ActionContext" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%
	if(!CheckUser.ck_class())
	{
		response.sendRedirect("webjsp/mylogin.jsp");
	}
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", 0);  
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'bookEdit.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<s:head/>
<sx:head/>
<script type="text/javascript" language="javascript">
function canc()
{
	location.href="PaperbagAction";
}
function compa(curr)
{
	var disp1=document.getElementById("equal");
	var disp2=document.getElementById("differ");
	var result ="<%=session.getAttribute("appver")%>";
	if(curr!=result)
	{
		disp1.style.display="none";
		disp2.style.display="block";
	}
	if(curr==result)
	{
		disp1.style.display="block";
		disp2.style.display="none";
	}

}
function upcover()
{
	var cover=document.getElementById("conver");
	var diy=document.getElementById("dis");
	cover.src=diy.value;
}

</script>

  </head>
  <body onload="compa('<s:property value="appVer"/>')">
  <hr style="filter: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 size=1>
<h3>图片包信息修改</h3>
<s:form action="ModifyPaperbag" method="post" theme="simple" enctype="multipart/form-data">
<table width="1000" border="1" cellpadding="10" cellspacing="1">
  <tr>
    <td width="120"><strong>应用图标</strong></td>
    <td colspan="3"><input type="image" id="conver" src="http://<%=CONSTANTS.ADDR %>ipaper/upload/<%=session.getAttribute("pgid")%>/<%=session.getAttribute("pgid")%>.png"/>
    <br/>
    <s:file name="diy" id="dis"/>
    </td>
  </tr>
  <tr>
    <td><strong>内核版本</strong></td>
    <td width="379">
    <table width="360" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <s:textfield name="appVer" readonly="true" size="2" maxlength="3" />
    </td>
    <td>
    <div id="equal" style="display:none; float:left; width:auto">最新</div>
    <div id="differ" style="display:none; float:left; width:auto" ><font color="#ff0000">升级</font> </div>
    </td>
  </tr>
</table>

    </td>
    <td width="120"><strong>资源版本</strong></td>
    <td width="381">
    <s:textfield name="bookVer" size="2" maxlength="3" />若本页有改动请改变版本号（3位数字）
    </td>
  </tr>
  <tr>
    <td><strong>更换压缩包</strong></td>
    <td>
    <s:file name="repub" id="repub"/>
    </td>
    <td><strong>包包ID</strong></td>
    <td>
    <s:textfield name="id" id="id" readonly="true"  />
    </td>
  </tr>
  <tr>
    <td><strong>包包名称</strong></td>
    <td>
    <s:textfield name="bagName" />
    </td>
    <td><strong>包包上传日期</strong></td>
    <td>
    <s:property value="uptime"/>
    </td>
  </tr>
  <tr>
    <td><strong>包包备注</strong></td>
    <td colspan="3">
    <s:textarea name="content" wrap="true" cols="40" rows="6" />
    </td>
  </tr>
  <tr>
    <td><strong>包包标签</strong></td>
    <td colspan="3">
    <s:textarea name="tag" wrap="true" cols="40" rows="3" />
    </td>
  </tr>
    <tr>
    <td><strong>包包转世号</strong></td>
    <td colspan="3">
    <s:property value="unionId"/>
    <s:textfield name="unionChangeId" size="3" maxlength="4" />
    </td>
  </tr>
  <tr>
    <td><strong>推荐状态</strong></td>
    <td>
    <s:property value="recommendState"/>
    </td>
    <td><strong>上架状态</strong></td>
    <td><s:property value="state"/></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="center">
    <s:submit value="完成"  /><input type="button" value="取消" onclick="canc()"/>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</s:form>
<%
File file=new File("/epub/cloud/java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/"+session.getAttribute("pgid")+"/"+session.getAttribute("pgname")+"/");
File[] filelist=file.listFiles();
 %>
<table width="900" border="0" cellpadding="0" cellspacing="10">
<%
 
%>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
  </body>
</html>
