<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<form method="post" action="<c:url value="/servlet/login"/>">
  
<table width="24%" border="0" cellspacing="2" bgcolor="#A6D2D2" align="center">
  <tr> 
    <td width="21%"><fmt:message key="menu.login"/></td>
    <td width="79%"> 
      <input type="text" name="login" value="Jefferson" size="16" maxlength="16">
    </td>
  </tr>
  <tr> 
    <td width="21%"><fmt:message key="menu.password"/></td>
    <td width="79%"> 
      <input type="password" name="password" value="jeff" size="16" maxlength="16">
    </td>
  </tr>
  <tr>
    <td width="79%"><input type="SUBMIT" value="Entrar"></td>
  </tr>
</table>
  
<p>&nbsp;</p>
</form>
