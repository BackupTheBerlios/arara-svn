<%@ taglib uri="/core" prefix="c"%>

<c:set var="photoToDelete" value="${param.photoId}" scope="request" />
<c:import url="/frame.jsp">
	<c:param name="pageToShow" value="/jsp/photo/search/doDeletePhoto.jsp" />
</c:import>
