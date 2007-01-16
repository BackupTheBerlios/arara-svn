<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page import="net.indrix.arara.servlets.pagination.*"%>

<font face="Verdana" color="#ffffff" size="-1">
<tr>
	<td align="center">
		[<fmt:message	key="pagination.page"/> ${paginationBean.currentPage} <fmt:message key="pagination.page.of"/> ${paginationBean.numberOfPages}]
		<% PaginationController p = (PaginationController)session.getAttribute("soundPaginationController");
          boolean previous = false;
          boolean next = false;
          if ((p != null) && (p.hasPrevious())){
       %> <a href="<c:url value="${servletToCall}?action=FIRST"/>"><b><fmt:message
		key="pagination.first" /></b></a>&nbsp;&nbsp;|&nbsp;&nbsp; <%
	   	   }
       %> <% 
          if ((p != null) && (p.hasPrevious())){
             previous = true;
       %> <a href="<c:url value="${servletToCall}?action=PREVIOUS"/>"><b><fmt:message key="pagination.previous" /></b></a> 
       <%
	   	   }
       %> <% 
          if ((p != null) && (p.hasNext())){
             if (previous) {
       %> &nbsp;&nbsp;|&nbsp;&nbsp; <%
             }
       %> <a href="<c:url value="${servletToCall}?action=NEXT"/>"><b><fmt:message
		key="pagination.next" /> ></b></a>&nbsp;&nbsp;|&nbsp;&nbsp; <%
	   	   }
       %> <% 
          if ((p != null) && (p.hasNext())){
       %> <a href="<c:url value="${servletToCall}?action=LAST"/>"><b><fmt:message
		key="pagination.last" /></b></a> <%
	   	   }
       %></td>
</tr>
</font>