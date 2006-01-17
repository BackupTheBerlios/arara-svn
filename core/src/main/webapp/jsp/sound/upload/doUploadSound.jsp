<%@ taglib uri="/core" prefix="c"%>

<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.uploadForm.action = "<c:url value="/servlet/retrieveSpecies?toPage=/jsp/sound/upload/uploadSound.jsp&data=SOUND"/>"; 
       document.uploadForm.submit(); 
    } 
</script> 


<c:set var="selectedFamilyId" value="${uploadSoundBean.selectedFamilyId}"/>
<c:set var="selectedSpecieId" value="${uploadSoundBean.selectedSpecieId}"/>

<form name="uploadForm" method="post" 
      action="<c:url value="/servlet/uploadSound"/>"
      enctype="multipart/form-data">
      
  <table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr> 
    <td width="15%"><b>Família</b></td>
    <td width="85%"> 
        <select name="familyId" onChange="familySelected()">
          <c:forEach items="${uploadSoundBean.familyList}" var="familyBean">
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
        <c:forEach items="${uploadSoundBean.specieList}" var="specieBean">
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
    <td width="15%"><b>Local</b></td>
    <td width="85%"> 
        <input type="text" name="location" value="${lastUploadSoundBean.location}" size="64" maxlength="64">
    </td>
  </tr>

  <tr> 
    <td width="15%"><b>Cidade</b></td>
    <td width="85%"> 
        <input type="text" name="city" value="${lastUploadBean.city}" size="64" maxlength="64">
        <b>Estado</b> <select name="stateId">
          <c:forEach items="${stateList}" var="state">
		      <option value="${state.id}">${state.acronym}</option>
          </c:forEach>
        </select>
        
    </td>
  </tr>


  <tr>
    <td width="15%"><b>Comentário</b></td>
    <td width="85%">
        <textarea rows="5" cols="70" name="comment"/></textarea>
    </td>
  </tr>

  <tr> 
    <td width="15%"><b>Arquivo</b></td>
    <td width="85%"> 
        <input type="file" name="fileName"> 
        <font color="#FF000">Max 500Kb</font>
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
