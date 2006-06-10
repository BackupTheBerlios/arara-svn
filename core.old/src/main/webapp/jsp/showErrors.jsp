<%@ taglib uri="/core" prefix="c"%>

<c:if test="${erros != null && !empty erros}">
  <h3>Erro</h3>
  <c:forEach items="${erros}" var="erro">
	<font color="#FF0000">${erro}</font><br>
	<hr>
  </c:forEach>
</c:if>

