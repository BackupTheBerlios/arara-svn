<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<font size="2" face="Verdana">
<c:if test="${!empty currentPhoto.identifications}">
	<table border="0" width="100%">
	  <tr><td align="center"><b><fmt:message key="show.one.photo.identifications"/></b></td></tr>
	</table>
	<table bgcolor="#005500" width="100%">
	  <tr>
	  	<td width="14%"><b><font color="#FFFFFF"><fmt:message key="user.login"/></font></b></td>
	  	<td width="18%"><b><font color="#FFFFFF"><fmt:message key="photo.date"/></font></b></td>
	  	<td width="24%"><b><font color="#FFFFFF"><fmt:message key="family"/></font></b></td>
	  	<td width="24%"><b><font color="#FFFFFF"><fmt:message key="specie"/></font></b></td>
	  	<td width="10%"><b><font color="#FFFFFF"><fmt:message key="specie.sex"/></font></b></td>
	  	<td width="10%"><b><font color="#FFFFFF"><fmt:message key="specie.age"/></font></b></td>
	  </tr>	  
	</table>

  <c:set var="count" value="${0}"/>
  <c:set var="color" value="${'#339966'}"/>
	<c:forEach items="${currentPhoto.identifications}" var="identification">
	  <c:if test="${(count % 2) == 0}">
		<c:set var="color" value="${'#339966'}"/>
	  </c:if>

	  <c:if test="${(count % 2) != 0}">
		<c:set var="color" value="${'#D8D8D8'}"/>
	  </c:if>
	  
	  <table bgcolor="${color}" width="100%">
	  	<tr>
		  	<td width="14%"><font size="1" face="Verdana">${identification.user.login}</font></td>
		  	<td width="18%"><font size="1" face="Verdana">${f:dateTimeAsString(identification.date)}</font></td>
		  	<td width="24%"><font size="1" face="Verdana">${identification.specie.family.name}</font></td>
		  	<td width="24%"><font size="1" face="Verdana">${identification.specie.name}</font></td>
		  	<td width="10%" ><font size="1" face="Verdana">${identification.sex.sex}</font></td>
		  	<td width="10%" ><font size="1" face="Verdana">${identification.age.age}</font></td>
		</tr>
	  	<tr>
		  	<td width="14%" align="left"><font size="1" face="Verdana"><fmt:message key="photo.identify.comment"/></font></td>
		  	<td width="86%" colspan="2" align="left"><font size="1" face="Verdana">${identification.comment}</font></td>
		</tr>
	  </table>

      <c:set var="count" value="${count + 1}"/>
	</c:forEach>
	<br>
</c:if>
</font>
