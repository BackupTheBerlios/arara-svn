

<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<%@ page import='java.util.*' %>
<%@ page import='net.indrix.arara.model.*' %>

<%
	PhotoModel model = new PhotoModel();
	List listOfPhotos = model.retrievePhotosIDsWithMoreComments(4);
	request.setAttribute("listOfPhotos", listOfPhotos);	
%>
		<table class="comBordaSimples"  width="100%" border="0" cellspacing="2" height="100%">
			<c:set var="w" value="${100}" />
			<tr>
				<td colspan="4">
					<b><fmt:message key="photos.with.more.comments" /></b><br><br>
				</td>
			</tr>
			<tr>
			
			<c:forEach items="${listOfPhotos}" var="photo">
				
				<c:if test="${photo.specie.id == -1}">
					<c:set var="identification" value="${true}" />
				</c:if>
			
				<td align="center">
					<a href="<c:url value="/servlet/searchPhotosWithMoreComments?action=BEGIN"/>">				
					    <!-- The linkKey below is set in the InitServlet class -->
						<img src="<c:url value="${linkKey}${photo.thumbnailRelativePathAsLink}"/>"
							width="${f:thumbnailWidth(w, photo.smallImage.width, photo.smallImage.height)}"
							height="${f:thumbnailHeight(w, photo.smallImage.width, photo.smallImage.height)}"
							align="bottom" border="0"/>
					</a> 
			    </td>
		    </c:forEach>
		    </tr>
	  	  </table>
