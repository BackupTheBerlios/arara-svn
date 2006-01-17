<%@ taglib uri="/core" prefix="c"%>
		      <c:if test="${user.addSound == true}">
		        <tr> 
		          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initUploadSounds"/>">Enviar</a>
		          </font></td>
		        </tr>
		      </c:if>
