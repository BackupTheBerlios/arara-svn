<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="robots" content="index, follow" />
<meta name="keywords" content="animal planet, birs, aves" />
<meta name="description" content="Welcome to the Arara Web Site." />
<meta name="network" content="Birds Planet" />
<meta name="launchDate" content="01/01/2006" />
<meta name="category" content="homepage" />
<meta name="type" content="network" />
<title>Aves do Brasil</title>
<script type="text/javascript">
	console.log("%d ways to skin a cat.", 101);
	var djConfig = {isDebug: true, debugAtAllCosts: true,baseRelativePath:"<%= request.getContextPath()%>/"};
</script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dojo-core.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/prototype.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/rico.js"></script>

<!-- 
<script type="text/javascript" src="dojo-minimal.js"></script>
<script type="text/javascript" src="dojo-ajax.js"></script>
<script type="text/javascript" src="dojo-widget.js"></script>
    dojo.setModulePrefix("dojo", "<%= request.getContextPath()%>/src");

	baseScriptUri:'<%= request.getContextPath()%>/',     
	baseRelativePath: "<%= request.getContextPath()%>/src",
-->
<script language="JavaScript" type="text/javascript">

	dojo.require("dojo.widget.*");
	dojo.require("dojo.widget.SlideShow");	
	dojo.require("dojo.widget.FisheyeList");
	dojo.hostenv.writeIncludes();
</script>

<script>
	function load_app(id){
		document.location.assign('<%= request.getContextPath()%>'+id);		
	}
</script>

<script type="text/javascript">
  new Ajax.PeriodicalUpdater("statistics", "<%= request.getContextPath()%>/statistics.jsp", 
	{ // initial number of seconds interval between calls 
	 frequency : 10, 
	 decay : 2 
	});

</script>

<style>
.dojoHtmlFisheyeListBar {
	margin: 0 auto;
	text-align: center;
}
.outerbar {
	background-color: #669900;
	text-align: center;
	width: 30%;
}
body {
	font-family: Arial, Helvetica, sans-serif;
	padding: 0;
	margin: 0;
}
.page {
	padding: 60px 20px 20px 20px;
}

</style>

</head>
<!--  #669900 top  menu#FFFF00  fundo #333300 -->
<body bgcolor="#333300">

<!-- defines the maximum width for an image -->
<c:if test="${width == null}">
	<c:set var="width" value="600" scope="application" />
</c:if>

<c:choose>
	<c:when test="${param.pageToShow == null || param.pageToShow == ''}">
		<fmt:message key="main.page" var="mainPage" />
		<c:set var="page" value="${mainPage}" />
	</c:when>
	<c:otherwise>
		<c:if test="${param.pageToShow == 'main.page'}">
			<fmt:message key="main.page" var="mainPage" />
			<c:set var="page" value="${mainPage}" />
		</c:if>
		<c:if test="${param.pageToShow != 'main.page'}">
			<c:set var="page" value="${param.pageToShow}" />
		</c:if>
	</c:otherwise>
</c:choose>

<table width="800" border="0" align="center" cellspacing="1">
  <tr>
    <td colspan="3">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#669900">
      		<tr>
        		<td>
			        <c:import url="/title.jsp" />
        		</td>
      		</tr>
    	</table>
    </td>
  </tr>
  <tr>
    <td width="150" align="left" valign="top">
    	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#669900">
      		<tr>
        		<td align="left">
					<img dojoType="SlideShow" 
					imgUrls="<%= request.getContextPath()%>/images/arara.jpg;servlet/getPhoto?photoId=852&identification=" 
					transitionInterval="700"
					delay="7000" 
					src="<%= request.getContextPath()%>/images/arara.jpg"
					imgWidth="150" imgHeight="115" />	
					<hr>		
			        <c:import url="/menu.jsp" />		
					<hr>	
					<img src="<%= request.getContextPath()%>/images/chart.png" alt="" width="20" height="20"><b><fmt:message key="menu.statistics.statistics"/></b><br/>
					<div id="statistics" style="position: relative;">
					<div id="updatesponsor">
					<p>
					<fmt:message key="menu.statistics.load"/>
					</p>
					</div>
					</div>              
        		</td>
      		</tr>
    	</table>
      </td>
      <td colspan="2" valign="top" width="650" height="100%">
    	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#669900">
      		<tr>
        		<td>
        			<c:import url="/jsp/showErrors.jsp" /> 
				    <c:import url="/jsp/showMessages.jsp" /> 
				    <c:import url="${page}" />        
		        </td>
      		</tr>
    	</table>
      </td>
  </tr>
  <tr>
    <td colspan="3">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#669900">
      		<tr>
		        <td>
		        	<c:import url="/footer.jsp" />
		        </td>
      		</tr>
		</table>      		
    </td>
  </tr>
</table>

<center><font face="Verdana" color="#ffffff" size="-1">
Copyright © 2006 Indrix.
</font></center>

</body>
</html>
