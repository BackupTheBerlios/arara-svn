<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<%@ page import="net.indrix.arara.servlets.pagination.*"%>

		<% PaginationController p = (PaginationController)session.getAttribute("photoPaginationController");
        boolean previous = false;
        boolean next = false;
        %> 

<tr>
<form name="pageForm" action="<c:url value="${servletToCall}"/>" onSubmit="return submitPageForm()">
    <input type=hidden name="servletToCall" value="${servletToCall}">
    <input type=hidden name="action" value="GO">
    <input type=hidden name="identification" value="${identification}">
    <input type=hidden name="id" value="${id}">
    <input type=hidden name="nextPage" value="${nextPage}">
    <input type=hidden name="pageToShow" value="${pageToShow}">

	<td align="center" valign="center">
	<table width="100%">
	<tr>
	<td width="25%"  align="right">
        <% 
        if ((p != null) && (p.hasPrevious())){
       	%> 
       		<a href="<c:url value="${servletToCall}?identification=${identification}&action=FIRST&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
				<fmt:message key="pagination.first" var="paginagion.first"/>
       			<b><img border="0" align="middle" title="${paginagion.first}" src="<c:url value="/images/navigate_left2.gif"/>" width="24" height="24"></b></a>&nbsp;&nbsp;&nbsp;&nbsp; 
       	<%
	   	}
        
        if ((p != null) && (p.hasPrevious())){
             previous = true;
        %> 
        <a href="<c:url value="${servletToCall}?identification=${identification}&action=PREVIOUS&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        	<fmt:message key="pagination.previous" var="paginagion.previous"/>
        	<b><img border="0" align="middle" title="${paginagion.previous}" src="<c:url value="/images/navigate_left.gif"/>" width="24" height="24"></b>
        </a> 
        <%
	   	   }
        %> 
	</td>
	<td width="50%" align="center">
		[<fmt:message	key="pagination.page"/> 
		
		<c:forEach var='page' begin='${paginationBean.initialPageForIndex}' end='${paginationBean.finalPageForIndex}'>
			<c:if test="${page == paginationBean.currentPage}">
				<c:out value='${page}'/>
			</c:if>
			<c:if test="${page != paginationBean.currentPage}">
		        <a href="<c:url value="${servletToCall}?identification=${identification}&action=GO&pageNumber=${page}&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>"><c:out value='${page}'/></a>
			</c:if>
		    
		</c:forEach>		
		
		<c:if test="${paginationBean.finalPageForIndex < paginationBean.numberOfPages}">
			<fmt:message key="pagination.page.of"/> 
			<a href="<c:url value="${servletToCall}?identification=${identification}&action=LAST&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>"><c:out value='${paginationBean.numberOfPages}'/></a>			
		</c:if>
		]&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${paginationBean.numberOfPages > 1}">
			<fmt:message	key="pagination.page"/>: <input name="pageNumber" type="text" size="2" maxlength="2">
		</c:if>
	</td>
	<td width="25%"  align="left">
        <% if ((p != null) && (p.hasNext())){
             if (previous) {
        %> &nbsp;&nbsp;&nbsp;&nbsp; 
        <%
             }
        %>
        <a href="<c:url value="${servletToCall}?identification=${identification}&action=NEXT&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        	<fmt:message	key="pagination.next" var="paginagion.next"/>
        	<b><img border="0" align="middle" title="${paginagion.next}" src="<c:url value="/images/navigate_right.gif"/>" width="24" height="24"></b></a>
        	&nbsp;&nbsp;&nbsp;&nbsp; 
        <%
	   	   }
        %>
        <% 
          if ((p != null) && (p.hasNext())){
        %> 
        	<a href="<c:url value="${servletToCall}?identification=${identification}&action=LAST&id=${id}&nextPage=${nextPage}&pageToShow=${pageToShow}"/>">
        		<fmt:message key="pagination.last" var="paginagion.last"/>
        		<b><img border="0" align="middle" title="${paginagion.last}" src="<c:url value="/images/navigate_right2.gif"/>" width="24" height="24"></b>
        	</a> 
       	<%
	   	   }
        %>
	</td>
	</tr>
	</table>
	</td>
</form>
</tr>
