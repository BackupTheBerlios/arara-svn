<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<tr> 
  <td><b><font size="2" face="Verdana">
  	&nbsp;&nbsp;&nbsp;<fmt:message key="menu.photos.search"/>
  </font></b></td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/searchPhotos?action=BEGIN"/>"><fmt:message key="menu.photos.search.all"/></a>
  </font></td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosByFamily"/>"><fmt:message key="menu.photos.search.family"/></a></font></td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosBySpecie"/>"><fmt:message key="menu.photos.search.specie"/></a></font></td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosByCommonName"/>"><fmt:message key="menu.photos.search.commonName"/></a></font></td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosByUser"/>"><fmt:message key="menu.photos.search.user"/></a></font></td>
</tr>
<tr> 
  <td><font size="2" face="Verdana">
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/searchPhotos?action=BEGIN&identification=true"/>"><fmt:message key="menu.photos.search.identification"/></a></font></td>
</tr>
