<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<form name="uploadForm" method="post" 
      action="<c:url value="/servlet/uploadPhotoIdentify"/>"
      enctype="multipart/form-data">
      
  <table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr> 
    <td width="15%"><b><fmt:message key="photo.camera"/></b></td>
    <td width="85%"> 
        <input type="text" name="camera" value="${lastUploadBean.camera}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b><fmt:message key="photo.len"/></b></td>
    <td width="85%"> 
        <input type="text" name="lens" value="${lastUploadBean.lens}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b><fmt:message key="photo.film"/></b></td>
    <td width="85%"> 
        <input type="text" name="film" value="${lastUploadBean.film}" size="64" maxlength="64">
    </td>
  </tr>

  <tr> 
    <td width="15%"><b><fmt:message key="photo.location"/></b></td>
    <td width="85%"> 
        <input type="text" name="location" value="${lastUploadBean.location}" size="64" maxlength="64">
    </td>
  </tr>

  <tr> 
    <td width="15%"><b><fmt:message key="state"/></b></td>
    <td width="85%">     
        <select name="stateId" onChange="stateSelected()">
          <c:forEach items="${lastUploadBean.statesList}" var="stateBean">        
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
          <c:forEach items="${lastUploadBean.citiesList}" var="city">
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
        <input type="text" name="date" value="${lastUploadBean.date}" size="16" maxlength="16">
    </td>
  </tr>
  <tr>
    <td width="15%"><b><fmt:message key="photo.author.comment"/></b></td>
    <td width="85%">
        <textarea rows="5" cols="70" name="comment">${lastUploadBean.comment}</textarea>
    </td>
  </tr>
  <tr> 
    <td width="15%"><b><fmt:message key="photo.file"/></b></td>
    <td width="85%"> 
        <input type="file" name="fileName"> 
        <font color="#FF000">Max 250Kb</font>
    </td>
  </tr>
  </table>
  
  <table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
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
