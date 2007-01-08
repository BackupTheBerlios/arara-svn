<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<br><br>
<c:forEach items="${listOfSounds}" var="sound">
	<c:if test="${sound.id == soundToDelete}">		
		<table align="center" class="formBorder" width="40%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
			<tr height="25" bgcolor="#000000">
				<td align="center" colspan="3">
					<font color="#FFFFFF">
						<fmt:message key="delete.sound.confirm.question" />
					</font>
				</td>
			</tr>
			<tr height="5">
				<td colspan="3"></td>
			</tr>
			<tr height="10">
				<td align="center" colspan="3">
					<br>${sound.specie.name} - ${sound.specie.commonNameString}<br><br>
				</td>
			</tr>
			<tr>
				<td align="center">
					<font color="#000000">
						<a href="<c:url value="/servlet/deleteSound?soundId=${sound.id}"/>"><fmt:message key="delete.confirm" /></a> 
						<a href="<c:url value="/index.jsp"/>"><fmt:message key="delete.cancel" /></a>
					</font>
				</td>
			</tr>
			<tr height="5">
				<td colspan="3"></td>
			</tr>
		</table>

	</c:if>
</c:forEach>
