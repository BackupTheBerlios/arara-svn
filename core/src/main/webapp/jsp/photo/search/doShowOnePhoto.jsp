<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>

<table border="1" width="70%">
	<tr>
		<td align="left">
		    <a href="<c:url value="/servlet/getPhoto?photoId=${currentPhoto.id}"/>" target="_blank">
			<img:showImage imgW="${currentPhoto.realImage.width}" imgH="${currentPhoto.realImage.height}"/>
			</a>
		</td>
	</tr>
	<c:if test="${identification != 'true'}">
	<tr>
	  	<td align="left">
	  		<table width="100%" bgcolor="#005500">
	  			<tr><td><font size="2" face="Verdana" color="#ffffff">Dados da ave</font></td></tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Família</b></font></td>
	  				<td><font size="2" face="Verdana"><a href="<c:url value="/servlet/searchPhotosByFamily?familyId=${currentPhoto.specie.family.id}"/>">
		   			  ${currentPhoto.specie.family.name}<br></a></font></td>
	  			</tr>
	  		</table>

	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Espécie</b></font></td>
	  				<td><font size="2" face="Verdana"><a href="<c:url value="/servlet/searchPhotosBySpecie?specieId=${currentPhoto.specie.id}"/>">
                       ${currentPhoto.specie.name}</a></font></td>
	  			</tr>
	  		</table>

	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Nome Comun</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.specie.commonNameString}</font></td>
	  			</tr>
	  		</table>

	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Idade</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.age.age}</font></td>
	  			</tr>
	  		</table>

	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Sexo</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.sex.sex}</font></td>
	  			</tr>
	  		</table>
			
		</td>
	</tr>
	</c:if>
	<tr>
	  	<td align="left">
	  		<table width="100%" bgcolor="#005500">
	  			<tr><td><font color="#ffffff" size="2" face="Verdana">Dados da foto</font></td></tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Autor</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.user.name} 
		                 <a href="<c:url value="/servlet/searchPhotosByUser?userId=${currentPhoto.user.id}"/>">
                         (${currentPhoto.user.login})</a></font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Local</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.location}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Data</b></font></td>
	  				<td><font size="2" face="Verdana">${f:dateAsString(currentPhoto.date)}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Câmera</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.camera}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Lente</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.lens}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Filme</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.film}</font></td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Comentário</b></font></td>
	  				<td>
                       <textarea rows="5" cols="50" readonly name="comment">${currentPhoto.comment}</textarea>
	  				<!--
				           <pre>${currentPhoto.comment}</pre>
				    -->
	  				</td>
	  			</tr>
	  		</table>
	  		<table width="100%">
	  			<tr>
	  				<td width="20%"><font size="2" face="Verdana"><b>Tamanho</b></font></td>
	  				<td><font size="2" face="Verdana">${currentPhoto.realImage.width}x${currentPhoto.realImage.height}</font></td>
	  			</tr>
	  		</table>
		</td>
	</tr>
</table>
<font size="2" face="Verdana">
<c:if test="${!empty currentPhoto.comments}">
	<table border="0" width="75%">
	  <tr><td align="center"><b>Comentários</b></td></tr>
	</table>
	<c:forEach items="${currentPhoto.comments}" var="comment">
	<table border="1" width="75%">
	  <tr>
	  	<td>
	  		<table border="0" width="100%">
	  		  <tr>
	  		  	  <td align="left"><b><font color="#005500"><c:out value="${comment.user.login}"/></font></b><td>
	  		  	  <td align="right"><b><font color="#005500"><c:out value="${f:dateTimeAsString(comment.date)}"/></font></b><td>
	  		  </tr>
	  		</table>
		    <table align="center" width="90%" border="0" cellpadding="0" cellspacing="6">
		      <tr>
		        <td ><font color="#0000FF"><c:out value="${comment.comment}"/></font></td>
		      </tr>
		    </table>
	    	
	  	</td>
	  </tr>
	</table>
	<br>
	</c:forEach>
</c:if>
</font>
