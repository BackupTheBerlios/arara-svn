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


<table border="1" width="${tableW}%">
<tr>
	<td align="${imageAlign}">
		<c:if test="${viewMode == 'viewMode'}">
			<a href="<c:url value="/servlet/getPhoto?photoId=${currentPhoto.id}&identification=${identification}"/>" target="_blank"> 
				<img:showImage identification="${identification}" />
			</a>
		</c:if> 
		
		<c:if test="${viewMode == 'identificationMode'}">
			<table>
			<tr>
				<td align="left">
					<c:import url="/jsp/photo/search/doShowPhotoData.jsp" />
					<td align="left">
						<a href="<c:url value="/servlet/getPhoto?photoId=${currentPhoto.id}&identification=${identification}"/>" target="_blank"> 
						<img
							src="<c:url value="/servlet/getThumbnail?photoId=${currentPhoto.id}"/>"
							width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
							height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
							align="bottom" /> 
						</a>
					</td>
				</td>
			</tr>
			</table>
		</c:if>
	</td>
</tr>

<c:if test="${currentPhoto.specie.id != -1}">
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
					<a href="<c:url value="/servlet/searchPhotosByFamily?id=${currentPhoto.specie.family.id}"/>">${currentPhoto.specie.family.name}</a><br>
				</c:if> 
				<c:if test="${currentPhoto.specie.family.subFamilyName != null}">
					<a href="<c:url value="/servlet/searchPhotosByFamily?id=${currentPhoto.specie.family.id}"/>">${currentPhoto.specie.family.name} (${currentPhoto.specie.family.subFamilyName})</a>
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
				<a href="<c:url value="/servlet/searchPhotosBySpecie?id=${currentPhoto.specie.id}"/>">
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

	</td>
</tr>
</c:if>

<!-- Photo for IDENTIFICATION, in view mode. Present photo data to user  -->
<c:if test="${viewMode == 'viewMode'}">
<tr>
	<td align="left">
		<c:import url="/jsp/photo/search/doShowPhotoData.jsp" />
	</td>
</tr>
</c:if>

<!-- Photo for IDENTIFICATION, in identification mode. Present to user family and specie for selection -->
<c:if test="${viewMode == 'identificationMode'}">
<tr>
	<td>
		<c:import url="/jsp/photo/identify/doIdentifyPhoto.jsp" />
	</td>
</tr>
</c:if>
</table>

<c:if test="${viewMode == 'viewMode'}">
	<c:import url="/jsp/photo/search/doShowPhotoComments.jsp" />
</c:if>

<c:if test="${viewMode == 'viewMode'}">
	<c:import url="/jsp/photo/identify/doShowPhotoIdentifications.jsp" />
</c:if>
