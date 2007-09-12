<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:set var="col1" value="${5}%"/>
<c:set var="col2" value="${15}%"/>
<c:set var="col3" value="${80}%"/>

<br><br>
<table class="formBorder" width="90%" align="center" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
		<td width="${col1}"></td>
    	<td width="${col2}">
			<c:import url="/jsp/user/registrationData.jsp"/>
			<br><br>
			<b><fmt:message key="user.registered.confirmation.email" /></b>
		</td>
	</tr>
</table>
<div align="left"></div>
