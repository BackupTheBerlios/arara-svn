<%@ taglib uri="/core" prefix="c"%>

<c:set var="soundToDelete" value="${param.soundId}" scope="request" />
<c:import url="/frame.jsp">
	<c:param name="pageToShow" value="/jsp/sound/delete/doDeleteSound.jsp" />
</c:import>