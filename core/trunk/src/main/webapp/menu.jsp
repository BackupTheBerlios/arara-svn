<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<%@ page import="net.indrix.arara.vo.User"%>
<% User user = (User)session.getAttribute("user"); %>


<div style="margin-top:6px; border-top-width:1px; border-top-style:solid; background-color:#FFDD55;" id="accordionExample">
	<div id="panel1">
	  <div id="panel1Header">
	    <img src="<%= request.getContextPath()%>/images/photo.png" alt="" width="20" height="20">
	  	<fmt:message key="menu.photos.photos" />
	  </div>
	  <div id="panel1Content" >
		<c:import url="/menuPhotos.jsp" />		 
	  </div>
	</div>
	<div id="panel2">
	  <div id="panel2Header">
	     <img src="<%= request.getContextPath()%>/images/cd_music.png" alt="" width="20" height="20">
	  	<fmt:message key="menu.sounds.sounds" />
	  </div>
		<div id="panel2Content">
	      <c:import url="/menuSounds.jsp" />				      
		</div>
	  </div>
	<div id="panel3">
	  <div id="panel3Header">
	     <img src="<%= request.getContextPath()%>/images/videocamera.png" alt="" width="20" height="20">
	  	<fmt:message key="menu.videos.videos" />
	  </div>
		<div id="panel3Content">
	      <c:import url="/menuVideos.jsp" />				      
		</div>
	  </div>
	  
</div>
<script> new Rico.Accordion( 'accordionExample', {panelHeight:200, expandedBg:'#FF6600', hoverBg:'#FF6600', collapsedBg:'#000000' } );  </script>

