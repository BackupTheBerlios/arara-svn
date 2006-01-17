<%@ attribute name="imgW" required="true" rtexprvalue="true" %>
<%@ attribute name="imgH" required="true" rtexprvalue="true" %>

<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<c:if test="${width == null}">
	<c:set var="width" value="${600}"/>
</c:if>

<c:if test="${currentPhoto.realImage.width > width}">
	<c:set var="w" value="${width}"/>
</c:if>
<c:if test="${currentPhoto.realImage.width <= width}">
	<c:set var="w" value="${currentPhoto.realImage.width}"/>
</c:if>

<img src="<c:url value="/servlet/getPhoto?photoId=${currentPhoto.id}"/>" 
     width="${f:thumbnailWidth(w, currentPhoto.realImage.width, currentPhoto.realImage.height)}" 
     height="${f:thumbnailHeight(w, currentPhoto.realImage.width, currentPhoto.realImage.height)}" 
     align="bottom"/>