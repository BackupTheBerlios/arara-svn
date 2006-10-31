<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<c:if test="${!empty currentPhoto.comments}">
<table class="lineBorder" align="center" width="99%">
<tr><td>
	<table border="0" width="100%">
		<tr>
			<td align="center">
			<b>
			<font size="3" face="Verdana"> 
				<fmt:message key="show.one.photo.comments" />
			</font>
			</b>
			</td>
		</tr>
	</table>
	
	<c:set var="count" value="${0}" />
	<c:set var="color" value="${'#339966'}" />
	<c:forEach items="${currentPhoto.comments}" var="comment">
		<font size="1" face="Verdana"> 
	
		<c:if test="${(count % 2) == 0}">
			<c:set var="color" value="${'#339966'}" />
		</c:if>

		<c:if test="${(count % 2) != 0}">
			<c:set var="color" value="${'#D8D8D8'}" />
		</c:if>
	
		<table width="100%" bgcolor="${color}">
			<tr>
				<td>
				<table border="0" width="100%">
					<tr>
						<td align="left">
							<font color="#005500" size="2" face="Verdana">
								<b><c:out	value="${comment.user.login}" /></b>
							</font>
							
						<td>
						<td align="right">
							<font color="#005500" size="2" face="Verdana">
								<b><c:out value="${f:dateTimeAsString(comment.date)}" /></b>
							</font>
						<td>
					</tr>
				</table>
				<table width="100%" align="center" border="0" cellpadding="0" cellspacing="6">
					<tr>
						<td align="left">
							<font size="2" color="#000000" face="Verdana">
<pre>
<c:out value="${comment.comment}" />
</pre>
							</font>
						</td>
					</tr>
				</table>

				</td>
			</tr>
		</table>
		</font>	
		<c:set var="count" value="${count + 1}" />
	</c:forEach>
</c:if> 

<a href="<c:url value="/servlet/initCommentPhoto?photoId=${currentPhoto.id}&identification=${identification}"/>"> 
	<font size="1" face="Verdana"> 
		<fmt:message key="button.comment.tooltip" var="commentToolTip"/>
			<img title="${commentToolTip}" src="<c:url value="/jsp/images/comment.gif"/>" width="48" height="48"> 
	</font>
</a>
</td>
</tr>
</table>
