<%@ taglib uri="/core" prefix="c"%>
<%@ page import="net.indrix.arara.servlets.*"%>

<tr bgcolor="#339966">
<td align="center">
       <% PaginationController p = (PaginationController)session.getAttribute("paginationController");
          boolean previous = false;
          boolean next = false;
          if ((p != null) && (p.hasPrevious())){
       %>
	       <a href="<c:url value="${servletToCall}?action=FIRST"/>"><b>Primeira</b></a>&nbsp;&nbsp;|&nbsp;&nbsp;
	   <%
	   	   }
       %>

       <% 
          if ((p != null) && (p.hasPrevious())){
             previous = true;
       %>
	       <a href="<c:url value="${servletToCall}?action=PREVIOUS"/>"><b>< Anterior</b></a>
	   <%
	   	   }
       %>
       <% 
          if ((p != null) && (p.hasNext())){
             if (previous) {
       %>
                &nbsp;&nbsp;|&nbsp;&nbsp;
       <%
             }
       %>
	       <a href="<c:url value="${servletToCall}?action=NEXT"/>"><b>Próxima ></b></a>&nbsp;&nbsp;|&nbsp;&nbsp;
	   <%
	   	   }
       %>
       <% 
          if ((p != null) && (p.hasNext())){
       %>
       <a href="<c:url value="${servletToCall}?action=LAST"/>"><b>Última</b></a>
	   <%
	   	   }
       %>
</td>
</tr>
