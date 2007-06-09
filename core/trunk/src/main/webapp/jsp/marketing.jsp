<%@ taglib uri="/core" prefix="c"%>

<c:if test="${marketingCounter != null}">
	<c:set var="marketingCounter" value="${marketingCounter + 1}" scope="application"/>
</c:if>
<c:if test="${marketingCounter == null}">
	<c:set var="marketingCounter" value="${1}" scope="application"/>
</c:if>
<c:if test="${marketingCounter == 9}">
	<c:set var="marketingCounter" value="${1}" scope="application"/>
</c:if>
	<c:choose>
		<c:when test="${marketingCounter == 1}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 2}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 3}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 4}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 5}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 6}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 7}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
		<c:when test="${marketingCounter == 8}">
			<a href="http://www.ecorotas.com.br" target="_blanck"> 
				<img width="200" height="100" hspace ="5" src="<c:url value="/jsp/images/ecorotas.jpg"/>"	align="center">
			</a> 
		</c:when>
	</c:choose>
		