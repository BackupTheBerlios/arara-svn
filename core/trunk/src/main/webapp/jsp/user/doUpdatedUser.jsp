<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<div align="center"></div>
<table width="100%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr>
      
    <td width="15%"> 
      <div align="left"><b><fmt:message key="user.updated.success"/></b></div><br>
      <b><fmt:message key="user.name"/>:</b> ${user.name}<br>
      <b><fmt:message key="user.login"/>:</b> ${user.login}<br>
      <b><fmt:message key="user.email"/>:</b>${user.email}<br>
      <b><fmt:message key="user.language.label"/>:</b>${user.language}<br>

      <c:if test="${user.emailOnNewPhoto == true}">
	      <br><fmt:message key="user.registered.emailOnNewPhoto"/>
      </c:if>
      <c:if test="${user.emailOnNewPhoto == false}">
    	  <br><fmt:message key="user.registered.no.emailOnNewPhoto"/>
      </c:if>

      <c:if test="${user.emailOnNewIdPhoto == true}">
	      <br><fmt:message key="user.registered.emailOnNewIdPhoto"/>
      </c:if>
      <c:if test="${user.emailOnNewIdPhoto == false}">
    	  <br><fmt:message key="user.registered.no.emailOnNewIdPhoto"/>
      </c:if>

      <c:if test="${user.emailOnNewSound == true}">
	      <br><fmt:message key="user.registered.emailOnNewSound"/>
      </c:if>
      <c:if test="${user.emailOnNewSound == false}">
    	  <br><fmt:message key="user.registered.no.emailOnNewSound"/>
      </c:if>
    </td>
    </tr>
  </table>
<div align="left"></div>