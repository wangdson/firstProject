<%@ page language="java" import="java.util.*,com.dxs.Entity.*,com.dxs.Util.*,java.text.SimpleDateFormat,com.dxs.Service.Impl.*,com.dxs.Service.Intf.*,com.opensymphony.xwork2.ActionContext" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(!CheckUser.ck_class())
	{
		response.sendRedirect("webjsp/mylogin.jsp");
	}
 %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Upload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="webjsp/styles.css">
	<link rel="stylesheet" type="text/css" href="webjsp/styleshis.css">
	
	    <!-- The styles -->
	<link id="bs-css" href="<%=basePath%>css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	<link href="<%=basePath%>css/bootstrap-responsive.css" rel="stylesheet">
	<link href="<%=basePath%>css/charisma-app.css" rel="stylesheet">
	<link href="<%=basePath%>css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
	<link href='<%=basePath%>css/fullcalendar.css' rel='stylesheet'>
	<link href='<%=basePath%>css/fullcalendar.print.css' rel='stylesheet'  media='print'>
	<link href='<%=basePath%>css/chosen.css' rel='stylesheet'>
	<link href='<%=basePath%>css/uniform.default.css' rel='stylesheet'>
	<link href='<%=basePath%>css/colorbox.css' rel='stylesheet'>
	<link href='<%=basePath%>css/jquery.cleditor.css' rel='stylesheet'>
	<link href='<%=basePath%>css/jquery.noty.css' rel='stylesheet'>
	<link href='<%=basePath%>css/noty_theme_default.css' rel='stylesheet'>
	<link href='<%=basePath%>css/elfinder.min.css' rel='stylesheet'>
	<link href='<%=basePath%>css/elfinder.theme.css' rel='stylesheet'>
	<link href='<%=basePath%>css/jquery.iphone.toggle.css' rel='stylesheet'>
	<link href='<%=basePath%>css/opa-icons.css' rel='stylesheet'>
	<link href='<%=basePath%>css/uploadify.css' rel='stylesheet'>

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<!-- The fav icon -->
	<link rel="shortcut icon" href="<%=basePath%>img/favicon.ico">
	
		<!-- jQuery -->
	<script src="js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="js/jquery-ui-1.8.21.custom.min.js"></script>
	
<s:head/>
<sx:head/>

<script type="text/javascript" language="javascript">

function mydisp(a){
	var disp3=document.getElementById("disp3");
	var disp4=document.getElementById("disp4");
	//a=ActionContext.getContext().getSession().get("fnum");
	if(a==3)
	{
		disp3.style.display="block";
		disp4.style.display="none";
	}
	if(a==4)
	{
		disp3.style.display="none"; 
		disp4.style.display="block";	
	}
}
function txtchange()
{
	var pgfile=document.getElementById("pgfile");
	var tagtxt=document.getElementById("tagtxt");
	var nn=pgfile.value;
	var sta=nn.lastIndexOf("\\");
	var end=nn.lastIndexOf(".");
	var cc=nn.substring(sta+1,end);
	tagtxt.value=cc;
}
</script>
  </head>

  <body>
  
  <div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-edit"></i> 上传图片包</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<form class="form-horizontal">
						  <fieldset>
							<legend>图片包上传</legend>
                              <table >
                                <tr>
                                  <td width="120px" height="45px"> 压缩包：</td>
                                  <td width="400px"> <input type="file" name="doc" id="pgfile" onchange="txtchange()" /></td>                                      
                                </tr>
                                <tr>
                                  <td height="45px"> 图标：</td>
                                  <td> <input type="file" name="cov" id="cov"/></td>                                      
                                </tr>
                                <tr>
                                  <td height="120px"> 备注：</td>
                                  <td> <textarea name="content" wrap="true"  cols="50" rows="6" ></textarea></td>                                      
                                </tr>
                                <tr>
                                  <td height="120px"> 标签：</td>
                                  <td> <textarea name="tag" id="tagtxt" wrap="true" cols="50" rows="6" ></textarea></td>                                      
                                </tr>
								<tr>
                                  <td> <button type="submit" onclick="mydisp(3)" class="btn btn-primary" style="width: 100px">上传</button></td>
                                  <td> </td>                                      
                                </tr>
                              </table>

						  </fieldset>
						</form>   
						
						<br/>
	                  <div id="disp3" style="display:none"><h3>上传中...</h3></div>
	                  <div id="disp4" style="display:none"><h3>上传完成</h3></div>
	                  <span style="font-size: 16px"> 上传文件类型：</span><s:property value="contentType"/><br/><br/>
	                  <span style="font-size: 16px"> 文件保存路径：</span><input type="text" name="fileDir" onchange="mydisp(4)" size="120" /><br/>

					</div>
				</div><!--/span-->


			</div><!--/row-->
  
 <!-- <hr style="filter: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 size=1>
 <b>上传图片包：</b>
	<s:form action="UploadFileAction" theme="simple"  method="post" enctype="multipart/form-data">
		压缩包：<s:file name="doc" id="pgfile" onchange="txtchange()" /><br/>
		图标：<s:file name="cov" id="cov"/><br/>
		备注：<s:textarea name="content" wrap="true"  cols="50" rows="6" /><br/>
		标签：<s:textarea name="tag" id="tagtxt" wrap="true" cols="50" rows="6" /><br/>
		<s:submit value="上传" onclick="mydisp(3)" />
	</s:form>
	<br/>
	<div id="disp3" style="display:none"><h4>上传中...</h4></div>
	<div id="disp4" style="display:none"><h4>上传完成</h4></div>
	上传文件类型：<s:property value="contentType"/><br/>
	文件保存路径:<input type="text" name="fileDir" onchange="mydisp(4)" size="120" /><br/>
 -->
  
  <!-- transition / effect library -->
	<script src="js/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script src="js/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script src="js/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script src="js/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script src="js/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script src="js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="js/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script src="js/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script src="js/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<script src="js/bootstrap-collapse.js"></script>
	<!-- carousel slideshow library (optional, not used in demo) -->
	<script src="js/bootstrap-carousel.js"></script>
	<!-- autocomplete library -->
	<script src="js/bootstrap-typeahead.js"></script>
	<!-- tour library -->
	<script src="js/bootstrap-tour.js"></script>
	<!-- library for cookie management -->
	<script src="js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script src='js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script src="js/excanvas.js"></script>
	<script src="js/jquery.flot.min.js"></script>
	<script src="js/jquery.flot.pie.min.js"></script>
	<script src="js/jquery.flot.stack.js"></script>
	<script src="js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script src="js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script src="js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script src="js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script src="js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script src="js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="js/charisma.js"></script>
 
  </body>
</html>
