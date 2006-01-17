<%@ taglib uri="/core" prefix="c"%>

<html>
<head>
<title>Aves do Brasil</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF">

<!-- defines the maximum width for an image -->
<c:if test="${width == null}">
  <c:set var="width" value="600" scope="application"/>
</c:if>

<c:choose>
	<c:when test="${param.pageToShow == null || param.pageToShow == ''}">
	  <c:set var="pageToShow" value="/main.jsp"/>
	</c:when>
	<c:otherwise>	
	  <c:set var="pageToShow" value="${param.pageToShow}"/>
	</c:otherwise>	
</c:choose>

<table width="100%" border="0" cellspacing="2" height="50%" bgcolor="#A6D2D2">
  <tr bgcolor="#005500"> 
    <td colspan="2" height="38"> 
    	<c:import url="/title.jsp"/>
    </td>
  </tr>
  <tr> 
    <td width="17%" align="left" valign="top" bgcolor="#339966"> 
        <c:import url="/menu.jsp"/>
    </td>
    <td width="83%"> 
        <c:import url="/jsp/showErrors.jsp"/>  
        <c:import url="/jsp/showMessages.jsp"/>  
    	  <c:import url="${pageToShow}"/>
    </td>
  </tr>
  <tr bgcolor="#005500"> 
    <td colspan="2">
	    <c:import url="/footer.jsp"/>
    </td>
  </tr>
</table>
</body>
</html>
