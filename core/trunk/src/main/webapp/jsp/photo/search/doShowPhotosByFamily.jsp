<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:set var="selectedFamilyId" value="${familyList.selectedId}" />

<form name="showPhotosByFamilyForm" method="get"
	action="<c:url value="/servlet/searchPhotosByFamily?action=BEGIN"/>">

<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
	<tr>
		<td width="10%"><b><fmt:message key="family" /></b></td>
		<td width="90%"><select name="id">
			<c:forEach items="${familyList.list}" var="familyBean">
				<c:if
					test="${selectedFamilyId != null && selectedFamilyId == familyBean.value}">
					<option selected value="${familyBean.value}">${familyBean.label}</option>
				</c:if>
				<c:if
					test="${selectedFamilyId == null || selectedFamilyId != familyBean.value}">
					<option value="${familyBean.value}">${familyBean.label}</option>
				</c:if>
			</c:forEach>
		</select></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="2" align="center"
	bgcolor="#A6D2D2">
	<tr>
		<td width="10%"></td>
		<td>
		<div align="left"><input type="SUBMIT"
			value="<fmt:message key="button.submit.send"/>"></div>
		</td>
	</tr>
</table>
</form>
