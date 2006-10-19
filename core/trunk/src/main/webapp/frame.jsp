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
<meta name="launchDate" content="01/09/2005" />
<meta name="category" content="homepage" />
<meta name="type" content="network" />
<title>Aves do Brasil</title>
<script type="text/javascript">
	console.log("%d ways to skin a cat.", 101);
	var djConfig = {isDebug: true, debugAtAllCosts: true,baseRelativePath:"<c:url value="/"/>"};
</script>
<script type="text/javascript" src="<c:url value="/dojo-core.js"/>"></script>
<script type="text/javascript" src="<c:url value="/prototype.js"/>"></script>
<script type="text/javascript" src="<c:url value="/rico.js"/>"></script>

<script language="javascript">
function change(source){
	if(document.getElementById("alerta"+quem+"").className=='hidden') {
		document.getElementById("alerta"+quem+"").className='show';
	} else {
		document.getElementById("alerta"+quem+"").className='hidden';
	};
}
</script>


<!-- 
	var djConfig = {isDebug: true, debugAtAllCosts: true,baseRelativePath:"<c:url value="/"/>"};
<script type="text/javascript" src="dojo-minimal.js"></script>
<script type="text/javascript" src="dojo-ajax.js"></script>
<script type="text/javascript" src="dojo-widget.js"></script>
    dojo.setModulePrefix("dojo", "<c:url value="/src"/>");

	baseScriptUri:'<c:url value="/"/>',     
	baseRelativePath: "<c:url value="/src"/>",
	
    dojo.setModulePrefix("dojo", "<c:url value="/src"/>");	
-->
<script language="JavaScript" type="text/javascript">
	dojo.require("dojo.widget.*");
	dojo.require("dojo.widget.SlideShow");	
	dojo.require("dojo.widget.FisheyeList");
	dojo.hostenv.writeIncludes();
</script>

<script>
	function load_app(id){
		document.location.assign('<c:url value="/"/>'+id);		
	}
</script>

<script type="text/javascript">
  new Ajax.PeriodicalUpdater("statistics", "<c:url value="/statistics.jsp"/>", 
	{ // initial number of seconds interval between calls 
	 frequency : 30, 
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

<c:set var="mainBgColor" value="#669900" scope="application" />

<c:choose>
	<c:when test="${param.pageToShow == null || param.pageToShow == ''}">
		<c:if test="${pageToShow == null || pageToShow == ''}">
			<fmt:message key="main.page" var="mainPage" />
			<c:set var="page" value="${mainPage}" />
		</c:if>
		<c:if test="${pageToShow != null}">
			<c:set var="page" value="${pageToShow}" />
		</c:if>
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

<table width="98%" border="0" align="center" cellspacing="1">
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
    <td width="17%" align="left" valign="top">
    	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#669900">
      		<tr>
        		<td align="left">
        			<c:set var="contextPath" value="<%=request.getContextPath()%>" />
        		
					<img dojoType="SlideShow" 
					imgUrls="${f:getSlideShow(contextPath)}" 					
					transitionInterval="1000"
					delay="30000" 					
					src="<c:url value="/images/arara.jpg"/>"
					imgWidth="150" imgHeight="115" />	
					<hr>		
			        <c:import url="/menu.jsp" />		
					<hr>	
					<img src="<c:url value="/images/chart.png"/>" alt="" width="20" height="20"><b><fmt:message key="menu.statistics.statistics"/></b><br/>
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
      <td colspan="2" valign="top" width="83%" height="100%" bgcolor="#669900">
		<!-- 
    	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#669900">
      		<tr>
        		<td>
		 -->        		
        			<c:import url="/jsp/showErrors.jsp" /> 
				    <c:import url="/jsp/showMessages.jsp" /> 
				    <c:import url="${page}" />        
		<!-- 
		        </td>
      		</tr>
    	</table>
		 -->    	
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
