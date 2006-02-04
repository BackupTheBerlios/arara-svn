<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<tr> 
  <td>
  	<font size="2" face="Verdana">
  		<a href="<c:url value="/index.jsp?menuLevel1=0"/>">
  			<fmt:message key="menu.common.home"/>
  		</a>
  	</font>
  </td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  		<a href="<c:url value="/jsp/user/changePassword.jsp"/>">
  		<fmt:message key="menu.common.changePassword"/>
  		</a>
  </font></td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  	<a href="<c:url value="/servlet/logout"/>">
  		<fmt:message key="menu.common.logout"/>
  	</a>
  </font></td>
</tr>        
<tr bgcolor="#005599">
	<td></td>
</tr>
