<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<c:if test="${user.addPhoto == true}">
<br/>
<font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<a
			href="<c:url value="/servlet/initUploadPhotos"/>"><fmt:message
			key="menu.photos.send" /> </a> </font>

<br/>
<font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<a
			href="<c:url value="/servlet/initUploadPhotosForIdentification"/>"><fmt:message
			key="menu.photos.send.identification" /> </a> </font>
</c:if>
<!--  -->
<br/>
<c:if test="${param.submenu == 'changePhoto'}">
	<c:if test="${(param.userId == user.id) or (user.admin == true)}">
		<c:if test="${viewMode == 'viewMode'}">
			<font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<a
					href="<c:url value="/servlet/initEditPhoto?identification=${identification}"/>"><fmt:message
					key="menu.photos.edit" /> </a> </font>
			<font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<a
					href="<c:url value="/jsp/photo/search/deletePhoto.jsp?photoId=${param.photoId}"/>"><fmt:message
					key="menu.photos.delete" /> </a> </font>
		</c:if>
	</c:if>
</c:if>
<br/>
<c:if test="${param.currentPage == 'showOnePhoto'}">
	<c:if test="${identification != true}">
<font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<a
				href="<c:url value="/jsp/photo/comment/commentPhoto.jsp"/>"><fmt:message
				key="menu.photos.comment" /> </a> </font>
	</c:if>
</c:if>

<!--
  <c:if test="${param.currentPage == 'showOnePhoto'}">
	  <c:if test="${identification == true}">
	    <tr> 
	      <td><font size="2" face="Verdana">
	      	&nbsp;&nbsp;&nbsp;<a href="<c:url value="/jsp/photo/identify/identifyPhoto.jsp"/>"><fmt:message key="menu.photos.identify"/>
	      	</a>
	      </font></td>
	    </tr>
	  </c:if>
  </c:if>
-->
