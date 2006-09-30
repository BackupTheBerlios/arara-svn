<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<tr>
	<td><b><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<fmt:message
		key="menu.sounds.search" /> </font></b></td>
</tr>

<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/searchSounds?action=BEGIN"/>"><fmt:message
		key="menu.sounds.search.all" /></a> </font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchSoundsByFamily"/>"><fmt:message
		key="menu.sounds.search.family" /></a></font></td>
</tr>
		             
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchSoundsBySpecie"/>"><fmt:message
		key="menu.sounds.search.specie" /></a></font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchSoundsByCommonName"/>"><fmt:message
		key="menu.sounds.search.commonName" /></a></font></td>
</tr>
<tr>
	<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
		href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchSoundsByUser&action=BEGIN"/>"><fmt:message
		key="menu.sounds.search.user" /></a></font></td>
</tr>
