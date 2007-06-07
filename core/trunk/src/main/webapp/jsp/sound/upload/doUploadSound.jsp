<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.uploadForm.action = "<c:url value="/servlet/retrieveSpecies?nextPage=/jsp/sound/upload/uploadSound.jsp&data=SOUND&action=UPLOAD"/>"; 
       document.uploadForm.submit(); 
    } 

    function stateSelected() { 
       document.uploadForm.action = "<c:url value="/servlet/retrieveCitiesForState?nextPage=/jsp/sound/upload/uploadSound.jsp&data=SOUND&action=UPLOAD"/>"; 
       document.uploadForm.submit(); 
    } 
</script>


<c:set var="selectedFamilyId"
	value="${uploadSoundBean.selectedFamilyId}" />
<c:set var="selectedSpecieId"
	value="${uploadSoundBean.selectedSpecieId}" />
<c:set var="selectedStateId" value="${uploadSoundBean.selectedStateId}" />
<c:set var="selectedCityId" value="${uploadSoundBean.selectedCityId}" />
<c:set var="selectedAgeId" value="${uploadSoundBean.selectedAgeId}" />
<c:set var="selectedSexId" value="${uploadSoundBean.selectedSexId}" />

<c:set var="col1" value="${5}%"/>
<c:set var="col2" value="${15}%"/>
<c:set var="col3" value="${80}%"/>
<br><br>
<form name="uploadForm" method="post"
	action="<c:url value="/servlet/uploadSound"/>"
	enctype="multipart/form-data">

<table class="formBorder" width="80%" align="center" border="0" cellspacing="2">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="family" /></b></td>
		<td width="${col3}"><select name="familyId" onChange="familySelected()">
			<c:forEach items="${uploadSoundBean.familyList}" var="familyBean">
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
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="specie" /></b></td>
		<td width="${col3}"><select name="specieId">
			<c:forEach items="${uploadSoundBean.specieList}" var="specieBean">
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

	<tr>
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="specie.age" /></b></td>
		<td width="${col3}"><select name="ageId">
			<c:forEach items="${ageList}" var="age">
				<c:if test="${selectedAgeId != null && selectedAgeId == age.id}">
					<option selected value="${age.id}">${age.age}</option>
				</c:if>
				<c:if test="${selectedAgeId == null || selectedAgeId != age.id}">
					<option value="${age.id}">${age.age}</option>
				</c:if>
			</c:forEach>
		</select></td>
	</tr>

	<tr>
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="specie.sex" /></b></td>
		<td width="${col3}"><select name="sexId">
			<c:forEach items="${sexList}" var="sex">
				<c:if test="${selectedSexId != null && selectedSexId == sex.id}">
					<option selected value="${sex.id}">${sex.sex}</option>
				</c:if>
				<c:if test="${selectedSexId == null || selectedSexId != sex.id}">
					<option value="${sex.id}">${sex.sex}</option>
				</c:if>
			</c:forEach>
		</select></td>
	</tr>

	<tr>
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="sound.location" /></b></td>
		<td width="${col3}"><input type="text" name="location"
			value="${lastUploadSoundBean.location}" size="64" maxlength="64"></td>
	</tr>

	<tr>
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="state" /></b></td>
		<td width="${col3}"><select name="stateId" onChange="stateSelected()">
			<c:forEach items="${uploadSoundBean.statesList}" var="stateBean">
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
			<c:forEach items="${uploadSoundBean.citiesList}" var="city">
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
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="sound.author.comment" /></b></td>
		<td width="${col3}"><textarea rows="5" cols="60" name="comment" /></textarea>
		</td>
	</tr>

	<tr>
		<td width="${col1}"></td>
		<td width="${col2}"><b><fmt:message key="sound.file" /></b></td>
		<td width="${col3}"><input type="file" name="fileName"> <font
			color="#FF000">Max 500Kb</font></td>
	</tr>
	<tr>
		<td width="${col1}"></td>
		<td width="${col2}"></td>
		<td>
		<div align="left"><input type="SUBMIT"
			value="<fmt:message key="sound.submit"/>"></div>
		</td>
	</tr>
    <tr height="5">
		<td colspan="2"></td>
    </tr>
</table>
</form>
