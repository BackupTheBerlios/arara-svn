<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<font size="2" face="Verdana">

<a href="<c:url value="/servlet/searchSounds?action=BEGIN"/>">
<fmt:message key="menu.sounds.search.all" /></a>

<br/>
<fmt:message key="menu.sounds.search" /> 

<br/>
<a href="<c:url value="/servlet/searchSounds?action=BEGIN"/>">
<fmt:message key="menu.sounds.search.all" /></a> 

<br/>
<a href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchSoundsByFamily"/>">
<fmt:message key="menu.sounds.search.family" /></a>

<br/>
<a href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchSoundsBySpecie"/>">
<fmt:message key="menu.sounds.search.specie" /></a>

<br/>
<a href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchSoundsByCommonName"/>">
<fmt:message key="menu.sounds.search.commonName" /></a>

<br/>
<a href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchSoundsByUser&action=BEGIN"/>">
<fmt:message key="menu.sounds.search.user" /></a>

</font>
