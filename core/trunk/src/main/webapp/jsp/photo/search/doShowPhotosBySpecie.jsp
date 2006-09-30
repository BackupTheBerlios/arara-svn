<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.initShowPhotosBySpecieForm.method = "POST"; 
       document.initShowPhotosBySpecieForm.action = "<c:url value="/servlet/retrieveSpecies?toPage=/jsp/photo/search/showPhotosBySpecie.jsp&data=PHOTO"/>"; 
       document.initShowPhotosBySpecieForm.submit(); 
    } 
</script>


<c:set var="selectedFamilyId" value="${familyList.selectedId}" />
<c:set var="selectedSpecieId" value="${specieList.selectedId}" />

<form name="initShowPhotosBySpecieForm" method="get"
	action="<c:url value="/servlet/searchPhotosBySpecie"/>"
	enctype="multipart/form-data">

<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
	<tr>
		<td width="15%"><b><fmt:message key="family" /></b></td>
		<td width="85%"><select name="familyId" onChange="familySelected()">
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
	<tr>
		<td width="15%"><b><fmt:message key="specie" /></b></td>
		<td width="85%"><select name="id">
			<c:forEach items="${specieList.list}" var="specieBean">
				<c:if
					test="${selectedSpecieId != null && selectedSpecieId == specieBean.value}">
					<option selected value="${specieBean.value}">${specieBean.label}</option>
				</c:if>
				<c:if
					test="${selectedSpecieId == null || selectedSpecieId != specieBean.value}">
					<option value="${specieBean.value}">${specieBean.label}</option>
				</c:if>
			</c:forEach>
		</select></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="2" align="center"
	bgcolor="#A6D2D2">
	<tr>
		<td width="15%"></td>
		<td>
		<div align="left"><input type="SUBMIT"
			value="<fmt:message key="button.submit.send"/>"></div>
		</td>
	</tr>
</table>
</form>
