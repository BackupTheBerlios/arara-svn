<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<% User user = (User)session.getAttribute("user"); %>

<font size="2" face="Verdana">

<!--
<li>
  <a href="#"><b><fmt:message key="menu.sounds.search" /></b></a>
  <ul>
-->
  	<li>
		<a href="<c:url value="/servlet/searchSounds?action=BEGIN"/>">
		<fmt:message key="menu.sounds.search.all" /></a> 
	</li>
  	<li>
		<a href="<c:url value="/servlet/searchRecentSounds?action=BEGIN"/>">
		<fmt:message key="menu.sounds.search.recent" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchSoundsByFamily&action=BEGIN"/>">
		<fmt:message key="menu.sounds.search.family" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchSoundsBySpecie&action=BEGIN"/>">
		<fmt:message key="menu.sounds.search.specie" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchSoundsByCommonName&action=BEGIN"/>">
		<fmt:message key="menu.sounds.search.commonName" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchSoundsByUser&action=BEGIN"/>">
		<fmt:message key="menu.sounds.search.user" /></a>
	</li>
<!--	
  </ul>
</li>
-->

<c:if test='<%= user != null %>'>
	<c:if test="${user.addSound == true}">
<!--
	<li>
    	<a href="#"><b><fmt:message key="menu.sounds.send" /></b></a>
		<ul>
-->
			<li>
				<a href="<c:url value="/servlet/initUploadSounds"/>">
				<fmt:message key="menu.sounds.send" /></a>
			</li>
<!--	
		</ul>
	</li>
-->
	</c:if>
</c:if>

</font>
