<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<c:if test="${user.addSound == true}">
	<tr>
		<td><font size="2" face="Verdana"> &nbsp;&nbsp;&nbsp;<a
			href="<c:url value="/servlet/initUploadSounds"/>"><fmt:message
			key="menu.sounds.send" /></a> </font></td>
	</tr>
</c:if>
