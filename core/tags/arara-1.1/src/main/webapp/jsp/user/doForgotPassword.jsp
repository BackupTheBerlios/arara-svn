<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<c:set var="col1" value="${5}%"/>
<c:set var="col2" value="${30}%"/>
<c:set var="col3" value="${65}%"/>

<form method="post" action="<c:url value="/servlet/sendPassword"/>">

<br><br>
<table align="center" class="formBorder" width="70%" border="0" cellspacing="2">
	<tr height="10" bgcolor="#000000">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
		<td width="${col1}"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.forgot.password.login" />
		</td>
		<td align="left" width="${col3}%">
			<input type="text" name="login" size="50" maxlength="128">
		</td>
	</tr>
	<tr>
		<td width="${col1}"></td>
		<td align="left" width="${col2}%"></td>
		<td align="left" width="${col3}%">
			<input type="SUBMIT" value="<fmt:message key="user.login.submit"/>">
		</td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
</table>
</form>
