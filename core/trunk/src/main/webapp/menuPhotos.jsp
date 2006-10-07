<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<% User user = (User)session.getAttribute("user"); %>

<font size="2" face="Verdana"> 

&nbsp;<b><fmt:message key="menu.photos.search" /></b>
<br/>

&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/searchPhotos?action=BEGIN"/>">
<fmt:message key="menu.photos.search.all" /></a>
<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchByFamily?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByFamily.jsp&servletToCall=/servlet/searchPhotosByFamily&action=BEGIN"/>">
<fmt:message key="menu.photos.search.family" /></a>
<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchBySpecie?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataBySpecie.jsp&servletToCall=/servlet/searchPhotosBySpecie&action=BEGIN"/>">
<fmt:message key="menu.photos.search.specie" /></a>
<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchByCommonName?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByCommonName.jsp&servletToCall=/servlet/searchPhotosByCommonName&action=BEGIN"/>">
<fmt:message key="menu.photos.search.commonName" /></a>
<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/initSearchByUser?nextPage=/frame.jsp&pageToShow=/jsp/common/display/doShowDataByUser.jsp&servletToCall=/servlet/searchPhotosByUser&action=BEGIN"/>">
<fmt:message key="menu.photos.search.user" /></a>
<br/>
&nbsp;&nbsp;&nbsp;
<a href="<c:url value="/servlet/searchPhotos?action=BEGIN&identification=true"/>">
<fmt:message key="menu.photos.search.identification" /></a>

<c:if test='<%= user != null %>'>
<br/>
&nbsp;<b><fmt:message key="menu.photos.send" /></b>

<c:if test="${user.addPhoto == true}">
	<br/>
	&nbsp;&nbsp;&nbsp;
	<a href="<c:url value="/servlet/initUploadPhotos"/>">
	<fmt:message key="menu.photos.send" /> </a>

	<br/>
	&nbsp;&nbsp;&nbsp;
	<a href="<c:url value="/servlet/initUploadPhotosForIdentification"/>">
	<fmt:message key="menu.photos.send.identification" /> </a>
</c:if>
</c:if>

<c:if test='<%= user != null %>'>
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
<c:if test='<%= user != null %>'>
<c:if test="${param.currentPage == 'showOnePhoto'}">
	<c:if test="${identification != true}">
		<br/>					
		&nbsp;&nbsp;&nbsp;
		<a href="<c:url value="/jsp/photo/comment/commentPhoto.jsp"/>">
		<fmt:message key="menu.photos.comment" /> </a>
	</c:if>
</c:if>
</c:if>
<!--
  <c:if test="${param.currentPage == 'showOnePhoto'}">
	  <c:if test="${identification == true}">
		<br/>					
	      	&nbsp;&nbsp;&nbsp;
	      	<a href="<c:url value="/jsp/photo/identify/identifyPhoto.jsp"/>">
	      	<fmt:message key="menu.photos.identify"/>
	      	</a>
	  </c:if>
  </c:if>
-->
		
</font>






