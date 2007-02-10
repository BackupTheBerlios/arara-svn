<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>
<c:set var="w" value="${240}" />
<c:forEach items="${listOfPhotos}" var="photo">
	<c:if test="${photo.id == photoToDelete}">
		<table width="240">
			<tr bgcolor="#005500">
				<td><font color="#ffffff"><fmt:message key="delete.confirm.question" /></font></td>
			</tr>
			<tr>
				<td align="left"><img
					src="<c:url value="${linkKey}${photo.thumbnailRelativePathAsLink}"/>"
					width="${f:thumbnailWidth(w, photo.smallImage.width, photo.smallImage.height)}"
					height="${f:thumbnailHeight(w, photo.smallImage.width, photo.smallImage.height)}"
					align="bottom" /></td>
			</tr>
			<tr bgcolor="#005500">
				<td align="center"><a
					href="<c:url value="/servlet/deletePhoto?photoId=${photo.id}"/>"><font
					color="#ffffff"><fmt:message key="delete.confirm" /></font></a> <a
					href="<c:url value="/index.jsp"/>"><font color="#ffffff"><fmt:message
					key="delete.cancel" /></font></a></td>
			</tr>
		</table>
	</c:if>
</c:forEach>
