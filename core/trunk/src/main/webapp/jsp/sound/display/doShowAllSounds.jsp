<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>


<table width="100%" border="0" cellspacing="1">
	<tr valign="top">
		<td>
		<table border="0" width="100%">
			<tr>
				<td align="center"><b><fmt:message key="all.sounds.title" /></b></td>
			</tr>
		</table>

		<table width="100%" border="0" cellspacing="1">
			<tr>
				<td><c:if test="${empty listOfSounds}">
					<br>
					<h3>Não há sons para a opção selecionada.</h3>
				</c:if>
				<table bgcolor="#005500" width="100%">
					<tr>
						<td width="14%"><b><font color="#FFFFFF"><fmt:message
							key="user.login" /></font></b></td>
						<td width="24%"><b><font color="#FFFFFF"><fmt:message key="family" /></font></b></td>
						<td width="24%"><b><font color="#FFFFFF"><fmt:message key="specie" /></font></b></td>
						<td width="10%"><b><font color="#FFFFFF"><fmt:message
							key="specie.sex" /></font></b></td>
						<td width="10%"><b><font color="#FFFFFF"><fmt:message
							key="specie.age" /></font></b></td>
						<td width="10%"><b><font color="#FFFFFF"><fmt:message
							key="sound.link.title" /></font></b></td>
					</tr>
				</table>
				<c:set var="count" value="${0}" /> <c:set var="color"
					value="${'#D8D8D8'}" /> <c:forEach items="${listOfSounds}"
					var="sound">
					<c:if test="${(count % 2) == 0}">
						<c:set var="color" value="${'#D8D8D8'}" />
					</c:if>

					<c:if test="${(count % 2) != 0}">
						<c:set var="color" value="${'#339966'}" />
					</c:if>

					<table bgcolor="${color}" width="100%">
						<tr>
							<td width="14%"><font size="1" face="Verdana">${sound.user.login}</font></td>
							<td width="24%"><font size="1" face="Verdana">${sound.specie.family.name}</font></td>
							<td width="24%"><font size="1" face="Verdana">${sound.specie.name}</font></td>
							<td width="10%"><font size="1" face="Verdana">${sound.sex.sex}</font></td>
							<td width="10%"><font size="1" face="Verdana">${sound.age.age}</font></td>
							<td width="10%"><a
								href="<c:url value="${linkKey}${sound.relativePathAsLink}"/>"
								target="_blank"> <font size="1" face="Verdana"> <fmt:message
								key="sound.link.lable" /> </font> </a></td>
						</tr>
						<tr>
							<td width="14%" align="left"><font size="1" face="Verdana"><fmt:message
								key="photo.identify.comment" /></font></td>
							<td width="86%" colspan="2" align="left"><font size="1"
								face="Verdana">${sound.comment}</font></td>
						</tr>
					</table>

					<c:set var="count" value="${count + 1}" />
				</c:forEach></td>
			</tr>
		</table>

		</td>
	</tr>
	<c:import url="/jsp/sound/display/pagination.jsp" />
</table>
