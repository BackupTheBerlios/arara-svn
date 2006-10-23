<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<br><br>
<form name="showPhotosByCommonNameForm" method="get"
	action="<c:url value="${servletToCall}?action=${action}"/>">
<table align="center" class="formBorder"  width="60%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td></td>
	</tr>
	<tr align="center">
		<td width="20%">
			<br><b><fmt:message key="common.name" /></b>
			<select name="id">
				<c:forEach items="${commonNameList}" var="cnBean">
					<option value="${cnBean.value}">${cnBean.label}</option>
				</c:forEach>
			</select>
			<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
			<br>&nbsp;
		</td>
	</tr>
</table>
</form>
