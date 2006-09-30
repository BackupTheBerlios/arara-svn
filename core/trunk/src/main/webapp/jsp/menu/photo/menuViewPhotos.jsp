<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<tr>
	<td><b><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<fmt:message
		key="menu.photos.search" /> </font></b></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/searchPhotos?action=BEGIN"/>"><fmt:message
		key="menu.photos.search.all" /></a> </font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchPhotosByFamily&action=BEGIN"/>"><fmt:message
		key="menu.photos.search.family" /></a></font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchPhotosBySpecie&action=BEGIN"/>"><fmt:message
		key="menu.photos.search.specie" /></a></font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchPhotosByCommonName&action=BEGIN"/>"><fmt:message
		key="menu.photos.search.commonName" /></a></font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchPhotosByUser&action=BEGIN"/>"><fmt:message
		key="menu.photos.search.user" /></a></font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/searchPhotos?action=BEGIN&identification=true"/>"><fmt:message
		key="menu.photos.search.identification" /></a></font></td>
</tr>
