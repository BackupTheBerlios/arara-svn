<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<c:set var="w" value="${120}"/>

            <img src="<c:url value="/servlet/getThumbnail?photoId=${currentPhoto.id}&identification=true"/>" 
	     		width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
                height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
		   	  	align="bottom"/>

<br><b>Foto enviada com sucesso. <br>
Um email foi enviado para os usuários, convidando-os a ajudar na identificação da foto.<br>
Você será notificado por email quando algum usuário fizer uma identificação na foto.
</b>