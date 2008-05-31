<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<font size="2" face="Verdana"> 

<!--
  <a href="#"><b><fmt:message key="menu.photos.search" /></b></a>
-->
  	<li>
		<a href="<c:url value="/servlet/searchPhotos?doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.all" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/searchRecentPhotos?doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.recent" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/searchPhotosWithMoreComments?doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.more.comments" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchPhotosByFamily&doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.family" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchPhotosBySpecie&doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.specie" /></a>
	</li>
<!--
  	<li>
		<a href="<c:url value="/servlet/initSearchByEnglishName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByEnglishName.jsp&servletToCall=/servlet/searchPhotosByEnglishName&doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.english.name" /></a>
	</li>
-->	
  	<li>
		<a href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchPhotosByCommonName&doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.commonName" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByPlace?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByPlace.jsp&servletToCall=/servlet/searchPhotosByPlace&doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.place" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchPhotosByUser&doAction=BEGIN"/>">
		<fmt:message key="menu.photos.search.user" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/searchPhotos?doAction=BEGIN&identification=true"/>">
		<fmt:message key="menu.photos.search.identification" /></a>
	</li>
</font>

<!--
<c:if test="${user != null}">
<c:if test="${param.submenu == 'changePhoto'}">
	<c:if test="${(param.userId == user.id) or (user.admin == true)}">
		<c:if test="${viewMode == 'viewMode'}">
			<br/>		
			&nbsp;&nbsp;&nbsp;
			<a href="<c:url value="/servlet/initEditPhoto?identification=${identification}"/>">
			<fmt:message key="menu.photos.edit" /> </a> 
			<br/>					
			&nbsp;&nbsp;&nbsp;
			<a href="<c:url value="/jsp/photo/search/deletePhoto.jsp?photoId=${param.photoId}"/>">
			<fmt:message key="menu.photos.delete" /> </a>
		</c:if>
	</c:if>
</c:if>
</c:if>
<br/>
<c:if test="${user != null}">
<c:if test="${param.currentPage == 'showOnePhoto'}">
	<c:if test="${identification != true}">
		<br/>					
		&nbsp;&nbsp;&nbsp;
		<a href="<c:url value="/jsp/photo/comment/commentPhoto.jsp"/>">
		<fmt:message key="menu.photos.comment" /> </a>
	</c:if>
</c:if>
</c:if>
-->







