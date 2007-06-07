<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>

<font size="2" face="Verdana"> 
  	<li>
		<a href="<c:url value="/frame.jsp?pageToShow=/jsp/birdwatching/concept.jsp"/>">
		<fmt:message key="menu.birdwatching.concept" /></a>
	</li>
  	<li>
		<a href="<c:url value="/frame.jsp?pageToShow=/jsp/birdwatching/equipments.jsp"/>">
		<fmt:message key="menu.birdwatching.equipments" /></a>
	</li>
  	<li>
		<a href="<c:url value="/frame.jsp?pageToShow=/jsp/birdwatching/places.jsp"/>">
		<fmt:message key="menu.birdwatching.places" /></a>
	</li>
  	<li>
		<a href="<c:url value="/frame.jsp?pageToShow=/jsp/birdwatching/clubs.jsp"/>">
		<fmt:message key="menu.birdwatching.clubs" /></a>
	</li>
</font>