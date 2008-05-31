<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<SCRIPT language="JavaScript"> 
    function stateSelected() { 
       document.showPhotosByPlaceForm.action = "<c:url value="/servlet/retrieveCitiesForState?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByPlace.jsp"/>"; 
       document.showPhotosByPlaceForm.submit(); 
    }    
</script> 

<c:set var="selectedState" value="${selectedStateId}"/>


<br><br>
<form name="showPhotosByPlaceForm" method="get"
	action="<c:url value="/servlet/searchPhotosByPlace"/>">

<c:import url="/jsp/common/display/hiddenFields.jsp"/>

<table align="center" class="formBorder"  width="60%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td></td>
	</tr>
	<tr align="center">
		<td width="10%">
			<br><b><fmt:message key="state" /></b>
	        <select name="stateId" onChange="stateSelected()">
	          <c:forEach items="${stateList}" var="stateBean">        
				<c:if test="${selectedState != null && selectedState == stateBean.value}">
			      <option selected value="${stateBean.value}">${stateBean.label}</option>
			    </c:if>
				<c:if test="${selectedState == null || selectedState != stateBean.value}">
			      <option value="${stateBean.value}">${stateBean.label}</option>
			    </c:if>		      
	          </c:forEach>
	        </select>  
			<b><fmt:message key="city"/></b>
	        <select name="id">
	          <c:forEach items="${citiesList}" var="city">
			      <option value="${city.id}">${city.name}</option>
	          </c:forEach>
	        </select>  
			<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
			<br>&nbsp;
		</td>
	</tr>
</table>
</form>

