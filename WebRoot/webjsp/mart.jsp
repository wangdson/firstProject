<%@ page language="java" import="java.util.*,com.dxs.Util.*" pageEncoding="UTF-8"%>
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>安装统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="webjsp/styles.css">
	
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
	 $(document).ready(function(){
	    $("select#pageNo").val(${pageNo});
    	$("select#pageSize").val(${pageSize});
    	var no = ${pageNo};
     	if (no<=1)
	   {
	     $("td.pre").addClass("disabled");
	     if (no >= ${totalPage})
	     {
	       $("td.next").addClass("disabled");
	     }
	     
	     $("a.next").click(function()
	     {
	        var pageNo = ${pageNo};
	        pageNo++;
	        $("select#pageNo").val(pageNo);
	        window.document.form1.submit();
	     });
	   }
	   else if (no >= ${totalPage})
	   {
	     $("select#pageNo").val(${totalPage});
	     $("td.pre").removeClass("disabled");
	     $("td.next").addClass("disabled");
	     $("a.prev").click(function()
	    {
	        var pageNo = ${pageNo};
	        pageNo--;
	        $("select#pageNo").val(pageNo);
	        window.document.form1.submit();
	    });
	   }
	   else
	   {
	      $("td.pre").removeClass("disabled");
	      $("td.next").removeClass("disabled");
	      $("a.next").click(function()
	     {
	        var pageNo = ${pageNo};
	        pageNo++;
	        $("select#pageNo").val(pageNo);
	        window.document.form1.submit();
	     });
	     $("a.prev").click(function()
	    {
	        var pageNo = ${pageNo};
	        pageNo--;
	        $("select#pageNo").val(pageNo);
	        window.document.form1.submit();
	    });
	   }
     
	$("a#query").click(function()
	{
	   window.document.form1.submit();
	});
	$("select").change(function()
	{
	   window.document.form1.submit();
	});
	$("#all").click(function()
	{
	   var checked=$("#all").attr("checked");
	    
	   if (checked == "checked")
	   {
	      $("div#uniform-userId span").addClass("checked");
	   }
	   else
	   {
	      $("div#uniform-userId span").removeClass("checked");
	   }
	   
	   
	});
	 });
	
	
	</script>
<s:head/>
<sx:head/>
  </head>
  
  <body>
  <p style="font-size: 10px;font-weight: bold;">
			<div class="row-fluid">		
				<div class="box span12">
				<form name="form1" action="MartAnalyse.action" method="post">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>安装统计管理</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<br/>
					<span style="font-size: 16px;margin-left: 20px">imei用户量统计：</span>  <font color="red"><s:property value="imei" />
					</font><br/>
					<br/>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable" >
						  <thead>
							  <tr>
								  <th>书名</th>
								  <th>市场0</th>
								  <th>市场1</th>
								  <th>市场2</th>
								  <th>市场3</th>
								  <th>市场4</th>
								  <th>市场5</th>
								  <th>合计</th>
							</tr>
						  </thead>   
						  <tbody>
						    <s:iterator value="mar" status="st">
							<tr>
								<td class="center"><s:property value="bagName" /></td>
									<s:if test="%{c00>0}">
										<td><font color="#0000ff"><s:property value="c00" />
										</font>
										</td>
									</s:if>
									<s:else>
										<td><s:property value="c00" />
										</td>
									</s:else>
									<s:if test="%{c01>0}">
										<td><font color="#0000ff"><s:property value="c01" />
										</font>
										</td>
									</s:if>
									<s:else>
										<td><s:property value="c01" />
										</td>
									</s:else>
									<s:if test="%{c02>0}">
										<td><font color="#0000ff"><s:property value="c02" />
										</font>
										</td>
									</s:if>
									<s:else>
										<td><s:property value="c02" />
										</td>
									</s:else>
									<s:if test="%{c03>0}">
										<td><font color="#0000ff"><s:property value="c03" />
										</font>
										</td>
									</s:if>
									<s:else>
										<td><s:property value="c03" />
										</td>
									</s:else>
									<s:if test="%{c04>0}">
										<td><font color="#0000ff"><s:property value="c04" />
										</font>
										</td>
									</s:if>
									<s:else>
										<td><s:property value="c04" />
										</td>
									</s:else>
									<s:if test="%{c05>0}">
										<td><font color="#0000ff"><s:property value="c05" />
										</font>
										</td>
									</s:if>
									<s:else>
										<td><s:property value="c05" />
										</td>
									</s:else>
									<s:if test="%{total>50}">
										<td><font color="#ff0000"><s:property
													value="total" />
										</font>
										</td>
									</s:if>
									<s:else>
										<td><s:property value="total" />
										</td>
									</s:else>
								</tr>
							</s:iterator>
						  </tbody>
					  </table>
					  
					</div>
					
					</form>
				</div><!--/span-->
				
			
			</div><!--/row-->
  
  <!--
  <hr style="filter: progid:DXImageTransform.Microsoft.Shadow(color:#987cb9,direction:145,strength:15)" width="100%" color=#987cb9 size=1>
imei用户量统计：  <s:property value="imei" /><br/>

    <table id="customers">
  <tr>
    <th>书名</th>
    <th>市场0</th>
    <th>市场1</th>
    <th>市场2</th>
    <th>市场3</th>
    <th>市场4</th>
    <th>市场5</th>
    <th>合计</th>
  </tr>
  <s:iterator value="mar" status="st">
  <tr>
    <td><s:property value="bagName" /></td>
    <s:if test="%{c00>0}">
    <td><font color="#0000ff"><s:property value="c00"/></font></td>
    </s:if><s:else>
    <td><s:property value="c00"/></td>
    </s:else>
    <s:if test="%{c01>0}">
    <td><font color="#0000ff"><s:property value="c01"/></font></td>
    </s:if><s:else>
    <td><s:property value="c01"/></td>
    </s:else>
    <s:if test="%{c02>0}">
    <td><font color="#0000ff"><s:property value="c02"/></font></td>
    </s:if><s:else>
    <td><s:property value="c02"/></td>
    </s:else>
    <s:if test="%{c03>0}">
    <td><font color="#0000ff"><s:property value="c03"/></font></td>
    </s:if><s:else>
    <td><s:property value="c03"/></td>
    </s:else>
    <s:if test="%{c04>0}">
    <td><font color="#0000ff"><s:property value="c04"/></font></td>
    </s:if><s:else>
    <td><s:property value="c04"/></td>
    </s:else>
    <s:if test="%{c05>0}">
    <td><font color="#0000ff"><s:property value="c05"/></font></td>
    </s:if><s:else>
    <td><s:property value="c05"/></td>
    </s:else>
    <s:if test="%{total>50}">
    <td><font color="#ff0000"><s:property value="total" /></font></td>
    </s:if><s:else>
    <td><s:property value="total" /></td>
    </s:else>
  </tr>
  </s:iterator>
</table>
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
