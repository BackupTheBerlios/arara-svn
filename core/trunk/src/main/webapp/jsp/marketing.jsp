<%@ taglib uri="/core" prefix="c"%>

<c:if test="${marketingCounter != null}">
	<c:set var="marketingCounter" value="${marketingCounter + 1}" scope="session"/>
</c:if>
<c:if test="${marketingCounter == null}">
	<c:set var="marketingCounter" value="${1}" scope="session"/>
</c:if>
<c:if test="${marketingCounter > 5}">
	<c:set var="marketingCounter" value="${1}" scope="session"/>
</c:if>
	<c:choose>
		<c:when test="${marketingCounter == 1}">
			<a href="http://www.ecorotas.com.br" target="_blank"> 
				<img width="200" height="100" hspace ="5" src="<%= request.getContextPath()%>/jsp/images/ecorotas.jpg"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 2}">
			<a href="http://www.itamambuca.com.br" target="_blank"> 
				<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
				codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="200"
				height="100">
				<param name="movie" value="<%= request.getContextPath()%>/itamambuca_nosound.swf">
				<param name="quality" value="high">
				<embed src="<%= request.getContextPath()%>/itamambuca_nosound.swf" quality="high"
				pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="200"
				height="100"></embed>
				</object>
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 3}">
			<a href="http://www.aquasuper.com" target="_blank"> 
				<img width="200" height="100" hspace ="5" src="<%= request.getContextPath()%>/jsp/images/aqua.jpg"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 4}">
			<a href="http://http://www.avistarbrasil.com.br/concurso/" target="_blank"> 
				<img border="0" width="200" height="100" hspace ="5" src="<%= request.getContextPath()%>/jsp/images/avistar.jpg"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 5}">
			<a href="http://www.aves.brasil.nom.br/jsp/images/mailmkt1.jpg" target="_blanck"> 
				<img width="200" height="100" src="<c:url value="/jsp/images/quental.jpg"/>" align="bottom"/> 
			</a> 
		</c:when>
	</c:choose>
		