<%@ taglib uri="/core" prefix="c"%>
	        <tr> 
	          <td><b><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;Consultar</font></b></td>
	        </tr>
	        <tr> 
	          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/searchPhotos?action=BEGIN"/>">Todas</a></font></td>
	        </tr>
	        <tr> 
	          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosByFamily"/>">Por fam�lia</a></font></td>
	        </tr>
	        <tr> 
	          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosBySpecie"/>">Por esp�cie</a></font></td>
	        </tr>
	        <tr> 
	          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosByCommonName"/>">Por nome comum</a></font></td>
	        </tr>
	        <tr> 
	          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/initSearchPhotosByUser"/>">Por usu�rio</a></font></td>
	        </tr>
	        <tr> 
	          <td><font size="2" face="Verdana">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/servlet/searchPhotos?action=BEGIN&identification=true"/>">Para identifica��o</a></font></td>
	        </tr>
	        