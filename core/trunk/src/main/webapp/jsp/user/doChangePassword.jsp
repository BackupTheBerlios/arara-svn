<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<form method="post" action="<c:url value="/servlet/changePassword"/>">

<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
	<tr>
		<td width="15%"><fmt:message key="user.current.password" /></td>
		<td width="85%"><input type="password" name="password" size="16"
			maxlength="16"></td>
	</tr>
	<tr>
		<td width="15%"><fmt:message key="user.new.password" /></td>
		<td width="85%"><input type="password" name="newPassword" size="16"
			maxlength="16"></td>
	</tr>
	<tr>
		<td width="15%"><fmt:message key="user.new.password2" /></td>
		<td width="85%"><input type="password" name="newPassword2" size="16"
			maxlength="16"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="2" align="center"
	bgcolor="#A6D2D2">
	<tr>
		<td width="15%"></td>
		<td>
		<div align="left"><input type="SUBMIT"
			value="<fmt:message key="user.change.password.submit"/>"></div>
		</td>
	</tr>
</table>
<p>&nbsp;</p>
</form>
