<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.editForm.action = "<c:url value="/servlet/retrieveSpecies?nextPage=/frame.jsp&pageToShow=/jsp/photo/upload/doEditPhoto.jsp&data=PHOTO&action=EDIT"/>"; 
       document.editForm.submit(); 
    } 

    function stateSelected() { 
       document.editForm.action = "<c:url value="/servlet/retrieveCitiesForState?toPage=/jsp/photo/upload/editPhoto.jsp&data=PHOTO&action=EDIT"/>"; 
       document.editForm.submit(); 
    } 
    
</script>

<c:set var="col1" value="${5}"/>
<c:set var="col2" value="${15}"/>
<c:set var="col3" value="${80}"/>
<br>

<c:set var="selectedFamilyId" value="${editBean.selectedFamilyId}" />
<c:set var="selectedSpecieId" value="${editBean.selectedSpecieId}" />
<c:set var="selectedStateId" value="${editBean.selectedStateId}" />
<c:set var="selectedCityId" value="${editBean.selectedCityId}" />
<c:set var="selectedAgeId" value="${editBean.selectedAgeId}" />
<c:set var="selectedSexId" value="${editBean.selectedSexId}" />

<form name="editForm" method="post"
	action="<c:url value="/servlet/editPhoto?identification=${identification}"/>"
	enctype="multipart/form-data">

<table class="formBorder" width="80%" align="center" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <c:if test="${identification != 'true'}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
			<b><fmt:message key="family" /></b>
		</td>
		<td width="${col3}%">
			<select name="familyId" onChange="familySelected()">
				<c:forEach items="${editBean.familyList}" var="familyBean">
					<c:if test="${selectedFamilyId != null && selectedFamilyId == familyBean.value}">
						<option selected value="${familyBean.value}">${familyBean.label}</option>
					</c:if>
					<c:if test="${selectedFamilyId == null || selectedFamilyId != familyBean.value}">
						<option value="${familyBean.value}">${familyBean.label}</option>
					</c:if>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
			<b><fmt:message key="specie" /></b>
		</td>
		<td width="${col3}%">
			<select name="specieId">
				<c:forEach items="${editBean.specieList}" var="specieBean">
					<c:if
						test="${selectedSpecieId != null && selectedSpecieId == specieBean.value}">
						<option selected value="${specieBean.value}">${specieBean.label}</option>
					</c:if>
					<c:if
						test="${selectedSpecieId == null || selectedSpecieId != specieBean.value}">
						<option value="${specieBean.value}">${specieBean.label}</option>
					</c:if>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
			<b><fmt:message key="specie.age" /></b>
		</td>
		<td width="${col3}%">
			<select name="ageId">
				<c:forEach items="${ageList}" var="age">
					<option value="${age.id}">${age.age}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
			<b><fmt:message key="specie.sex" /></b>
		</td>
		<td width="${col3}%">
			<select name="sexId">
				<c:forEach items="${sexList}" var="sex">
					<option value="${sex.id}">${sex.sex}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
  </c:if>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
			<b><fmt:message key="photo.camera" /></b>
		</td>
		<td width="${col3}%"><input type="text" name="camera"
			value="${editBean.camera}" size="64" maxlength="64"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.len" /></b>
	    </td>
		<td width="${col3}%"><input type="text" name="lens"
			value="${editBean.lens}" size="64" maxlength="64"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.film" /></b>
	    </td>
		<td width="${col3}%"><input type="text" name="film"
			value="${editBean.film}" size="64" maxlength="64"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.location" /></b>
	    </td>
		<td width="${col3}%">
			<input type="text" name="location"
			value="${editBean.location}" size="64" maxlength="64">
		</td>
	</tr>

	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="state" /></b>
	    </td>
		<td width="${col3}%"><select name="stateId" onChange="stateSelected()">
			<c:forEach items="${editBean.statesList}" var="stateBean">
				<c:if
					test="${selectedStateId != null && selectedStateId == stateBean.value}">
					<option selected value="${stateBean.value}">${stateBean.label}</option>
				</c:if>
				<c:if
					test="${selectedStateId == null || selectedStateId != stateBean.value}">
					<option value="${stateBean.value}">${stateBean.label}</option>
				</c:if>
			</c:forEach>
		</select> <b><fmt:message key="city" /></b> <select name="cityId">
			<c:forEach items="${editBean.citiesList}" var="city">
				<c:if test="${selectedCityId != null && selectedCityId == city.id}">
					<option selected value="${city.id}">${city.name}</option>
				</c:if>
				<c:if
					test="${selectedCityId == null || selectedCityId != cityBean.value}">
					<option value="${city.id}">${city.name}</option>
				</c:if>
			</c:forEach>
		</select></td>
	</tr>

	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.date" /></b>
	    </td>
		<td width="${col3}%"><input type="text" name="date"
			value="${editBean.date}" size="16" maxlength="16"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.author.comment" /></b>
	    </td>
		<td width="${col3}%"><textarea rows="5" cols="70" name="comment">${editBean.comment}</textarea>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
		<td>
		<div align="left"><input type="SUBMIT"
			value="<fmt:message key="photo.submit"/>"></div>
		</td>
	</tr>
	<tr height="5">
		<td colspan="2"></td>
  	</tr>
</table>
</form>
