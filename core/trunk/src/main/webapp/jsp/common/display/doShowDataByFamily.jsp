<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:set var="selectedFamilyId" value="${familyList.selectedId}" />

<form name="showPhotosByFamilyForm" method="get"
	action="<c:url value="${servletToCall}?action=${action}"/>">

<br><br>
<table align="center" class="formBorder"  width="60%" border="0" cellspacing="2">
	<tr height="10" bgcolor="#000000">
		<td></td>
	</tr>
	<tr align="center">
		<td>
		<br><b><fmt:message key="family" /></b>
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
		<br>&nbsp;
		</td>
	</tr>
</table>
</form>
