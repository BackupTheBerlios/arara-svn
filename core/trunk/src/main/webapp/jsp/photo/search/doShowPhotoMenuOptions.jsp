<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<c:if test="${identification != 'true'}">
	<c:set var="tableW" value="${70}" />
	<c:set var="imageAlign" value="${'left'}" />
	<c:set var="fontSize" value="${2}" />
</c:if>
<c:if test="${identification == 'true'}">
	<c:set var="w" value="${240}" />
	<c:set var="tableW" value="${100}" />
	<c:set var="imageAlign" value="${'center'}" />
	<c:set var="fontSize" value="${1}" />
</c:if>

<table class="lineBorder" width="100%">
	<tr>
		<td>
			<a href="<c:url value="/servlet/initCommentPhoto?photoId=${currentPhoto.id}&identification=${identification}"/>"> 
				<font size="1" face="Verdana"> 
					<fmt:message key="button.comment.tooltip" var="commentToolTip"/>
						<img title="${commentToolTip}" src="<c:url value="/jsp/images/comment.gif"/>" width="48" height="48" border="0"> 
				</font>
			</a>

			<c:if test="${(currentPhoto.user.id == user.id) or (user.admin == true)}">
				<a href="<c:url value="/servlet/initEditPhoto?photoId=${currentPhoto.id}&identification=${identification}"/>"> 
					<font size="1" face="Verdana"> 
						<fmt:message key="button.edit.tooltip" var="editToolTip"/>
						<img title="${editToolTip}"src="<c:url value="/jsp/images/edit.gif"/>"  width="48" height="48"border="0"> 
					</font>
				</a>
				<a href="<c:url value="/jsp/photo/search/deletePhoto.jsp?photoId=${currentPhoto.id}"/>"> 
					<font size="1" face="Verdana"> 
						<fmt:message key="button.delete.tooltip" var="deleteToolTip"/>
						<img title="${deleteToolTip}" src="<c:url value="/jsp/images/delete.gif"/>"  width="48" height="48" border="0"> 
					</font>
				</a>
			</c:if> 
		</td>
	</tr>
</table>
				