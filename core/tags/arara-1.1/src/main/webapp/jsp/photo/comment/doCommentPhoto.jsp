<%@ taglib uri="/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<style type="text/css">
table.comBordaSimples {
	border-collapse: collapse; /* CSS2 */
	border: 2px solid #404040;
}
table.comBordaSimples tr {
	border: 1px solid #404040;
}
</style>

<br>
<form name="commentForm" method="post"
	action="<c:url value="/servlet/commentPhoto"/>"><input type=hidden
	name="photoId" value="${currentPhoto.id}">

<table align="center" class="formBorder" width="70%" border="0" cellspacing="2">
	<tr height="10" bgcolor="#000000">
		<td colspan="2"></td>
	</tr>
	<tr height="5">
		<td colspan="2"></td>
	</tr>
	<tr>
		<td colspacing="2" align="left">
			<c:set var="w" value="${240}" />
			<img
			src="<c:url value="/servlet/getThumbnail?photoId=${currentPhoto.id}"/>"
			width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
			height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
			align="bottom" />
		</td>
		<td colspacing="2" align="left" valign="bottom">
			<br>		
			<b><fmt:message key="comment.title" /></b><br>
			<textarea rows="4" cols="40" name="comment"></textarea><br>
			<input type="SUBMIT" value="<fmt:message key="comment.submit"/>">
		</td>
	</tr>
	<tr height="5">
		<td colspan="2"></td>
  	</tr>
</table>
</form>
