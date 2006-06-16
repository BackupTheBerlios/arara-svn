<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Google Maps</title>
<script
	src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAsGlxfJNNFT2UMdPvkWu4chRm87X1HRhpamZHTilSRw2Sr65wTBSt0eQyTHNf1DmOtbIoq1CNnvzFuA"
	type="text/javascript"></script>
<script type="text/javascript">
    //<![CDATA[
    function load() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map"));
        map.setCenter(new GLatLng(37.4419, -122.1419), 13);
      }
    }
    //]]>
    </script>
</head>
<body onload="load()" onunload="GUnload()">
<div id="map" style="width: 500px; height: 300px"></div>
<P><a
	href="http://www.ibge.gov.br/cidadesat/xtras/perfilwindowat.php?codmun=140010">CIDADES@</a><br />
<a
	href="http://www.sidra.ibge.gov.br/bda/territorio/infomun.asp?codmun=1400100">SIDRA</a></P>
<P>
<hr>
<hr>
<center><img src="http://www.ibge.gov.br/mapas/google/1400101p.jpg"
	width="150" height="85"></center>
</font>
<hr>
<hr>
</body>
</html>
