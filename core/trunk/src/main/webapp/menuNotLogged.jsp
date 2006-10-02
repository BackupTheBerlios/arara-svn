<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<c:if test="${!empty param.menuLevel1}">
	<c:set var="menu" scope="session" value="${param.menuLevel1}" />
</c:if>

<div style="margin-top:6px; border-top-width:1px; border-top-style:solid; background-color:#FFDD55;" id="accordionExample">
	<div id="panel1">
	  <div id="panel1Header">
	    <img src="<%= request.getContextPath()%>/images/folder_movie.png" alt="" width="20" height="20">
	  	<fmt:message key="menu.photos.photos" />
	  </div>
	  <div id="panel1Content" >
	<c:import url="/jsp/menu/photo/menuPhoto.jsp" />		 
	  </div>
	</div>
	<div id="panel2">
	  <div id="panel2Header">
	     <img src="<%= request.getContextPath()%>/images/folder_music.png" alt="" width="20" height="20">
	  	<fmt:message key="menu.sounds.sounds" />
	  </div>
		<div id="panel2Content">
	      <c:import url="/jsp/menu/sound/menuSound.jsp" />			
		</div>
	  </div>
</div>
<script> new Rico.Accordion( 'accordionExample', {panelHeight:150, expandedBg:'#FF6600', hoverBg:'#FF6600', collapsedBg:'#000000' } );  </script>