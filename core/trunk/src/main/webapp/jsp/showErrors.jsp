<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<c:if test="${erros != null && !empty erros}">
  <h3><fmt:message key="errors.title"/></h3>
  <c:forEach items="${erros}" var="erro">
	<li><font color="#FF0000"><fmt:message key="${erro}"/></font><br>
  </c:forEach>
  <hr>
</c:if>

