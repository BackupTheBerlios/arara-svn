<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<table class="lineBorder" width="100%">
	<tr>
		<td>
			<a href="<c:url value="/servlet/initCommentPhoto?photoId=${currentPhoto.id}&identification=${currentPhoto.specie.id == -1}"/>"> 
				<font size="1" face="Verdana"> 
					<fmt:message key="button.comment.tooltip" var="commentToolTip"/>
						<img title="${commentToolTip}" src="<%= request.getContextPath()%>/jsp/images/comment.gif" width="48" height="48" border="0"> 
				</font>
			</a>

			<c:if test="${(currentPhoto.user.id == user.id) || (user.admin) || (user.photoEditModerator && currentPhoto.specie.id != -1)}">
					<a href="<c:url value="/servlet/initEditPhoto?photoId=${currentPhoto.id}&identification=${currentPhoto.specie.id == -1}"/>"> 
						<font size="1" face="Verdana"> 
							<fmt:message key="button.edit.tooltip" var="editToolTip"/>
							<img title="${editToolTip}"src="<%= request.getContextPath()%>/jsp/images/edit.gif"  width="48" height="48"border="0"> 
						</font>
					</a>
			</c:if> 
			<c:if test="${(currentPhoto.user.id == user.id) || (user.admin)}">
					<a href="<c:url value="/jsp/photo/search/deletePhoto.jsp?photoId=${currentPhoto.id}"/>"> 
						<font size="1" face="Verdana"> 
							<fmt:message key="button.delete.tooltip" var="deleteToolTip"/>
							<img title="${deleteToolTip}" src="<%= request.getContextPath()%>/jsp/images/delete.gif"  width="48" height="48" border="0"> 
						</font>
					</a>
			</c:if>
		</td>
	</tr>
</table>
				