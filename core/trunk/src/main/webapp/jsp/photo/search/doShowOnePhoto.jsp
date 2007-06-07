<%@ taglib tagdir="/WEB-INF/tags" prefix="img"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="functions" prefix="f"%>

<c:if test="${identification != 'true'}">
	<c:set var="tableW" value="${100}" />
	<c:set var="imageAlign" value="${'left'}" />
	<c:set var="fontSize" value="${2}" />
</c:if>
<c:if test="${identification == 'true'}">
	<c:set var="w" value="${240}" />
	<c:set var="tableW" value="${100}" />
	<c:set var="imageAlign" value="${'center'}" />
	<c:set var="fontSize" value="${1}" />
</c:if>

<table width="100%" border="0">
<tr>
  <td width="85%" valign="top">

	<table width="${tableW}%">
	<tr>
		<td align="${imageAlign}">
		
			<!-- Show photo when in View Mode -->
			<c:if test="${viewMode == 'viewMode'}">
		        <c:if test="${hasPrevious}">
			        <a href="<c:url value="${servletToCall}?identification=${identification}&action=PREVIOUS&id=${id}&nextPage=${nextPage}&photoId=${currentPhoto.id}&pageToShow=${pageToShow}"/>">
			        	<fmt:message key="pagination.previous" var="paginagion.previous"/>
			        	<b><img border="0" align="middle" title="${paginagion.previous}" src="<c:url value="/images/navigate_left.gif"/>" width="48" height="48"></b>
			        </a> 
			    </c:if>
				&nbsp;&nbsp;&nbsp;&nbsp; 					
				<a href="<c:url value="${linkKey}${currentPhoto.relativePathAsLink}"/>" target="_blank"> 
					<img:showImage identification="${identification}" />
				</a>
	        	&nbsp;&nbsp;&nbsp;&nbsp; 					
		        <c:if test="${hasNext}">
					<a href="<c:url value="${servletToCall}?identification=${identification}&action=NEXT&id=${id}&nextPage=${nextPage}&photoId=${currentPhoto.id}&pageToShow=${pageToShow}"/>">
			        	<fmt:message	key="pagination.next" var="paginagion.next"/>
			        	<b><img border="0" align="middle" title="${paginagion.next}" src="<c:url value="/images/navigate_right.gif"/>" width="48" height="48"></b>
			        </a>
			    </c:if>
			</c:if> 
			
			<!-- Show photo when in Identification Mode -->
			<c:if test="${viewMode == 'identificationMode'}">
				<br>
				<table class="formBorder"width="95%">
				<tr>
					<td align="left">
						<c:import url="/jsp/photo/search/doShowPhotoData.jsp" />
						<td align="center">
							<a href="<c:url value="${linkKey}${currentPhoto.relativePathAsLink}"/>" target="_blank"> 
							<img
								src="<c:url value="${linkKey}${currentPhoto.relativePathAsLink}"/>"
								width="${f:thumbnailWidth(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
								height="${f:thumbnailHeight(w, currentPhoto.smallImage.width, currentPhoto.smallImage.height)}"
								align="bottom" /> 
							</a>
						</td>
					</td>
				</tr>
				</table>
			</c:if>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<c:import url="/jsp/photo/search/doShowBirdData.jsp" />
		</td>
	</tr>
	
	<!-- Photo for IDENTIFICATION, in view mode. Present photo data to user  -->
	<c:if test="${viewMode == 'viewMode'}">
		<tr>
			<td align="left">
				<c:import url="/jsp/photo/search/doShowPhotoData.jsp" />
			</td>
		</tr>
	</c:if>
	
	<!-- Photo for IDENTIFICATION, in identification mode. Present to user family and specie for selection -->
	<c:if test="${viewMode == 'identificationMode'}">
		<tr>
			<td>
				<c:import url="/jsp/photo/identify/doIdentifyPhoto.jsp" />
			</td>
		</tr>
	</c:if>
	
	<c:if test="${viewMode == 'viewMode'}">
		<tr>
			<td align="left">
				<c:import url="/jsp/photo/search/doShowPhotoMenuOptions.jsp" />
			</td>
		</tr>
		<tr>
			<td>
				<c:import url="/jsp/photo/search/doShowPhotoComments.jsp" />
			</td>
		</tr>
	</c:if>
	
		<tr>
			<td>
				<c:import url="/jsp/photo/identify/doShowPhotoIdentifications.jsp" />
			</td>
		</tr>
	</table>
  </td>
  <td width="15%" valign="top" align="center">
	<script type="text/javascript"><!--
	google_ad_client = "pub-3030374396324619";
	google_ad_width = 120;
	google_ad_height = 240;
	google_ad_format = "120x240_as";
	google_ad_type = "text_image";
	google_ad_channel = "";
	//--></script>
	<script type="text/javascript"
	  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
	
	<script type="text/javascript"><!--
	google_ad_client = "pub-3030374396324619";
	google_ad_width = 120;
	google_ad_height = 600;
	google_ad_format = "120x600_as";
	google_ad_type = "image";
	google_ad_channel = "";
	//--></script>
	<script type="text/javascript"
	  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>
	  </td>
</tr>
</table>