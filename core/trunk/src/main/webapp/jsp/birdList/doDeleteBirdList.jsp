<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:forEach items="${userLists}" var="birdList">
	<c:if test="${birdList.id == param.listId}">

		<table align="center" class="formBorder" width="40%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
			<tr height="25" bgcolor="#000000">
				<td align="center" colspan="3">
					<font color="#FFFFFF">
						<fmt:message key="birdlist.delete.confirm.question" />
					</font>
				</td>
			</tr>
			<tr height="5">
				<td colspan="3"></td>
			</tr>
			<tr height="10">
				<td align="center" colspan="3">
					<b><font color="#FFFFFF">
					<table bgcolor="#000000" width="50%">
						<tr>
							<td width="50%">
								<fmt:message key="birdlist.list.name.label" />
							</td>
							<td width="50%">
								<fmt:message key="birdlist.list.type.label" />
							</td>
						</tr>
					</table>
					</font></b>							
					<table bgcolor="#AAD1D8" width="50%">
						<tr>
							<td width="50%">
								${birdList.name}								
							</td>
							<td width="50%">
								<fmt:message key="${birdList.typeAsString}" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center">
					<font color="#000000">
						<a href="<c:url value="/servlet/deleteBirdList?listId=${birdList.id}"/>"><fmt:message key="delete.confirm" /></a> 
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
