<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<%@ page import="net.indrix.arara.servlets.pagination.*"%>

<tr>
	<td align="center" valign="center">
		[<fmt:message	key="pagination.page"/> ${paginationBean.currentPage} <fmt:message key="pagination.page.of"/> ${paginationBean.numberOfPages}]&nbsp;&nbsp;&nbsp;&nbsp;
		<% PaginationController p = (PaginationController)session.getAttribute("photoPaginationController");
        boolean previous = false;
        boolean next = false;
        if ((p != null) && (p.hasPrevious())){
       	%> 
       		<a href="<c:url value="${servletToCall}?identification=${identification}&action=FIRST&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
				<fmt:message key="pagination.first" var="paginagion.first"/>
       			<b><img border="0" align="middle" title="${paginagion.first}" src="<c:url value="/images/navigate_left2.png"/>" width="24" height="24"></b>
       		</a>&nbsp;&nbsp;&nbsp;&nbsp; 
       	<%
	   	}
        %> 
        <% if ((p != null) && (p.hasPrevious())){
             previous = true;
        %> 
        <a href="<c:url value="${servletToCall}?identification=${identification}&action=PREVIOUS&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        	<fmt:message key="pagination.previous" var="paginagion.previous"/>
        	<b><img border="0" align="middle" title="${paginagion.previous}" src="<c:url value="/images/navigate_left.png"/>" width="24" height="24"></b>
        </a> 
        <%
	   	   }
        %> 
        <% if ((p != null) && (p.hasNext())){
             if (previous) {
        %> &nbsp;&nbsp;&nbsp;&nbsp; 
        <%
             }
        %>
        <a href="<c:url value="${servletToCall}?identification=${identification}&action=NEXT&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        	<fmt:message	key="pagination.next" var="paginagion.next"/>
        	<b><img border="0" align="middle" title="${paginagion.next}" src="<c:url value="/images/navigate_right.png"/>" width="24" height="24"></b></a>
        	&nbsp;&nbsp;&nbsp;&nbsp; 
        <%
	   	   }
        %>
        <% 
          if ((p != null) && (p.hasNext())){
        %> 
        	<a href="<c:url value="${servletToCall}?identification=${identification}&action=LAST&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        		<fmt:message key="pagination.last" var="paginagion.last"/>
        		<b><img border="0" align="middle" title="${paginagion.last}" src="<c:url value="/images/navigate_right2.png"/>" width="24" height="24"></b>
        	</a> 
       	<%
	   	   }
        %>
	</td>
</tr>
