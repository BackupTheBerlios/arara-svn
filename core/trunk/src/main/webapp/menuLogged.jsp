<%@ taglib uri="/core" prefix="c"%>
<c:if test="${!empty param.menuLevel1}">
	<c:set var="menu" scope="session" value="${param.menuLevel1}" />
</c:if>

<table width="100%" border="0" cellspacing="5" align="center">
	<c:import url="/jsp/menu/menuLoggedCommon.jsp" />

	<c:import url="/jsp/menu/photo/menuPhoto.jsp" />
	<c:if test="${menu == '1'}">
		<c:import url="/jsp/menu/photo/menuPhotoActions.jsp" />
	</c:if>


	<c:import url="/jsp/menu/sound/menuSound.jsp" />
	<c:if test="${menu == '2'}">
		<c:import url="/jsp/menu/sound/menuSoundActions.jsp" />
	</c:if>


	<tr bgcolor="#005599">
		<td></td>
	</tr>
</table>
