<%@ taglib uri="/fmt" prefix="fmt"%>

<fmt:setLocale value="${param.lang}" scope="session" />
<fmt:setBundle basename="Resources" scope="session" />

<jsp:forward page="/index.jsp" />
