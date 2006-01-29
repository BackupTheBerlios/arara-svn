<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>


<form method="post" action="<c:url value="/servlet/register"/>">

<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr>
      <td width="15%"><fmt:message key="user.name"/></td>
      <td width="85%">
        <input type="text" name="name" size="64" maxlength="64">
      </td>
    </tr>
    <tr>
      <td width="15%"><fmt:message key="user.email"/></td>
      <td width="85%">
        <input type="text" name="email" size="64" maxlength="128">
      </td>
    </tr>
    <tr>
      <td width="15%"><fmt:message key="user.login"/></td>
      <td width="85%">
        <input type="text" name="login" size="16" maxlength="16">
      </td>
    </tr>
    <tr>
      <td width="15%"><fmt:message key="user.password"/></td>
      <td width="85%">
        <input type="password" name="password" size="16" maxlength="16">
      </td>
    </tr>
    <tr>
      <td width="15%"><fmt:message key="user.password2"/></td>
      <td width="85%">
        <input type="password" name="password2" size="16" maxlength="16">
      </td>
    </tr>
    <tr>
      <td width="15%"></td>
      <td width="85%">
        <input type="checkbox" name="emailOnNewPhoto" size="16" maxlength="16" checked><fmt:message key="user.emailOnNewPhoto"/>
      </td>
    </tr>
    <tr>
      <td width="15%"></td>
      <td width="85%">
        <input type="checkbox" name="emailOnNewSound" size="16" maxlength="16" checked><fmt:message key="user.emailOnNewSound"/>
      </td>
    </tr>
  </table>
  
<table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  <tr>
      <td width="15%"></td>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="<fmt:message key="user.register.submit"/>">
        </div>
      </td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
