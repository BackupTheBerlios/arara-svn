<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<form name="showPhotosByCommonNameForm" method="get" 
      action="<c:url value="/servlet/searchPhotosByCommonName"/>">
      
  <table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr> 
    <td width="20%"><b><fmt:message key="common.name"/></b></td>
    <td width="80%"> 
        <select name="id">
          <c:forEach items="${commonNameList}" var="cnBean">
	      	<option value="${cnBean.value}">${cnBean.label}</option>
          </c:forEach>
        </select>
    </td>
  </tr>
  </table>
  
  <table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  <tr>
      <td width="20%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="<fmt:message key="button.submit.send"/>">
        </div>
      </td>
    </tr>
  </table>  
</form>
