<%@ taglib uri="/core" prefix="c"%>

<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.editForm.action = "<c:url value="/servlet/retrieveSpecies?toPage=/jsp/upload/uploadPhoto.jsp"/>"; 
       document.editForm.submit(); 
    } 
</script> 


<c:set var="selectedFamilyId" value="${editBean.selectedFamilyId}"/>
<c:set var="selectedSpecieId" value="${editBean.selectedSpecieId}"/>

<form name="editForm" method="post" 
      action="<c:url value="/servlet/editPhoto"/>"
      enctype="multipart/form-data">
      
  <table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr> 
    <td width="15%"><b>Família</b></td>
    <td width="85%"> 
        <select disabled name="familyId" onChange="familySelected()">
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
    <td width="15%"><b>Espécie</b></td>
    <td width="85%"> 
        <select name="specieId">
        <c:forEach items="${editBean.specieList}" var="specieBean">
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
    <td width="15%"><b>Idade</b></td>
    <td width="85%"> 
        <select name="ageId">
          <c:forEach items="${ageList}" var="age">
		      <option value="${age.id}">${age.age}</option>
          </c:forEach>
        </select>
    </td>
  </tr>

  <tr> 
    <td width="15%"><b>Sexo</b></td>
    <td width="85%"> 
        <select name="sexId">
          <c:forEach items="${sexList}" var="sex">
		      <option value="${sex.id}">${sex.sex}</option>
          </c:forEach>
        </select>
    </td>
  </tr>

  <tr> 
    <td width="15%"><b>Camera</b></td>
    <td width="85%"> 
        <input type="text" name="camera" value="${editBean.camera}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b>Lente</b></td>
    <td width="85%"> 
        <input type="text" name="lens" value="${editBean.lens}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b>Filme</b></td>
    <td width="85%"> 
        <input type="text" name="film" value="${editBean.film}" size="64" maxlength="64">
    </td>
  </tr>
  <tr> 
    <td width="15%"><b>Local</b></td>
    <td width="85%"> 
        <input type="text" name="location" value="${editBean.location}" size="64" maxlength="64">
    </td>
  </tr>
  <tr>
    <td width="15%"><b>Data</b></td>
    <td width="85%">
        <input type="text" name="date" value="${editBean.date}" size="16" maxlength="16">
    </td>
  </tr>
  <tr>
    <td width="15%"><b>Comentário do Autor</b></td>
    <td width="85%">
        <textarea rows="5" cols="70" name="comment">${editBean.comment}</textarea>
    </td>
  </tr>
  </table>
  
  <table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  <tr>
      <td width="15%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="Enviar">
        </div>
      </td>
    </tr>
  </table>  
</form>
