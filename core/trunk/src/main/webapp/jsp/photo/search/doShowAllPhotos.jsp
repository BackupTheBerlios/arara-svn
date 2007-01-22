<style type="text/css">
table.comBordaSimples {
	border-collapse: collapse; /* CSS2 */
	border: 2px solid #404040;
}
table.comBordaSimples tr {
	border: 1px solid #404040;
}
</style>

<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<table width="100%" border="0" cellspacing="2" height="100%">
<tr><td>
		<c:if test="${empty listOfPhotos}">
			<br><br>
			<table align="center" class="formBorder" width="50%" border="0" cellspacing="2" bgcolor="${mainBgColor}">
				<tr height="10" bgcolor="#000000">
					<td colspan="3"></td>
				</tr>
				<tr height="5">
					<td colspan="3"></td>
				</tr>
				<tr>
					<td align="center" width="15%">
						<h3><fmt:message key="show.all.photos.not.found" /></h3>
					</td>
				</tr>
				<tr height="5">
					<td colspan="3"></td>
				</tr>
			</table>
		</c:if>
	<table class="comBordaSimples"  width="100%" border="0" cellspacing="2" height="100%">
		<c:set var="index" value="${0}" />
		<c:set var="newLine" value="${true}" />
		<c:set var="w" value="${100}" />
		<c:forEach items="${listOfPhotos}" var="photo">
			<c:if test="${photo.specie.id == -1}">
				<c:set var="identification" value="${true}" />
			</c:if>
		
			<c:set var="index" value="${index + 1}" />
			<c:if test="${newLine == true}">
				<c:set var="newLine" value="${false}" />
				<tr>
			</c:if>
			<td align="left">
	  			<table height="${w+10}" width="100%" border="0" cellpadding="2" cellspacing="2">
				<tr>
					<td width="20" align="right">
					<c:if test="${photo.soundAvailable == true}">
						<a  href="<c:url value="${linkKey}${photo.sound.relativePathAsLink}"/>" target="_blank"> 
							<font size="1" face="Verdana"> 
								<fmt:message key="button.sound.tooltip" var="soundToolTip"/>
								<img title="${soundToolTip}" src="<c:url value="/jsp/images/sound.gif"/>" width="20" height="20" border="0"> 
							</font> 
						</a>
					</c:if> 
					<c:if test="${photo.soundAvailable == false}">
							<font size="1" face="Verdana"> 
								<fmt:message key="button.noSound.tooltip" var="soundToolTip"/>
								<img title="${soundToolTip}" src="<c:url value="/jsp/images/noSound.gif"/>" width="20" height="20" border="0"> 
							</font> 
					</c:if> 
					</td>
	        		<td width="${w+10}" colspan="1" rowspan="4" align="left">
					<a href="<c:url value="/servlet/showOnePhoto?nextPage=/frame.jsp&pageToShow=/jsp/photo/search/doShowOnePhoto.jsp&photoId=${photo.id}&identification=${identification}"/>">
					<img src="<c:url value="/servlet/getThumbnail?photoId=${photo.id}&identification=${identification}"/>"
						width="${f:thumbnailWidth(w, photo.smallImage.width, photo.smallImage.height)}"
						height="${f:thumbnailHeight(w, photo.smallImage.width, photo.smallImage.height)}"
						align="bottom" border="0"/>
					</a> 
					</td>
					
					<!-- INSERTING SPECIE DATA -->
	        		<td colspan="1" rowspan="4" align="left">
						<c:if test="${photo.specie.id != -1}">
							<!-- INSERTING family name -->
							<font size="1" face="Verdana"><br><fmt:message key="family" />: 
							<c:if test="${photo.specie.family.subFamilyName == null}">
								<a href="<c:url value="/servlet/searchPhotosByFamily?id=${photo.specie.family.id}&action=BEGIN&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">${photo.specie.family.name}
								</a>
							</c:if> 
							<c:if test="${photo.specie.family.subFamilyName != null}">
								<a href="<c:url value="/servlet/searchPhotosByFamily?id=${photo.specie.family.id}&action=BEGIN&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">${photo.specie.family.name} (${photo.specie.family.subFamilyName}) 
								</a>
							</c:if> 
							</font>
							
							<!-- INSERTING specie name -->
							<font size="1" face="Verdana"><br><fmt:message key="specie" />: 
								<a href="<c:url value="/servlet/searchPhotosBySpecie?id=${photo.specie.id}&action=BEGIN&nextPage=${nextPage}&pageToShow=${pageToShow}"/>"><i>${photo.specie.name}</i>
								</a>
							</font> 
							<!-- INSERTING specie common name -->
							<font size="1" face="Verdana"><br><fmt:message key="common.name" />: 
								<a href="<c:url value="/servlet/searchPhotosBySpecie?id=${photo.specie.id}&action=BEGIN&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">${photo.specie.commonNameString}
								</a>
							</font> 
						</c:if> 
						<c:if test="${photo.specie.id == -1}">
							<b><font color="#FF0000" size="1" face="Verdana"><br><fmt:message key="photo.not.identified.title" /> 
							</font></b>
						</c:if> 
						<!-- INSERTING user login -->
						<font size="1" face="Verdana"><br><fmt:message key="user.title" />: 
							<a href="<c:url value="/servlet/searchPhotosByUser?id=${photo.user.id}&action=BEGIN&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">${photo.user.login}</a>

							<c:if test="${(photo.user.id == user.id) or (user.admin == true)}">
								<a  href="<c:url value="/frame.jsp?pageToShow=/jsp/email/doWriteEmail.jsp&loginTo=${photo.user.login}"/>"> 
									<fmt:message key="button.sound.tooltip" var="soundToolTip"/>
									<img align="middle" title="${soundToolTip}" src="<c:url value="/jsp/images/mail_write.png"/>" width="20" height="20" border="0"> 
								</a>							
							</c:if> 
						</font> 
						<c:if test="${photo.specie.id == -1}">
							<font size="1" face="Verdana"><br>
							<fmt:message key="photo.click.msg1" /> <a href="<c:url value="/servlet/initIdentification?photoId=${photo.id}"/>"><fmt:message key="photo.click.here" /></a> <fmt:message key="photo.click.msg2" />
							</font>
						</c:if>
					</td>
				</tr>
				<tr>
				<td align="right">
				    <!--  COMMENT -->
					<a href="<c:url value="/servlet/initCommentPhoto?photoId=${photo.id}&identification=${identification}"/>"> 
						<font size="1" face="Verdana"> 
							<fmt:message key="button.comment.tooltip" var="commentToolTip"/>
							<img title="${commentToolTip}" src="<c:url value="/jsp/images/comment.gif"/>" width="20" height="20" border="0"> 
						</font>
					</a>
				</td>
				</tr>
				<!-- EDIT and DELETE -->
				<c:if test="${(photo.user.id == user.id) or (user.admin == true)}">
					<tr>
					<td align="right">
						<a href="<c:url value="/servlet/initEditPhoto?photoId=${photo.id}&identification=${identification}"/>"> 
							<font size="1" face="Verdana"> 
								<fmt:message key="button.edit.tooltip" var="editToolTip"/>
								<img title="${editToolTip}"src="<c:url value="/jsp/images/edit.gif"/>" width="20" height="20" border="0"> 
							</font>
						</a>
					</td>
					</tr>
					<tr>
					<td align="right">
						<a href="<c:url value="/jsp/photo/search/deletePhoto.jsp?photoId=${photo.id}"/>"> 
							<font size="1" face="Verdana"> 
								<fmt:message key="button.delete.tooltip" var="deleteToolTip"/>
								<img title="${deleteToolTip}" src="<c:url value="/jsp/images/delete.gif"/>" width="20" height="20" border="0"> 
							</font>
						</a>
					</td>
					</tr>
				</c:if> 
				<c:if test="${(user == null) or ((photo.user.id != user.id) and (user.admin == false))}">
					<tr>
					<td align="right">
						<font size="1" face="Verdana"> 
							<fmt:message key="button.noEdit.tooltip" var="editToolTip"/>
							<img title="${editToolTip}"src="<c:url value="/jsp/images/noEdit.gif"/>" width="20" height="20" border="0"> 
						</font>
					</td>
					</tr>
					<tr>
					<td align="right">
						<font size="1" face="Verdana"> 
							<fmt:message key="button.noDelete.tooltip" var="deleteToolTip"/>
							<img title="${deleteToolTip}" src="<c:url value="/jsp/images/noDelete.gif"/>" width="20" height="20" border="0"> 
						</font>
					</td>
					</tr>
				</c:if> 
				</table>
				
		</td>
		<c:if test="${index > 1}">
			<c:set var="index" value="${0}" />
			<c:set var="newLine" value="${true}" />
			</tr>
		</c:if>
	    </c:forEach>
  	  </table>
</td></tr>
<c:import url="/jsp/photo/search/pagination.jsp" />
</table>
