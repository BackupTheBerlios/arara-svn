<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<SCRIPT language="JavaScript"> 

    function familySelected() { 
       document.uploadForm.action = "<c:url value="/servlet/filterPhotoServlet?nextPage=/jsp/photo/upload/uploadPhoto.jsp&data=PHOTO&doAction=UPLOAD"/>"; 
       document.uploadForm.submit(); 
    } 

    function specieSelected() { 
       document.uploadForm.action = "<c:url value="/servlet/filterPhotoServlet?nextPage=/jsp/photo/upload/uploadPhoto.jsp&data=PHOTO&doAction=UPLOAD"/>"; 
       document.uploadForm.submit(); 
    } 
    
    function stateSelected() { 
       document.uploadForm.action = "<c:url value="/servlet/filterMediaServlet?nextPage=/jsp/photo/upload/uploadPhoto.jsp&data=PHOTO&doAction=UPLOAD"/>"; 
       document.uploadForm.submit(); 
    }    
</script> 

<c:set var="maximumNumberOfPhotos" value="${6}"/>


<c:set var="selectedFamilyId" value="${uploadPhotoBean.selectedFamilyId}"/>
<c:set var="selectedSpecieId" value="${uploadPhotoBean.selectedSpecieId}"/>
<c:set var="selectedStateId" value="${uploadPhotoBean.selectedStateId}"/>
<c:set var="selectedCityId" value="${uploadPhotoBean.selectedCityId}"/>
<c:set var="selectedAgeId" value="${uploadPhotoBean.selectedAgeId}"/>
<c:set var="selectedSexId" value="${uploadPhotoBean.selectedSexId}"/>
<c:set var="numberOfPhotos" value="${numberOfPhotosFromUser}"/>

<c:if test="${numberOfPhotos == null}">
	<c:set var="numberOfPhotos" value="${0}"/>
</c:if>

<c:if test="${numberOfPhotos != null && numberOfPhotos < maximumNumberOfPhotos}">
	<c:set var="editar" value="enabled"/>
</c:if> 
<c:if test="${numberOfPhotos != null && numberOfPhotos >= maximumNumberOfPhotos}">
	<c:set var="editar" value="disabled"/>
</c:if> 

<c:set var="col1" value="${5}"/>
<c:set var="col2" value="${15}"/>
<c:set var="col3" value="${80}"/>
<br>
<form onsubmit="return submit_form()"
	  name="uploadForm" method="post" 
      action="<c:url value="/servlet/uploadPhoto"/>"
      enctype="multipart/form-data">
      
  <table class="formBorder" width="80%" align="center" border="0" cellspacing="2">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="3"></td>
	</tr>
	<tr height="5">
		<td colspan="3"></td>
	</tr>
	<tr>
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="family"/></b>
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
    	<b><fmt:message key="specie"/></b>
    </td>
    <td width="${col3}%"> 
        <select name="specieId" onChange="specieSelected()">
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
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="specie.age"/></b>
    </td>
    <td width="${col3}%"> 
        <select ${editar} name="ageId">
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
    	&nbsp;&nbsp;&nbsp;<b><fmt:message key="specie.sex"/></b>
        <select ${editar} name="sexId">
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
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.camera"/></b>
    </td>
    <td width="${col3}%"> 
        <input ${editar} type="text" name="camera" value="${uploadPhotoBean.camera}" size="32" maxlength="64">
    	&nbsp;&nbsp;&nbsp;<b><fmt:message key="photo.len"/></b>
        <input ${editar} type="text" name="lens" value="${uploadPhotoBean.lens}" size="32" maxlength="64">
    </td>
  </tr>
  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.film"/></b>
    </td>
    <td width="${col3}%"> 
        <input ${editar} type="text" name="film" value="${uploadPhotoBean.film}" size="16" maxlength="64">
    </td>
  </tr>

  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.f_stop"/></b>
    </td>
    <td width="${col3}%"> 
        <input ${editar} type="text" name="f_stop" value="${uploadPhotoBean.fstop}" size="10" maxlength="10">
    	&nbsp;&nbsp;&nbsp;<b><fmt:message key="photo.shutter_speed"/></b>
        <input ${editar} type="text" name="shutter_speed" value="${uploadPhotoBean.shutterSpeed}" size="10" maxlength="10">
    </td>
  </tr>

  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.iso"/></b>
    </td>
    <td width="${col3}%"> 
        <input ${editar} type="text" name="iso" value="${uploadPhotoBean.iso}" size="10" maxlength="10">
    	&nbsp;&nbsp;&nbsp;<b><fmt:message key="photo.zoom"/></b>
        <input ${editar} type="text" name="zoom" value="${uploadPhotoBean.zoom}" size="10" maxlength="10">
    	&nbsp;&nbsp;&nbsp;<b><fmt:message key="photo.flash"/></b>
    	<c:if test="${uploadPhotoBean.flash}">
			<c:set var="checked" value="checked"/>
        </c:if>
        <input ${checked} type="checkbox" name="flash" value="${uploadPhotoBean.flash}" size="10" maxlength="10">
    </td>
  </tr>

  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.location"/></b>
    </td>
    <td width="${col3}%"> 
        <input ${editar} type="text" name="location" value="${uploadPhotoBean.location}" size="64" maxlength="256">
    </td>
  </tr>
  
  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="state"/></b>
    </td>
    <td width="${col3}%">     
        <select ${editar} name="stateId" onChange="stateSelected()">
          <c:forEach items="${uploadPhotoBean.statesList}" var="stateBean">        
			<c:if test="${selectedStateId != null && selectedStateId == stateBean.value}">
		      <option selected value="${stateBean.value}">${stateBean.label}</option>
		    </c:if>
			<c:if test="${selectedStateId == null || selectedStateId != stateBean.value}">
		      <option value="${stateBean.value}">${stateBean.label}</option>
		    </c:if>		      
          </c:forEach>
        </select>  
		&nbsp;&nbsp;&nbsp;<b><fmt:message key="city"/></b>
        <select ${editar} name="cityId">
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
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.date"/></b>
    </td>
    <td width="${col3}%">
        <input ${editar} type="text" name="date" value="${uploadPhotoBean.date}" size="16" maxlength="16">
    </td>
  </tr>
  <tr>
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.author.comment"/></b>
    </td>
    <td width="${col3}%">
        <textarea ${editar} rows="4" cols="60" name="comment">${uploadPhotoBean.comment}</textarea>
    </td>
  </tr>
  <tr> 
	<td width="${col1}%"></td>
    <td width="${col2}%">
    	<b><fmt:message key="photo.file"/></b>
    </td>
    <td width="${col3}%"> 
        <input ${editar} type="file" name="fileName" label="Search"> 
        <font color="#FF0000" size="2" face="Verdana">Max 400Kb</font>
    </td>
  </tr>
  <c:if test="${numberOfPhotos != null && numberOfPhotos < maximumNumberOfPhotos}">
	  <tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%"></td>
	    <td> 
	        <div align="left">
	            <input type="SUBMIT" value="<fmt:message key="photo.submit"/>">
	        </div>
	    </td>
	  </tr>
  </c:if>	  
  <c:if test="${numberOfPhotos != null && numberOfPhotos >= maximumNumberOfPhotos}">
	  <tr>
		<td width="${col1}%"></td>
	    <td width="${col2}%"></td>
	    <td> 
        	<font color="#FF0000" size="2" face="Verdana">
				<fmt:message key="maximum.number.photos1"/>${maximumNumberOfPhotos}<fmt:message key="maximum.number.photos2"/>
			</font>
	    </td>
	  </tr>
  </c:if>	  
  <tr height="5">
	<td colspan="2"></td>
  </tr>
  </table>  
</form>
