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

<br>

<table width="80%" align="center" border="0" cellspacing="1">
	<tr valign="top">
	<td align = "center" width="33%">
		<a href="<c:url value="/servlet/showListOfSpeciesWithPhoto?doAction=ONLY"/>">Com fotos
		</a>
	</td>
	<td align = "center" width="33%">
		<a href="<c:url value="/servlet/showListOfSpeciesWithPhoto?doAction=NOT_ONLY"/>">Sem fotos
		</a>
	</td>
	<td align = "center" width="34%">
		<a href="<c:url value="/servlet/showListOfSpeciesWithPhoto?doAction=ALL"/>">Todas
		</a>
	</td>

<table width="80%" align="center" border="0" cellspacing="1">
	<tr valign="top">
	<td>
		<table class="formBorder" width="100%" border="0" cellspacing="1">
		<c:if test="${!empty specieList}">
			<tr>
				<td>
				<table bgcolor="#000000" width="100%">
					<tr>
						<td width="30%"><b><font color="#FFFFFF"><fmt:message key="family" /></font></b></td>
						<td width="30%"><b><font color="#FFFFFF"><fmt:message key="specie" /></font></b></td>
						<td width="40%"><b><font color="#FFFFFF"><fmt:message key="common.name" /></font></b></td>
					</tr>
				</table>
		</c:if>				
				<c:set var="count" value="${0}" /> 
				<c:set var="color" value="${'#D8D8D8'}" /> 
				<c:forEach items="${specieList}" var="specie">
					<c:if test="${(count % 2) == 0}">
						<c:set var="color" value="${'#D8D8D8'}" />
					</c:if>

					<c:if test="${(count % 2) != 0}">
						<c:set var="color" value="${'#AAD1D8'}" />
					</c:if>

					<table bgcolor="${color}" width="100%">
						<tr>
							<c:if test="${specie.family.subFamilyName == null}">
								<td width="30%"><font size="1" face="Verdana">${specie.family.name}</font></td>
							</c:if> 
							<c:if test="${specie.family.subFamilyName != null}">
								<td width="30%"><font size="1" face="Verdana">${specie.family.name} (${specie.family.subFamilyName})</font></td>
							</c:if> 
							<td width="30%"><font size="1" face="Verdana"><i>${specie.name}</i></font></td>
							<td width="40%"><font size="1" face="Verdana">${specie.commonNameString}</font>
							<c:if test="${doAction == 'ALL'}">
								<c:if test="${specie.photo == true}">
								<td width="30%">
									<img src="<c:url value="/jsp/images/check.png"/>"  width="16" height="16" border="0">
								</td>
								</c:if> 
							</c:if> 
							<c:if test="${doAction == 'ALL'}">
								<c:if test="${specie.photo == false}">
								<td width="30%">
									<img src="<c:url value="/jsp/images/delete.gif"/>"  width="16" height="16" border="0">								
								</td>
								</c:if> 
							</c:if> 
							</td>
						</tr>
					</table>

					<c:set var="count" value="${count + 1}" />
				</c:forEach></td>
			</tr>
		</table>
	</td>
	</tr>
</table>
