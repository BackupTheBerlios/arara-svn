<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.editForm.action = "<c:url value="/servlet/filterMediaServlet?nextPage=/jsp/photo/upload/editPhoto.jsp&data=PHOTO&doAction=EDIT"/>"; 
       document.editForm.submit(); 
    } 

    function stateSelected() { 
       document.editForm.action = "<c:url value="/servlet/filterMediaServlet?nextPage=/jsp/photo/upload/editPhoto.jsp&data=PHOTO&doAction=EDIT"/>"; 
       document.editForm.submit(); 
    } 
    
</script>

<c:set var="col1" value="${5}"/>
<c:set var="col2" value="${15}"/>
<c:set var="col3" value="${80}"/>
<br>

<c:set var="selectedFamilyId" value="${uploadPhotoBean.selectedFamilyId}" />
<c:set var="selectedSpecieId" value="${uploadPhotoBean.selectedSpecieId}" />
<c:set var="selectedStateId" value="${uploadPhotoBean.selectedStateId}" />
<c:set var="selectedCityId" value="${uploadPhotoBean.selectedCityId}" />
<c:set var="selectedAgeId" value="${uploadPhotoBean.selectedAgeId}" />
<c:set var="selectedSexId" value="${uploadPhotoBean.selectedSexId}" />

<form name="editForm" method="post"
	action="<c:url value="/servlet/editPhoto?identification=${identification}"/>"
	enctype="multipart/form-data">

<input type=hidden name="photoId" value="${photoId}">

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
				<c:forEach items="${uploadPhotoBean.familyList}" var="familyBean">
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
				<c:forEach items="${uploadPhotoBean.specieList}" var="specieBean">
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
	            <fmt:message key="${age.age}" var="ageI18N"/>
				<c:if test="${selectedAgeId != null && selectedAgeId == age.id}">
			      <option selected value="${age.id}">${ageI18N}</option>
			    </c:if>
				<c:if test="${selectedAgeId == null || selectedAgeId != age.id}">
			      <option value="${age.id}">${ageI18N}</option>
			    </c:if>
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
	            <fmt:message key="${sex.sex}" var="sexI18N"/>
				<c:if test="${selectedSexId != null && selectedSexId == sex.id}">
			      <option selected value="${sex.id}">${sexI18N}</option>
			    </c:if>
				<c:if test="${selectedSexId == null || selectedSexId != sex.id}">
			      <option value="${sex.id}">${sexI18N}</option>
			    </c:if>
	          </c:forEach>
	        </select>
		</td>
	</tr>
  </c:if>
  <c:if test="${(uploadPhotoBean.user.id == user.id) or (user.admin)}">
	  <c:set var="editar" value="enabled"/>
  </c:if> 
  <c:if test="${(uploadPhotoBean.user.id != user.id) and (!user.admin)}">
	  <c:set var="editar" value="disabled"/>
  </c:if> 
  
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
			<b><fmt:message key="photo.camera" /></b>
		</td>
		<td width="${col3}%">
			<input ${editar} type="text" name="camera"
			value="${uploadPhotoBean.camera}" size="64" maxlength="64"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.len" /></b>
	    </td>
		<td width="${col3}%">
			<input ${editar} type="text" name="lens"
			value="${uploadPhotoBean.lens}" size="64" maxlength="64"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.film" /></b>
	    </td>
		<td width="${col3}%">
			<input ${editar} type="text" name="film"
			value="${uploadPhotoBean.film}" size="64" maxlength="64"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.location" /></b>
	    </td>
		<td width="${col3}%">
			<input ${editar} type="text" name="location"
			value="${uploadPhotoBean.location}" size="64" maxlength="64">
		</td>
	</tr>

	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="state" /></b>
	    </td>
		<td width="${col3}%">
			<select ${editar} name="stateId" onChange="stateSelected()">
			<c:forEach items="${uploadPhotoBean.statesList}" var="stateBean">
				<c:if test="${selectedStateId != null && selectedStateId == stateBean.value}">
					<option selected value="${stateBean.value}">${stateBean.label}</option>
				</c:if>
				<c:if
					test="${selectedStateId == null || selectedStateId != stateBean.value}">
					<option value="${stateBean.value}">${stateBean.label}</option>
				</c:if>
			</c:forEach>
		</select> 
		<b><fmt:message key="city" /></b> 
			<select ${editar} name="cityId">
			<c:forEach items="${uploadPhotoBean.citiesList}" var="city">
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
		<td width="${col3}%">
			<input ${editar} type="text" name="date"
			value="${uploadPhotoBean.date}" size="16" maxlength="16"></td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
	    	<b><fmt:message key="photo.author.comment" /></b>
	    </td>
		<td width="${col3}%">
			<textarea ${editar} rows="5" cols="70" name="comment">${uploadPhotoBean.comment}</textarea>
		</td>
	</tr>
	<tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%">
		<td>
		<div align="left">
			<input type="SUBMIT" value="<fmt:message key="photo.submit"/>"></div>
		</td>
	</tr>
	<tr height="5">
		<td colspan="2"></td>
  	</tr>
</table>
</form>
