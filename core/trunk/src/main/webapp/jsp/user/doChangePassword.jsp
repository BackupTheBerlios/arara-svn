<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<c:set var="col1" value="${5}" />
<c:set var="col2" value="${35}"/>
<c:set var="col3" value="${60}"/>
<br><br>
<form method="post" action="<c:url value="/servlet/changePassword"/>">

<table align="center" class="formBorder" width="40%" border="0">
	<tr height="10" bgcolor="#000000">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.current.password" />
		</td>
		<td width="${col3}%"><input type="password" name="password" size="16"
			maxlength="16"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.new.password" />
		</td>
		<td width="${col3}%"><input type="password" name="newPassword" size="16"
			maxlength="16"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<fmt:message key="user.new.password2" />
		</td>
		<td width="${col3}%"><input type="password" name="newPassword2" size="16"
			maxlength="16"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td>
		<div align="left"><input type="SUBMIT"
			value="<fmt:message key="user.change.password.submit"/>"></div>
		</td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
</table>
<p>&nbsp;</p>
</form>
