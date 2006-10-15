<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<form name="showPhotosByUserForm" method="get"
	action="<c:url value="${servletToCall}?action=${action}"/>">

<table width="100%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr>
		<td width="10%">
			<b><fmt:message key="user.title" /></b>
			<select name="id">
				<c:forEach items="${userList}" var="userBean">
					<option value="${userBean.value}">${userBean.label}</option>
				</c:forEach>
			</select>
			<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
		</td>
	</tr>
</table>
</form>
