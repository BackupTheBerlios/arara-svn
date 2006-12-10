var RSSRequestObject = false; // XMLHttpRequest Object
var Backend = 'http://localhost:8080/arara/rss.jsp'; // Backend url
window.setInterval("update_timer()", 1200000); // update the data every 20 mins

makeRequest(Backend);

/*
if (window.XMLHttpRequest) // try to create XMLHttpRequest
	RSSRequestObject = new XMLHttpRequest();

if (window.ActiveXObject)	// if ActiveXObject use the Microsoft.XMLHTTP
	RSSRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
*/

/*
* onreadystatechange function
*/
function ReqChange() {

	// If data received correctly
	if (RSSRequestObject.readyState==4) {
	
		// if data is valid
		if (RSSRequestObject.responseText.indexOf('invalid') == -1) 
		{ 	
			// Parsing RSS
			var node = RSSRequestObject.responseXML.documentElement; 
			
			
			// Get Channel information
			var channel = node.getElementsByTagName('channel').item(0);
			var title = channel.getElementsByTagName('title').item(0).firstChild.data;
			var link = channel.getElementsByTagName('link').item(0).firstChild.data;
			
			content = '<div class="channeltitle"><a href="'+link+'">'+title+'</a></div><ul>';
		
			// Browse items
			var items = channel.getElementsByTagName('item');
			var count = 0;
			for (var n=0; n < items.length&&count<3; n++)
			{
				var itemTitle = items[n].getElementsByTagName('title').item(0).firstChild.data;
				var itemLink = items[n].getElementsByTagName('link').item(0).firstChild.data;
				try 
				{ 
					var itemPubDate = '<font color=gray>['+items[n].getElementsByTagName('pubDate').item(0).firstChild.data+'] ';
				} 
				catch (e) 
				{ 
					var itemPubDate = '';
				}
				
			
				content += '<li>'+itemPubDate+'</font><a href="'+itemLink+'">'+itemTitle+'</a></li>';
				count += 1;
			}
			
			
			content += '</ul>';
			// Display the result
			document.getElementById("ajaxreader").innerHTML = content;

			// Tell the reader the everything is done
			document.getElementById("status").innerHTML = "Done.";
			
		}
		else {
			// Tell the reader that there was error requesting data
			document.getElementById("status").innerHTML = "<div class=error>Error requesting data.<div>";
		}
		
		HideShow('status');
	}
	
}

/*
* Main AJAX RSS reader request
*/
function RSSRequest() {

	// change the status to requesting data
	HideShow('status');
	document.getElementById("status").innerHTML = "Requesting data ...";

/*	
	// Prepare the request
	RSSRequestObject.open("GET", Backend , true);
	// Set the onreadystatechange function
	RSSRequestObject.onreadystatechange = ReqChange;
	// Send
	RSSRequestObject.send(null); 
*/
	if (!RSSRequestObject) 
	{
		alert('Giving up :( Cannot create an XMLHTTP instance');
		return false;
	}
		RSSRequestObject.onreadystatechange = alertContents;
		RSSRequestObject.open('GET', Backend, true);
		RSSRequestObject.send(null);

}

/*
* Timer
*/
function update_timer() {
	RSSRequest();
}


function HideShow(id){
	var el = GetObject(id);
	if(el.style.display=="none")
	el.style.display='';
	else
	el.style.display='none';
}

function GetObject(id){
	var el = document.getElementById(id);
	return(el);
}

/*
* 
*/

function makeRequest(url) {
	if (window.XMLHttpRequest) 
	{ // for mozilla and firefox
/*	
		try	
		{ netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead");
		} catch (e)
		{ alert("Permission UniversalBrowserRead denied.");
		}
*/		
		RSSRequestObject = false;
		RSSRequestObject = new XMLHttpRequest();
		if (RSSRequestObject.overrideMimeType)
		{ RSSRequestObject.overrideMimeType('text/xml');
		}
		}else if (window.ActiveXObject)
		{ //for ie
		try 
		{ RSSRequestObject = new ActiveXObject("Msxml2.XMLHTTP");
		}catch (e) 
			{ try 
				{ RSSRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e)
				{ }
			}
		}
/*
	if (!http_request) 
	{
		alert('Giving up :( Cannot create an XMLHTTP instance');
		return false;
	}
		http_request.onreadystatechange = alertContents;
		http_request.open('GET', url, true);
		http_request.send(null);
*/
}


function alertContents()
{ if (http_request.readyState == 4)
{ if (http_request.status == 200)
{ var string = http_request.responseText;
var xmldoc;
if (window.XMLHttpRequest) 
{ var parser = new DOMParser();
var doc = parser.parseFromString(string, "text/xml");
xmldoc = doc.documentElement;
}else if (window.ActiveXObject)
{ xmldoc = http_request.responseXML;
}
var title = xmldoc.getElementsByTagName("title")[0].firstChild.nodeValue;
var desc = xmldoc.getElementsByTagName("description")[0].firstChild.nodeValue;
alert('title '+title);alert('description '+desc);
} else 
{ alert('There was a problem with the request.');
}
}
}

