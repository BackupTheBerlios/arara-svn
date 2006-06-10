<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<table width="100%" border="0" cellspacing="2" height="100%">
<tr>
<td>
<table width="100%" border="0" cellspacing="2" height="100%">

<c:if test="${empty listOfPhotos}">
   <br><h3><fmt:message key="show.all.photos.not.found"/></h3>
</c:if>
<c:set var="index" value="${0}"/>
<c:set var="newLine" value="${true}"/>
<c:set var="w" value="${120}"/>
<c:forEach items="${listOfPhotos}" var="photo">
   <c:set var="index" value="${index + 1}"/>
   <c:if test="${newLine == true}">
     <c:set var="newLine" value="${false}"/>
     <tr>
   </c:if>
   <td align="left">
   		  <c:if test="${photo.soundAvailable == true}">
   		    
       		<img src="/teste/jsp/images/sound.gif" width="20" height="20"
					 onClick="window.open('<c:url value="/jsp/sound/play/playSound.jsp?specieId=${photo.specie.id}"/>','mywindow','width=200,height=120')"
       		>
       		
   		  </c:if>
        <a href="<c:url value="/servlet/showOnePhoto?photoId=${photo.id}&identification=${identification}"/>">
            <img src="<c:url value="/servlet/getThumbnail?photoId=${photo.id}&identification=${identification}"/>" 
	     		width="${f:thumbnailWidth(w, photo.smallImage.width, photo.smallImage.height)}" 
                height="${f:thumbnailHeight(w, photo.smallImage.width, photo.smallImage.height)}" 
		   	  	align="bottom"/>
        </a>
        <font size="2" face="Verdana"><br><fmt:message key="family"/>:
          <a href="<c:url value="/servlet/searchPhotosByFamily?familyId=${photo.specie.family.id}&action=BEGIN"/>">${photo.specie.family.name}
          </a></font>
        <font size="2" face="Verdana"><br><fmt:message key="specie"/>:
          <a href="<c:url value="/servlet/searchPhotosBySpecie?specieId=${photo.specie.id}&action=BEGIN"/>">${photo.specie.name}
          </a></font>
        <font size="2" face="Verdana"><br><fmt:message key="common.name"/>:
          <a href="<c:url value="/servlet/searchPhotosBySpecie?specieId=${photo.specie.id}&action=BEGIN"/>">${photo.specie.commonNameString}
          </a></font>
        <font size="2" face="Verdana"><br><fmt:message key="user.title"/>:
          <a href="<c:url value="/servlet/searchPhotosByUser?userId=${photo.user.id}&action=BEGIN"/>">${photo.user.login}
          </a></font>
   </td>
   <c:if test="${index > 2}">
       <c:set var="index" value="${0}"/>
       <c:set var="newLine" value="${true}"/>
       </tr>
   </c:if>
</c:forEach>
</table>
</td>
</tr>
<c:import url="/jsp/photo/search/pagination.jsp"/>
</table>   