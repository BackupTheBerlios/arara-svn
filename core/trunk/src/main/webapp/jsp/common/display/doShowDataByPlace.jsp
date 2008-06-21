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

<table align="center" class="formBorder"  width="60%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
	<tr height="10" bgcolor="${formTitleColor}">
		<td></td>
	</tr>
    <tr>
      <td></td>
    </tr>
    <tr>
      <td>
      <table style="text-align: left; width: 100%;" border="0" cellpadding="2" cellspacing="2">
        <tbody>
          <tr>
			<form name="showPhotosByStateForm" method="get"	action="<c:url value="/servlet/searchPhotosByState"/>">
			<c:import url="/jsp/common/display/hiddenFields.jsp"/>
            <td style="width: 10%;"></td>
            <td>
            <table style="text-align: left; width: 100%;" border="0" cellpadding="2" cellspacing="2">
              <tbody>
                <tr>
                  <td style="width: 33%;">
					<b><fmt:message key="state" /></b>
			        <select name="id" >
			          <c:forEach items="${stateList}" var="stateBean">        
						<c:if test="${selectedState != null && selectedState == stateBean.value}">
					      <option selected value="${stateBean.value}">${stateBean.label}</option>
					    </c:if>
						<c:if test="${selectedState == null || selectedState != stateBean.value}">
					      <option value="${stateBean.value}">${stateBean.label}</option>
					    </c:if>		      
			          </c:forEach>
			        </select>                  
				  </td>
                  <td colspan="1" rowspan="1" style="width: 33%; text-align: right;">
                  	<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
                  </td>
                </tr>
              </tbody>
            </table>
            </td>
            <td style="width: 20%;"></td>
			</form>       
          </tr>
        </tbody>
      </table>
      </td>
    </tr>
    <tr>
      <td>
      <table style="text-align: left; width: 100%;" border="0" cellpadding="2" cellspacing="2">
        <tbody>
          <tr>
			<form name="showPhotosByPlaceForm" method="get"	action="<c:url value="/servlet/searchPhotosByPlace"/>">
			<c:import url="/jsp/common/display/hiddenFields.jsp"/>
            <td style="width: 10%;"></td>
            <td>
            <table style="text-align: left; width: 100%;" border="0" cellpadding="2" cellspacing="2">
              <tbody>
                <tr>
                  <td style="width: 33%;">
					<b><fmt:message key="state" /></b>
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
				  </td>
                  <td style="width: 33%;">
					<b><fmt:message key="city"/></b>
			        <select name="id">
			          <c:forEach items="${citiesList}" var="city">
					      <option value="${city.id}">${city.name}</option>
			          </c:forEach>
			        </select>  
                  </td>
                  <td style="width: 33%; text-align: right;">
                  	<input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
                  </td>
                </tr>
              </tbody>
            </table>
            </td>
            <td style="width: 20%;"></td>
			</form>        
          </tr>
        </tbody>
      </table>
      </td>
    </tr>
    <tr>
      <td></td>
    </tr>
  </tbody>
</table>



