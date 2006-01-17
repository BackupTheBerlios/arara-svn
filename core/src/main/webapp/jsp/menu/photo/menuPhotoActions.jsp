<%@ taglib uri="/core" prefix="c"%>
		      <c:if test="${user.addPhoto == true}">
		        <tr> 
		          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initUploadPhotos"/>">Enviar</a>
		          </font></td>
		        </tr>
		        <tr> 
		          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;<a href="<c:url value="/jsp/photo/upload/uploadPhotoIdentify.jsp"/>">Enviar p/ identif.</a>
		          </font></td>
		        </tr>
		      </c:if>
		      <c:if test="${param.submenu == 'changePhoto'}">
		       <c:if test="${(param.userId == user.id) or (user.admin == true)}">
		          <tr> 
		            <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initEditPhoto"/>">Editar
		            </a></font></td>          
		          </tr>
		          <tr> 
		            <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;<a href="<c:url value="/jsp/photo/search/deletePhoto.jsp?photoId=${param.photoId}"/>">Apagar
		            </a></font></td>
		          </tr>
		       </c:if>
		      </c:if>
		      <c:if test="${param.currentPage == 'showOnePhoto'}">
		        <tr> 
		          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;<a href="<c:url value="/jsp/photo/comment/commentPhoto.jsp"/>">Comentar</a>
		          </font></td>
		        </tr>
		      </c:if>
