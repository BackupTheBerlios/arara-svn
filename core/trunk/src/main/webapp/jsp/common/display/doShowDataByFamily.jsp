<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:set var="selectedFamilyId" value="${familyList.selectedId}" />

<form name="showPhotosByFamilyForm" method="get"
	action="<c:url value="${servletToCall}?action=${action}"/>">

<table width="60%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr>
		<td><b><fmt:message key="family" /></b>
		<select name="id">
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
		</select>
		<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
		</td>
	</tr>
</table>
</form>
