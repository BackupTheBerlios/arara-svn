<%@ page import="net.indrix.arara.vo.User"%>
<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<% User user = (User)session.getAttribute("user"); %>

<c:if test="${!empty param.menuLevel1}">
	<c:set var="menu" scope="session" value="${param.menuLevel1}" />
</c:if>

	<c:import url="/jsp/menu/menuLoggedCommon.jsp" />

	<c:import url="/jsp/menu/photo/menuPhoto.jsp" />

	<c:import url="/jsp/menu/photo/menuPhotoActions.jsp" />

	<c:import url="/jsp/menu/sound/menuSound.jsp" />

	<c:import url="/jsp/menu/sound/menuSoundActions.jsp" />


<div style="margin-top:6px; border-top-width:1px; border-top-style:solid;" id="accordionExample">

	<div id="panel1">
	  <div id="panel1Header">
	  	Foto
	  </div>
	  <div id="panel1Content" >
		 <br/>This 
	  </div>
	</div>

	<div id="panel2">
	  <div id="panel2Header">
	  	Som
	  </div>
		<div id="panel2Content">
		<br/>The 
		</div>
	  </div>
</div>
<script> onloads.push( accord ); function accord() { new Rico.Accordion( 'accordionExample', {panelHeight:227} ); } </script>
