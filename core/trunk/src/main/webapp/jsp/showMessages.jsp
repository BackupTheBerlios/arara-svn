<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:if test="${messages != null && !empty messages}">
	<h3><fmt:message key="messages.title" /></h3>
	<c:forEach items="${messages}" var="msg">
		<font color="#FF0000"><fmt:message key="${msg}" /></font>
		<br>
	</c:forEach>
	<hr>
</c:if>


	