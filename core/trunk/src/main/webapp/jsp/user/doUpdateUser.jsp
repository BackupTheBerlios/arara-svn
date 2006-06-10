<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>


<form method="post" action="<c:url value="/servlet/update"/>">

<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
    <tr>
      <td width="20%"><fmt:message key="user.login"/></td>
      <td width="80%">
        <input disabled type="text" name="login" size="16" maxlength="16" value="${user.login}">
      </td>
    </tr>
   <tr>
      <td width="20%"><fmt:message key="user.name"/></td>
      <td width="80%">
        <input type="text" name="name" size="64" maxlength="64" value="${user.name}">
      </td>
    </tr>
    <tr>
      <td width="20%"><fmt:message key="user.email"/></td>
      <td width="80%">
        <input type="text" name="email" size="64" maxlength="128" value="${user.email}" >
      </td>
    </tr>
    <tr>
      <td width="20%"><fmt:message key="user.language.label"/></td>
      <td width="80%">
       <select name="language">
			<c:if test="${user.language == 'pt'}">
				<option selected value="pt"><fmt:message key="user.language.pt"/></option>
				<option value="en"><fmt:message key="user.language.en"/></option>
			</c:if>
			<c:if test="${user.language == 'en'}">
				<option value="pt"><fmt:message key="user.language.pt"/></option>
				<option selected value="en"><fmt:message key="user.language.en"/></option>
			</c:if>
        </select>
      </td>
    </tr>
    
    <tr>
      <td width="20%"></td>
      <td width="80%">
		<c:if test="${user.emailOnNewPhoto == true}">
	        <input type="checkbox" checked name="emailOnNewPhoto" size="16" maxlength="16"><fmt:message key="user.emailOnNewPhoto"/>
		</c:if>
		<c:if test="${user.emailOnNewPhoto == false}">
	        <input type="checkbox" name="emailOnNewPhoto" size="16" maxlength="16"><fmt:message key="user.emailOnNewPhoto"/>
		</c:if>
      </td>
    </tr>
    <tr>
      <td width="20%"></td>
      <td width="80%">
		<c:if test="${user.emailOnNewIdPhoto == true}">
	        <input type="checkbox" checked name="emailOnNewIdPhoto" size="16" maxlength="16"><fmt:message key="user.emailOnNewIdPhoto"/>
		</c:if>
		<c:if test="${user.emailOnNewIdPhoto == false}">
	        <input type="checkbox" name="emailOnNewIdPhoto" size="16" maxlength="16"><fmt:message key="user.emailOnNewIdPhoto"/>
		</c:if>
      </td>
    </tr>
    <tr>
      <td width="20%"></td>
      <td width="80%">
		<c:if test="${user.emailOnNewSound == true}">
        	<input type="checkbox" checked name="emailOnNewSound" size="16" maxlength="16"><fmt:message key="user.emailOnNewSound"/>
		</c:if>
		<c:if test="${user.emailOnNewSound == false}">
        	<input type="checkbox" name="emailOnNewSound" size="16" maxlength="16"><fmt:message key="user.emailOnNewSound"/>
		</c:if>
      </td>
    </tr>
  </table>
  
<table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  <tr>
      <td width="20%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="<fmt:message key="user.update.submit"/>">
        </div>
      </td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
