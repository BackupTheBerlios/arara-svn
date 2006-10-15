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

<form name="commentForm" method="post"
	action="<c:url value="/servlet/commentPhoto"/>"><input type=hidden
	name="photoId" value="${currentPhoto.id}">

<table class="comBordaSimples" width="7n0%" border="0" cellspacing="2" bgcolor="#669900n">
	<tr>
		<td>
			<c:set var="w" value="${240}" /> <img
			src="<c:url value="/servlet/getThumbnail?photoId=${currentPhoto.id}"/>"
			width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
			height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
			align="bottom" />
			<br>		
			<b><fmt:message key="comment.title" /></b>
			<textarea rows="5" cols="40" name="comment"></textarea>
			<input type="SUBMIT" value="<fmt:message key="comment.submit"/>">
		</td>
	</tr>
</table>
</form>
