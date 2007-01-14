<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<%@ page import="net.indrix.arara.servlets.pagination.*"%>

<tr>
	<td align="center">
		[Página ${paginationBean.currentPage} de ${paginationBean.numberOfPages}]
		<% PaginationController p = (PaginationController)session.getAttribute("photoPaginationController");
        boolean previous = false;
        boolean next = false;
        if ((p != null) && (p.hasPrevious())){
       	%> 
       		<a href="<c:url value="${servletToCall}?identification=${identification}&action=FIRST&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
       			<b><fmt:message	key="pagination.first" /></b>
       		</a>&nbsp;&nbsp;|&nbsp;&nbsp; 
       	<%
	   	}
        %> 
        <% if ((p != null) && (p.hasPrevious())){
             previous = true;
        %> 
        <a href="<c:url value="${servletToCall}?identification=${identification}&action=PREVIOUS&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        	<b>< <fmt:message key="pagination.previous" /></b>
        </a> 
        <%
	   	   }
        %> 
        <% if ((p != null) && (p.hasNext())){
             if (previous) {
        %> &nbsp;&nbsp;|&nbsp;&nbsp; 
        <%
             }
        %>
        <a href="<c:url value="${servletToCall}?identification=${identification}&action=NEXT&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        	<b><fmt:message	key="pagination.next" /> ></b>
        </a>&nbsp;&nbsp;|&nbsp;&nbsp; 
        <%
	   	   }
        %>
        <% 
          if ((p != null) && (p.hasNext())){
        %> 
        	<a href="<c:url value="${servletToCall}?identification=${identification}&action=LAST&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        		<b><fmt:message	key="pagination.last" /></b>
        	</a> 
       	<%
	   	   }
        %>
	</td>
</tr>
