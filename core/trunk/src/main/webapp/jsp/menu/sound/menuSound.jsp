<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<tr>
	<td><font size="2" face="Verdana"> <a
		href="<c:url value="/index.jsp?menuLevel1=2"/>"> <fmt:message
		key="menu.sounds.sounds" /> </a> </font></td>
</tr>
<c:if test="${menu == '2'}">
	<c:import url="/jsp/menu/sound/menuViewSounds.jsp" />
</c:if>
