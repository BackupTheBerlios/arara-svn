<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:set var="col1" value="${10}"/>
<c:set var="col2" value="${15}"/>
<c:set var="col3" value="${75}"/>

<form method="post" action="<c:url value="/servlet/login"/>"><input
	type=hidden name="nextResource" value="${nextResource}">

<br><br>
<table align="center" class="formBorder" width="40%" border="0" cellspacing="2">
	<tr height="10" bgcolor="#000000">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr align="center">
		<td width="${col1}"></td>
		<td align="left" width="${col2}"><fmt:message key="user.login" /></td>
		<td align="left" width="${col3}"><input type="text" name="login" value="" size="16"
			maxlength="16"></td>
	</tr>
	<tr align="center">
		<td width="${col1}"></td>
		<td align="left" width="${col2}"><fmt:message key="user.password" /></td>
		<td align="left" width="${col3}"><input type="password" name="password" value=""
			size="16" maxlength="16"></td>
	</tr>
	<tr align="center">
		<td width="${col1}"></td>
		<td align="left" width="${col2}"></td>
		<td align="left" width="${col3}">
			<input type="SUBMIT" value="<fmt:message key="user.login.submit"/>">
		</td>
	</tr>
	<tr align="center">
		<td width="${col1}"></td>
		<td align="left" colspan="2" width="90%"><fmt:message key="user.login.password.msg" /><a
			href="<c:url value="/jsp/user/forgotPassword.jsp"/>"><fmt:message
			key="user.login.password.msg.link" /></a></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
</table>

<p>&nbsp;</p>
</form>
