<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="commonFunctions" prefix="f"%>

<c:set var="w" value="${120}"/>

            <img src="<c:url value="/servlet/getThumbnail?photoId=${currentPhoto.id}&identification=true"/>" 
	     		width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
                height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
		   	  	align="bottom"/>

<br><b><fmt:message key="upload.photo.identify.success"/></b>