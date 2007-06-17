<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<font size="2" face="Verdana"> 
  	<li>
		<a href="<c:url value="/servlet/showAllPublicLists"/>">
		<fmt:message key="menu.birdlists.list" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/showUsersBirdLists"/>">
		<fmt:message key="menu.birdlists.mylists" /></a>
	</li>
  	<li>
		<a href="<c:url value="/servlet/initCreateBirdList"/>">
		<fmt:message key="menu.birdlists.create" /></a>
	</li>
</font>







