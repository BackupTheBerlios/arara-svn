<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.uploadForm.action = "<c:url value="/servlet/retrieveSpecies?nextPage=/jsp/photo/upload/uploadPhoto.jsp&data=PHOTO&action=UPLOAD"/>"; 
       document.uploadForm.submit(); 
    } 
    
    function stateSelected() { 
       document.uploadForm.action = "<c:url value="/servlet/retrieveCitiesForState?toPage=/jsp/photo/upload/uploadPhoto.jsp&data=PHOTO&action=UPLOAD"/>"; 
       document.uploadForm.submit(); 
    } 
</script> 


<c:set var="selectedFamilyId" value="${uploadPhotoBean.selectedFamilyId}"/>
<c:set var="selectedSpecieId" value="${uploadPhotoBean.selectedSpecieId}"/>
<c:set var="selectedStateId" value="${uploadPhotoBean.selectedStateId}"/>
<c:set var="selectedCityId" value="${uploadPhotoBean.selectedCityId}"/>
<c:set var="selectedAgeId" value="${uploadPhotoBean.selectedAgeId}"/>
<c:set var="selectedSexId" value="${uploadPhotoBean.selectedSexId}"/>

<form name="uploadForm" method="post" 
      action="<c:url value="/servlet/uploadPhoto"/>"
      enctype="multipart/form-data">
      
  <table width="100%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
  <tr> 
    <td width="15%"><b><fmt:message key="family"/></b></td>
    <td width="85%"> 
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
    <td width="15%"><b><fmt:message key="specie"/></b></td>
    <td width="85%"> 
        <select name="specieId">
        <c:forEach items="${uploadPhotoBean.specieList}" var="specieBean">
			<c:if test="${selectedSpecieId != null && selectedSpecieId == specieBean.value}">
		      <option selected value="${specieBean.value}">${specieBean.label}</option>
		    </c:if>
			<c:if test="${selectedSpecieId == null || selectedSpecieId != specieBean.value}">
		      <option value="${specieBean.value}">${specieBean.label}</option>
		    </c:if>
        </c:forEach>
        </select>
    </td>
  </tr>
  <tr> 
    <td width="15%"><b><fmt:message key="specie.age"/></b></td>
    <td width="85%"> 
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
    <td width="15%"><b><fmt:message key="specie.sex"/></b></td>
    <td width="85%"> 
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
  <tr> 
    <td width="15%"><b><fmt:message key="photo.camera"/></b></td>
    <td width="85%"> 
        <input type="text" name="camera" value="${uploadPhotoBean.camera}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b><fmt:message key="photo.len"/></b></td>
    <td width="85%"> 
        <input type="text" name="lens" value="${uploadPhotoBean.lens}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b><fmt:message key="photo.film"/></b></td>
    <td width="85%"> 
        <input type="text" name="film" value="${uploadPhotoBean.film}" size="64" maxlength="64">
    </td>
  </tr>

  <tr> 
    <td width="15%"><b><fmt:message key="photo.location"/></b></td>
    <td width="85%"> 
        <input type="text" name="location" value="${uploadPhotoBean.location}" size="64" maxlength="256">
    </td>
  </tr>
  
  <tr> 
    <td width="15%"><b><fmt:message key="state"/></b></td>
    <td width="85%">     
        <select name="stateId" onChange="stateSelected()">
          <c:forEach items="${uploadPhotoBean.statesList}" var="stateBean">        
			<c:if test="${selectedStateId != null && selectedStateId == stateBean.value}">
		      <option selected value="${stateBean.value}">${stateBean.label}</option>
		    </c:if>
			<c:if test="${selectedStateId == null || selectedStateId != stateBean.value}">
		      <option value="${stateBean.value}">${stateBean.label}</option>
		    </c:if>		      
          </c:forEach>
        </select>  
		<b><fmt:message key="city"/></b>
        <select name="cityId">
          <c:forEach items="${uploadPhotoBean.citiesList}" var="city">
			<c:if test="${selectedCityId != null && selectedCityId == city.id}">
		      <option selected value="${city.id}">${city.name}</option>
		    </c:if>
			<c:if test="${selectedCityId == null || selectedCityId != cityBean.value}">
		      <option value="${city.id}">${city.name}</option>
		    </c:if>		      
          </c:forEach>
        </select>  
	</td>      
  </tr>
  
  <tr>
    <td width="15%"><b><fmt:message key="photo.date"/></b></td>
    <td width="85%">
        <input type="text" name="date" value="${uploadPhotoBean.date}" size="16" maxlength="16">
    </td>
  </tr>
  <tr>
    <td width="15%"><b><fmt:message key="photo.author.comment"/></b></td>
    <td width="85%">
        <textarea rows="5" cols="70" name="comment">${uploadPhotoBean.comment}</textarea>
    </td>
  </tr>
  <tr> 
    <td width="15%"><b><fmt:message key="photo.file"/></b></td>
    <td width="85%"> 
        <input type="file" name="fileName" label="Search"> 
        <font color="#FF000">Max 250Kb</font>
    </td>
  </tr>
  </table>
  
  <table width="100%" border="0" cellspacing="2" align="center" bgcolor="${mainBgColor}">
  <tr>
      <td width="15%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="<fmt:message key="photo.submit"/>">
        </div>
      </td>
    </tr>
  </table>  
</form>
