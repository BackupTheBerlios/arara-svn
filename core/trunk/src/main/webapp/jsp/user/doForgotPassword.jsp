<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<form method="post" action="<c:url value="/servlet/sendPassword"/>">

<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr>
      <td width="15%"><fmt:message key="user.forgot.password.login"/></td>
      <td width="85%">
        <input type="text" name="login" size="16" maxlength="16">
      </td>
    </tr>
  </table>
  
<table width="100%" border="0" cellspacing="2" align="center" bgcolor="#A6D2D2">
  <tr>
      <td width="15%"></td>
      <td> 
        <div align="left">
           <input type="SUBMIT" value="<fmt:message key="user.login.submit"/>">
        </div>
      </td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
