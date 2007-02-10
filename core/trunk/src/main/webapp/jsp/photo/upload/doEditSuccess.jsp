<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<c:set var="w" value="${120}" />


<br>
<table align="center" class="formBorder" width="40%" border="0" cellspacing="2">
	<tr height="10" bgcolor="#000000">
		<td></td>
	</tr>
	<tr height="5">
		<td></td>
	</tr>
	<tr align="center">
		<td>
			<a href="<c:url value="/servlet/showOnePhoto?nextPage=/frame.jsp&pageToShow=/jsp/photo/search/doShowOnePhoto.jsp&photoId=${currentPhoto.id}&identification=${identification}"/>">
			<img
				src="<c:url value="${linkKey}${currentPhoto.thumbnailRelativePathAsLink}"/>"
				width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
				height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
				align="bottom" /> </a>
	
			<br>
			<b><fmt:message key="edit.success" /></b>
		</td>
	</tr>
	<tr height="5">
		<td colspan="2"></td>
  	</tr>
</table>
