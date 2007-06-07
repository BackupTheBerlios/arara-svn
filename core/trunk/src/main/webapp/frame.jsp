<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
<%@ taglib uri="/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta name="robots" content="index, follow" />
<meta name="keywords" content="animal planet, birs, aves" />
<meta name="description" content="Welcome to the Arara Web Site." />
<meta name="network" content="Birds Planet" />
<meta name="launchDate" content="01/09/2005" />
<meta name="category" content="homepage" />
<meta name="type" content="network" />
<title><fmt:message key="main.title"/></title>

<script type="text/javascript" src="<c:url value="/addclasskillclass.js"/>"></script>
<script type="text/javascript" src="<c:url value="/attachevent.js"/>"></script>
<script type="text/javascript" src="<c:url value="/addcss.js"/>"></script>
<script type="text/javascript" src="<c:url value="/tabtastic.js"/>"></script>


<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-1395834-1";
urchinTracker();
</script>

<style type="text/css">
#tabBlock {
top: 150px;
left: 120px;
width: 100%;
height: 640px;
}

#size1TabBlock {
top: 150px;
left: 120px;
width: 100%;
height: 200px;
}

#size2TabBlock {
top: 150px;
left: 120px;
width: 100%;
height: 300px;
}

#size3TabBlock {
top: 150px;
left: 120px;
width: 100%;
height: 400px;
}

#placeTabBlock {
top: 150px;
left: 120px;
width: 100%;
height: 400px;
}
#mainTabContainer {
width: 100%;
height: 100%
}

</style>



<script language="javascript">
var form_submitted = false;

function change(source){
	if(document.getElementById("alerta"+quem+"").className=='hidden') {
		document.getElementById("alerta"+quem+"").className='show';
	} else {
		document.getElementById("alerta"+quem+"").className='hidden';
	};
}

function submit_form ( ){
  if ( form_submitted ){
    alert ( "Processando. Aguarde..." );
   	return false;
  }	else {
    form_submitted = true;
   	return true;
  }
}

</script>

<style type="text/css">
table.formBorder {
	border-collapse: collapse; /* CSS2 */
	border: 2px solid #000000;
	background-color:#DDDDDD;
}
table.lineBorder {
	border-collapse: collapse; /* CSS2 */
	border: 2px solid #000000;
}
</style>


<style>
.outerbar {
	background-color: #669900;
	text-align: center;
	width: 30%;
}
body {
	font-family: Arial, Helvetica, sans-serif;
	padding: 0;
	margin: 0;
}
.page {
	padding: 60px 20px 20px 20px;
}
div.channeltitle
{
	clear: both;
	border-top: #333 1px solid;
	font-weight: bold;
	font-size: 16px;
	padding-bottom: 2px;
	margin: 5px;
	padding-top: 2px;
	border-bottom: #333 1px solid;
	font-family: Georgia, serif, 'Times New Roman';
	width: 100%
}

div.channeltitle a
{
	color: white;
	text-decoration: none;
}
div.channeltitle a:hover
{
	text-decoration: underline;
}
</style>

	<style type="text/css">
	html{
		height:100%;
	}
	body{
		font-family: Trebuchet MS, Lucida Sans Unicode, Arial, sans-serif;
		font-size:0.8em;
		margin:0px;
		padding:0px;

		background-color:#E2EBED;
		height:100%;
		text-align:center;
	}
	.clear{
		clear:both;
	}
	
	#mainContainer{
		width:760px;
		text-align:left;
		margin:0 auto;
		background-color: #FFF;
		border-left:1px solid #000;
		border-right:1px solid #000;
		height:100%;
	}
	
	#topBar{
		width:760px;
		height:100px;
	}
	#leftMenu{
		width:200px;
		padding-left:10px;
		padding-right:10px;
		float:left;
	}
	#mainContent{
		width: 520px;
		padding-right:10px;	
		float:left;
	}
	/*
	General rules
	*/

	#dhtmlgoodies_slidedown_menu li{
		list-style-type:none;
		position:relative;
	}
	#dhtmlgoodies_slidedown_menu ul{
		margin:0px;
		padding:0px;
		position:relative;

	}

	#dhtmlgoodies_slidedown_menu div{
		margin:0px;
		padding:0px;
	}
	/* 	Layout CSS */
	#dhtmlgoodies_slidedown_menu{		
		width:205px;	
	}

	/* All A tags - i.e menu items. */
	#dhtmlgoodies_slidedown_menu a{
		color: #000;
		text-decoration:none;	
		display:block;
		clear:both;
		width:170px;	
		padding-left:2px;	

	}
	
	/*
	A tags 
	*/
	#dhtmlgoodies_slidedown_menu .slMenuItem_depth1{	/* Main menu items */
		margin-top:1px;
		border-bottom:1px solid #000;
		font-weight:bold;
	}	
	#dhtmlgoodies_slidedown_menu .slMenuItem_depth2{	/* Sub menu items */
		margin-top:1px;
	}	
	#dhtmlgoodies_slidedown_menu .slMenuItem_depth3{	/* Sub menu items */
		margin-top:1px;
		font-style:italic;
		color:blue;
	}	
	#dhtmlgoodies_slidedown_menu .slMenuItem_depth4{	/* Sub menu items */
		margin-top:1px;
		color:red;
	}	
	#dhtmlgoodies_slidedown_menu .slMenuItem_depth5{	/* Sub menu items */
		margin-top:1px;
	}

	/* UL tags, i.e group of menu utems. 
	It's important to add style to the UL if you're specifying margins. If not, assign the style directly
	to the parent DIV, i.e. 
	
	#dhtmlgoodies_slidedown_menu .slideMenuDiv1
	
	instead of 
	
	#dhtmlgoodies_slidedown_menu .slideMenuDiv1 ul
	*/
	
	#dhtmlgoodies_slidedown_menu .slideMenuDiv1 ul{
		padding:1px;
	}
	#dhtmlgoodies_slidedown_menu .slideMenuDiv2 ul{
		margin-left:5px;
		padding:1px;
	}
	#dhtmlgoodies_slidedown_menu .slideMenuDiv3 ul{
		margin-left:10px;
		padding:1px;
	}
	#dhtmlgoodies_slidedown_menu .slMenuItem_depth4 ul{
		margin-left:15px;
		padding:1px;
	}
	
	</style>
	<script type="text/javascript">
	/************************************************************************************************************
	(C) www.dhtmlgoodies.com, October 2005
	
	This is a script from www.dhtmlgoodies.com. You will find this and a lot of other scripts at our website.	
	
	Terms of use:
	You are free to use this script as long as the copyright message is kept intact. However, you may not
	redistribute, sell or repost it without our permission.
	
	Update log:
	
		March, 15th: Fixed problem with sliding in MSIE
	
	Thank you!
	
	www.dhtmlgoodies.com
	Alf Magne Kalleland
	
	************************************************************************************************************/	
var expandFirstItemAutomatically = false;	// Expand first menu item automatically ?
var initMenuIdToExpand = false;	// Id of menu item that should be initially expanded. the id is defined in the <li> tag.
var expandMenuItemByUrl = true;	// Menu will automatically expand by url - i.e. if the href of the menu item is in the current location, it will expand


var initialMenuItemAlwaysExpanded = true;	// NOT IMPLEMENTED YET

var dhtmlgoodies_slmenuObj;
var divToScroll = false;
var ulToScroll = false;	
var divCounter = 1;
var otherDivsToScroll = new Array();
var divToHide = false;
var parentDivToHide = new Array();
var ulToHide = false;
var offsetOpera = 0;
if(navigator.userAgent.indexOf('Opera')>=0)offsetOpera=1;	
var slideMenuHeightOfCurrentBox = 0;
var objectsToExpand = new Array();
var initExpandIndex = 0;
var alwaysExpanedItems = new Array();
	
function popMenusToShow()
{
	var obj = divToScroll;
	var endArray = new Array();
	while(obj && obj.tagName!='BODY'){
		if(obj.tagName=='DIV' && obj.id.indexOf('slideDiv')>=0){
			var objFound = -1;
			for(var no=0;no<otherDivsToScroll.length;no++){
				if(otherDivsToScroll[no]==obj){
					objFound = no;		
				}					
			}	
			if(objFound>=0){
				otherDivsToScroll.splice(objFound,1);	
			}		
		}	
		obj = obj.parentNode;
	}	
}

function showSubMenu(e,inputObj)
{

	if(this && this.tagName)inputObj = this.parentNode;
	if(inputObj && inputObj.tagName=='LI'){
		divToScroll = inputObj.getElementsByTagName('DIV')[0];
		for(var no=0;no<otherDivsToScroll.length;no++){
			if(otherDivsToScroll[no]==divToScroll)return;
		}			
	}
	hidingInProcess = false;
	if(otherDivsToScroll.length>0){
		if(divToScroll){				
			if(otherDivsToScroll.length>0){
				popMenusToShow();
			}
			if(otherDivsToScroll.length>0){	
				autoHideMenus();
				hidingInProcess = true;
			}
		}	
	}		
	if(divToScroll && !hidingInProcess){
		divToScroll.style.display='';
		otherDivsToScroll.length = 0;
		otherDivToScroll = divToScroll.parentNode;
		otherDivsToScroll.push(divToScroll);	
		while(otherDivToScroll && otherDivToScroll.tagName!='BODY'){
			if(otherDivToScroll.tagName=='DIV' && otherDivToScroll.id.indexOf('slideDiv')>=0){
				otherDivsToScroll.push(otherDivToScroll);
									
			}
			otherDivToScroll = otherDivToScroll.parentNode;
		}			
		ulToScroll = divToScroll.getElementsByTagName('UL')[0];
		if(divToScroll.style.height.replace('px','')/1<=1)scrollDownSub();
	}	
	

}



function autoHideMenus()
{
	if(otherDivsToScroll.length>0){
		divToHide = otherDivsToScroll[otherDivsToScroll.length-1];
		parentDivToHide.length=0;
		var obj = divToHide.parentNode.parentNode.parentNode;
		while(obj && obj.tagName=='DIV'){			
			if(obj.id.indexOf('slideDiv')>=0)parentDivToHide.push(obj);
			obj = obj.parentNode.parentNode.parentNode;
		}
		var tmpHeight = (divToHide.style.height.replace('px','')/1 - slideMenuHeightOfCurrentBox);
		if(tmpHeight<0)tmpHeight=0;
		if(slideMenuHeightOfCurrentBox)divToHide.style.height = tmpHeight  + 'px';
		ulToHide = divToHide.getElementsByTagName('UL')[0];
		slideMenuHeightOfCurrentBox = ulToHide.offsetHeight;
		scrollUpMenu();		
	}else{
		slideMenuHeightOfCurrentBox = 0;
		showSubMenu();			
	}
}


function scrollUpMenu()
{

	var height = divToHide.offsetHeight;
	height-=15;
	if(height<0)height=0;
	divToHide.style.height = height + 'px';

	for(var no=0;no<parentDivToHide.length;no++){	
		parentDivToHide[no].style.height = parentDivToHide[no].getElementsByTagName('UL')[0].offsetHeight + 'px';
	}
	if(height>0){
		setTimeout('scrollUpMenu()',5);
	}else{
		divToHide.style.display='none';
		otherDivsToScroll.length = otherDivsToScroll.length-1;
		autoHideMenus();			
	}
}	

function scrollDownSub()
{
	if(divToScroll){			
		var height = divToScroll.offsetHeight/1;
		var offsetMove =Math.min(15,(ulToScroll.offsetHeight - height));
		height = height +offsetMove ;
		divToScroll.style.height = height + 'px';
		
		for(var no=1;no<otherDivsToScroll.length;no++){
			var tmpHeight = otherDivsToScroll[no].offsetHeight/1 + offsetMove;
			otherDivsToScroll[no].style.height = tmpHeight + 'px';
		}			
		if(height<ulToScroll.offsetHeight)setTimeout('scrollDownSub()',5); else {
			divToScroll = false;
			ulToScroll = false;
			if(objectsToExpand.length>0 && initExpandIndex<(objectsToExpand.length-1)){
				initExpandIndex++;
				
				showSubMenu(false,objectsToExpand[initExpandIndex]);
			}
		}
	}
}
	
function initSubItems(inputObj,currentDepth)
{		
	divCounter++;		
	var div = document.createElement('DIV');	// Creating new div		
	div.style.overflow = 'hidden';	
	div.style.position = 'relative';
	div.style.display='none';
	div.style.height = '1px';
	div.id = 'slideDiv' + divCounter;
	div.className = 'slideMenuDiv' + currentDepth;		
	inputObj.parentNode.appendChild(div);	// Appending DIV as child element of <LI> that is parent of input <UL>		
	div.appendChild(inputObj);	// Appending <UL> to the div
	var menuItem = inputObj.getElementsByTagName('LI')[0];
	while(menuItem){
		if(menuItem.tagName=='LI'){
			var aTag = menuItem.getElementsByTagName('A')[0];
			aTag.className='slMenuItem_depth'+currentDepth;	
			var subUl = menuItem.getElementsByTagName('UL');
			if(subUl.length>0){
				initSubItems(subUl[0],currentDepth+1);					
			}
			aTag.onclick = showSubMenu;				
		}			
		menuItem = menuItem.nextSibling;						
	}		
}

function initSlideDownMenu()
{
	dhtmlgoodies_slmenuObj = document.getElementById('dhtmlgoodies_slidedown_menu');
	dhtmlgoodies_slmenuObj.style.visibility='visible';
	var mainUl = dhtmlgoodies_slmenuObj.getElementsByTagName('UL')[0];		
	var mainMenuItem = mainUl.getElementsByTagName('LI')[0];
	mainItemCounter = 1;
	while(mainMenuItem){			
		if(mainMenuItem.tagName=='LI'){
			var aTag = mainMenuItem.getElementsByTagName('A')[0];
			aTag.className='slMenuItem_depth1';	
			var subUl = mainMenuItem.getElementsByTagName('UL');
			if(subUl.length>0){
				mainMenuItem.id = 'mainMenuItem' + mainItemCounter;
				initSubItems(subUl[0],2);
				aTag.onclick = showSubMenu;
				mainItemCounter++;
			}				
		}			
		mainMenuItem = mainMenuItem.nextSibling;	
	}		
	
	if(location.search.indexOf('mainMenuItemToSlide')>=0){
		var items = location.search.split('&');
		for(var no=0;no<items.length;no++){
			if(items[no].indexOf('mainMenuItemToSlide')>=0){
				values = items[no].split('=');
				showSubMenu(false,document.getElementById('mainMenuItem' + values[1]));	
				initMenuIdToExpand = false;				
			}
		}			
	}else if(expandFirstItemAutomatically>0){
		if(document.getElementById('mainMenuItem' + expandFirstItemAutomatically)){
			showSubMenu(false,document.getElementById('mainMenuItem' + expandFirstItemAutomatically));
			initMenuIdToExpand = false;
		}
	}

	if(expandMenuItemByUrl)
	{
		var aTags = dhtmlgoodies_slmenuObj.getElementsByTagName('A');
		for(var no=0;no<aTags.length;no++){
			var hrefToCheckOn = aTags[no].href;				
			if(location.href.indexOf(hrefToCheckOn)>=0 && hrefToCheckOn.indexOf('#')<hrefToCheckOn.length-1){
				initMenuIdToExpand = false;
				var obj = aTags[no].parentNode;
				while(obj && obj.id!='dhtmlgoodies_slidedown_menu'){
					if(obj.tagName=='LI'){							
						var subUl = obj.getElementsByTagName('UL');
						if(initialMenuItemAlwaysExpanded)alwaysExpanedItems[obj.parentNode] = true;
						if(subUl.length>0){								
							objectsToExpand.unshift(obj);
						}
					}
					obj = obj.parentNode;	
				}
				showSubMenu(false,objectsToExpand[0]);
				break;					
			}			
		}
	}
			
	if(initMenuIdToExpand)
	{
		objectsToExpand = new Array();
		var obj = document.getElementById(initMenuIdToExpand)
		while(obj && obj.id!='dhtmlgoodies_slidedown_menu'){
			if(obj.tagName=='LI'){
				var subUl = obj.getElementsByTagName('UL');
				if(initialMenuItemAlwaysExpanded)alwaysExpanedItems[obj.parentNode] = true;
				if(subUl.length>0){						
					objectsToExpand.unshift(obj);
				}
			}
			obj = obj.parentNode;	
		}
		
		showSubMenu(false,objectsToExpand[0]);

	}
	

		
}
window.onload = initSlideDownMenu;
</script>


</head>
<body bgcolor="#333300">

<c:set var="col1" value="${10}" scope="application"/>
<c:set var="col2" value="${15}" scope="application"/>
<c:set var="col3" value="${75}" scope="application"/>

<!-- defines the maximum width for an image -->
<c:if test="${width == null}">
	<c:set var="width" value="600" scope="application" />
</c:if>

<c:set var="mainBgColor" value="#669900" scope="application" />
<c:set var="formTitleColor" value="#000000" scope="application" />

<!-- DEBUG
PageToShow:${pageToShow}<br>
param.pageToShow:${param.pageToShow}
-->

<c:if test="${pageToShow != null && pageToShow != ''}">
	<c:set var="page" value="${pageToShow}" />
</c:if>
<c:if test="${pageToShow == null || pageToShow == ''}">
	<c:choose>
		<c:when test="${param.pageToShow == null || param.pageToShow == ''}">
			<c:if test="${pageToShow == null || pageToShow == ''}">
				<fmt:message key="main.page" var="mainPage" />
				<c:set var="page" value="${mainPage}" />
			</c:if>
			<c:if test="${pageToShow != null}">
				<c:set var="page" value="${pageToShow}" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${param.pageToShow == 'main.page'}">
				<fmt:message key="main.page" var="mainPage" />
				<c:set var="page" value="${mainPage}" />
			</c:if>
			<c:if test="${param.pageToShow != 'main.page'}">
				<c:set var="page" value="${param.pageToShow}" />
			</c:if>
		</c:otherwise>
	</c:choose>
</c:if>

<table width="98%" border="0" align="center" cellspacing="1">
  <tr>
    <td colspan="3">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="${mainBgColor}">
      		<tr>
        		<td>
			        <c:import url="/title.jsp" />
        		</td>
      		</tr>
    	</table>
    </td>
  </tr>
  <tr>
    <td width="20%" height="100%" align="left" valign="top" bgcolor="${mainBgColor}">
        <c:import url="/menu.jsp" />		
		<%--
		<c:import url="/jsp/marketing.jsp"/>
		--%>
		<hr>					
		&nbsp;<a href="<c:url value="/frame.jsp?pageToShow=/jsp/colaboradores.html"/>">Colaboradores do site</a>
        <hr>
		<img src="<c:url value="/images/chart.png"/>" alt="" width="20" height="20"><b><fmt:message key="menu.statistics.statistics"/></b><br/>
        <c:import url="/statistics.jsp" />
    </td>
    <td width="80%" colspan="2" valign="top" align="left" height="100%" bgcolor="${mainBgColor}">
		<c:import url="/jsp/showErrors.jsp" /> 
	    <c:import url="/jsp/showMessages.jsp" /> 
	    <c:import url="${page}" />        
	</td>
  </tr>
  <tr>
    <td colspan="3">
    	<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="${mainBgColor}">
      		<tr>
		        <td>
		        	<c:import url="/footer.jsp" />
		        </td>
      		</tr>
		</table>      		
    </td>
  </tr>
</table>

<center><font face="Verdana" color="#ffffff" size="-1">
Copyright � 2006 Indrix.
</font></center>

</body>
</html>
