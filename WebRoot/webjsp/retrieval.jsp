<%@ page language="java" import="java.util.*,com.dxs.Entity.*,com.dxs.Util.*,com.dxs.Service.Impl.*,com.dxs.Service.Intf.*,com.opensymphony.xwork2.ActionContext" pageEncoding="UTF-8"%>
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>检索统计</title>

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
<s:head/>
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
	        document.form1.action="RetrievalViewAction";
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
	        document.form1.action="RetrievalViewAction";
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
	        document.form1.action="RetrievalViewAction";
	        window.document.form1.submit();
	     });
	     $("a.prev").click(function()
	    {
	        var pageNo = ${pageNo};
	        pageNo--;
	        $("select#pageNo").val(pageNo);
	        document.form1.action="RetrievalViewAction";
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
	
	 $("#modRetrieval").modal("show");
     $("div#msg").fadeOut(3000);
	 });
	</script>
	</head>

  <%
     List<Retrieval> retrievalList=(List<Retrieval>)session.getAttribute("retrievalList");
	
	%>
	<body>
	<c:if test="${result ne null }">
	<div id="msg" class="popover-inner" style="margin-left: 300px">
		<h3 class="popover-title">操作结果</h3>
		<div class="popover-content">
			<p align="center" style="border:1px solid #43a1da;display: block; background-color: #43a1da;color: white;">
				操作成功！<br>
			<p>
		</div>
	</div>
	</c:if>
	<p style="font-size: 10px;font-weight: bold;">
			<div class="row-fluid">		
				<div class="box span12">
				<form id="form1" name="form1" action="RetrievalViewAction" method="post">
				
				<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>检索统计</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable" >
						  <thead>
							  <tr>
								<th>检索字</th>
								<th>检索次数</th>
								<th>最后检索时间</th>
								<th>手机显示/用户检索</th>
							</tr>
						  </thead>   
						  <tbody>
							<%
							    Integer pageNo = (Integer)session.getAttribute("pageNo");
							    Integer pageSize = (Integer)session.getAttribute("pageSize");
							    for (int i = 0; i < retrievalList.size(); i++)
							    {
							        Retrieval retrieval = (Retrieval)retrievalList.get(i);
							%>
							<tr>
								<td>
									<%
									    out.print("<a href='EditRecommendAction?retrievalId=" + retrieval.getRetrievalId() + "&flag=fromEdit&pageNo="+pageNo+"&pageSize="+pageSize+"'>"
									            + retrieval.getUserKeyWord() + "</a>");
									%>
								</td>
								<td><%=retrieval.getNumber()%></td>
								<td><%=retrieval.getSearchTime()%></td>
								<td>
									<%
									    out.print("<a href='EditRecommendAction?retrievalId=" + retrieval.getRetrievalId() + "&flag=fromEdit&pageNo="+pageNo+"&pageSize="+pageSize+"'>"
									            + retrieval.getCate() + "</a>");
									%>
								</td>
							</tr>
							<%} %>
						</tbody>
					  </table>
					  
					 <%
					   Retrieval ret = (Retrieval)session.getAttribute("ret");
					 %>
		<c:if test="<%=ret!=null %>">			 
		<!-- 弹出框 -->
		<div class="modal hide fade" id="modRetrieval">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>修改检索字</h3>
			</div>
			<div class="modal-body">
				<div class="row-fluid sortable">
				<div class="box span12">
					<div class="box-content">
							<br />
							<table>
								<tr>
									<td>检索字:</td>
									<td><input id="userKeyWord" type="text" name="userKeyWord"
										value="<%=ret.getUserKeyWord() %>" />
									</td>
								</tr>
								<tr>
									<td>手机显示/用户检索:</td>
									<td><input id="cate" type="text" name="cate" value="<%=ret.getCate() %>" />
									</td>
								</tr>
								<tr>
									<td><input id="mod"
										onclick="javascript:document.form1.action='EditRecommendAction?flag=toEdit';window.document.form1.submit();"
										type="submit" value="确认修改" class="btn btn-primary"
										style="width: 100px" />
									</td>
								</tr>
							</table>

					</div>
				</div><!--/span-->
			</div>
			</div>
			</div>
			</c:if>
					  
					  <div class="span12 center">
					   <div class="dataTables_paginate paging_bootstrap pagination">
					   <table style="margin-left: 200px;">
					     <tr>
					       <td class="pre disabled">
					        <a href="#"  class="prev">上一页</a>
					        </td>
					        <td>
			                   | 当前页：${pageNo }
					        </td>
					        <td>
			                   | 总数：${count }
					        </td>
					        <td >
			                   | 跳转至 <select name="pageNo" id="pageNo" style="width: 60px">
			                   <c:forEach begin="1" var="num" end="${totalPage }">
					           <option value="${num }" >${num}</option>
					           </c:forEach>
					        </select>页
					        </td>
					        <td>
					          | 每页
					        <select name="pageSize" id="pageSize" style="width: 60px">
					           <option value="10">10</option>
					           <option value="25">25</option>
					           <option value="50">50</option>
					           <option value="100">100</option>
					        </select>
					                          条记录 |
					        </td>
					        <td class="next">
					        <a href="#" class="next">下一页</a>
					        </td>
					     </tr>
					   </table>
					   </div>
					</div>
					  
					</div>
					
					</form>

				</div><!--/span-->
				
			
			</div><!--/row-->
	
	
	
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
