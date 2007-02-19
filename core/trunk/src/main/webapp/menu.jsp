<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<div style="margin-top:6px; border-top-width:1px; border-top-style:solid; background-color:#FFDD55;" id="accordionExample">
	<div id="panel1">
	  <div id="panel1Header">
	    <img src="<c:url value="/images/photo.png"/>" alt="" width="20" height="30">
	  	<fmt:message key="menu.photos.photos" />
	  </div>
	  <div id="panel1Content" >
		<c:import url="/jsp/menu/menuPhotos.jsp" />		 
	  </div>
	</div>
	<div id="panel2">
	  <div id="panel2Header">
	     <img src="<c:url value="/images/cd_music.png"/>" alt="" width="20" height="20">
	  	<fmt:message key="menu.sounds.sounds" />
	  </div>
		<div id="panel2Content">
	      <c:import url="/jsp/menu/menuSounds.jsp" />				      
		</div>
	  </div>
	<div id="panel3">
	  <div id="panel3Header">
	     <img src="<c:url value="/images/videocamera.png"/>" alt="" width="20" height="20">
	  	<fmt:message key="menu.videos.videos" />
	  </div>
		<div id="panel3Content">
	      <c:import url="/jsp/menu/menuVideos.jsp" />				      
		</div>
	  </div>
	<div id="panel4">
		<div id="panel4Header">
	    	<img src="<c:url value="/images/find.png"/>" alt="" width="20" height="20">
			<fmt:message key="menu.birdwatching" />
		</div>
		<div id="panel4Content">
			<c:import url="/jsp/menu/menuBirdwatching.jsp" />				      
		</div>
	</div>
	<div id="panel4">
		<div id="panel4Header">
	    	<!-- <img src="<c:url value="/images/videocamera.png"/>" alt="" width="20" height="20"> -->
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="menu.blogs" />
		</div>
		<div id="panel4Content">
			<c:import url="/jsp/menu/menuBlogs.jsp" />				      
		</div>
	</div>
<!-- 
	<div id="panel4">
	  <div id="panel3Header">
	     <img src="<c:url value="/images/eclub.png"/>" alt="" width="20" height="20">
	  	<fmt:message key="menu.eclub.eclub" />
	  </div>
		<div id="panel4Content">
	      <c:import url="/jsp/menu/menueClub.jsp" />				      
		</div>
	</div>
 -->	  
	  
</div>
<script> new Rico.Accordion( 'accordionExample', {panelHeight:210, expandedBg:'#FF6600', hoverBg:'#FF6600', collapsedBg:'#000000' } );  </script>

