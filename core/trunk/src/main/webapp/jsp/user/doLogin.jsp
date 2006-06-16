<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<form method="post" action="<c:url value="/servlet/login"/>"><input
	type=hidden name="nextResource" value="${nextResource}">

<table width="30%" border="0" cellspacing="2" bgcolor="#A6D2D2"
	align="center">
	<tr>
		<td width="17%"><fmt:message key="user.login" /></td>
		<td width="83%"><input type="text" name="login" value="" size="16"
			maxlength="16"></td>
	</tr>
	<tr>
		<td width="17%"><fmt:message key="user.password" /></td>
		<td width="83%"><input type="password" name="password" value=""
			size="16" maxlength="16"></td>
	</tr>
	<tr>
		<td width="17%"></td>
		<td width="83%"><input type="SUBMIT"
			value="<fmt:message key="user.login.submit"/>"></td>
	</tr>
</table>
<table width="30%" border="0" cellspacing="2" bgcolor="#A6D2D2"
	align="center">
	<tr>
		<td width="100%"><fmt:message key="user.login.password.msg" /><a
			href="<c:url value="/jsp/user/forgotPassword.jsp"/>"><fmt:message
			key="user.login.password.msg.link" /></a></td>
	</tr>
</table>

<p>&nbsp;</p>
</form>
