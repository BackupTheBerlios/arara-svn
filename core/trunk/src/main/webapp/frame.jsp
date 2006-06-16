<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<html>
<head>
<title>Aves do Brasil</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body bgcolor="#FFFFFF">

<!-- defines the maximum width for an image -->
<c:if test="${width == null}">
	<c:set var="width" value="600" scope="application" />
</c:if>

<c:choose>
	<c:when test="${param.pageToShow == null || param.pageToShow == ''}">
		<fmt:message key="main.page" var="pageToShow" />
		<c:set var="page" value="${pageToShow}" />
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

<table width="100%" border="0" cellspacing="2" height="50%"
	bgcolor="#A6D2D2">
	<tr bgcolor="#005500">
		<td colspan="2" height="38"><c:import url="/title.jsp" /></td>
	</tr>
	<tr>
		<td width="17%" align="left" valign="top" bgcolor="#339966">
		<table height="100%" border="0">
			<tr valign="top" height="90%">
				<td><c:import url="/menu.jsp" /></td>
			</tr>
			<tr valign="bottom" height="10%">
				<td><font size="1" face="Verdana"> <b><fmt:message key="stat.users" />:
				${f:numberOfUsers()}</b><br>
				<b><fmt:message key="stat.photos" /> : ${f:numberOfPhotos()}</b><br>
				<b><fmt:message key="family.with.photos" /> :
				${f:numberOfFamilies()}</b><br>
				<b><fmt:message key="species.with.photos" /> :
				${f:numberOfSpecies()}</b><br>
				<b><fmt:message key="users.on.line" /> : ${usersOnLine}</b><br>
				<br>
				<br>
				</font></td>
			</tr>
		</table>
		</td>
		<td valign="top" width="83%"><c:import url="/jsp/showErrors.jsp" /> <c:import
			url="/jsp/showMessages.jsp" /> <c:import url="${page}" /></td>
	</tr>
	<tr bgcolor="#005500">
		<td colspan="2"><c:import url="/footer.jsp" /></td>
	</tr>
</table>
</body>
</html>
