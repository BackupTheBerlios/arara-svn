<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<br><br>
<form name="showPhotosByUserForm" method="get"
	action="<c:url value="${servletToCall}"/>">

<c:import url="/jsp/common/display/hiddenFields.jsp"/>

<table align="center" class="formBorder"  width="60%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td></td>
	</tr>
	<tr align="center">
		<td width="10%">
			<br><b><fmt:message key="user.title" /></b>
			<select name="id">
				<c:forEach items="${userList}" var="userBean">
					<option value="${userBean.value}">${userBean.label}</option>
				</c:forEach>
			</select>
			<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
			<br>&nbsp;
		</td>
	</tr>
</table>
</form>
