<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Google Maps</title>
<!-- LOCALHOST 
http://localhost/
ABQIAAAAsGlxfJNNFT2UMdPvkWu4chT2yXp_ZAY8_ufC3CFXhHIE1NvwkxTFVQci6Lwf-GDQJcF-481NyTg4Uw
http://localhost:8080/
ABQIAAAAsGlxfJNNFT2UMdPvkWu4chTwM0brOpm-All5BF6PoaKBxRWWERR4tnaAl_JKjqy0OuJ1dkifXIzORQ

OK
http://localhost:8080/arara/
ABQIAAAAsGlxfJNNFT2UMdPvkWu4chTPBnQVl4chRFz4gd_wd0IFnLwQYxQqCYRgnGLrqaL4QcAP-p7lzxIXiQ

http://201.72.55.61:8080/arara
ABQIAAAAsGlxfJNNFT2UMdPvkWu4chSXfif7qM1OeSkUCwGfI9nRZvUk7xQinUjgk0a-BmsdLb5eSinnMORZUw
-->
<script
	src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAsGlxfJNNFT2UMdPvkWu4chTPBnQVl4chRFz4gd_wd0IFnLwQYxQqCYRgnGLrqaL4QcAP-p7lzxIXiQ"
	type="text/javascript"></script>

<!-- LOCALHOST www.aves.
<script
	src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAsGlxfJNNFT2UMdPvkWu4chRm87X1HRhpamZHTilSRw2Sr65wTBSt0eQyTHNf1DmOtbIoq1CNnvzFuA"
	type="text/javascript"></script>
-->	
<script type="text/javascript">
    //<![CDATA[
    function load() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map"));
/*        
		GEvent.addListener(map, "moveend", function() {
		  var center = map.getCenter();
		  document.getElementById("message").innerHTML = center.toString();
		});
*/        
		map.addControl(new GSmallMapControl());
		map.addControl(new GMapTypeControl());
	  //map.setCenter(new GLatLng(37.4419, -122.1419), 13);	
	  //map.setCenter(new GLatLng(-47.06142, -22.90655), 13,G_HYBRID_MAP);
        map.setCenter(new GLatLng(<%= request.getAttribute("city.coords")%>), 5,G_HYBRID_MAP);
//		map.openInfoWindow(map.getCenter(), document.createTextNode("<%= request.getAttribute("city.name")%>"));
		
		// Our info window content
		var infoTabs = [
  			new GInfoWindowTab("<%= request.getAttribute("city.name") %>", "<%= request.getAttribute("city.name") %>"),
//			new GInfoWindowTab("Tab #2", "This is tab #2 content")
		];

		// Place a marker in the center of the map and open the info window
		// automatically
		var marker = new GMarker(map.getCenter());		
		GEvent.addListener(marker, "Info", function() {
//			marker.openInfoWindowTabsHtml(infoTabs);
			marker.openInfoWindowHtml("<%= request.getAttribute("city.name") %>");
		});
		map.addOverlay(marker);
//		marker.openInfoWindowTabsHtml(infoTabs);
		marker.openInfoWindowHtml("<%= request.getAttribute("city.name") %>");		
      }
    }
    //]]>
    </script>
</head>
<body onload="load()" onunload="GUnload()">
<div id="map" style="width: 550px; height: 500px"></div>
<%= request.getAttribute("city.info") %>

<!-- 
<a href="http://www.ibge.gov.br/cidadesat/xtras/perfilwindowat.php?codmun=140010">INFO</a><br />
<a href="http://www.sidra.ibge.gov.br/bda/territorio/infomun.asp?codmun=1400100">More info</a>
</P>
<center><img src="http://www.ibge.gov.br/mapas/google/1400101p.jpg"	width="150" height="85"></center>
 -->

</body>
</html>
