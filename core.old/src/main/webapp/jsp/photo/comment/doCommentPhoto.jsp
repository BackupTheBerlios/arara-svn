<%@ taglib uri="/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<form name="commentForm" method="post" 
      action="<c:url value="/servlet/commentPhoto"/>"
>
<input type=hidden name="photoId" value="${currentPhoto.id}">

<table width="50%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr>
    <td>
    <c:set var="w" value="${240}"/>

    <img src="<c:url value="/servlet/getThumbnail?photoId=${currentPhoto.id}"/>" 
   		width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
        height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}" 
  	  	align="bottom"/>
    
	</td>
  </tr>
  <tr>
    <td><b><fmt:message key="comment.title"/></b></td>
  </tr>
  <tr>
    <td>
        <textarea rows="5" cols="40" name="comment"></textarea>
    </td>
  </tr>
</table>
  
<table width="50%" border="0" cellspacing="2" bgcolor="#A6D2D2">
  <tr>
      <td> 
        <div align="left">
            <input type="SUBMIT" value="<fmt:message key="comment.submit"/>">
        </div>
      </td>
    </tr>
</table>  
</form>
