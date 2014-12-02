<%@ page language="java" import="java.util.*,java.io.*,com.dxs.Util.*,com.dxs.Entity.*,com.dxs.Service.Impl.*,com.dxs.Service.Intf.*,com.opensymphony.xwork2.ActionContext" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://www.wangpei.com/test/functions" %>
<%
	if(!CheckUser.ck_class())
	{
		response.sendRedirect("webjsp/mylogin.jsp");
	}
%>	
<%
	
   	PaperBagService service=new PaperBagServiceImpl();
    List<PaperBag> sbList=service.getAllPaperBag();
	XmlOut xout=new XmlOut();
		try 
		{
			xout.BuildIsbn2PG(sbList);
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	int total=service.findbagCount();
	int subtotal=service.findstandCount();
	int rectotal=service.findRecCount();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'books.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="webjsp/styles.css">
	<link id="bs-css" href="<%=basePath%>css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body 
	  {
		padding-bottom: 40px;
	  }
	  .sidebar-nav 
	  {
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
	
	$('.btn-advance').click(function(e){
		$("div#advance").show();
		$('.btn-advance').hide();
	});
	
	$('.btn-low').click(function(e){
		$("div#advance").hide();
		$('.btn-advance').show();
	});
	$("input#all").click(function()
	{
	   var checked=$("input#all").attr("checked");
	    
	   if (checked == "checked")
	   {
	      $("div.checker span").addClass("checked");
	   }
	   else
	   {
	      $("div.checker span").removeClass("checked");
	   }
	});
});
	
	</script>
<s:head/>
<sx:head/>
<script type="text/javascript" language="javascript">


function transferForm(me)
{
	tf=document.forms[0];
	tf.action=me;
	tf.submit();
}

function checkAll(obj,id)
{
	var check=document.getElementsByName(id);
	
	for(i=0;i<check.length;i++)
	{
		check[i].checked=obj.checked;
	}
}
	function change(pa)
	{
		var ep=document.getElementById("epubVer");
		var ap=document.getElementById("apkVer");
		var linep=document.getElementById("eplink");
		var linap=document.getElementById("aplink");
		var bb=document.getElementById("hidEpub");
		var cc=document.getElementById("hidApk");
		if(pa==1)
		{
			alert("ModifyVersion?id="+bb.value+"&epubVer="+ep.value+"&apkVer="+ap.value);
			location.href="ModifyVersion?id="+bb.value+"&epubVer="+ep.value+"&apkVer="+ap.value;
		}
		
		if(pa==2)
		{
			location.href="ModifyVersion?id="+cc.value+"&epubVer="+ep.value+"&apkVer="+ap.value;
		}
	}
	
	function verr()
	{
		location.href="BooksAction";
	}
	
	function pagechange()
	{
		var tonum=document.getElementById("tonum");
		tonum.value="1";
	}

</script>

  </head>  
  <body >
  <p style="font-size: 10px;font-weight: bold;">
  <font size="2px">当前内核版本为：<%=ActionContext.getContext().getSession().get("appver") %> | 总量：<%=total %> | 上架量：<%=subtotal %> | 推荐量：<%=rectotal%>
  <br/>
  <!-- 进度:<s:textarea name="res" wrap="true" cols="70" rows="8"/> --></font>		
			<div class="row-fluid">		
				<div class="box span12">
				<form name="form1" action="PaperbagViewAction" method="post">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i> 图片包管理</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
					 <div>
					  <div align="left" style="float: left;">
					    ID : <input type="text" name="idQuery" style="width: 200px;height: 23px;"/>
					    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					     <a class="btn btn-small btn-primary btn-add" style="width: 50px;">查询</a>
					  </div>
					  <div align="right" style="float: right;">
					   <a class="btn btn-small btn-primary btn-add" onclick="transferForm('CKAction!upBooks');">上架</a>
					   <a class="btn btn-small btn-primary btn-add" onclick="transferForm('CKAction!downBooks');">下架</a>
					   <a class="btn btn-small btn-primary btn-add" onclick="transferForm('CompileAction');">编译</a>
					   <a class="btn btn-small btn-primary btn-add" onclick="transferForm('CKAction!downlo');">打包</a>
					   <a class="btn btn-small btn-primary btn-add" onclick="transferForm('PaperbagViewAction');">刷新编译进度</a>
					   
					   <br/><a href="#" class="btn-advance">>>>高级</a>
						</div>
						<div id="advance" align="right" style="display: none;float: right;">
					     <br/>
					      <a class="btn btn-small btn-primary btn-add" href="DownLoadWallpaperPackageAction">批量下载</a>
					      <a class="btn btn-small btn-primary btn-add" onclick="transferForm('UpdateSver');">资源版本批量+1</a>
					      <a class="btn btn-small btn-primary btn-add" onclick="transferForm('ModifyPaperRecState!ckExeJoin');">批量推荐</a>
					      <a class="btn btn-small btn-primary btn-add" onclick="transferForm('ModifyPaperRecState!ckExeCancel');">批量取消推荐</a>
					      <a class="btn btn-small btn-primary btn-add" onclick="transferForm('ShortFresh')">补齐短链</a>
					      <a class="btn btn-small btn-primary btn-add" onclick="transferForm('TagFresh')">初始化勾选项的标签</a>
					      <a class="btn btn-small btn-primary btn-add" onclick="transferForm('UnionIdFresh')">ID转世</a>
					      <br/>
					      <a href="#" class="btn-low">>>>简单操作</a>
						</div>
						</div>
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th>全选<input type="checkbox" id="all" name="allck" onclick="checkAll(this,'ck')"/></th>
								  <th>ID</th>
								  <th width="160px">包名</th>
								  <th>上架时间</th>
								  <th>上架/推荐</th>
								  <th>内核版本</th>
								  <th>资源版本</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:forEach var="item" items="${pgList }">
							<tr>
								<td><input type="checkbox"  name="ck"  value="${item.id }"/></td>
								<td class="center">${item.id }</td>
								<td class="center" width="160px">${item.bagName }</td>
								<td class="center">
									${item.uploadTime}
								</td>
								<td class="center">
								<c:if test="${item.state eq '1'}">
								<font color='#0000ff'>是</font> /
								<a href="ModifyPaperRecState?id=${item.id}&sta=${item.state_rec eq '取消推荐'?'1':'0'}">${item.state_rec}</a>
                                </c:if>
                                <c:if test="${item.state eq '0'}">
										否
                                </c:if>
                                </td> 
								<td class="center">
								    <input type="text" id="epubVer" style="width: 40px;height: 20px;" readonly="readonly" value="${fn:getSubString(item.version,0,3) }" size="2" maxlength="3"/>
								    <c:if test="${fn:getSubString(item.version,0,3) eq appver}">
								                        最新
								    </c:if>
								    <c:if test="${fn:getSubString(item.version,0,3) ne appver}">
								       <font color="red">升级</font>
								    </c:if>
								</td> 
								<td class="center">
								    <input type="text" id="apkVer" readonly="readonly" style="width: 40px;height: 20px;" value="${fn:getSubString(item.version,3,6)}" size="2" maxlength="3"/>
									<!-- <input type="text" name="sourceVersion" style="width: 40px;height: 20px;" value="100" style="width: 40px;height: 20px;" value="100"/>最新 -->
								    <c:if test="${fn:getSubString(item.version,3,6) eq item.sver}">
								                        最新
								    </c:if>
								    <c:if test="${fn:getSubString(item.version,3,6) ne item.sver}">
								       <font color="red">升级</font>
								    </c:if>
								</td> 
								<td class="center">
									<a class="btn btn-info" href="PaperEdit?id=${item.id }">
										<i class="icon-edit icon-white"></i>  
										编辑                                            
									</a>
									<a class="btn btn-danger" href="Deldata?id=${item.id }">
										<i class="icon-trash icon-white"></i> 
										删除
									</a>
									<c:if test="${item.initial eq '1'}">
									   <span class="label">编译中...</span> 
									</c:if>
									<c:if test="${item.initial eq '2'}">
									   <span class="label label-warning">等待中</span>
									</c:if>
									<c:if test="${item.initial eq '3'}">
									   <span class="label label-success">结束</span><a href="DownLoadApkPackageAction?id=${item.id}">下载</a>
									</c:if>
									<c:if test="${item.initial eq '4'}">
									<a class="btn btn-success" href="DownLoadApkPackageAction?id=${item.id}">
										<i class="icon-zoom-in icon-white"></i>  
										下载APK                                            
									</a>
									</c:if>
									<c:if test="${item.initial eq '5'}">
									  <span class="label label-important">编译出错</span>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						  </tbody>
					  </table>
					  
					  <div class="span12 center">
					   <div class="dataTables_paginate paging_bootstrap pagination">
					   <table style="margin-left: 230px;">
					     <tr>
					       <td class="pre disabled">
					        <a class="prev" href="#">上一页</a>
					        </td>
					        <td>
			                   | 当前页：${pageNo }
					        </td>
					        <td>
			                   | 总数：${tcount }
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
					        <select name="pageSize" id="pageSize" style="width: 60px" style="width: 50px">
					           <option value="10">10</option>
					           <option value="25">25</option>
					           <option value="50">50</option>
					           <option value="100">100</option>
					        </select>
					                          条记录 |
					        </td>
					        <td class="next">
					        <a class="next" href="#">下一页</a>
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

