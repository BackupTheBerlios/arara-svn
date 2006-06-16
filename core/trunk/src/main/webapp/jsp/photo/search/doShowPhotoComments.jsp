<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<font size="2" face="Verdana"> <c:if
	test="${!empty currentPhoto.comments}">
	<table border="0" width="75%">
		<tr>
			<td align="center"><b><fmt:message key="show.one.photo.comments" /></b></td>
		</tr>
	</table>
	<c:forEach items="${currentPhoto.comments}" var="comment">
		<table border="1" width="75%">
			<tr>
				<td>
				<table border="0" width="100%">
					<tr>
						<td align="left"><b><font color="#005500"><c:out
							value="${comment.user.login}" /></font></b>
						<td>
						<td align="right"><b><font color="#005500"><c:out
							value="${f:dateTimeAsString(comment.date)}" /></font></b>
						<td>
					</tr>
				</table>
				<table align="center" width="90%" border="0" cellpadding="0"
					cellspacing="6">
					<tr>
						<td><font color="#0000FF"><c:out value="${comment.comment}" /></font></td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
		<br>
	</c:forEach>
</c:if> </font>
