<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<% User user = (User)session.getAttribute("user"); %>

<font size="2" face="Verdana">
  	<li>
		<a href="<c:url value="/servlet/searchSounds?doAction=BEGIN"/>">
		<fmt:message key="menu.sounds.search.all" /></a> 
	</li>
  	<li>
		<a href="<c:url value="/servlet/searchRecentSounds?doAction=BEGIN"/>">
		<fmt:message key="menu.sounds.search.recent" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchSoundsByFamily&doAction=BEGIN"/>">
		<fmt:message key="menu.sounds.search.family" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchSoundsBySpecie&doAction=BEGIN"/>">
		<fmt:message key="menu.sounds.search.specie" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchSoundsByCommonName&doAction=BEGIN"/>">
		<fmt:message key="menu.sounds.search.commonName" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchSoundsByUser&doAction=BEGIN"/>">
		<fmt:message key="menu.sounds.search.user" /></a>
	</li>
</font>
