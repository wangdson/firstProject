<%@ page language="java" import="java.util.*,com.dxs.Entity.*,com.dxs.Service.Impl.*,com.dxs.Service.Intf.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登陆页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<!-- The styles -->
	<link id="bs-css" href="css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	<link href="css/charisma-app.css" rel="stylesheet">
	<link href="css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
	<link href='css/uniform.default.css' rel='stylesheet'>
	<link href='css/jquery.iphone.toggle.css' rel='stylesheet'>
	<link href='css/opa-icons.css' rel='stylesheet'>

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<!-- The fav icon -->
	<link rel="shortcut icon" href="img/favicon.ico">
	
		<!-- jQuery -->
	<script src="js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="js/jquery-ui-1.8.21.custom.min.js"></script>
	
	<script type="text/javascript">
	    $(document).ready(function(){
	       $("button.btn").click(function(){
	          var username=$("input#username").val();
	          var password=$("input#password").val();
	          if(username==null || username=="")
	          {
	             $("div.alert").html("<font color='red'>用户名不能为空，请输入用户名！</font>");
	          }
	          else if(password==null || password=="")
	          {
	             $("div.alert").html("<font color='red'>密码不能为空，请输入密码！</font>");
	          }
	          else
	          {
	             loadXML();
	             $("form#form1").submit();
	          }
	          
	       });
	    });
	    
	    function loadXML()
       {
           var xmlhttp;
           if(window.XMLHttpRequest)
           {
              xmlhttp=new XMLHttpRequest();
           }
           else
           {
             xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
           }
           xmlhttp.onreadystatechange=function()
           {
              if (xmlhttp.readyState==4 && xmlhttp.status==200)
              { 
                 document.getElementById("div1").innerHTML=xmlhttp.responseText;
              }
           }
           xmlhttp.open("POST","MyLogin!checkUser.action",true);
           xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
           xmlhttp.send("logName="+document.getElementById("username").value);
       }
	    
	    
	</script>
<s:head/>
<sx:head/>
  </head>
  
  <body>
   
   <div class="container-fluid">
		<div class="row-fluid">
		
			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>图片包管理平台系统</h2>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info" id="div1">
						请输入用户名和密码登陆。
					</div>
					<form class="form-horizontal" id="form1" action="MyLogin" method="post">
						<fieldset>
							<div class="input-prepend" title="用户名" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10" name="logName" id="username" type="text"/>
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="密码" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span><input class="input-large span10" name="psw" id="password" type="password"  />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend">
							<label class="remember" for="remember"><input type="checkbox" id="remember" />Remember me</label>
							</div>
							<div class="clearfix"></div>

							<p class="center span5">
							<button type="button" class="btn btn-primary">登陆</button>
							</p>
						</fieldset>
					</form>
					<s:property value="msg" />
				</div><!--/span-->
			</div><!--/row-->
				</div><!--/fluid-row-->
   </div><!--/.fluid-container-->

	<!-- external javascript
	================================================== -->

	<!-- library for cookie management -->
	<script src="js/jquery.cookie.js"></script>

	<!-- checkbox, radio, and file input styler -->
	<script src="js/jquery.uniform.min.js"></script>
	<!-- rich text editor library -->
	<script src="js/jquery.cleditor.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="js/jquery.iphone.toggle.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="js/charisma.js"></script>
	
  </body>
</html>
