<%@ page import="net.indrix.vo.User"%>
<% User user = (User)session.getAttribute("user"); %>

      <% if (user == null) {
      %>
	    <jsp:include page="/menuNotLogged.jsp"/>
      <% } else {
      %>
	    <jsp:include page="/menuLogged.jsp"/>
      <% }
      %>

