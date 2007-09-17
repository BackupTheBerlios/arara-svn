<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:set var="selectedFamilyId" value="${familyList.selectedId}" />
<c:set var="selectedSpecieId" value="${specieList.selectedId}" />

<script language="JavaScript">
function selFamily(){
document.photosBySpecie.method="POST";
document.photosBySpecie.action="<c:url value="/servlet/retrieveSpecies?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=${servletToCall}"/>"; 
document.photosBySpecie.submit();
}
</script>

<br><br>
<form action="<c:url value="${servletToCall}"/>"
	name="photosBySpecie" 
	method="get"
	enctype="multipart/form-data">

<c:import url="/jsp/common/display/hiddenFields.jsp"/>

<table align="center" class="formBorder" width="80%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td colspan="3"></td>
	</tr>
	<tr height="5" >
		<td colspan="3"></td>
	</tr>
	<tr align="center">
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<b><fmt:message key="family" /></b>
		</td>
		<td align="left" width="${col3}%">
			<select name="familyId" onChange="selFamily()">
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
	<tr height="30">
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%">
			<b><fmt:message key="specie" /></b>
		</td>
		<td align="left" width="${col3}%">
			<select name="id">
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
			</select>  <input name="text" type="text" size="30"/>
		</td>		
	</tr>
	<tr height="30">
		<td width="${col1}%"></td>
		<td align="left" width="${col2}%"></td>
		<td align="left" width="${col3}%">
			<div align="left">
				<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
			</div>
		</td>
	</tr>
	<tr height="5" >
		<td colspan="3"></td>
	</tr>
</table>
</form>
