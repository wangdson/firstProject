<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>模板页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

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
     
 <script type="text/javascript">
  $(document).ready(function()
  {
     $("a.ajax-link").click(function(){
     $("a#child").html($(this).text());
     $("iframe#ifmContent").attr("height","600px");
     });
     $("a.color1").click(function(){
       
     });
  });
  
function iFrameHeight(me) 
{ 
   var ifm= document.getElementById(me); 
   var subWeb = document.frames ? document.frames[me].document : ifm.contentDocument; 
   if(ifm != null && subWeb != null) 
   { 
      ifm.height = subWeb.body.scrollHeight;
   } 
}
	</script>
  
  </head>
  
  <body>
    <!-- topbar starts -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="index.html"> <img alt="Charisma Logo" src="<%=basePath%>img/logo20.png" /> <span>My Web Design</span></a>
				
				<!-- theme selector starts -->
				<div class="btn-group pull-right theme-container" >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-tint"></i><span class="hidden-phone"> 修改主题 /皮肤</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" id="themes">
						<li><a class="color1" data-value="classic" href="#"><i class="icon-blank"></i> 经典</a></li>
						<li><a class="color1" data-value="cerulean" href="#"><i class="icon-blank"></i> 天蓝色</a></li>
						<li><a class="color1" data-value="cyborg" href="#"><i class="icon-blank"></i> 赛博格</a></li>
						<li><a class="color1" data-value="redy" href="#"><i class="icon-blank"></i> Redy</a></li>
						<li><a class="color1" data-value="journal" href="#"><i class="icon-blank"></i> 日志</a></li>
						<li><a class="color1" data-value="simplex" href="#"><i class="icon-blank"></i> 简单</a></li>
						<li><a class="color1" data-value="slate" href="#"><i class="icon-blank"></i> 石板</a></li>
						<li><a class="color1" data-value="spacelab" href="#"><i class="icon-blank"></i> Spacelab</a></li>
						<li><a class="color1" data-value="united" href="#"><i class="icon-blank"></i> United</a></li>
					</ul>
				</div>
				<!-- theme selector ends -->
				
				<!-- user dropdown starts -->
				<div class="btn-group pull-right" >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone"> admin</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">详细信息</a></li>
						<li class="divider"></li>
						<li><a href="webjsp/mylogin.jsp">退出</a></li>
					</ul>
				</div>
				
				<div class="top-nav nav-collapse">
					<ul class="nav">
					</ul>
				</div><!--/.nav-collapse -->
			</div>
		</div>
	</div>
	
	<!-- left menu starts -->
			<div class="span2 main-menu-span">
				<div class="well nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li id="menu" class="nav-header hidden-tablet">图片操作</li>
						<li><a class="ajax-link" href="PaperbagViewAction" target="ifmContent"><i class="icon-home"></i><span class="hidden-tablet"> 图片包管理</span></a></li>
						<li><a class="ajax-link" href="webjsp/Upload.jsp" target="ifmContent"><i class="icon-eye-open"></i><span class="hidden-tablet"> 上载图片</span></a></li>
						<li><a class="ajax-link" href="ClassificationAction" target="ifmContent"><i class="icon-edit"></i><span class="hidden-tablet"> 分类管理</span></a></li>
						<li><a class="ajax-link" href="TagMangerAction" target="ifmContent"><i class="icon-list-alt"></i><span class="hidden-tablet"> 标签管理</span></a></li>
						<li><a class="ajax-link" href="TagShowAction!showTagBook" target="ifmContent"><i class="icon-font"></i><span class="hidden-tablet"> 标签、类别查看</span></a></li>
						<li><a class="ajax-link" href="PushMsgViewAction" target="ifmContent"><i class="icon-picture"></i><span class="hidden-tablet"> 消息管理</span></a></li>
						<li><a class="ajax-link" href="RetrievalViewAction" target="ifmContent"><i class="icon-star"></i><span class="hidden-tablet"> 检索统计</span></a></li>
						<li><a class="ajax-link" href="MartAnalyse" target="ifmContent"><i class="icon-ban-circle"></i><span class="hidden-tablet"> 安装统计</span></a></li>
						
						<li id="menu" class="nav-header hidden-tablet">图片查询</li>
						<li><a class="ajax-link" href="GetPaperViewAction?flag=new" target="ifmContent"><i class="icon-align-justify"></i><span class="hidden-tablet"> 最新（上架时间）</span></a></li>
						<li><a class="ajax-link" href="GetPaperViewAction?flag=hot" target="ifmContent"><i class="icon-calendar"></i><span class="hidden-tablet"> 排行（安装量）</span></a></li>
						<li><a class="ajax-link" href="GetPaperViewAction?flag=rec" target="ifmContent"><i class="icon-th"></i><span class="hidden-tablet"> 推荐（推荐时间）</span></a></li>
						
						<li id="menu" class="nav-header hidden-tablet">其他</li>
						<li><a class="ajax-link" href="ACTime!execute" target="ifmContent"><i class="icon-folder-open"></i><span class="hidden-tablet">自动执行</span></a></li>
						<li><a href="ModifyAppVersion" target="ifmContent"><i class="icon-globe"></i><span class="hidden-tablet"> 更改版本号</span></a></li>
					</ul>
					<label id="for-is-ajax" class="hidden-tablet" for="is-ajax"><input id="is-ajax" type="checkbox"> Ajax on menu</label>
				</div><!--/.well -->
			</div><!--/span-->
			<!-- left menu ends -->
			
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			<div id="content" class="span11">
			<!-- content starts -->
			

			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#" id="parent">图片操作</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#" id="child">图片包管理</a>
					</li>
				</ul>
			</div>
			<div id="content">
             <iframe id="ifmContent" name="ifmContent" src="PaperbagViewAction"  frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no"  onload="javascript:iFrameHeight('ifmContent');" width="100%"></iframe>
            </div>
            <footer id="test">
			<p class="pull-left">&copy; <a href="#" target="_blank">isoftstone</a> 2014</p>
			<p class="pull-right">Powered by: <a href="#">wang</a></p>
		    </footer>
            </div>
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
