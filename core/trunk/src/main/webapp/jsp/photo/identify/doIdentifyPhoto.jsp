<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<SCRIPT language="JavaScript"> 
    function familySelected() { 
       document.identifyPhotoForm.method = "POST"; 
       document.identifyPhotoForm.action = "<c:url value="/servlet/retrieveSpeciesForIdentification"/>"; 
       document.identifyPhotoForm.submit(); 
    } 
</script>

<c:set var="selectedFamilyId"
	value="${identificationPhotoBean.selectedFamilyId}" />
<c:set var="selectedSpecieId"
	value="${identificationPhotoBean.selectedSpecieId}" />
<c:set var="selectedAgeId"
	value="${identificationPhotoBean.selectedAgeId}" />
<c:set var="selectedSexId"
	value="${identificationPhotoBean.selectedSexId}" />
<c:set var="fontSize" value="${1}" />

<form name="identifyPhotoForm" method="post"
	action="<c:url value="/servlet/identifyPhoto"/>">

<table class="formBorder" width="100%" border="0" cellspacing="2">
	<tr>
		<td align="left" width="8%"><font size="${fontSize}" face="Verdana"><b><fmt:message
			key="family" /></b></font></td>
		<td align="left" width="22%"><font size="${fontSize}" face="Verdana">
		<select name="familyId" onChange="familySelected()">
			<c:forEach items="${identificationPhotoBean.familyList}"
				var="familyBean">
				<c:if
					test="${selectedFamilyId != null && selectedFamilyId == familyBean.value}">
					<option selected value="${familyBean.value}">${familyBean.label}</option>
				</c:if>
				<c:if
					test="${selectedFamilyId == null || selectedFamilyId != familyBean.value}">
					<option value="${familyBean.value}">${familyBean.label}</option>
				</c:if>
			</c:forEach>
		</select> </font></td>
		<td align="left" width="8%"><font size="${fontSize}" face="Verdana"><b><fmt:message
			key="specie" /></b></font></td>
		<td align="left" width="22%"><font size="${fontSize}" face="Verdana">
		<select name="specieId">
			<c:forEach items="${identificationPhotoBean.specieList}"
				var="specieBean">
				<c:if
					test="${selectedSpecieId != null && selectedSpecieId == specieBean.value}">
					<option selected value="${specieBean.value}">${specieBean.label}</option>
				</c:if>
				<c:if
					test="${selectedSpecieId == null || selectedSpecieId != specieBean.value}">
					<option value="${specieBean.value}">${specieBean.label}</option>
				</c:if>
			</c:forEach>
		</select> </font></td>
		<td width="5%"><font size="${fontSize}" face="Verdana"><b><fmt:message
			key="specie.age" /></b></font></td>
		<td width="15%"><font size="${fontSize}" face="Verdana"> <select
			name="ageId">
			<c:forEach items="${ageList}" var="age">
				<c:if test="${selectedAgeId != null && selectedAgeId == age.id}">
					<option selected value="${age.id}">${age.age}</option>
				</c:if>
				<c:if test="${selectedAgeId == null || selectedAgeId != age.id}">
					<option value="${age.id}">${age.age}</option>
				</c:if>
			</c:forEach>
		</select> </font></td>
		<td width="5%"><font size="${fontSize}" face="Verdana"><b><fmt:message
			key="specie.sex" /></b></font></td>
		<td width="15%"><font size="${fontSize}" face="Verdana"> <select
			name="sexId">
			<c:forEach items="${sexList}" var="sex">
				<c:if test="${selectedSexId != null && selectedSexId == sex.id}">
					<option selected value="${sex.id}">${sex.sex}</option>
				</c:if>
				<c:if test="${selectedSexId == null || selectedSexId != sex.id}">
					<option value="${sex.id}">${sex.sex}</option>
				</c:if>
			</c:forEach>
		</select> </font></td>
	</tr>
</table>
<table class="formBorder" width="100%" border="0" cellspacing="2">
	<tr>
		<td width="10%">
			<font size="${fontSize}" face="Verdana">
				<b><fmt:message	key="photo.identify.comment" /></b>
			</font>
		</td>
		<td width="80%">
			<c:if test="${user.admin == true}">
				<input type="text" name="comment"
					value="${identificationPhotoBean.comment}" size="60" maxlength="256">
				<input type="checkbox" name="finishIdentification"><fmt:message	key="photo.identify.finish" />					
			</c:if>
			<c:if test="${user.admin != true}">
				<input type="text" name="comment"
					value="${identificationPhotoBean.comment}" size="100" maxlength="256">
			</c:if>
		</td>
		<td width="10%">
		<div align="left"><input type="SUBMIT"
			value="<fmt:message key="button.submit.send"/>"></div>
		</td>
	</tr>
</table>

<!--  
  <table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  	<tr>
      <td width="15%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
        </div>
      </td>
  	</tr>
  </table>  
--></form>
