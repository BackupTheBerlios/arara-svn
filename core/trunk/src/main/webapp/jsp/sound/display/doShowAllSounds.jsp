<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<table width="100%" border="0" >
	<tr>
		<td align="center">
			<script type="text/javascript"><!--
			google_ad_client = "pub-3030374396324619";
			google_ad_width = 728;
			google_ad_height = 90;
			google_ad_format = "728x90_as";
			google_ad_type = "text_image";
			google_ad_channel = "";
			//--></script>
			<script type="text/javascript"
			  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="1">
	<tr valign="top">
		<td>
			<c:if test="${empty listOfSounds}">
				<br><br>
				<table align="center" class="formBorder" width="50%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
					<tr height="10" bgcolor="#000000">
						<td colspan="3"></td>
					</tr>
					<tr height="5">
						<td colspan="3"></td>
					</tr>
					<tr>
						<td align="center" width="15%">
							<h3><fmt:message key="show.all.sounds.not.found" /></h3>
						</td>
					</tr>
					<tr height="5">
						<td colspan="3"></td>
					</tr>
				</table>
			</c:if>
		<table class="formBorder" width="100%" border="0" cellspacing="1">
		<c:if test="${!empty listOfSounds}">
			<tr>
				<td>
				<table bgcolor="#000000" width="100%">
					<tr>
						<td width="10%"><b><font color="#FFFFFF"><fmt:message
							key="user.login" /></font></b></td>
						<td width="22%"><b><font color="#FFFFFF"><fmt:message key="family" /></font></b></td>
						<td width="22%"><b><font color="#FFFFFF"><fmt:message key="specie" /></font></b></td>
						<td width="22%"><b><font color="#FFFFFF"><fmt:message key="common.name" /></font></b></td>
						<td width="8%"><b><font color="#FFFFFF"><fmt:message
							key="specie.sex" /></font></b></td>
						<td width="8%"><b><font color="#FFFFFF"><fmt:message
							key="specie.age" /></font></b></td>
						<td width="8%"><b><font color="#FFFFFF"><fmt:message
							key="sound.link.title" /></font></b></td>
					</tr>
				</table>
		</c:if>				
				<c:set var="count" value="${0}" /> 
				<c:set var="color" value="${'#D8D8D8'}" /> 
				<c:forEach items="${listOfSounds}" var="sound">
					<c:if test="${(count % 2) == 0}">
						<c:set var="color" value="${'#D8D8D8'}" />
					</c:if>

					<c:if test="${(count % 2) != 0}">
						<c:set var="color" value="${'#AAD1D8'}" />
					</c:if>

					<table bgcolor="${color}" width="100%">
						<tr>
							<td width="10%"><font size="1" face="Verdana">${sound.user.login}</font></td>

							<c:if test="${sound.specie.family.subFamilyName == null}">
								<td width="22%"><font size="1" face="Verdana">${sound.specie.family.name}</font></td>
							</c:if> 
							<c:if test="${sound.specie.family.subFamilyName != null}">
								<td width="22%"><font size="1" face="Verdana">${sound.specie.family.name} (${sound.specie.family.subFamilyName})</font></td>
							</c:if> 
							<td width="22%"><font size="1" face="Verdana"><i>${sound.specie.name}</i></font></td>
							<td width="22%"><font size="1" face="Verdana">${sound.specie.commonNameString}</font></td>
							<td width="8%"><font size="1" face="Verdana">${sound.sex.sex}</font></td>
							<td width="8%"><font size="1" face="Verdana">${sound.age.age}</font></td>
							<td width="8%">
								<font size="1" face="Verdana"> 
								<a href="<c:url value="${linkKey}${sound.relativePathAsLink}"/>" target="_blank"><fmt:message key="sound.link.lable" /></a>
								</font>
								<c:if test="${(sound.user.id == user.id) or (user.admin == true)}">
									<a href="<c:url value="/jsp/sound/delete/deleteSound.jsp?soundId=${sound.id}"/>"> 
										<font size="1" face="Verdana"> 
											<fmt:message key="button.delete.sound.tooltip" var="deleteToolTip"/>
											<img hspace="3" title="${deleteToolTip}" src="<c:url value="/jsp/images/delete.gif"/>" width="20" height="20" border="0"> 
										</font>
									</a>
								</c:if>
							</td>
							
						</tr>
						<tr>
							<td width="10%" align="left"><font size="1" face="Verdana"><fmt:message
								key="photo.identify.comment" /></font></td>
							<td width="90%" colspan="2" align="left"><font size="1"
								face="Verdana">${sound.comment}</font></td>
						</tr>
					</table>

					<c:set var="count" value="${count + 1}" />
				</c:forEach></td>
			</tr>
		</table>

		</td>
	</tr>
	<c:if test="${!empty listOfSounds}">
		<c:import url="/jsp/sound/display/pagination.jsp" />
	</c:if>				
</table>
