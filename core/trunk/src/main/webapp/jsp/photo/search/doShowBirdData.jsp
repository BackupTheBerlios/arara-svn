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

<c:if test="${currentPhoto.specie.id != -1}">
<table class="lineBorder" width="100%">
	<tr>
		<td align="left">
		<table width="100%" bgcolor="#005500">
		<tr>
			<td>
				<font size="${fontSize}" face="Verdana" color="#ffffff">
					<fmt:message key="show.one.bird.data.title" />
				</font>
			</td>
		</tr>
		</table>
		<table width="100%">
		<tr>
			<td width="20%">
				<font size="${fontSize}" face="Verdana">
					<b><fmt:message key="family" /></b>
				</font>
			</td>
			<td>
				<font size="${fontSize}" face="Verdana">
					<c:if test="${currentPhoto.specie.family.subFamilyName == null}">
						<a href="<c:url value="/servlet/searchPhotosByFamily?id=${currentPhoto.specie.family.id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">${currentPhoto.specie.family.name}</a><br>
					</c:if> 
					<c:if test="${currentPhoto.specie.family.subFamilyName != null}">
						<a href="<c:url value="/servlet/searchPhotosByFamily?id=${currentPhoto.specie.family.id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">${currentPhoto.specie.family.name} (${currentPhoto.specie.family.subFamilyName})</a>
					</c:if> 
				</font>
			</td>
		</tr>
		</table>
		<table width="100%">
		<tr>
			<td width="20%">
				<font size="${fontSize}" face="Verdana">
					<b><fmt:message key="specie" /></b>
				</font>
			</td>
			<td>
				<font size="${fontSize}" face="Verdana">
					<a href="<c:url value="/servlet/searchPhotosBySpecie?id=${currentPhoto.specie.id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
						<i>${currentPhoto.specie.name}</i></a>
				</font>
			</td>
		</tr>
		</table>
		<table width="100%">
		<tr>
			<td width="20%">
				<font size="${fontSize}" face="Verdana">
					<b><fmt:message key="common.name" /></b>
				</font>
			</td>
			<td>
				<font size="${fontSize}" face="Verdana">${currentPhoto.specie.commonNameString}</font>
			</td>
		</tr>
		</table>

		<table width="100%">
		<tr>
			<td width="20%">
				<font size="${fontSize}" face="Verdana">
					<b><fmt:message key="english.name" /></b>
				</font>
			</td>
			<td>
				<font size="${fontSize}" face="Verdana">${currentPhoto.specie.englishName}</font>
			</td>
		</tr>
		</table>
	
		<table width="100%">
		<tr>
			<td width="20%">
				<font size="${fontSize}" face="Verdana">
					<b><fmt:message key="specie.age" /></b>
				</font>
			</td>
			<td>
				<font size="${fontSize}" face="Verdana">${currentPhoto.age.age}</font>
			</td>
		</tr>
		</table>
	
		<table width="100%">
		<tr>
			<td width="20%">
				<font size="${fontSize}" face="Verdana">
					<b><fmt:message key="specie.sex" /></b>
				</font>
			</td>
			
			<td>
				<font size="${fontSize}" face="Verdana">${currentPhoto.sex.sex}</font>
			</td>
		</tr>
		</table>

		<c:if test="${currentPhoto.specie.minimumSize != null}">
			<table width="100%">
			<tr>
				<td width="20%">
					<font size="${fontSize}" face="Verdana">
						<b><fmt:message key="specie.size" /></b>
					</font>
				</td>
				
				<td>
					<c:if test="${currentPhoto.specie.minimumSize != currentPhoto.specie.maximumSize}">
						<font size="${fontSize}" face="Verdana">${currentPhoto.specie.minimumSize}-${currentPhoto.specie.maximumSize}&nbsp;<fmt:message key="specie.size.unit" /></font>
					</c:if> 	
					<c:if test="${currentPhoto.specie.minimumSize == currentPhoto.specie.maximumSize}">
						<font size="${fontSize}" face="Verdana">${currentPhoto.specie.minimumSize}&nbsp;<fmt:message key="specie.size.unit" /></font>
					</c:if> 	
				</td>
			</tr>
			</table>
		</c:if> 	
		</td>
	</tr>
</table>
</c:if>
