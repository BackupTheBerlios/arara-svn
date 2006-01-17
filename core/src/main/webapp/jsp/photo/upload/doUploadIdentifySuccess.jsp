<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<c:set var="w" value="${120}"/>

            <img src="<c:url value="/servlet/getThumbnail?photoId=${currentPhoto.id}&identification=true"/>" 
	     		width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
                height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
		   	  	align="bottom"/>

<br><b>Foto enviada com sucesso. <br>
Um email foi enviado para os usu�rios, convidando-os a ajudar na identifica��o da foto.<br>
Voc� ser� notificado por email quando algum usu�rio fizer uma identifica��o na foto.
</b>