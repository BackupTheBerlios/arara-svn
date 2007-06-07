<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page import="net.indrix.arara.servlets.pagination.*"%>

		<% 
		PaginationController p = (PaginationController)session.getAttribute("soundPaginationController");
        boolean previous = false;
        boolean next = false;
		%>
<font face="Verdana" color="#ffffff" size="-1">
<tr>
	<td align="center">
	<table width="100%">
	<tr>
	<td width="25%"  align="right">
        <% 
        if ((p != null) && (p.hasPrevious())){
     	%>        
			<a href="<c:url value="${servletToCall}?action=FIRST"/>">
		       	<fmt:message key="pagination.first" var="paginationFirst"/>
		        <b><img border="0" align="middle" title="${paginationFirst}" src="<c:url value="/images/navigate_left2.gif"/>" width="24" height="24"></b>
			</a>
	        &nbsp;&nbsp;&nbsp;&nbsp; 
	    <%
		}
       	%>
       	<% 
        if ((p != null) && (p.hasPrevious())){
             previous = true;
       	%> 
	       	<a href="<c:url value="${servletToCall}?action=PREVIOUS"/>">
	       		<fmt:message key="pagination.previous" var="paginationPrevious"/>
	       		<b><img border="0" align="middle" title="${paginationPrevious}" src="<c:url value="/images/navigate_left.gif"/>" width="24" height="24"></b>
	       	</a> 
		<%
		}
       	%>
	</td>
	<td width="50%" align="center">
		[<fmt:message	key="pagination.page"/> ${paginationBean.currentPage} <fmt:message key="pagination.page.of"/> ${paginationBean.numberOfPages}]&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td width="25%"  align="left">
       	<% 
        if ((p != null) && (p.hasNext())){
             if (previous) {
       	%> 
		       	&nbsp;&nbsp;&nbsp;&nbsp; 
       	<%
             }
       	%> 
	       	<a href="<c:url value="${servletToCall}?action=NEXT"/>">
	       		<fmt:message key="pagination.next" var="paginationNext"/>
	        	<b><img border="0" align="middle" title="${paginationNext}" src="<c:url value="/images/navigate_right.gif"/>" width="24" height="24"></b></a>
			&nbsp;&nbsp;&nbsp;&nbsp; 
		<%
	   	}
       	%> 
       	<% 
		if ((p != null) && (p.hasNext())){
		%> 
	       	<a href="<c:url value="${servletToCall}?action=LAST"/>">
	       		<fmt:message key="pagination.last" var="paginationLast"/>
				<b><img border="0" align="middle" title="${paginationLast}" src="<c:url value="/images/navigate_right2.gif"/>" width="24" height="24"></b>	       		
	       	</a> 
		<%
	   	}
       	%>
	</td>
	</tr>
	</table>
	</td>
</tr>
</font>