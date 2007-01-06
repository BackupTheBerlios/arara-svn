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
			<img hspace ="5" src="<c:url value="/jsp/images/Propaganda1.jpg"/>"	align="center">		
		</c:when>
		<c:when test="${marketingCounter == 2}">
			<img hspace ="5"  src="<c:url value="/jsp/images/Propaganda2.jpg"/>" align="center">		
		</c:when>
		<c:when test="${marketingCounter == 3}">
			<img hspace ="5"  src="<c:url value="/jsp/images/Propaganda3.jpg"/>" align="center">		
		</c:when>
		<c:when test="${marketingCounter == 4}">
			<img hspace ="5"  src="<c:url value="/jsp/images/Propaganda4.jpg"/>" align="center">		
		</c:when>
		<c:when test="${marketingCounter == 5}">
			<img hspace ="5"  src="<c:url value="/jsp/images/Propaganda1.jpg"/>" align="center">		
		</c:when>
		<c:when test="${marketingCounter == 6}">
			<img hspace ="5"  src="<c:url value="/jsp/images/Propaganda2.jpg"/>" align="center">		
		</c:when>
		<c:when test="${marketingCounter == 7}">
			<img hspace ="5"  src="<c:url value="/jsp/images/Propaganda3.jpg"/>" align="center">		
		</c:when>
		<c:when test="${marketingCounter == 8}">
			<img hspace ="5"  src="<c:url value="/jsp/images/Propaganda4.jpg"/>" align="center">		
		</c:when>
	</c:choose>
		