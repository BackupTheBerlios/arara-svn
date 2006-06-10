<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<c:if test="${identification != 'true'}">
  <c:set var="tableW" value="${70}"/>
  <c:set var="imageAlign" value="${'left'}"/>
  <c:set var="fontSize" value="${2}"/>
</c:if>
<c:if test="${identification == 'true'}">
  <c:set var="w" value="${240}"/>
  <c:set var="tableW" value="${100}"/>
  <c:set var="imageAlign" value="${'center'}"/>
  <c:set var="fontSize" value="${1}"/>
</c:if>

	  		<table width="100%" bgcolor="#005500">
	  			<tr><td><font color="#ffffff" size="${fontSize}" face="Verdana"><fmt:message key="show.one.photo.data.title"/></font></td></tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="photo.author"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${currentPhoto.user.name} 
		                 <a href="<c:url value="/servlet/searchPhotosByUser?id=${currentPhoto.user.id}"/>">
                         (${currentPhoto.user.login})</a></font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="photo.location"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${currentPhoto.location}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="city"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${currentPhoto.city.name} - ${currentPhoto.city.state.acronym}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="photo.date"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${f:dateString(currentPhoto.date)}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="photo.camera"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${currentPhoto.camera}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="photo.len"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${currentPhoto.lens}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="photo.film"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${currentPhoto.film}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="comment.title"/></b></font></td>
	  				<td>
                       <textarea rows="5" cols="50" readonly name="comment">${currentPhoto.comment}</textarea>
	  				</td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="${fontSize}" face="Verdana"><b><fmt:message key="show.one.photo.size"/></b></font></td>
	  				<td><font size="${fontSize}" face="Verdana">${currentPhoto.realImage.width}x${currentPhoto.realImage.height}</font></td>
	  			</tr>
	  		</table>
