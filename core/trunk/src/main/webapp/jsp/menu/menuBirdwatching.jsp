<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ taglib uri="/core" prefix="c"%>

<font size="2" face="Verdana"> 
  	<li>
		<fmt:message key="birdwatching.page.concept" var="conceptPage" />
		<a href="<c:url value="/frame.jsp?pageToShow=${conceptPage}"/>">
		<fmt:message key="menu.birdwatching.concept" /></a>
	</li>
  	<li>
		<fmt:message key="birdwatching.page.equipment" var="equipmentsPage" />
		<a href="<c:url value="/frame.jsp?pageToShow=${equipmentsPage}"/>">
		<fmt:message key="menu.birdwatching.equipments" /></a>
	</li>
  	<li>
		<fmt:message key="birdwatching.page.places" var="placesPage" />
		<a href="<c:url value="/frame.jsp?pageToShow=${placesPage}"/>">
		<fmt:message key="menu.birdwatching.places" /></a>
	</li>
  	<li>
		<fmt:message key="birdwatching.page.clubs" var="clubsPage" />
		<a href="<c:url value="/frame.jsp?pageToShow=${clubsPage}"/>">
		<fmt:message key="menu.birdwatching.clubs" /></a>
	</li>
</font>