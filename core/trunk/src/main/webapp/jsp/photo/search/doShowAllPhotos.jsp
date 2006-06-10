<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>
<table width="100%" border="0" cellspacing="2" height="100%">
  <tr><td>
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
			  		<a href="<c:url value="${linkKey}${photo.sound.relativePathAsLink}"/>" target="_blank">
					  	<font size="1" face="Verdana">
					  		<img src="<c:url value="/jsp/images/sound.gif"/>" width="20" height="20">
					  	</font>
			  		</a>
		   		</c:if>
		        <a href="<c:url value="/servlet/showOnePhoto?photoId=${photo.id}&identification=${identification}"/>">
		            <img src="<c:url value="/servlet/getThumbnail?photoId=${photo.id}&identification=${identification}"/>" 
			     		width="${f:thumbnailWidth(w, photo.smallImage.width, photo.smallImage.height)}" 
		                height="${f:thumbnailHeight(w, photo.smallImage.width, photo.smallImage.height)}" 
				   	  	align="bottom"/></a>			   	  	
		        <c:if test="${photo.specie.id != -1}">
			        <font size="1" face="Verdana"><br><fmt:message key="family"/>:
		              <c:if test="${photo.specie.family.subFamilyName == null}">
			            <a href="<c:url value="/servlet/searchPhotosByFamily?id=${photo.specie.family.id}&action=BEGIN"/>">${photo.specie.family.name}
			            </a>
			          </c:if>  
		              <c:if test="${photo.specie.family.subFamilyName != null}">
			            <a href="<c:url value="/servlet/searchPhotosByFamily?id=${photo.specie.family.id}&action=BEGIN"/>">${photo.specie.family.name} (${photo.specie.family.subFamilyName})
			            </a></font>
			          </c:if>  
			        <font size="1" face="Verdana"><br><fmt:message key="specie"/>:
			          <a href="<c:url value="/servlet/searchPhotosBySpecie?id=${photo.specie.id}&action=BEGIN"/>">${photo.specie.name}
			          </a></font>
			        <font size="1" face="Verdana"><br><fmt:message key="common.name"/>:
			          <a href="<c:url value="/servlet/searchPhotosBySpecie?id=${photo.specie.id}&action=BEGIN"/>">${photo.specie.commonNameString}
			          </a></font>
		   		</c:if>
		        <c:if test="${photo.specie.id == -1}">
			        <b><font color="#FF0000" size="1" face="Verdana"><br><fmt:message key="photo.not.identified.title"/>
			        </font></b>
		   		</c:if>
		        <font size="1" face="Verdana"><br><fmt:message key="user.title"/>:
		          <a href="<c:url value="/servlet/searchPhotosByUser?id=${photo.user.id}&action=BEGIN"/>">${photo.user.login}
		          </a></font>
		        <c:if test="${photo.specie.id == -1}">
			        <font size="1" face="Verdana"><br>
			        <fmt:message key="photo.click.msg1"/> <a href="<c:url value="/servlet/initIdentification?photoId=${photo.id}"/>"><fmt:message key="photo.click.here"/></a> <fmt:message key="photo.click.msg2"/>
			        </font>
		   		</c:if>
		   </td>
		   <c:if test="${index > 2}">
		       <c:set var="index" value="${0}"/>
		       <c:set var="newLine" value="${true}"/>
		       </tr>
		   </c:if>
		</c:forEach>
	</table>
  </td></tr>
  <c:import url="/jsp/photo/search/pagination.jsp"/>
</table>   
