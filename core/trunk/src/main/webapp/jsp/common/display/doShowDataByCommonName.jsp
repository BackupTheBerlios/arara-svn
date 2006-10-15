<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<form name="showPhotosByCommonNameForm" method="get"
	action="<c:url value="${servletToCall}?action=${action}"/>">

<table width="100%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr>
		<td width="20%">
			<b><fmt:message key="common.name" /></b>
			<select name="id">
				<c:forEach items="${commonNameList}" var="cnBean">
					<option value="${cnBean.value}">${cnBean.label}</option>
				</c:forEach>
			</select>
			<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
		</td>
	</tr>
</table>
</form>
