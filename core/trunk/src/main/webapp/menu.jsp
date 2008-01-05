<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="/fmt" prefix="fmt"%>
<%@ page import="net.indrix.arara.vo.User"%>

<b><fmt:message key="menu.title" /></b>
<table>
 <tr>
  <td width="5%"></td>
  <td width="95%">
	<!-- START OF MENU -->
	<div id="dhtmlgoodies_slidedown_menu">

		<ul id="containerul">
		  <li>
		  	<a href="#"><fmt:message key="menu.photos.photos" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuPhotos.jsp" />		 
			</ul>
		  </li>
<c:if test="${user != null && user.addPhoto == true}">
		  <li>
		  	<a href="#"><fmt:message key="menu.photos.photos.send" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuPhotosSend.jsp" />		 
			</ul>
		  </li> 
</c:if>
		  <li>
			<a href="#"><fmt:message key="menu.sounds.sounds" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuSounds.jsp" />				      
			</ul>
		  </li>
<c:if test="${user != null && user.addSound == true}">
		  <li>
		  	<a href="#"><fmt:message key="menu.sounds.sounds.send" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuSoundsSend.jsp" />		 
			</ul>
		  </li> 
</c:if>
		  <li>
			<a href="#"><fmt:message key="menu.videos.videos" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuVideos.jsp" />				      
			</ul>
		  </li>
		  <li>
			<a href="#"><fmt:message key="menu.birdwatching" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuBirdwatching.jsp" />				      
			</ul>
		  </li>
<!--
		  <li>
			<a href="#"><fmt:message key="menu.birdlists" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuBirdLists.jsp" />				      
			</ul>
		  </li>	  
--> 		 
		  <li>
			<a href="#"><fmt:message key="menu.blogs" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuBlogs.jsp" />				      
			</ul>
		  </li>
		  <li>
			<a href="#"><fmt:message key="menu.statistics.statistics" /></a>
		  	<ul>
				<c:import url="/jsp/menu/menuStatistics.jsp" />				      
			</ul>
		  </li>
		</ul>
	</div>
	<!-- END OF MENU -->
  </td>
 </tr>
</table>