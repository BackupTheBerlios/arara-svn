<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<% User user = (User)session.getAttribute("user"); %>

<font size="2" face="Verdana">

&nbsp;<b><fmt:message key="menu.sounds.search" /></b>
<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/searchSounds?action=BEGIN"/>">
<fmt:message key="menu.sounds.search.all" /></a> 

<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchSoundsByFamily&action=BEGIN"/>">
<fmt:message key="menu.sounds.search.family" /></a>

<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchSoundsBySpecie&action=BEGIN"/>">
<fmt:message key="menu.sounds.search.specie" /></a>

<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchSoundsByCommonName&action=BEGIN"/>">
<fmt:message key="menu.sounds.search.commonName" /></a>

<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchSoundsByUser&action=BEGIN"/>">
<fmt:message key="menu.sounds.search.user" /></a>

<c:if test='<%= user != null %>'>
	<c:if test="${user.addSound == true}">
	<br/>
	&nbsp;<b><fmt:message key="menu.sounds.send" /></b>
	<br/>
	&nbsp;&nbsp;&nbsp;
	<a href="<c:url value="/servlet/initUploadSounds"/>">
	<fmt:message key="menu.sounds.send" /></a>
	</c:if>
</c:if>

</font>
