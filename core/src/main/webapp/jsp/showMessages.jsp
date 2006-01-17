<%@ taglib uri="/core" prefix="c"%>

<c:if test="${messages != null && !empty messages}">
  <h3>Messages</h3>
  <c:forEach items="${messages}" var="msg">
	<font color="#FF0000">${msg}</font><br>
	<hr>
  </c:forEach>
</c:if>
