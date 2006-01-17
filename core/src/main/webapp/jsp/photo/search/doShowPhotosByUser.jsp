<%@ taglib uri="/core" prefix="c"%>

<form name="showPhotosByUserForm" method="get" 
      action="<c:url value="/servlet/searchPhotosByUser"/>">
      
  <table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr> 
    <td width="10%"><b>Usuário</b></td>
    <td width="90%"> 
        <select name="userId">
          <c:forEach items="${userList}" var="userBean">
	      	<option value="${userBean.value}">${userBean.label}</option>
          </c:forEach>
        </select>
    </td>
  </tr>
  </table>
  
  <table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  <tr>
      <td width="10%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="Enviar">
        </div>
      </td>
    </tr>
  </table>  
</form>
