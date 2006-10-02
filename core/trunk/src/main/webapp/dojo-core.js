/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

if(typeof dojo=="undefined"){
var dj_global=this;
function dj_undef(_1,_2){
if(_2==null){
_2=dj_global;
}
return (typeof _2[_1]=="undefined");
}
if(dj_undef("djConfig")){
var djConfig={};
}
if(dj_undef("dojo")){
var dojo={};
}
dojo.version={major:0,minor:3,patch:1,flag:"",revision:Number("$Rev: 4342 $".match(/[0-9]+/)[0]),toString:function(){
with(dojo.version){
return major+"."+minor+"."+patch+flag+" ("+revision+")";
}
}};
dojo.evalProp=function(_3,_4,_5){
return (_4&&!dj_undef(_3,_4)?_4[_3]:(_5?(_4[_3]={}):undefined));
};
dojo.parseObjPath=function(_6,_7,_8){
var _9=(_7!=null?_7:dj_global);
var _a=_6.split(".");
var _b=_a.pop();
for(var i=0,l=_a.length;i<l&&_9;i++){
_9=dojo.evalProp(_a[i],_9,_8);
}
return {obj:_9,prop:_b};
};
dojo.evalObjPath=function(_d,_e){
if(typeof _d!="string"){
return dj_global;
}
if(_d.indexOf(".")==-1){
return dojo.evalProp(_d,dj_global,_e);
}
var _f=dojo.parseObjPath(_d,dj_global,_e);
if(_f){
return dojo.evalProp(_f.prop,_f.obj,_e);
}
return null;
};
dojo.errorToString=function(_10){
if(!dj_undef("message",_10)){
return _10.message;
}else{
if(!dj_undef("description",_10)){
return _10.description;
}else{
return _10;
}
}
};
dojo.raise=function(_11,_12){
if(_12){
_11=_11+": "+dojo.errorToString(_12);
}
try{
dojo.hostenv.println("FATAL: "+_11);
}
catch(e){
}
throw Error(_11);
};
dojo.debug=function(){
};
dojo.debugShallow=function(obj){
};
dojo.profile={start:function(){
},end:function(){
},stop:function(){
},dump:function(){
}};
function dj_eval(_14){
return dj_global.eval?dj_global.eval(_14):eval(_14);
}
dojo.unimplemented=function(_15,_16){
var _17="'"+_15+"' not implemented";
if(_16!=null){
_17+=" "+_16;
}
dojo.raise(_17);
};
dojo.deprecated=function(_18,_19,_1a){
var _1b="DEPRECATED: "+_18;
if(_19){
_1b+=" "+_19;
}
if(_1a){
_1b+=" -- will be removed in version: "+_1a;
}
dojo.debug(_1b);
};
dojo.inherits=function(_1c,_1d){
if(typeof _1d!="function"){
dojo.raise("dojo.inherits: superclass argument ["+_1d+"] must be a function (subclass: ["+_1c+"']");
}
_1c.prototype=new _1d();
_1c.prototype.constructor=_1c;
_1c.superclass=_1d.prototype;
_1c["super"]=_1d.prototype;
};
dojo.render=(function(){
function vscaffold(_1e,_1f){
var tmp={capable:false,support:{builtin:false,plugin:false},prefixes:_1e};
for(var _21 in _1f){
tmp[_21]=false;
}
return tmp;
}
return {name:"",ver:dojo.version,os:{win:false,linux:false,osx:false},html:vscaffold(["html"],["ie","opera","khtml","safari","moz"]),svg:vscaffold(["svg"],["corel","adobe","batik"]),vml:vscaffold(["vml"],["ie"]),swf:vscaffold(["Swf","Flash","Mm"],["mm"]),swt:vscaffold(["Swt"],["ibm"])};
})();
dojo.hostenv=(function(){
var _22={isDebug:false,allowQueryConfig:false,baseScriptUri:"",baseRelativePath:"",libraryScriptUri:"",iePreventClobber:false,ieClobberMinimal:true,preventBackButtonFix:true,searchIds:[],parseWidgets:true};
if(typeof djConfig=="undefined"){
djConfig=_22;
}else{
for(var _23 in _22){
if(typeof djConfig[_23]=="undefined"){
djConfig[_23]=_22[_23];
}
}
}
return {name_:"(unset)",version_:"(unset)",getName:function(){
return this.name_;
},getVersion:function(){
return this.version_;
},getText:function(uri){
dojo.unimplemented("getText","uri="+uri);
}};
})();
dojo.hostenv.getBaseScriptUri=function(){
if(djConfig.baseScriptUri.length){
return djConfig.baseScriptUri;
}
var uri=new String(djConfig.libraryScriptUri||djConfig.baseRelativePath);
if(!uri){
dojo.raise("Nothing returned by getLibraryScriptUri(): "+uri);
}
var _26=uri.lastIndexOf("/");
djConfig.baseScriptUri=djConfig.baseRelativePath;
return djConfig.baseScriptUri;
};
(function(){
var _27={pkgFileName:"__package__",loading_modules_:{},loaded_modules_:{},addedToLoadingCount:[],removedFromLoadingCount:[],inFlightCount:0,modulePrefixes_:{dojo:{name:"dojo",value:"src"}},setModulePrefix:function(_28,_29){
this.modulePrefixes_[_28]={name:_28,value:_29};
},getModulePrefix:function(_2a){
var mp=this.modulePrefixes_;
if((mp[_2a])&&(mp[_2a]["name"])){
return mp[_2a].value;
}
return _2a;
},getTextStack:[],loadUriStack:[],loadedUris:[],post_load_:false,modulesLoadedListeners:[],unloadListeners:[],loadNotifying:false};
for(var _2c in _27){
dojo.hostenv[_2c]=_27[_2c];
}
})();
dojo.hostenv.loadPath=function(_2d,_2e,cb){
var uri;
if((_2d.charAt(0)=="/")||(_2d.match(/^\w+:/))){
uri=_2d;
}else{
uri=this.getBaseScriptUri()+_2d;
}
if(djConfig.cacheBust&&dojo.render.html.capable){
uri+="?"+String(djConfig.cacheBust).replace(/\W+/g,"");
}
try{
return ((!_2e)?this.loadUri(uri,cb):this.loadUriAndCheck(uri,_2e,cb));
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.hostenv.loadUri=function(uri,cb){
if(this.loadedUris[uri]){
return 1;
}
var _33=this.getText(uri,null,true);
if(_33==null){
return 0;
}
this.loadedUris[uri]=true;
if(cb){
_33="("+_33+")";
}
var _34=dj_eval(_33);
if(cb){
cb(_34);
}
return 1;
};
dojo.hostenv.loadUriAndCheck=function(uri,_36,cb){
var ok=true;
try{
ok=this.loadUri(uri,cb);
}
catch(e){
dojo.debug("failed loading ",uri," with error: ",e);
}
return ((ok)&&(this.findModule(_36,false)))?true:false;
};
dojo.loaded=function(){
};
dojo.unloaded=function(){
};
dojo.hostenv.loaded=function(){
this.loadNotifying=true;
this.post_load_=true;
var mll=this.modulesLoadedListeners;
for(var x=0;x<mll.length;x++){
mll[x]();
}
this.modulesLoadedListeners=[];
this.loadNotifying=false;
dojo.loaded();
};
dojo.hostenv.unloaded=function(){
var mll=this.unloadListeners;
while(mll.length){
(mll.pop())();
}
dojo.unloaded();
};
dojo.addOnLoad=function(obj,_3d){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.modulesLoadedListeners.push(obj);
}else{
if(arguments.length>1){
dh.modulesLoadedListeners.push(function(){
obj[_3d]();
});
}
}
if(dh.post_load_&&dh.inFlightCount==0&&!dh.loadNotifying){
dh.callLoaded();
}
};
dojo.addOnUnload=function(obj,_40){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.unloadListeners.push(obj);
}else{
if(arguments.length>1){
dh.unloadListeners.push(function(){
obj[_40]();
});
}
}
};
dojo.hostenv.modulesLoaded=function(){
if(this.post_load_){
return;
}
if((this.loadUriStack.length==0)&&(this.getTextStack.length==0)){
if(this.inFlightCount>0){
dojo.debug("files still in flight!");
return;
}
dojo.hostenv.callLoaded();
}
};
dojo.hostenv.callLoaded=function(){
if(typeof setTimeout=="object"){
setTimeout("dojo.hostenv.loaded();",0);
}else{
dojo.hostenv.loaded();
}
};
dojo.hostenv.getModuleSymbols=function(_42){
var _43=_42.split(".");
for(var i=_43.length-1;i>0;i--){
var _45=_43.slice(0,i).join(".");
var _46=this.getModulePrefix(_45);
if(_46!=_45){
_43.splice(0,i,_46);
break;
}
}
return _43;
};
dojo.hostenv._global_omit_module_check=false;
dojo.hostenv.loadModule=function(_47,_48,_49){
if(!_47){
return;
}
_49=this._global_omit_module_check||_49;
var _4a=this.findModule(_47,false);
if(_4a){
return _4a;
}
if(dj_undef(_47,this.loading_modules_)){
this.addedToLoadingCount.push(_47);
}
this.loading_modules_[_47]=1;
var _4b=_47.replace(/\./g,"/")+".js";
var _4c=this.getModuleSymbols(_47);
var _4d=((_4c[0].charAt(0)!="/")&&(!_4c[0].match(/^\w+:/)));
var _4e=_4c[_4c.length-1];
var _4f=_47.split(".");
if(_4e=="*"){
_47=(_4f.slice(0,-1)).join(".");
while(_4c.length){
_4c.pop();
_4c.push(this.pkgFileName);
_4b=_4c.join("/")+".js";
if(_4d&&(_4b.charAt(0)=="/")){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,((!_49)?_47:null));
if(ok){
break;
}
_4c.pop();
}
}else{
_4b=_4c.join("/")+".js";
_47=_4f.join(".");
var ok=this.loadPath(_4b,((!_49)?_47:null));
if((!ok)&&(!_48)){
_4c.pop();
while(_4c.length){
_4b=_4c.join("/")+".js";
ok=this.loadPath(_4b,((!_49)?_47:null));
if(ok){
break;
}
_4c.pop();
_4b=_4c.join("/")+"/"+this.pkgFileName+".js";
if(_4d&&(_4b.charAt(0)=="/")){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,((!_49)?_47:null));
if(ok){
break;
}
}
}
if((!ok)&&(!_49)){
dojo.raise("Could not load '"+_47+"'; last tried '"+_4b+"'");
}
}
if(!_49&&!this["isXDomain"]){
_4a=this.findModule(_47,false);
if(!_4a){
dojo.raise("symbol '"+_47+"' is not defined after loading '"+_4b+"'");
}
}
return _4a;
};
dojo.hostenv.startPackage=function(_51){
var _52=dojo.evalObjPath((_51.split(".").slice(0,-1)).join("."));
this.loaded_modules_[(new String(_51)).toLowerCase()]=_52;
var _53=_51.split(/\./);
if(_53[_53.length-1]=="*"){
_53.pop();
}
return dojo.evalObjPath(_53.join("."),true);
};
dojo.hostenv.findModule=function(_54,_55){
var lmn=(new String(_54)).toLowerCase();
if(this.loaded_modules_[lmn]){
return this.loaded_modules_[lmn];
}
var _57=dojo.evalObjPath(_54);
if((_54)&&(typeof _57!="undefined")&&(_57)){
this.loaded_modules_[lmn]=_57;
return _57;
}
if(_55){
dojo.raise("no loaded module named '"+_54+"'");
}
return null;
};
dojo.kwCompoundRequire=function(_58){
var _59=_58["common"]||[];
var _5a=(_58[dojo.hostenv.name_])?_59.concat(_58[dojo.hostenv.name_]||[]):_59.concat(_58["default"]||[]);
for(var x=0;x<_5a.length;x++){
var _5c=_5a[x];
if(_5c.constructor==Array){
dojo.hostenv.loadModule.apply(dojo.hostenv,_5c);
}else{
dojo.hostenv.loadModule(_5c);
}
}
};
dojo.require=function(){
dojo.hostenv.loadModule.apply(dojo.hostenv,arguments);
};
dojo.requireIf=function(){
if((arguments[0]===true)||(arguments[0]=="common")||(arguments[0]&&dojo.render[arguments[0]].capable)){
var _5d=[];
for(var i=1;i<arguments.length;i++){
_5d.push(arguments[i]);
}
dojo.require.apply(dojo,_5d);
}
};
dojo.requireAfterIf=dojo.requireIf;
dojo.provide=function(){
return dojo.hostenv.startPackage.apply(dojo.hostenv,arguments);
};
dojo.setModulePrefix=function(_5f,_60){
return dojo.hostenv.setModulePrefix(_5f,_60);
};
dojo.exists=function(obj,_62){
var p=_62.split(".");
for(var i=0;i<p.length;i++){
if(!(obj[p[i]])){
return false;
}
obj=obj[p[i]];
}
return true;
};
}
if(typeof window=="undefined"){
dojo.raise("no window object");
}
(function(){
if(djConfig.allowQueryConfig){
var _65=document.location.toString();
var _66=_65.split("?",2);
if(_66.length>1){
var _67=_66[1];
var _68=_67.split("&");
for(var x in _68){
var sp=_68[x].split("=");
if((sp[0].length>9)&&(sp[0].substr(0,9)=="djConfig.")){
var opt=sp[0].substr(9);
try{
djConfig[opt]=eval(sp[1]);
}
catch(e){
djConfig[opt]=sp[1];
}
}
}
}
}
if(((djConfig["baseScriptUri"]=="")||(djConfig["baseRelativePath"]==""))&&(document&&document.getElementsByTagName)){
var _6c=document.getElementsByTagName("script");
var _6d=/(__package__|dojo|bootstrap1)\.js([\?\.]|$)/i;
for(var i=0;i<_6c.length;i++){
var src=_6c[i].getAttribute("src");
if(!src){
continue;
}
var m=src.match(_6d);
if(m){
var _71=src.substring(0,m.index);
if(src.indexOf("bootstrap1")>-1){
_71+="../";
}
if(!this["djConfig"]){
djConfig={};
}
if(djConfig["baseScriptUri"]==""){
djConfig["baseScriptUri"]=_71;
}
if(djConfig["baseRelativePath"]==""){
djConfig["baseRelativePath"]=_71;
}
break;
}
}
}
var dr=dojo.render;
var drh=dojo.render.html;
var drs=dojo.render.svg;
var dua=drh.UA=navigator.userAgent;
var dav=drh.AV=navigator.appVersion;
var t=true;
var f=false;
drh.capable=t;
drh.support.builtin=t;
dr.ver=parseFloat(drh.AV);
dr.os.mac=dav.indexOf("Macintosh")>=0;
dr.os.win=dav.indexOf("Windows")>=0;
dr.os.linux=dav.indexOf("X11")>=0;
drh.opera=dua.indexOf("Opera")>=0;
drh.khtml=(dav.indexOf("Konqueror")>=0)||(dav.indexOf("Safari")>=0);
drh.safari=dav.indexOf("Safari")>=0;
var _79=dua.indexOf("Gecko");
drh.mozilla=drh.moz=(_79>=0)&&(!drh.khtml);
if(drh.mozilla){
drh.geckoVersion=dua.substring(_79+6,_79+14);
}
drh.ie=(document.all)&&(!drh.opera);
drh.ie50=drh.ie&&dav.indexOf("MSIE 5.0")>=0;
drh.ie55=drh.ie&&dav.indexOf("MSIE 5.5")>=0;
drh.ie60=drh.ie&&dav.indexOf("MSIE 6.0")>=0;
drh.ie70=drh.ie&&dav.indexOf("MSIE 7.0")>=0;
dojo.locale=(drh.ie?navigator.userLanguage:navigator.language).toLowerCase();
dr.vml.capable=drh.ie;
drs.capable=f;
drs.support.plugin=f;
drs.support.builtin=f;
if(document.implementation&&document.implementation.hasFeature&&document.implementation.hasFeature("org.w3c.dom.svg","1.0")){
drs.capable=t;
drs.support.builtin=t;
drs.support.plugin=f;
}
})();
dojo.hostenv.startPackage("dojo.hostenv");
dojo.render.name=dojo.hostenv.name_="browser";
dojo.hostenv.searchIds=[];
dojo.hostenv._XMLHTTP_PROGIDS=["Msxml2.XMLHTTP","Microsoft.XMLHTTP","Msxml2.XMLHTTP.4.0"];
dojo.hostenv.getXmlhttpObject=function(){
var _7a=null;
var _7b=null;
try{
_7a=new XMLHttpRequest();
}
catch(e){
}
if(!_7a){
for(var i=0;i<3;++i){
var _7d=dojo.hostenv._XMLHTTP_PROGIDS[i];
try{
_7a=new ActiveXObject(_7d);
}
catch(e){
_7b=e;
}
if(_7a){
dojo.hostenv._XMLHTTP_PROGIDS=[_7d];
break;
}
}
}
if(!_7a){
return dojo.raise("XMLHTTP not available",_7b);
}
return _7a;
};
dojo.hostenv.getText=function(uri,_7f,_80){
var _81=this.getXmlhttpObject();
if(_7f){
_81.onreadystatechange=function(){
if(4==_81.readyState){
if((!_81["status"])||((200<=_81.status)&&(300>_81.status))){
_7f(_81.responseText);
}
}
};
}
_81.open("GET",uri,_7f?true:false);
try{
_81.send(null);
if(_7f){
return null;
}
if((_81["status"])&&((200>_81.status)||(300<=_81.status))){
throw Error("Unable to load "+uri+" status:"+_81.status);
}
}
catch(e){
if((_80)&&(!_7f)){
return null;
}else{
throw e;
}
}
return _81.responseText;
};
dojo.hostenv.defaultDebugContainerId="dojoDebug";
dojo.hostenv._println_buffer=[];
dojo.hostenv._println_safe=false;
dojo.hostenv.println=function(_82){
if(!dojo.hostenv._println_safe){
dojo.hostenv._println_buffer.push(_82);
}else{
try{
var _83=document.getElementById(djConfig.debugContainerId?djConfig.debugContainerId:dojo.hostenv.defaultDebugContainerId);
if(!_83){
_83=document.getElementsByTagName("body")[0]||document.body;
}
var div=document.createElement("div");
div.appendChild(document.createTextNode(_82));
_83.appendChild(div);
}
catch(e){
try{
document.write("<div>"+_82+"</div>");
}
catch(e2){
window.status=_82;
}
}
}
};
dojo.addOnLoad(function(){
dojo.hostenv._println_safe=true;
while(dojo.hostenv._println_buffer.length>0){
dojo.hostenv.println(dojo.hostenv._println_buffer.shift());
}
});
function dj_addNodeEvtHdlr(_85,_86,fp,_88){
var _89=_85["on"+_86]||function(){
};
_85["on"+_86]=function(){
fp.apply(_85,arguments);
_89.apply(_85,arguments);
};
return true;
}
dj_addNodeEvtHdlr(window,"load",function(){
if(arguments.callee.initialized){
return;
}
arguments.callee.initialized=true;
var _8a=function(){
if(dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
};
if(dojo.hostenv.inFlightCount==0){
_8a();
dojo.hostenv.modulesLoaded();
}else{
dojo.addOnLoad(_8a);
}
});
dj_addNodeEvtHdlr(window,"unload",function(){
dojo.hostenv.unloaded();
});
dojo.hostenv.makeWidgets=function(){
var _8b=[];
if(djConfig.searchIds&&djConfig.searchIds.length>0){
_8b=_8b.concat(djConfig.searchIds);
}
if(dojo.hostenv.searchIds&&dojo.hostenv.searchIds.length>0){
_8b=_8b.concat(dojo.hostenv.searchIds);
}
if((djConfig.parseWidgets)||(_8b.length>0)){
if(dojo.evalObjPath("dojo.widget.Parse")){
var _8c=new dojo.xml.Parse();
if(_8b.length>0){
for(var x=0;x<_8b.length;x++){
var _8e=document.getElementById(_8b[x]);
if(!_8e){
continue;
}
var _8f=_8c.parseElement(_8e,null,true);
dojo.widget.getParser().createComponents(_8f);
}
}else{
if(djConfig.parseWidgets){
var _8f=_8c.parseElement(document.getElementsByTagName("body")[0]||document.body,null,true);
dojo.widget.getParser().createComponents(_8f);
}
}
}
}
};
dojo.addOnLoad(function(){
if(!dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
});
try{
if(dojo.render.html.ie){
document.write("<style>v:*{ behavior:url(#default#VML); }</style>");
document.write("<xml:namespace ns=\"urn:schemas-microsoft-com:vml\" prefix=\"v\"/>");
}
}
catch(e){
}
dojo.hostenv.writeIncludes=function(){
};
dojo.byId=function(id,doc){
if(id&&(typeof id=="string"||id instanceof String)){
if(!doc){
doc=document;
}
return doc.getElementById(id);
}
return id;
};
(function(){
if(typeof dj_usingBootstrap!="undefined"){
return;
}
var _92=false;
var _93=false;
var _94=false;
if((typeof this["load"]=="function")&&((typeof this["Packages"]=="function")||(typeof this["Packages"]=="object"))){
_92=true;
}else{
if(typeof this["load"]=="function"){
_93=true;
}else{
if(window.widget){
_94=true;
}
}
}
var _95=[];
if((this["djConfig"])&&((djConfig["isDebug"])||(djConfig["debugAtAllCosts"]))){
_95.push("debug.js");
}
if((this["djConfig"])&&(djConfig["debugAtAllCosts"])&&(!_92)&&(!_94)){
_95.push("browser_debug.js");
}
if((this["djConfig"])&&(djConfig["compat"])){
_95.push("compat/"+djConfig["compat"]+".js");
}
var _96=djConfig["baseScriptUri"];
if((this["djConfig"])&&(djConfig["baseLoaderUri"])){
_96=djConfig["baseLoaderUri"];
}
for(var x=0;x<_95.length;x++){
var _98=_96+"src/"+_95[x];
if(_92||_93){
load(_98);
}else{
try{
document.write("<scr"+"ipt type='text/javascript' src='"+_98+"'></scr"+"ipt>");
}
catch(e){
var _99=document.createElement("script");
_99.src=_98;
document.getElementsByTagName("head")[0].appendChild(_99);
}
}
}
})();
dojo.fallback_locale="en";
dojo.normalizeLocale=function(_9a){
return _9a?_9a.toLowerCase():dojo.locale;
};
dojo.requireLocalization=function(_9b,_9c,_9d){
dojo.debug("EXPERIMENTAL: dojo.requireLocalization");
var _9e=dojo.hostenv.getModuleSymbols(_9b);
var _9f=_9e.concat("nls").join("/");
_9d=dojo.normalizeLocale(_9d);
var _a0=_9d.split("-");
var _a1=[];
for(var i=_a0.length;i>0;i--){
_a1.push(_a0.slice(0,i).join("-"));
}
if(_a1[_a1.length-1]!=dojo.fallback_locale){
_a1.push(dojo.fallback_locale);
}
var _a3=[_9b,"_nls",_9c].join(".");
var _a4=dojo.hostenv.startPackage(_a3);
dojo.hostenv.loaded_modules_[_a3]=_a4;
var _a5=false;
for(var i=_a1.length-1;i>=0;i--){
var loc=_a1[i];
var pkg=[_a3,loc].join(".");
var _a8=false;
if(!dojo.hostenv.findModule(pkg)){
dojo.hostenv.loaded_modules_[pkg]=null;
var _a9=[_9f,loc,_9c].join("/")+".js";
_a8=dojo.hostenv.loadPath(_a9,null,function(_aa){
_a4[loc]=_aa;
if(_a5){
for(var x in _a5){
if(!_a4[loc][x]){
_a4[loc][x]=_a5[x];
}
}
}
});
}else{
_a8=true;
}
if(_a8&&_a4[loc]){
_a5=_a4[loc];
}
}
};
dojo.provide("dojo.lang.common");
dojo.require("dojo.lang");
dojo.lang._mixin=function(obj,_ad){
var _ae={};
for(var x in _ad){
if(typeof _ae[x]=="undefined"||_ae[x]!=_ad[x]){
obj[x]=_ad[x];
}
}
if(dojo.render.html.ie&&dojo.lang.isFunction(_ad["toString"])&&_ad["toString"]!=obj["toString"]){
obj.toString=_ad.toString;
}
return obj;
};
dojo.lang.mixin=function(obj,_b1){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(obj,arguments[i]);
}
return obj;
};
dojo.lang.extend=function(_b3,_b4){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(_b3.prototype,arguments[i]);
}
return _b3;
};
dojo.lang.find=function(arr,val,_b8,_b9){
if(!dojo.lang.isArrayLike(arr)&&dojo.lang.isArrayLike(val)){
var a=arr;
arr=val;
val=a;
}
var _bb=dojo.lang.isString(arr);
if(_bb){
arr=arr.split("");
}
if(_b9){
var _bc=-1;
var i=arr.length-1;
var end=-1;
}else{
var _bc=1;
var i=0;
var end=arr.length;
}
if(_b8){
while(i!=end){
if(arr[i]===val){
return i;
}
i+=_bc;
}
}else{
while(i!=end){
if(arr[i]==val){
return i;
}
i+=_bc;
}
}
return -1;
};
dojo.lang.indexOf=dojo.lang.find;
dojo.lang.findLast=function(arr,val,_c1){
return dojo.lang.find(arr,val,_c1,true);
};
dojo.lang.lastIndexOf=dojo.lang.findLast;
dojo.lang.inArray=function(arr,val){
return dojo.lang.find(arr,val)>-1;
};
dojo.lang.isObject=function(wh){
if(typeof wh=="undefined"){
return false;
}
return (typeof wh=="object"||wh===null||dojo.lang.isArray(wh)||dojo.lang.isFunction(wh));
};
dojo.lang.isArray=function(wh){
return (wh instanceof Array||typeof wh=="array");
};
dojo.lang.isArrayLike=function(wh){
if(dojo.lang.isString(wh)){
return false;
}
if(dojo.lang.isFunction(wh)){
return false;
}
if(dojo.lang.isArray(wh)){
return true;
}
if(typeof wh!="undefined"&&wh&&dojo.lang.isNumber(wh.length)&&isFinite(wh.length)){
return true;
}
return false;
};
dojo.lang.isFunction=function(wh){
if(!wh){
return false;
}
return (wh instanceof Function||typeof wh=="function");
};
dojo.lang.isString=function(wh){
return (wh instanceof String||typeof wh=="string");
};
dojo.lang.isAlien=function(wh){
if(!wh){
return false;
}
return !dojo.lang.isFunction()&&/\{\s*\[native code\]\s*\}/.test(String(wh));
};
dojo.lang.isBoolean=function(wh){
return (wh instanceof Boolean||typeof wh=="boolean");
};
dojo.lang.isNumber=function(wh){
return (wh instanceof Number||typeof wh=="number");
};
dojo.lang.isUndefined=function(wh){
return ((wh==undefined)&&(typeof wh=="undefined"));
};
dojo.provide("dojo.lang.array");
dojo.require("dojo.lang.common");
dojo.lang.has=function(obj,_ce){
try{
return (typeof obj[_ce]!="undefined");
}
catch(e){
return false;
}
};
dojo.lang.isEmpty=function(obj){
if(dojo.lang.isObject(obj)){
var tmp={};
var _d1=0;
for(var x in obj){
if(obj[x]&&(!tmp[x])){
_d1++;
break;
}
}
return (_d1==0);
}else{
if(dojo.lang.isArrayLike(obj)||dojo.lang.isString(obj)){
return obj.length==0;
}
}
};
dojo.lang.map=function(arr,obj,_d5){
var _d6=dojo.lang.isString(arr);
if(_d6){
arr=arr.split("");
}
if(dojo.lang.isFunction(obj)&&(!_d5)){
_d5=obj;
obj=dj_global;
}else{
if(dojo.lang.isFunction(obj)&&_d5){
var _d7=obj;
obj=_d5;
_d5=_d7;
}
}
if(Array.map){
var _d8=Array.map(arr,_d5,obj);
}else{
var _d8=[];
for(var i=0;i<arr.length;++i){
_d8.push(_d5.call(obj,arr[i]));
}
}
if(_d6){
return _d8.join("");
}else{
return _d8;
}
};
dojo.lang.forEach=function(_da,_db,_dc){
if(dojo.lang.isString(_da)){
_da=_da.split("");
}
if(Array.forEach){
Array.forEach(_da,_db,_dc);
}else{
if(!_dc){
_dc=dj_global;
}
for(var i=0,l=_da.length;i<l;i++){
_db.call(_dc,_da[i],i,_da);
}
}
};
dojo.lang._everyOrSome=function(_de,arr,_e0,_e1){
if(dojo.lang.isString(arr)){
arr=arr.split("");
}
if(Array.every){
return Array[(_de)?"every":"some"](arr,_e0,_e1);
}else{
if(!_e1){
_e1=dj_global;
}
for(var i=0,l=arr.length;i<l;i++){
var _e3=_e0.call(_e1,arr[i],i,arr);
if((_de)&&(!_e3)){
return false;
}else{
if((!_de)&&(_e3)){
return true;
}
}
}
return (_de)?true:false;
}
};
dojo.lang.every=function(arr,_e5,_e6){
return this._everyOrSome(true,arr,_e5,_e6);
};
dojo.lang.some=function(arr,_e8,_e9){
return this._everyOrSome(false,arr,_e8,_e9);
};
dojo.lang.filter=function(arr,_eb,_ec){
var _ed=dojo.lang.isString(arr);
if(_ed){
arr=arr.split("");
}
if(Array.filter){
var _ee=Array.filter(arr,_eb,_ec);
}else{
if(!_ec){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_ec=dj_global;
}
var _ee=[];
for(var i=0;i<arr.length;i++){
if(_eb.call(_ec,arr[i],i,arr)){
_ee.push(arr[i]);
}
}
}
if(_ed){
return _ee.join("");
}else{
return _ee;
}
};
dojo.lang.unnest=function(){
var out=[];
for(var i=0;i<arguments.length;i++){
if(dojo.lang.isArrayLike(arguments[i])){
var add=dojo.lang.unnest.apply(this,arguments[i]);
out=out.concat(add);
}else{
out.push(arguments[i]);
}
}
return out;
};
dojo.lang.toArray=function(_f3,_f4){
var _f5=[];
for(var i=_f4||0;i<_f3.length;i++){
_f5.push(_f3[i]);
}
return _f5;
};
dojo.provide("dojo.lang.extras");
dojo.require("dojo.lang.common");
dojo.lang.setTimeout=function(_f7,_f8){
var _f9=window,argsStart=2;
if(!dojo.lang.isFunction(_f7)){
_f9=_f7;
_f7=_f8;
_f8=arguments[2];
argsStart++;
}
if(dojo.lang.isString(_f7)){
_f7=_f9[_f7];
}
var _fa=[];
for(var i=argsStart;i<arguments.length;i++){
_fa.push(arguments[i]);
}
return setTimeout(function(){
_f7.apply(_f9,_fa);
},_f8);
};
dojo.lang.getNameInObj=function(ns,_fd){
if(!ns){
ns=dj_global;
}
for(var x in ns){
if(ns[x]===_fd){
return new String(x);
}
}
return null;
};
dojo.lang.shallowCopy=function(obj){
var ret={},key;
for(key in obj){
if(dojo.lang.isUndefined(ret[key])){
ret[key]=obj[key];
}
}
return ret;
};
dojo.lang.firstValued=function(){
for(var i=0;i<arguments.length;i++){
if(typeof arguments[i]!="undefined"){
return arguments[i];
}
}
return undefined;
};
dojo.lang.getObjPathValue=function(_102,_103,_104){
with(dojo.parseObjPath(_102,_103,_104)){
return dojo.evalProp(prop,obj,_104);
}
};
dojo.lang.setObjPathValue=function(_105,_106,_107,_108){
if(arguments.length<4){
_108=true;
}
with(dojo.parseObjPath(_105,_107,_108)){
if(obj&&(_108||(prop in obj))){
obj[prop]=_106;
}
}
};
dojo.provide("dojo.lang.declare");
dojo.require("dojo.lang.common");
dojo.require("dojo.lang.extras");
dojo.lang.declare=function(_109,_10a,init,_10c){
if((dojo.lang.isFunction(_10c))||((!_10c)&&(!dojo.lang.isFunction(init)))){
var temp=_10c;
_10c=init;
init=temp;
}
var _10e=[];
if(dojo.lang.isArray(_10a)){
_10e=_10a;
_10a=_10e.shift();
}
if(!init){
init=dojo.evalObjPath(_109,false);
if((init)&&(!dojo.lang.isFunction(init))){
init=null;
}
}
var ctor=dojo.lang.declare._makeConstructor();
var scp=(_10a?_10a.prototype:null);
if(scp){
scp.prototyping=true;
ctor.prototype=new _10a();
scp.prototyping=false;
}
ctor.superclass=scp;
ctor.mixins=_10e;
for(var i=0,l=_10e.length;i<l;i++){
dojo.lang.extend(ctor,_10e[i].prototype);
}
ctor.prototype.initializer=null;
ctor.prototype.declaredClass=_109;
if(dojo.lang.isArray(_10c)){
dojo.lang.extend.apply(dojo.lang,[ctor].concat(_10c));
}else{
dojo.lang.extend(ctor,(_10c)||{});
}
dojo.lang.extend(ctor,dojo.lang.declare.base);
ctor.prototype.constructor=ctor;
ctor.prototype.initializer=(ctor.prototype.initializer)||(init)||(function(){
});
dojo.lang.setObjPathValue(_109,ctor,null,true);
};
dojo.lang.declare._makeConstructor=function(){
return function(){
var self=this._getPropContext();
var s=self.constructor.superclass;
if((s)&&(s.constructor)){
if(s.constructor==arguments.callee){
this.inherited("constructor",arguments);
}else{
this._inherited(s,"constructor",arguments);
}
}
var m=(self.constructor.mixins)||([]);
for(var i=0,l=m.length;i<l;i++){
(((m[i].prototype)&&(m[i].prototype.initializer))||(m[i])).apply(this,arguments);
}
if((!this.prototyping)&&(self.initializer)){
self.initializer.apply(this,arguments);
}
};
};
dojo.lang.declare.base={_getPropContext:function(){
return (this.___proto||this);
},_inherited:function(_116,_117,args){
var _119=this.___proto;
this.___proto=_116;
var _11a=_116[_117].apply(this,(args||[]));
this.___proto=_119;
return _11a;
},inheritedFrom:function(ctor,prop,args){
var p=((ctor)&&(ctor.prototype)&&(ctor.prototype[prop]));
return (dojo.lang.isFunction(p)?p.apply(this,(args||[])):p);
},inherited:function(prop,args){
var p=this._getPropContext();
do{
if((!p.constructor)||(!p.constructor.superclass)){
return;
}
p=p.constructor.superclass;
}while(!(prop in p));
return (dojo.lang.isFunction(p[prop])?this._inherited(p,prop,args):p[prop]);
}};
dojo.declare=dojo.lang.declare;
dojo.provide("dojo.lang.func");
dojo.require("dojo.lang.common");
dojo.lang.hitch=function(_122,_123){
if(dojo.lang.isString(_123)){
var fcn=_122[_123];
}else{
var fcn=_123;
}
return function(){
return fcn.apply(_122,arguments);
};
};
dojo.lang.anonCtr=0;
dojo.lang.anon={};
dojo.lang.nameAnonFunc=function(_125,_126,_127){
var nso=(_126||dojo.lang.anon);
if((_127)||((dj_global["djConfig"])&&(djConfig["slowAnonFuncLookups"]==true))){
for(var x in nso){
if(nso[x]===_125){
return x;
}
}
}
var ret="__"+dojo.lang.anonCtr++;
while(typeof nso[ret]!="undefined"){
ret="__"+dojo.lang.anonCtr++;
}
nso[ret]=_125;
return ret;
};
dojo.lang.forward=function(_12b){
return function(){
return this[_12b].apply(this,arguments);
};
};
dojo.lang.curry=function(ns,func){
var _12e=[];
ns=ns||dj_global;
if(dojo.lang.isString(func)){
func=ns[func];
}
for(var x=2;x<arguments.length;x++){
_12e.push(arguments[x]);
}
var _130=(func["__preJoinArity"]||func.length)-_12e.length;
function gather(_131,_132,_133){
var _134=_133;
var _135=_132.slice(0);
for(var x=0;x<_131.length;x++){
_135.push(_131[x]);
}
_133=_133-_131.length;
if(_133<=0){
var res=func.apply(ns,_135);
_133=_134;
return res;
}else{
return function(){
return gather(arguments,_135,_133);
};
}
}
return gather([],_12e,_130);
};
dojo.lang.curryArguments=function(ns,func,args,_13b){
var _13c=[];
var x=_13b||0;
for(x=_13b;x<args.length;x++){
_13c.push(args[x]);
}
return dojo.lang.curry.apply(dojo.lang,[ns,func].concat(_13c));
};
dojo.lang.tryThese=function(){
for(var x=0;x<arguments.length;x++){
try{
if(typeof arguments[x]=="function"){
var ret=(arguments[x]());
if(ret){
return ret;
}
}
}
catch(e){
dojo.debug(e);
}
}
};
dojo.lang.delayThese=function(farr,cb,_142,_143){
if(!farr.length){
if(typeof _143=="function"){
_143();
}
return;
}
if((typeof _142=="undefined")&&(typeof cb=="number")){
_142=cb;
cb=function(){
};
}else{
if(!cb){
cb=function(){
};
if(!_142){
_142=0;
}
}
}
setTimeout(function(){
(farr.shift())();
cb();
dojo.lang.delayThese(farr,cb,_142,_143);
},_142);
};
dojo.provide("dojo.event");
dojo.require("dojo.lang.array");
dojo.require("dojo.lang.extras");
dojo.require("dojo.lang.func");
dojo.event=new function(){
this.canTimeout=dojo.lang.isFunction(dj_global["setTimeout"])||dojo.lang.isAlien(dj_global["setTimeout"]);
function interpolateArgs(args,_145){
var dl=dojo.lang;
var ao={srcObj:dj_global,srcFunc:null,adviceObj:dj_global,adviceFunc:null,aroundObj:null,aroundFunc:null,adviceType:(args.length>2)?args[0]:"after",precedence:"last",once:false,delay:null,rate:0,adviceMsg:false};
switch(args.length){
case 0:
return;
case 1:
return;
case 2:
ao.srcFunc=args[0];
ao.adviceFunc=args[1];
break;
case 3:
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isFunction(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
var _148=dl.nameAnonFunc(args[2],ao.adviceObj,_145);
ao.adviceFunc=_148;
}else{
if((dl.isFunction(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=dj_global;
var _148=dl.nameAnonFunc(args[0],ao.srcObj,_145);
ao.srcFunc=_148;
ao.adviceObj=args[1];
ao.adviceFunc=args[2];
}
}
}
}
break;
case 4:
if((dl.isObject(args[0]))&&(dl.isObject(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isString(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isFunction(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
var _148=dl.nameAnonFunc(args[1],dj_global,_145);
ao.srcFunc=_148;
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))&&(dl.isFunction(args[3]))){
ao.srcObj=args[1];
ao.srcFunc=args[2];
var _148=dl.nameAnonFunc(args[3],dj_global,_145);
ao.adviceObj=dj_global;
ao.adviceFunc=_148;
}else{
if(dl.isObject(args[1])){
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=dj_global;
ao.adviceFunc=args[3];
}else{
if(dl.isObject(args[2])){
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
ao.srcObj=ao.adviceObj=ao.aroundObj=dj_global;
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
ao.aroundFunc=args[3];
}
}
}
}
}
}
break;
case 6:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundFunc=args[5];
ao.aroundObj=dj_global;
break;
default:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundObj=args[5];
ao.aroundFunc=args[6];
ao.once=args[7];
ao.delay=args[8];
ao.rate=args[9];
ao.adviceMsg=args[10];
break;
}
if(dl.isFunction(ao.aroundFunc)){
var _148=dl.nameAnonFunc(ao.aroundFunc,ao.aroundObj,_145);
ao.aroundFunc=_148;
}
if(dl.isFunction(ao.srcFunc)){
ao.srcFunc=dl.getNameInObj(ao.srcObj,ao.srcFunc);
}
if(dl.isFunction(ao.adviceFunc)){
ao.adviceFunc=dl.getNameInObj(ao.adviceObj,ao.adviceFunc);
}
if((ao.aroundObj)&&(dl.isFunction(ao.aroundFunc))){
ao.aroundFunc=dl.getNameInObj(ao.aroundObj,ao.aroundFunc);
}
if(!ao.srcObj){
dojo.raise("bad srcObj for srcFunc: "+ao.srcFunc);
}
if(!ao.adviceObj){
dojo.raise("bad adviceObj for adviceFunc: "+ao.adviceFunc);
}
return ao;
}
this.connect=function(){
if(arguments.length==1){
var ao=arguments[0];
}else{
var ao=interpolateArgs(arguments,true);
}
if(dojo.lang.isArray(ao.srcObj)&&ao.srcObj!=""){
var _14a={};
for(var x in ao){
_14a[x]=ao[x];
}
var mjps=[];
dojo.lang.forEach(ao.srcObj,function(src){
if((dojo.render.html.capable)&&(dojo.lang.isString(src))){
src=dojo.byId(src);
}
_14a.srcObj=src;
mjps.push(dojo.event.connect.call(dojo.event,_14a));
});
return mjps;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
if(ao.adviceFunc){
var mjp2=dojo.event.MethodJoinPoint.getForMethod(ao.adviceObj,ao.adviceFunc);
}
mjp.kwAddAdvice(ao);
return mjp;
};
this.log=function(a1,a2){
var _152;
if((arguments.length==1)&&(typeof a1=="object")){
_152=a1;
}else{
_152={srcObj:a1,srcFunc:a2};
}
_152.adviceFunc=function(){
var _153=[];
for(var x=0;x<arguments.length;x++){
_153.push(arguments[x]);
}
dojo.debug("("+_152.srcObj+")."+_152.srcFunc,":",_153.join(", "));
};
this.kwConnect(_152);
};
this.connectBefore=function(){
var args=["before"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectAround=function(){
var args=["around"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectOnce=function(){
var ao=interpolateArgs(arguments,true);
ao.once=true;
return this.connect(ao);
};
this._kwConnectImpl=function(_15a,_15b){
var fn=(_15b)?"disconnect":"connect";
if(typeof _15a["srcFunc"]=="function"){
_15a.srcObj=_15a["srcObj"]||dj_global;
var _15d=dojo.lang.nameAnonFunc(_15a.srcFunc,_15a.srcObj,true);
_15a.srcFunc=_15d;
}
if(typeof _15a["adviceFunc"]=="function"){
_15a.adviceObj=_15a["adviceObj"]||dj_global;
var _15d=dojo.lang.nameAnonFunc(_15a.adviceFunc,_15a.adviceObj,true);
_15a.adviceFunc=_15d;
}
return dojo.event[fn]((_15a["type"]||_15a["adviceType"]||"after"),_15a["srcObj"]||dj_global,_15a["srcFunc"],_15a["adviceObj"]||_15a["targetObj"]||dj_global,_15a["adviceFunc"]||_15a["targetFunc"],_15a["aroundObj"],_15a["aroundFunc"],_15a["once"],_15a["delay"],_15a["rate"],_15a["adviceMsg"]||false);
};
this.kwConnect=function(_15e){
return this._kwConnectImpl(_15e,false);
};
this.disconnect=function(){
var ao=interpolateArgs(arguments,true);
if(!ao.adviceFunc){
return;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
return mjp.removeAdvice(ao.adviceObj,ao.adviceFunc,ao.adviceType,ao.once);
};
this.kwDisconnect=function(_161){
return this._kwConnectImpl(_161,true);
};
};
dojo.event.MethodInvocation=function(_162,obj,args){
this.jp_=_162;
this.object=obj;
this.args=[];
for(var x=0;x<args.length;x++){
this.args[x]=args[x];
}
this.around_index=-1;
};
dojo.event.MethodInvocation.prototype.proceed=function(){
this.around_index++;
if(this.around_index>=this.jp_.around.length){
return this.jp_.object[this.jp_.methodname].apply(this.jp_.object,this.args);
}else{
var ti=this.jp_.around[this.around_index];
var mobj=ti[0]||dj_global;
var meth=ti[1];
return mobj[meth].call(mobj,this);
}
};
dojo.event.MethodJoinPoint=function(obj,_16a){
this.object=obj||dj_global;
this.methodname=_16a;
this.methodfunc=this.object[_16a];
this.before=[];
this.after=[];
this.around=[];
};
dojo.event.MethodJoinPoint.getForMethod=function(obj,_16c){
if(!obj){
obj=dj_global;
}
if(!obj[_16c]){
obj[_16c]=function(){
};
if(!obj[_16c]){
dojo.raise("Cannot set do-nothing method on that object "+_16c);
}
}else{
if((!dojo.lang.isFunction(obj[_16c]))&&(!dojo.lang.isAlien(obj[_16c]))){
return null;
}
}
var _16d=_16c+"$joinpoint";
var _16e=_16c+"$joinpoint$method";
var _16f=obj[_16d];
if(!_16f){
var _170=false;
if(dojo.event["browser"]){
if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
_170=true;
dojo.event.browser.addClobberNodeAttrs(obj,[_16d,_16e,_16c]);
}
}
var _171=obj[_16c].length;
obj[_16e]=obj[_16c];
_16f=obj[_16d]=new dojo.event.MethodJoinPoint(obj,_16e);
obj[_16c]=function(){
var args=[];
if((_170)&&(!arguments.length)){
var evt=null;
try{
if(obj.ownerDocument){
evt=obj.ownerDocument.parentWindow.event;
}else{
if(obj.documentElement){
evt=obj.documentElement.ownerDocument.parentWindow.event;
}else{
evt=window.event;
}
}
}
catch(e){
evt=window.event;
}
if(evt){
args.push(dojo.event.browser.fixEvent(evt,this));
}
}else{
for(var x=0;x<arguments.length;x++){
if((x==0)&&(_170)&&(dojo.event.browser.isEvent(arguments[x]))){
args.push(dojo.event.browser.fixEvent(arguments[x],this));
}else{
args.push(arguments[x]);
}
}
}
return _16f.run.apply(_16f,args);
};
obj[_16c].__preJoinArity=_171;
}
return _16f;
};
dojo.lang.extend(dojo.event.MethodJoinPoint,{unintercept:function(){
this.object[this.methodname]=this.methodfunc;
this.before=[];
this.after=[];
this.around=[];
},disconnect:dojo.lang.forward("unintercept"),run:function(){
var obj=this.object||dj_global;
var args=arguments;
var _177=[];
for(var x=0;x<args.length;x++){
_177[x]=args[x];
}
var _179=function(marr){
if(!marr){
dojo.debug("Null argument to unrollAdvice()");
return;
}
var _17b=marr[0]||dj_global;
var _17c=marr[1];
if(!_17b[_17c]){
dojo.raise("function \""+_17c+"\" does not exist on \""+_17b+"\"");
}
var _17d=marr[2]||dj_global;
var _17e=marr[3];
var msg=marr[6];
var _180;
var to={args:[],jp_:this,object:obj,proceed:function(){
return _17b[_17c].apply(_17b,to.args);
}};
to.args=_177;
var _182=parseInt(marr[4]);
var _183=((!isNaN(_182))&&(marr[4]!==null)&&(typeof marr[4]!="undefined"));
if(marr[5]){
var rate=parseInt(marr[5]);
var cur=new Date();
var _186=false;
if((marr["last"])&&((cur-marr.last)<=rate)){
if(dojo.event.canTimeout){
if(marr["delayTimer"]){
clearTimeout(marr.delayTimer);
}
var tod=parseInt(rate*2);
var mcpy=dojo.lang.shallowCopy(marr);
marr.delayTimer=setTimeout(function(){
mcpy[5]=0;
_179(mcpy);
},tod);
}
return;
}else{
marr.last=cur;
}
}
if(_17e){
_17d[_17e].call(_17d,to);
}else{
if((_183)&&((dojo.render.html)||(dojo.render.svg))){
dj_global["setTimeout"](function(){
if(msg){
_17b[_17c].call(_17b,to);
}else{
_17b[_17c].apply(_17b,args);
}
},_182);
}else{
if(msg){
_17b[_17c].call(_17b,to);
}else{
_17b[_17c].apply(_17b,args);
}
}
}
};
if(this.before.length>0){
dojo.lang.forEach(this.before,_179);
}
var _189;
if(this.around.length>0){
var mi=new dojo.event.MethodInvocation(this,obj,args);
_189=mi.proceed();
}else{
if(this.methodfunc){
_189=this.object[this.methodname].apply(this.object,args);
}
}
if(this.after.length>0){
dojo.lang.forEach(this.after,_179);
}
return (this.methodfunc)?_189:null;
},getArr:function(kind){
var arr=this.after;
if((typeof kind=="string")&&(kind.indexOf("before")!=-1)){
arr=this.before;
}else{
if(kind=="around"){
arr=this.around;
}
}
return arr;
},kwAddAdvice:function(args){
this.addAdvice(args["adviceObj"],args["adviceFunc"],args["aroundObj"],args["aroundFunc"],args["adviceType"],args["precedence"],args["once"],args["delay"],args["rate"],args["adviceMsg"]);
},addAdvice:function(_18e,_18f,_190,_191,_192,_193,once,_195,rate,_197){
var arr=this.getArr(_192);
if(!arr){
dojo.raise("bad this: "+this);
}
var ao=[_18e,_18f,_190,_191,_195,rate,_197];
if(once){
if(this.hasAdvice(_18e,_18f,_192,arr)>=0){
return;
}
}
if(_193=="first"){
arr.unshift(ao);
}else{
arr.push(ao);
}
},hasAdvice:function(_19a,_19b,_19c,arr){
if(!arr){
arr=this.getArr(_19c);
}
var ind=-1;
for(var x=0;x<arr.length;x++){
var aao=(typeof _19b=="object")?(new String(_19b)).toString():_19b;
var a1o=(typeof arr[x][1]=="object")?(new String(arr[x][1])).toString():arr[x][1];
if((arr[x][0]==_19a)&&(a1o==aao)){
ind=x;
}
}
return ind;
},removeAdvice:function(_1a2,_1a3,_1a4,once){
var arr=this.getArr(_1a4);
var ind=this.hasAdvice(_1a2,_1a3,_1a4,arr);
if(ind==-1){
return false;
}
while(ind!=-1){
arr.splice(ind,1);
if(once){
break;
}
ind=this.hasAdvice(_1a2,_1a3,_1a4,arr);
}
return true;
}});
dojo.provide("dojo.string.common");
dojo.require("dojo.string");
dojo.string.trim=function(str,wh){
if(!str.replace){
return str;
}
if(!str.length){
return str;
}
var re=(wh>0)?(/^\s+/):(wh<0)?(/\s+$/):(/^\s+|\s+$/g);
return str.replace(re,"");
};
dojo.string.trimStart=function(str){
return dojo.string.trim(str,1);
};
dojo.string.trimEnd=function(str){
return dojo.string.trim(str,-1);
};
dojo.string.repeat=function(str,_1ae,_1af){
var out="";
for(var i=0;i<_1ae;i++){
out+=str;
if(_1af&&i<_1ae-1){
out+=_1af;
}
}
return out;
};
dojo.string.pad=function(str,len,c,dir){
var out=String(str);
if(!c){
c="0";
}
if(!dir){
dir=1;
}
while(out.length<len){
if(dir>0){
out=c+out;
}else{
out+=c;
}
}
return out;
};
dojo.string.padLeft=function(str,len,c){
return dojo.string.pad(str,len,c,1);
};
dojo.string.padRight=function(str,len,c){
return dojo.string.pad(str,len,c,-1);
};
dojo.provide("dojo.string.extras");
dojo.require("dojo.string.common");
dojo.require("dojo.lang");
dojo.string.substituteParams=function(_1bd,hash){
var map=(typeof hash=="object")?hash:dojo.lang.toArray(arguments,1);
return _1bd.replace(/\%\{(\w+)\}/g,function(_1c0,key){
return map[key]||dojo.raise("Substitution not found: "+key);
});
};
dojo.string.paramString=function(str,_1c3,_1c4){
dojo.deprecated("dojo.string.paramString","use dojo.string.substituteParams instead","0.4");
for(var name in _1c3){
var re=new RegExp("\\%\\{"+name+"\\}","g");
str=str.replace(re,_1c3[name]);
}
if(_1c4){
str=str.replace(/%\{([^\}\s]+)\}/g,"");
}
return str;
};
dojo.string.capitalize=function(str){
if(!dojo.lang.isString(str)){
return "";
}
if(arguments.length==0){
str=this;
}
var _1c8=str.split(" ");
for(var i=0;i<_1c8.length;i++){
_1c8[i]=_1c8[i].charAt(0).toUpperCase()+_1c8[i].substring(1);
}
return _1c8.join(" ");
};
dojo.string.isBlank=function(str){
if(!dojo.lang.isString(str)){
return true;
}
return (dojo.string.trim(str).length==0);
};
dojo.string.encodeAscii=function(str){
if(!dojo.lang.isString(str)){
return str;
}
var ret="";
var _1cd=escape(str);
var _1ce,re=/%u([0-9A-F]{4})/i;
while((_1ce=_1cd.match(re))){
var num=Number("0x"+_1ce[1]);
var _1d0=escape("&#"+num+";");
ret+=_1cd.substring(0,_1ce.index)+_1d0;
_1cd=_1cd.substring(_1ce.index+_1ce[0].length);
}
ret+=_1cd.replace(/\+/g,"%2B");
return ret;
};
dojo.string.escape=function(type,str){
var args=dojo.lang.toArray(arguments,1);
switch(type.toLowerCase()){
case "xml":
case "html":
case "xhtml":
return dojo.string.escapeXml.apply(this,args);
case "sql":
return dojo.string.escapeSql.apply(this,args);
case "regexp":
case "regex":
return dojo.string.escapeRegExp.apply(this,args);
case "javascript":
case "jscript":
case "js":
return dojo.string.escapeJavaScript.apply(this,args);
case "ascii":
return dojo.string.encodeAscii.apply(this,args);
default:
return str;
}
};
dojo.string.escapeXml=function(str,_1d5){
str=str.replace(/&/gm,"&amp;").replace(/</gm,"&lt;").replace(/>/gm,"&gt;").replace(/"/gm,"&quot;");
if(!_1d5){
str=str.replace(/'/gm,"&#39;");
}
return str;
};
dojo.string.escapeSql=function(str){
return str.replace(/'/gm,"''");
};
dojo.string.escapeRegExp=function(str){
return str.replace(/\\/gm,"\\\\").replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm,"\\$1");
};
dojo.string.escapeJavaScript=function(str){
return str.replace(/(["'\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.escapeString=function(str){
return ("\""+str.replace(/(["\\])/g,"\\$1")+"\"").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r");
};
dojo.string.summary=function(str,len){
if(!len||str.length<=len){
return str;
}else{
return str.substring(0,len).replace(/\.+$/,"")+"...";
}
};
dojo.string.endsWith=function(str,end,_1de){
if(_1de){
str=str.toLowerCase();
end=end.toLowerCase();
}
if((str.length-end.length)<0){
return false;
}
return str.lastIndexOf(end)==str.length-end.length;
};
dojo.string.endsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.endsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.startsWith=function(str,_1e2,_1e3){
if(_1e3){
str=str.toLowerCase();
_1e2=_1e2.toLowerCase();
}
return str.indexOf(_1e2)==0;
};
dojo.string.startsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.startsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.has=function(str){
for(var i=1;i<arguments.length;i++){
if(str.indexOf(arguments[i])>-1){
return true;
}
}
return false;
};
dojo.string.normalizeNewlines=function(text,_1e9){
if(_1e9=="\n"){
text=text.replace(/\r\n/g,"\n");
text=text.replace(/\r/g,"\n");
}else{
if(_1e9=="\r"){
text=text.replace(/\r\n/g,"\r");
text=text.replace(/\n/g,"\r");
}else{
text=text.replace(/([^\r])\n/g,"$1\r\n");
text=text.replace(/\r([^\n])/g,"\r\n$1");
}
}
return text;
};
dojo.string.splitEscaped=function(str,_1eb){
var _1ec=[];
for(var i=0,prevcomma=0;i<str.length;i++){
if(str.charAt(i)=="\\"){
i++;
continue;
}
if(str.charAt(i)==_1eb){
_1ec.push(str.substring(prevcomma,i));
prevcomma=i+1;
}
}
_1ec.push(str.substr(prevcomma));
return _1ec;
};
dojo.provide("dojo.io.IO");
dojo.require("dojo.string");
dojo.require("dojo.lang.extras");
dojo.io.transports=[];
dojo.io.hdlrFuncNames=["load","error","timeout"];
dojo.io.Request=function(url,_1ef,_1f0,_1f1){
if((arguments.length==1)&&(arguments[0].constructor==Object)){
this.fromKwArgs(arguments[0]);
}else{
this.url=url;
if(_1ef){
this.mimetype=_1ef;
}
if(_1f0){
this.transport=_1f0;
}
if(arguments.length>=4){
this.changeUrl=_1f1;
}
}
};
dojo.lang.extend(dojo.io.Request,{url:"",mimetype:"text/plain",method:"GET",content:undefined,transport:undefined,changeUrl:undefined,formNode:undefined,sync:false,bindSuccess:false,useCache:false,preventCache:false,load:function(type,data,evt){
},error:function(type,_1f6){
},timeout:function(type){
},handle:function(){
},timeoutSeconds:0,abort:function(){
},fromKwArgs:function(_1f8){
if(_1f8["url"]){
_1f8.url=_1f8.url.toString();
}
if(_1f8["formNode"]){
_1f8.formNode=dojo.byId(_1f8.formNode);
}
if(!_1f8["method"]&&_1f8["formNode"]&&_1f8["formNode"].method){
_1f8.method=_1f8["formNode"].method;
}
if(!_1f8["handle"]&&_1f8["handler"]){
_1f8.handle=_1f8.handler;
}
if(!_1f8["load"]&&_1f8["loaded"]){
_1f8.load=_1f8.loaded;
}
if(!_1f8["changeUrl"]&&_1f8["changeURL"]){
_1f8.changeUrl=_1f8.changeURL;
}
_1f8.encoding=dojo.lang.firstValued(_1f8["encoding"],djConfig["bindEncoding"],"");
_1f8.sendTransport=dojo.lang.firstValued(_1f8["sendTransport"],djConfig["ioSendTransport"],false);
var _1f9=dojo.lang.isFunction;
for(var x=0;x<dojo.io.hdlrFuncNames.length;x++){
var fn=dojo.io.hdlrFuncNames[x];
if(_1f9(_1f8[fn])){
continue;
}
if(_1f9(_1f8["handle"])){
_1f8[fn]=_1f8.handle;
}
}
dojo.lang.mixin(this,_1f8);
}});
dojo.io.Error=function(msg,type,num){
this.message=msg;
this.type=type||"unknown";
this.number=num||0;
};
dojo.io.transports.addTransport=function(name){
this.push(name);
this[name]=dojo.io[name];
};
dojo.io.bind=function(_200){
if(!(_200 instanceof dojo.io.Request)){
try{
_200=new dojo.io.Request(_200);
}
catch(e){
dojo.debug(e);
}
}
var _201="";
if(_200["transport"]){
_201=_200["transport"];
if(!this[_201]){
return _200;
}
}else{
for(var x=0;x<dojo.io.transports.length;x++){
var tmp=dojo.io.transports[x];
if((this[tmp])&&(this[tmp].canHandle(_200))){
_201=tmp;
}
}
if(_201==""){
return _200;
}
}
this[_201].bind(_200);
_200.bindSuccess=true;
return _200;
};
dojo.io.queueBind=function(_204){
if(!(_204 instanceof dojo.io.Request)){
try{
_204=new dojo.io.Request(_204);
}
catch(e){
dojo.debug(e);
}
}
var _205=_204.load;
_204.load=function(){
dojo.io._queueBindInFlight=false;
var ret=_205.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
var _207=_204.error;
_204.error=function(){
dojo.io._queueBindInFlight=false;
var ret=_207.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
dojo.io._bindQueue.push(_204);
dojo.io._dispatchNextQueueBind();
return _204;
};
dojo.io._dispatchNextQueueBind=function(){
if(!dojo.io._queueBindInFlight){
dojo.io._queueBindInFlight=true;
if(dojo.io._bindQueue.length>0){
dojo.io.bind(dojo.io._bindQueue.shift());
}else{
dojo.io._queueBindInFlight=false;
}
}
};
dojo.io._bindQueue=[];
dojo.io._queueBindInFlight=false;
dojo.io.argsFromMap=function(map,_20a,last){
var enc=/utf/i.test(_20a||"")?encodeURIComponent:dojo.string.encodeAscii;
var _20d=[];
var _20e=new Object();
for(var name in map){
var _210=function(elt){
var val=enc(name)+"="+enc(elt);
_20d[(last==name)?"push":"unshift"](val);
};
if(!_20e[name]){
var _213=map[name];
if(dojo.lang.isArray(_213)){
dojo.lang.forEach(_213,_210);
}else{
_210(_213);
}
}
}
return _20d.join("&");
};
dojo.io.setIFrameSrc=function(_214,src,_216){
try{
var r=dojo.render.html;
if(!_216){
if(r.safari){
_214.location=src;
}else{
frames[_214.name].location=src;
}
}else{
var idoc;
if(r.ie){
idoc=_214.contentWindow.document;
}else{
if(r.safari){
idoc=_214.document;
}else{
idoc=_214.contentWindow;
}
}
if(!idoc){
_214.location=src;
return;
}else{
idoc.location.replace(src);
}
}
}
catch(e){
dojo.debug(e);
dojo.debug("setIFrameSrc: "+e);
}
};
dojo.provide("dojo.io.cookie");
dojo.io.cookie.setCookie=function(name,_21a,days,path,_21d,_21e){
var _21f=-1;
if(typeof days=="number"&&days>=0){
var d=new Date();
d.setTime(d.getTime()+(days*24*60*60*1000));
_21f=d.toGMTString();
}
_21a=escape(_21a);
document.cookie=name+"="+_21a+";"+(_21f!=-1?" expires="+_21f+";":"")+(path?"path="+path:"")+(_21d?"; domain="+_21d:"")+(_21e?"; secure":"");
};
dojo.io.cookie.set=dojo.io.cookie.setCookie;
dojo.io.cookie.getCookie=function(name){
var idx=document.cookie.lastIndexOf(name+"=");
if(idx==-1){
return null;
}
var _223=document.cookie.substring(idx+name.length+1);
var end=_223.indexOf(";");
if(end==-1){
end=_223.length;
}
_223=_223.substring(0,end);
_223=unescape(_223);
return _223;
};
dojo.io.cookie.get=dojo.io.cookie.getCookie;
dojo.io.cookie.deleteCookie=function(name){
dojo.io.cookie.setCookie(name,"-",0);
};
dojo.io.cookie.setObjectCookie=function(name,obj,days,path,_22a,_22b,_22c){
if(arguments.length==5){
_22c=_22a;
_22a=null;
_22b=null;
}
var _22d=[],cookie,value="";
if(!_22c){
cookie=dojo.io.cookie.getObjectCookie(name);
}
if(days>=0){
if(!cookie){
cookie={};
}
for(var prop in obj){
if(prop==null){
delete cookie[prop];
}else{
if(typeof obj[prop]=="string"||typeof obj[prop]=="number"){
cookie[prop]=obj[prop];
}
}
}
prop=null;
for(var prop in cookie){
_22d.push(escape(prop)+"="+escape(cookie[prop]));
}
value=_22d.join("&");
}
dojo.io.cookie.setCookie(name,value,days,path,_22a,_22b);
};
dojo.io.cookie.getObjectCookie=function(name){
var _230=null,cookie=dojo.io.cookie.getCookie(name);
if(cookie){
_230={};
var _231=cookie.split("&");
for(var i=0;i<_231.length;i++){
var pair=_231[i].split("=");
var _234=pair[1];
if(isNaN(_234)){
_234=unescape(pair[1]);
}
_230[unescape(pair[0])]=_234;
}
}
return _230;
};
dojo.io.cookie.isSupported=function(){
if(typeof navigator.cookieEnabled!="boolean"){
dojo.io.cookie.setCookie("__TestingYourBrowserForCookieSupport__","CookiesAllowed",90,null);
var _235=dojo.io.cookie.getCookie("__TestingYourBrowserForCookieSupport__");
navigator.cookieEnabled=(_235=="CookiesAllowed");
if(navigator.cookieEnabled){
this.deleteCookie("__TestingYourBrowserForCookieSupport__");
}
}
return navigator.cookieEnabled;
};
if(!dojo.io.cookies){
dojo.io.cookies=dojo.io.cookie;
}
dojo.provide("dojo.AdapterRegistry");
dojo.require("dojo.lang.func");
dojo.AdapterRegistry=function(){
this.pairs=[];
};
dojo.lang.extend(dojo.AdapterRegistry,{register:function(name,_237,wrap,_239){
if(_239){
this.pairs.unshift([name,_237,wrap]);
}else{
this.pairs.push([name,_237,wrap]);
}
},match:function(){
for(var i=0;i<this.pairs.length;i++){
var pair=this.pairs[i];
if(pair[1].apply(this,arguments)){
return pair[2].apply(this,arguments);
}
}
throw new Error("No match found");
},unregister:function(name){
for(var i=0;i<this.pairs.length;i++){
var pair=this.pairs[i];
if(pair[0]==name){
this.pairs.splice(i,1);
return true;
}
}
return false;
}});
dojo.provide("dojo.json");
dojo.require("dojo.lang.func");
dojo.require("dojo.string.extras");
dojo.require("dojo.AdapterRegistry");
dojo.json={jsonRegistry:new dojo.AdapterRegistry(),register:function(name,_240,wrap,_242){
dojo.json.jsonRegistry.register(name,_240,wrap,_242);
},evalJson:function(json){
try{
return eval("("+json+")");
}
catch(e){
dojo.debug(e);
return json;
}
},evalJSON:function(json){
dojo.deprecated("dojo.json.evalJSON","use dojo.json.evalJson","0.4");
return this.evalJson(json);
},serialize:function(o){
var _246=typeof (o);
if(_246=="undefined"){
return "undefined";
}else{
if((_246=="number")||(_246=="boolean")){
return o+"";
}else{
if(o===null){
return "null";
}
}
}
if(_246=="string"){
return dojo.string.escapeString(o);
}
var me=arguments.callee;
var _248;
if(typeof (o.__json__)=="function"){
_248=o.__json__();
if(o!==_248){
return me(_248);
}
}
if(typeof (o.json)=="function"){
_248=o.json();
if(o!==_248){
return me(_248);
}
}
if(_246!="function"&&typeof (o.length)=="number"){
var res=[];
for(var i=0;i<o.length;i++){
var val=me(o[i]);
if(typeof (val)!="string"){
val="undefined";
}
res.push(val);
}
return "["+res.join(",")+"]";
}
try{
window.o=o;
_248=dojo.json.jsonRegistry.match(o);
return me(_248);
}
catch(e){
}
if(_246=="function"){
return null;
}
res=[];
for(var k in o){
var _24d;
if(typeof (k)=="number"){
_24d="\""+k+"\"";
}else{
if(typeof (k)=="string"){
_24d=dojo.string.escapeString(k);
}else{
continue;
}
}
val=me(o[k]);
if(typeof (val)!="string"){
continue;
}
res.push(_24d+":"+val);
}
return "{"+res.join(",")+"}";
}};
dojo.provide("dojo.dom");
dojo.require("dojo.lang.array");
dojo.dom.ELEMENT_NODE=1;
dojo.dom.ATTRIBUTE_NODE=2;
dojo.dom.TEXT_NODE=3;
dojo.dom.CDATA_SECTION_NODE=4;
dojo.dom.ENTITY_REFERENCE_NODE=5;
dojo.dom.ENTITY_NODE=6;
dojo.dom.PROCESSING_INSTRUCTION_NODE=7;
dojo.dom.COMMENT_NODE=8;
dojo.dom.DOCUMENT_NODE=9;
dojo.dom.DOCUMENT_TYPE_NODE=10;
dojo.dom.DOCUMENT_FRAGMENT_NODE=11;
dojo.dom.NOTATION_NODE=12;
dojo.dom.dojoml="http://www.dojotoolkit.org/2004/dojoml";
dojo.dom.xmlns={svg:"http://www.w3.org/2000/svg",smil:"http://www.w3.org/2001/SMIL20/",mml:"http://www.w3.org/1998/Math/MathML",cml:"http://www.xml-cml.org",xlink:"http://www.w3.org/1999/xlink",xhtml:"http://www.w3.org/1999/xhtml",xul:"http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul",xbl:"http://www.mozilla.org/xbl",fo:"http://www.w3.org/1999/XSL/Format",xsl:"http://www.w3.org/1999/XSL/Transform",xslt:"http://www.w3.org/1999/XSL/Transform",xi:"http://www.w3.org/2001/XInclude",xforms:"http://www.w3.org/2002/01/xforms",saxon:"http://icl.com/saxon",xalan:"http://xml.apache.org/xslt",xsd:"http://www.w3.org/2001/XMLSchema",dt:"http://www.w3.org/2001/XMLSchema-datatypes",xsi:"http://www.w3.org/2001/XMLSchema-instance",rdf:"http://www.w3.org/1999/02/22-rdf-syntax-ns#",rdfs:"http://www.w3.org/2000/01/rdf-schema#",dc:"http://purl.org/dc/elements/1.1/",dcq:"http://purl.org/dc/qualifiers/1.0","soap-env":"http://schemas.xmlsoap.org/soap/envelope/",wsdl:"http://schemas.xmlsoap.org/wsdl/",AdobeExtensions:"http://ns.adobe.com/AdobeSVGViewerExtensions/3.0/"};
dojo.dom.isNode=function(wh){
if(typeof Element=="object"){
try{
return wh instanceof Element;
}
catch(E){
}
}else{
return wh&&!isNaN(wh.nodeType);
}
};
dojo.dom.getTagName=function(node){
dojo.deprecated("dojo.dom.getTagName","use node.tagName instead","0.4");
var _250=node.tagName;
if(_250.substr(0,5).toLowerCase()!="dojo:"){
if(_250.substr(0,4).toLowerCase()=="dojo"){
return "dojo:"+_250.substring(4).toLowerCase();
}
var djt=node.getAttribute("dojoType")||node.getAttribute("dojotype");
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((node.getAttributeNS)&&(node.getAttributeNS(this.dojoml,"type"))){
return "dojo:"+node.getAttributeNS(this.dojoml,"type").toLowerCase();
}
try{
djt=node.getAttribute("dojo:type");
}
catch(e){
}
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((!dj_global["djConfig"])||(!djConfig["ignoreClassNames"])){
var _252=node.className||node.getAttribute("class");
if((_252)&&(_252.indexOf)&&(_252.indexOf("dojo-")!=-1)){
var _253=_252.split(" ");
for(var x=0;x<_253.length;x++){
if((_253[x].length>5)&&(_253[x].indexOf("dojo-")>=0)){
return "dojo:"+_253[x].substr(5).toLowerCase();
}
}
}
}
}
return _250.toLowerCase();
};
dojo.dom.getUniqueId=function(){
do{
var id="dj_unique_"+(++arguments.callee._idIncrement);
}while(document.getElementById(id));
return id;
};
dojo.dom.getUniqueId._idIncrement=0;
dojo.dom.firstElement=dojo.dom.getFirstChildElement=function(_256,_257){
var node=_256.firstChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.nextSibling;
}
if(_257&&node&&node.tagName&&node.tagName.toLowerCase()!=_257.toLowerCase()){
node=dojo.dom.nextElement(node,_257);
}
return node;
};
dojo.dom.lastElement=dojo.dom.getLastChildElement=function(_259,_25a){
var node=_259.lastChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.previousSibling;
}
if(_25a&&node&&node.tagName&&node.tagName.toLowerCase()!=_25a.toLowerCase()){
node=dojo.dom.prevElement(node,_25a);
}
return node;
};
dojo.dom.nextElement=dojo.dom.getNextSiblingElement=function(node,_25d){
if(!node){
return null;
}
do{
node=node.nextSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_25d&&_25d.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.nextElement(node,_25d);
}
return node;
};
dojo.dom.prevElement=dojo.dom.getPreviousSiblingElement=function(node,_25f){
if(!node){
return null;
}
if(_25f){
_25f=_25f.toLowerCase();
}
do{
node=node.previousSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_25f&&_25f.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.prevElement(node,_25f);
}
return node;
};
dojo.dom.moveChildren=function(_260,_261,trim){
var _263=0;
if(trim){
while(_260.hasChildNodes()&&_260.firstChild.nodeType==dojo.dom.TEXT_NODE){
_260.removeChild(_260.firstChild);
}
while(_260.hasChildNodes()&&_260.lastChild.nodeType==dojo.dom.TEXT_NODE){
_260.removeChild(_260.lastChild);
}
}
while(_260.hasChildNodes()){
_261.appendChild(_260.firstChild);
_263++;
}
return _263;
};
dojo.dom.copyChildren=function(_264,_265,trim){
var _267=_264.cloneNode(true);
return this.moveChildren(_267,_265,trim);
};
dojo.dom.removeChildren=function(node){
var _269=node.childNodes.length;
while(node.hasChildNodes()){
node.removeChild(node.firstChild);
}
return _269;
};
dojo.dom.replaceChildren=function(node,_26b){
dojo.dom.removeChildren(node);
node.appendChild(_26b);
};
dojo.dom.removeNode=function(node){
if(node&&node.parentNode){
return node.parentNode.removeChild(node);
}
};
dojo.dom.getAncestors=function(node,_26e,_26f){
var _270=[];
var _271=dojo.lang.isFunction(_26e);
while(node){
if(!_271||_26e(node)){
_270.push(node);
}
if(_26f&&_270.length>0){
return _270[0];
}
node=node.parentNode;
}
if(_26f){
return null;
}
return _270;
};
dojo.dom.getAncestorsByTag=function(node,tag,_274){
tag=tag.toLowerCase();
return dojo.dom.getAncestors(node,function(el){
return ((el.tagName)&&(el.tagName.toLowerCase()==tag));
},_274);
};
dojo.dom.getFirstAncestorByTag=function(node,tag){
return dojo.dom.getAncestorsByTag(node,tag,true);
};
dojo.dom.isDescendantOf=function(node,_279,_27a){
if(_27a&&node){
node=node.parentNode;
}
while(node){
if(node==_279){
return true;
}
node=node.parentNode;
}
return false;
};
dojo.dom.innerXML=function(node){
if(node.innerXML){
return node.innerXML;
}else{
if(node.xml){
return node.xml;
}else{
if(typeof XMLSerializer!="undefined"){
return (new XMLSerializer()).serializeToString(node);
}
}
}
};
dojo.dom.createDocument=function(){
var doc=null;
if(!dj_undef("ActiveXObject")){
var _27d=["MSXML2","Microsoft","MSXML","MSXML3"];
for(var i=0;i<_27d.length;i++){
try{
doc=new ActiveXObject(_27d[i]+".XMLDOM");
}
catch(e){
}
if(doc){
break;
}
}
}else{
if((document.implementation)&&(document.implementation.createDocument)){
doc=document.implementation.createDocument("","",null);
}
}
return doc;
};
dojo.dom.createDocumentFromText=function(str,_280){
if(!_280){
_280="text/xml";
}
if(!dj_undef("DOMParser")){
var _281=new DOMParser();
return _281.parseFromString(str,_280);
}else{
if(!dj_undef("ActiveXObject")){
var _282=dojo.dom.createDocument();
if(_282){
_282.async=false;
_282.loadXML(str);
return _282;
}else{
dojo.debug("toXml didn't work?");
}
}else{
if(document.createElement){
var tmp=document.createElement("xml");
tmp.innerHTML=str;
if(document.implementation&&document.implementation.createDocument){
var _284=document.implementation.createDocument("foo","",null);
for(var i=0;i<tmp.childNodes.length;i++){
_284.importNode(tmp.childNodes.item(i),true);
}
return _284;
}
return ((tmp.document)&&(tmp.document.firstChild?tmp.document.firstChild:tmp));
}
}
}
return null;
};
dojo.dom.prependChild=function(node,_287){
if(_287.firstChild){
_287.insertBefore(node,_287.firstChild);
}else{
_287.appendChild(node);
}
return true;
};
dojo.dom.insertBefore=function(node,ref,_28a){
if(_28a!=true&&(node===ref||node.nextSibling===ref)){
return false;
}
var _28b=ref.parentNode;
_28b.insertBefore(node,ref);
return true;
};
dojo.dom.insertAfter=function(node,ref,_28e){
var pn=ref.parentNode;
if(ref==pn.lastChild){
if((_28e!=true)&&(node===ref)){
return false;
}
pn.appendChild(node);
}else{
return this.insertBefore(node,ref.nextSibling,_28e);
}
return true;
};
dojo.dom.insertAtPosition=function(node,ref,_292){
if((!node)||(!ref)||(!_292)){
return false;
}
switch(_292.toLowerCase()){
case "before":
return dojo.dom.insertBefore(node,ref);
case "after":
return dojo.dom.insertAfter(node,ref);
case "first":
if(ref.firstChild){
return dojo.dom.insertBefore(node,ref.firstChild);
}else{
ref.appendChild(node);
return true;
}
break;
default:
ref.appendChild(node);
return true;
}
};
dojo.dom.insertAtIndex=function(node,_294,_295){
var _296=_294.childNodes;
if(!_296.length){
_294.appendChild(node);
return true;
}
var _297=null;
for(var i=0;i<_296.length;i++){
var _299=_296.item(i)["getAttribute"]?parseInt(_296.item(i).getAttribute("dojoinsertionindex")):-1;
if(_299<_295){
_297=_296.item(i);
}
}
if(_297){
return dojo.dom.insertAfter(node,_297);
}else{
return dojo.dom.insertBefore(node,_296.item(0));
}
};
dojo.dom.textContent=function(node,text){
if(text){
dojo.dom.replaceChildren(node,document.createTextNode(text));
return text;
}else{
var _29c="";
if(node==null){
return _29c;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
_29c+=dojo.dom.textContent(node.childNodes[i]);
break;
case 3:
case 2:
case 4:
_29c+=node.childNodes[i].nodeValue;
break;
default:
break;
}
}
return _29c;
}
};
dojo.dom.collectionToArray=function(_29e){
dojo.deprecated("dojo.dom.collectionToArray","use dojo.lang.toArray instead","0.4");
return dojo.lang.toArray(_29e);
};
dojo.dom.hasParent=function(node){
return node&&node.parentNode&&dojo.dom.isNode(node.parentNode);
};
dojo.dom.isTag=function(node){
if(node&&node.tagName){
var arr=dojo.lang.toArray(arguments,1);
return arr[dojo.lang.find(node.tagName,arr)]||"";
}
return "";
};
dojo.provide("dojo.graphics.color");
dojo.require("dojo.lang.array");
dojo.graphics.color.Color=function(r,g,b,a){
if(dojo.lang.isArray(r)){
this.r=r[0];
this.g=r[1];
this.b=r[2];
this.a=r[3]||1;
}else{
if(dojo.lang.isString(r)){
var rgb=dojo.graphics.color.extractRGB(r);
this.r=rgb[0];
this.g=rgb[1];
this.b=rgb[2];
this.a=g||1;
}else{
if(r instanceof dojo.graphics.color.Color){
this.r=r.r;
this.b=r.b;
this.g=r.g;
this.a=r.a;
}else{
this.r=r;
this.g=g;
this.b=b;
this.a=a;
}
}
}
};
dojo.graphics.color.Color.fromArray=function(arr){
return new dojo.graphics.color.Color(arr[0],arr[1],arr[2],arr[3]);
};
dojo.lang.extend(dojo.graphics.color.Color,{toRgb:function(_2a8){
if(_2a8){
return this.toRgba();
}else{
return [this.r,this.g,this.b];
}
},toRgba:function(){
return [this.r,this.g,this.b,this.a];
},toHex:function(){
return dojo.graphics.color.rgb2hex(this.toRgb());
},toCss:function(){
return "rgb("+this.toRgb().join()+")";
},toString:function(){
return this.toHex();
},blend:function(_2a9,_2aa){
return dojo.graphics.color.blend(this.toRgb(),new dojo.graphics.color.Color(_2a9).toRgb(),_2aa);
}});
dojo.graphics.color.named={white:[255,255,255],black:[0,0,0],red:[255,0,0],green:[0,255,0],blue:[0,0,255],navy:[0,0,128],gray:[128,128,128],silver:[192,192,192]};
dojo.graphics.color.blend=function(a,b,_2ad){
if(typeof a=="string"){
return dojo.graphics.color.blendHex(a,b,_2ad);
}
if(!_2ad){
_2ad=0;
}else{
if(_2ad>1){
_2ad=1;
}else{
if(_2ad<-1){
_2ad=-1;
}
}
}
var c=new Array(3);
for(var i=0;i<3;i++){
var half=Math.abs(a[i]-b[i])/2;
c[i]=Math.floor(Math.min(a[i],b[i])+half+(half*_2ad));
}
return c;
};
dojo.graphics.color.blendHex=function(a,b,_2b3){
return dojo.graphics.color.rgb2hex(dojo.graphics.color.blend(dojo.graphics.color.hex2rgb(a),dojo.graphics.color.hex2rgb(b),_2b3));
};
dojo.graphics.color.extractRGB=function(_2b4){
var hex="0123456789abcdef";
_2b4=_2b4.toLowerCase();
if(_2b4.indexOf("rgb")==0){
var _2b6=_2b4.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
var ret=_2b6.splice(1,3);
return ret;
}else{
var _2b8=dojo.graphics.color.hex2rgb(_2b4);
if(_2b8){
return _2b8;
}else{
return dojo.graphics.color.named[_2b4]||[255,255,255];
}
}
};
dojo.graphics.color.hex2rgb=function(hex){
var _2ba="0123456789ABCDEF";
var rgb=new Array(3);
if(hex.indexOf("#")==0){
hex=hex.substring(1);
}
hex=hex.toUpperCase();
if(hex.replace(new RegExp("["+_2ba+"]","g"),"")!=""){
return null;
}
if(hex.length==3){
rgb[0]=hex.charAt(0)+hex.charAt(0);
rgb[1]=hex.charAt(1)+hex.charAt(1);
rgb[2]=hex.charAt(2)+hex.charAt(2);
}else{
rgb[0]=hex.substring(0,2);
rgb[1]=hex.substring(2,4);
rgb[2]=hex.substring(4);
}
for(var i=0;i<rgb.length;i++){
rgb[i]=_2ba.indexOf(rgb[i].charAt(0))*16+_2ba.indexOf(rgb[i].charAt(1));
}
return rgb;
};
dojo.graphics.color.rgb2hex=function(r,g,b){
if(dojo.lang.isArray(r)){
g=r[1]||0;
b=r[2]||0;
r=r[0]||0;
}
var ret=dojo.lang.map([r,g,b],function(x){
x=new Number(x);
var s=x.toString(16);
while(s.length<2){
s="0"+s;
}
return s;
});
ret.unshift("#");
return ret.join("");
};
dojo.provide("dojo.uri.Uri");
dojo.uri=new function(){
this.joinPath=function(){
var arr=[];
for(var i=0;i<arguments.length;i++){
arr.push(arguments[i]);
}
return arr.join("/").replace(/\/{2,}/g,"/").replace(/((https*|ftps*):)/i,"$1/");
};
this.dojoUri=function(uri){
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri(),uri);
};
this.Uri=function(){
var uri=arguments[0];
for(var i=1;i<arguments.length;i++){
if(!arguments[i]){
continue;
}
var _2c8=new dojo.uri.Uri(arguments[i].toString());
var _2c9=new dojo.uri.Uri(uri.toString());
if(_2c8.path==""&&_2c8.scheme==null&&_2c8.authority==null&&_2c8.query==null){
if(_2c8.fragment!=null){
_2c9.fragment=_2c8.fragment;
}
_2c8=_2c9;
}else{
if(_2c8.scheme==null){
_2c8.scheme=_2c9.scheme;
if(_2c8.authority==null){
_2c8.authority=_2c9.authority;
if(_2c8.path.charAt(0)!="/"){
var path=_2c9.path.substring(0,_2c9.path.lastIndexOf("/")+1)+_2c8.path;
var segs=path.split("/");
for(var j=0;j<segs.length;j++){
if(segs[j]=="."){
if(j==segs.length-1){
segs[j]="";
}else{
segs.splice(j,1);
j--;
}
}else{
if(j>0&&!(j==1&&segs[0]=="")&&segs[j]==".."&&segs[j-1]!=".."){
if(j==segs.length-1){
segs.splice(j,1);
segs[j-1]="";
}else{
segs.splice(j-1,2);
j-=2;
}
}
}
}
_2c8.path=segs.join("/");
}
}
}
}
uri="";
if(_2c8.scheme!=null){
uri+=_2c8.scheme+":";
}
if(_2c8.authority!=null){
uri+="//"+_2c8.authority;
}
uri+=_2c8.path;
if(_2c8.query!=null){
uri+="?"+_2c8.query;
}
if(_2c8.fragment!=null){
uri+="#"+_2c8.fragment;
}
}
this.uri=uri.toString();
var _2cd="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";
var r=this.uri.match(new RegExp(_2cd));
this.scheme=r[2]||(r[1]?"":null);
this.authority=r[4]||(r[3]?"":null);
this.path=r[5];
this.query=r[7]||(r[6]?"":null);
this.fragment=r[9]||(r[8]?"":null);
if(this.authority!=null){
_2cd="^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$";
r=this.authority.match(new RegExp(_2cd));
this.user=r[3]||null;
this.password=r[4]||null;
this.host=r[5];
this.port=r[7]||null;
}
this.toString=function(){
return this.uri;
};
};
};
dojo.provide("dojo.style");
dojo.require("dojo.graphics.color");
dojo.require("dojo.uri.Uri");
dojo.require("dojo.lang.common");
(function(){
var h=dojo.render.html;
var ds=dojo.style;
var db=document["body"]||document["documentElement"];
ds.boxSizing={MARGIN_BOX:"margin-box",BORDER_BOX:"border-box",PADDING_BOX:"padding-box",CONTENT_BOX:"content-box"};
var bs=ds.boxSizing;
ds.getBoxSizing=function(node){
if((h.ie)||(h.opera)){
var cm=document["compatMode"];
if((cm=="BackCompat")||(cm=="QuirksMode")){
return bs.BORDER_BOX;
}else{
return bs.CONTENT_BOX;
}
}else{
if(arguments.length==0){
node=document.documentElement;
}
var _2d5=ds.getStyle(node,"-moz-box-sizing");
if(!_2d5){
_2d5=ds.getStyle(node,"box-sizing");
}
return (_2d5?_2d5:bs.CONTENT_BOX);
}
};
ds.isBorderBox=function(node){
return (ds.getBoxSizing(node)==bs.BORDER_BOX);
};
ds.getUnitValue=function(node,_2d8,_2d9){
var s=ds.getComputedStyle(node,_2d8);
if((!s)||((s=="auto")&&(_2d9))){
return {value:0,units:"px"};
}
if(dojo.lang.isUndefined(s)){
return ds.getUnitValue.bad;
}
var _2db=s.match(/(\-?[\d.]+)([a-z%]*)/i);
if(!_2db){
return ds.getUnitValue.bad;
}
return {value:Number(_2db[1]),units:_2db[2].toLowerCase()};
};
ds.getUnitValue.bad={value:NaN,units:""};
ds.getPixelValue=function(node,_2dd,_2de){
var _2df=ds.getUnitValue(node,_2dd,_2de);
if(isNaN(_2df.value)){
return 0;
}
if((_2df.value)&&(_2df.units!="px")){
return NaN;
}
return _2df.value;
};
ds.getNumericStyle=function(){
dojo.deprecated("dojo.(style|html).getNumericStyle","in favor of dojo.(style|html).getPixelValue","0.4");
return ds.getPixelValue.apply(this,arguments);
};
ds.setPositivePixelValue=function(node,_2e1,_2e2){
if(isNaN(_2e2)){
return false;
}
node.style[_2e1]=Math.max(0,_2e2)+"px";
return true;
};
ds._sumPixelValues=function(node,_2e4,_2e5){
var _2e6=0;
for(var x=0;x<_2e4.length;x++){
_2e6+=ds.getPixelValue(node,_2e4[x],_2e5);
}
return _2e6;
};
ds.isPositionAbsolute=function(node){
return (ds.getComputedStyle(node,"position")=="absolute");
};
ds.getBorderExtent=function(node,side){
return (ds.getStyle(node,"border-"+side+"-style")=="none"?0:ds.getPixelValue(node,"border-"+side+"-width"));
};
ds.getMarginWidth=function(node){
return ds._sumPixelValues(node,["margin-left","margin-right"],ds.isPositionAbsolute(node));
};
ds.getBorderWidth=function(node){
return ds.getBorderExtent(node,"left")+ds.getBorderExtent(node,"right");
};
ds.getPaddingWidth=function(node){
return ds._sumPixelValues(node,["padding-left","padding-right"],true);
};
ds.getPadBorderWidth=function(node){
return ds.getPaddingWidth(node)+ds.getBorderWidth(node);
};
ds.getContentBoxWidth=function(node){
node=dojo.byId(node);
return node.offsetWidth-ds.getPadBorderWidth(node);
};
ds.getBorderBoxWidth=function(node){
node=dojo.byId(node);
return node.offsetWidth;
};
ds.getMarginBoxWidth=function(node){
return ds.getInnerWidth(node)+ds.getMarginWidth(node);
};
ds.setContentBoxWidth=function(node,_2f3){
node=dojo.byId(node);
if(ds.isBorderBox(node)){
_2f3+=ds.getPadBorderWidth(node);
}
return ds.setPositivePixelValue(node,"width",_2f3);
};
ds.setMarginBoxWidth=function(node,_2f5){
node=dojo.byId(node);
if(!ds.isBorderBox(node)){
_2f5-=ds.getPadBorderWidth(node);
}
_2f5-=ds.getMarginWidth(node);
return ds.setPositivePixelValue(node,"width",_2f5);
};
ds.getContentWidth=ds.getContentBoxWidth;
ds.getInnerWidth=ds.getBorderBoxWidth;
ds.getOuterWidth=ds.getMarginBoxWidth;
ds.setContentWidth=ds.setContentBoxWidth;
ds.setOuterWidth=ds.setMarginBoxWidth;
ds.getMarginHeight=function(node){
return ds._sumPixelValues(node,["margin-top","margin-bottom"],ds.isPositionAbsolute(node));
};
ds.getBorderHeight=function(node){
return ds.getBorderExtent(node,"top")+ds.getBorderExtent(node,"bottom");
};
ds.getPaddingHeight=function(node){
return ds._sumPixelValues(node,["padding-top","padding-bottom"],true);
};
ds.getPadBorderHeight=function(node){
return ds.getPaddingHeight(node)+ds.getBorderHeight(node);
};
ds.getContentBoxHeight=function(node){
node=dojo.byId(node);
return node.offsetHeight-ds.getPadBorderHeight(node);
};
ds.getBorderBoxHeight=function(node){
node=dojo.byId(node);
return node.offsetHeight;
};
ds.getMarginBoxHeight=function(node){
return ds.getInnerHeight(node)+ds.getMarginHeight(node);
};
ds.setContentBoxHeight=function(node,_2fe){
node=dojo.byId(node);
if(ds.isBorderBox(node)){
_2fe+=ds.getPadBorderHeight(node);
}
return ds.setPositivePixelValue(node,"height",_2fe);
};
ds.setMarginBoxHeight=function(node,_300){
node=dojo.byId(node);
if(!ds.isBorderBox(node)){
_300-=ds.getPadBorderHeight(node);
}
_300-=ds.getMarginHeight(node);
return ds.setPositivePixelValue(node,"height",_300);
};
ds.getContentHeight=ds.getContentBoxHeight;
ds.getInnerHeight=ds.getBorderBoxHeight;
ds.getOuterHeight=ds.getMarginBoxHeight;
ds.setContentHeight=ds.setContentBoxHeight;
ds.setOuterHeight=ds.setMarginBoxHeight;
ds.getAbsolutePosition=ds.abs=function(node,_302){
node=dojo.byId(node);
var ret=[];
ret.x=ret.y=0;
var st=dojo.html.getScrollTop();
var sl=dojo.html.getScrollLeft();
if(h.ie){
with(node.getBoundingClientRect()){
ret.x=left-2;
ret.y=top-2;
}
}else{
if(document.getBoxObjectFor){
var bo=document.getBoxObjectFor(node);
ret.x=bo.x-ds.sumAncestorProperties(node,"scrollLeft");
ret.y=bo.y-ds.sumAncestorProperties(node,"scrollTop");
}else{
if(node["offsetParent"]){
var _307;
if((h.safari)&&(node.style.getPropertyValue("position")=="absolute")&&(node.parentNode==db)){
_307=db;
}else{
_307=db.parentNode;
}
if(node.parentNode!=db){
var nd=node;
if(window.opera){
nd=db;
}
ret.x-=ds.sumAncestorProperties(nd,"scrollLeft");
ret.y-=ds.sumAncestorProperties(nd,"scrollTop");
}
do{
var n=node["offsetLeft"];
ret.x+=isNaN(n)?0:n;
var m=node["offsetTop"];
ret.y+=isNaN(m)?0:m;
node=node.offsetParent;
}while((node!=_307)&&(node!=null));
}else{
if(node["x"]&&node["y"]){
ret.x+=isNaN(node.x)?0:node.x;
ret.y+=isNaN(node.y)?0:node.y;
}
}
}
}
if(_302){
ret.y+=st;
ret.x+=sl;
}
ret[0]=ret.x;
ret[1]=ret.y;
return ret;
};
ds.sumAncestorProperties=function(node,prop){
node=dojo.byId(node);
if(!node){
return 0;
}
var _30d=0;
while(node){
var val=node[prop];
if(val){
_30d+=val-0;
if(node==document.body){
break;
}
}
node=node.parentNode;
}
return _30d;
};
ds.getTotalOffset=function(node,type,_311){
return ds.abs(node,_311)[(type=="top")?"y":"x"];
};
ds.getAbsoluteX=ds.totalOffsetLeft=function(node,_313){
return ds.getTotalOffset(node,"left",_313);
};
ds.getAbsoluteY=ds.totalOffsetTop=function(node,_315){
return ds.getTotalOffset(node,"top",_315);
};
ds.styleSheet=null;
ds.insertCssRule=function(_316,_317,_318){
if(!ds.styleSheet){
if(document.createStyleSheet){
ds.styleSheet=document.createStyleSheet();
}else{
if(document.styleSheets[0]){
ds.styleSheet=document.styleSheets[0];
}else{
return null;
}
}
}
if(arguments.length<3){
if(ds.styleSheet.cssRules){
_318=ds.styleSheet.cssRules.length;
}else{
if(ds.styleSheet.rules){
_318=ds.styleSheet.rules.length;
}else{
return null;
}
}
}
if(ds.styleSheet.insertRule){
var rule=_316+" { "+_317+" }";
return ds.styleSheet.insertRule(rule,_318);
}else{
if(ds.styleSheet.addRule){
return ds.styleSheet.addRule(_316,_317,_318);
}else{
return null;
}
}
};
ds.removeCssRule=function(_31a){
if(!ds.styleSheet){
dojo.debug("no stylesheet defined for removing rules");
return false;
}
if(h.ie){
if(!_31a){
_31a=ds.styleSheet.rules.length;
ds.styleSheet.removeRule(_31a);
}
}else{
if(document.styleSheets[0]){
if(!_31a){
_31a=ds.styleSheet.cssRules.length;
}
ds.styleSheet.deleteRule(_31a);
}
}
return true;
};
ds.insertCssFile=function(URI,doc,_31d){
if(!URI){
return;
}
if(!doc){
doc=document;
}
var _31e=dojo.hostenv.getText(URI);
_31e=ds.fixPathsInCssText(_31e,URI);
if(_31d){
var _31f=doc.getElementsByTagName("style");
var _320="";
for(var i=0;i<_31f.length;i++){
_320=(_31f[i].styleSheet&&_31f[i].styleSheet.cssText)?_31f[i].styleSheet.cssText:_31f[i].innerHTML;
if(_31e==_320){
return;
}
}
}
var _322=ds.insertCssText(_31e);
if(_322&&djConfig.isDebug){
_322.setAttribute("dbgHref",URI);
}
return _322;
};
ds.insertCssText=function(_323,doc,URI){
if(!_323){
return;
}
if(!doc){
doc=document;
}
if(URI){
_323=ds.fixPathsInCssText(_323,URI);
}
var _326=doc.createElement("style");
_326.setAttribute("type","text/css");
var head=doc.getElementsByTagName("head")[0];
if(!head){
dojo.debug("No head tag in document, aborting styles");
return;
}else{
head.appendChild(_326);
}
if(_326.styleSheet){
_326.styleSheet.cssText=_323;
}else{
var _328=doc.createTextNode(_323);
_326.appendChild(_328);
}
return _326;
};
ds.fixPathsInCssText=function(_329,URI){
if(!_329||!URI){
return;
}
var pos=0;
var str="";
var url="";
while(pos!=-1){
pos=0;
url="";
pos=_329.indexOf("url(",pos);
if(pos<0){
break;
}
str+=_329.slice(0,pos+4);
_329=_329.substring(pos+4,_329.length);
url+=_329.match(/^[\t\s\w()\/.\\'"-:#=&?]*\)/)[0];
_329=_329.substring(url.length-1,_329.length);
url=url.replace(/^[\s\t]*(['"]?)([\w()\/.\\'"-:#=&?]*)\1[\s\t]*?\)/,"$2");
if(url.search(/(file|https?|ftps?):\/\//)==-1){
url=(new dojo.uri.Uri(URI,url).toString());
}
str+=url;
}
return str+_329;
};
ds.getBackgroundColor=function(node){
node=dojo.byId(node);
var _32f;
do{
_32f=ds.getStyle(node,"background-color");
if(_32f.toLowerCase()=="rgba(0, 0, 0, 0)"){
_32f="transparent";
}
if(node==document.getElementsByTagName("body")[0]){
node=null;
break;
}
node=node.parentNode;
}while(node&&dojo.lang.inArray(_32f,["transparent",""]));
if(_32f=="transparent"){
_32f=[255,255,255,0];
}else{
_32f=dojo.graphics.color.extractRGB(_32f);
}
return _32f;
};
ds.getComputedStyle=function(node,_331,_332){
node=dojo.byId(node);
var _331=ds.toSelectorCase(_331);
var _333=ds.toCamelCase(_331);
if(!node||!node.style){
return _332;
}else{
if(document.defaultView){
try{
var cs=document.defaultView.getComputedStyle(node,"");
if(cs){
return cs.getPropertyValue(_331);
}
}
catch(e){
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_331);
}else{
return _332;
}
}
}else{
if(node.currentStyle){
return node.currentStyle[_333];
}
}
}
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_331);
}else{
return _332;
}
};
ds.getStyleProperty=function(node,_336){
node=dojo.byId(node);
return (node&&node.style?node.style[ds.toCamelCase(_336)]:undefined);
};
ds.getStyle=function(node,_338){
var _339=ds.getStyleProperty(node,_338);
return (_339?_339:ds.getComputedStyle(node,_338));
};
ds.setStyle=function(node,_33b,_33c){
node=dojo.byId(node);
if(node&&node.style){
var _33d=ds.toCamelCase(_33b);
node.style[_33d]=_33c;
}
};
ds.toCamelCase=function(_33e){
var arr=_33e.split("-"),cc=arr[0];
for(var i=1;i<arr.length;i++){
cc+=arr[i].charAt(0).toUpperCase()+arr[i].substring(1);
}
return cc;
};
ds.toSelectorCase=function(_341){
return _341.replace(/([A-Z])/g,"-$1").toLowerCase();
};
ds.setOpacity=function setOpacity(node,_343,_344){
node=dojo.byId(node);
if(!_344){
if(_343>=1){
if(h.ie){
ds.clearOpacity(node);
return;
}else{
_343=0.999999;
}
}else{
if(_343<0){
_343=0;
}
}
}
if(h.ie){
if(node.nodeName.toLowerCase()=="tr"){
var tds=node.getElementsByTagName("td");
for(var x=0;x<tds.length;x++){
tds[x].style.filter="Alpha(Opacity="+_343*100+")";
}
}
node.style.filter="Alpha(Opacity="+_343*100+")";
}else{
if(h.moz){
node.style.opacity=_343;
node.style.MozOpacity=_343;
}else{
if(h.safari){
node.style.opacity=_343;
node.style.KhtmlOpacity=_343;
}else{
node.style.opacity=_343;
}
}
}
};
ds.getOpacity=function getOpacity(node){
node=dojo.byId(node);
if(h.ie){
var opac=(node.filters&&node.filters.alpha&&typeof node.filters.alpha.opacity=="number"?node.filters.alpha.opacity:100)/100;
}else{
var opac=node.style.opacity||node.style.MozOpacity||node.style.KhtmlOpacity||1;
}
return opac>=0.999999?1:Number(opac);
};
ds.clearOpacity=function clearOpacity(node){
node=dojo.byId(node);
var ns=node.style;
if(h.ie){
try{
if(node.filters&&node.filters.alpha){
ns.filter="";
}
}
catch(e){
}
}else{
if(h.moz){
ns.opacity=1;
ns.MozOpacity=1;
}else{
if(h.safari){
ns.opacity=1;
ns.KhtmlOpacity=1;
}else{
ns.opacity=1;
}
}
}
};
ds.setStyleAttributes=function(node,_34c){
var _34d={"opacity":dojo.style.setOpacity,"content-height":dojo.style.setContentHeight,"content-width":dojo.style.setContentWidth,"outer-height":dojo.style.setOuterHeight,"outer-width":dojo.style.setOuterWidth};
var _34e=_34c.replace(/(;)?\s*$/,"").split(";");
for(var i=0;i<_34e.length;i++){
var _350=_34e[i].split(":");
var name=_350[0].replace(/\s*$/,"").replace(/^\s*/,"").toLowerCase();
var _352=_350[1].replace(/\s*$/,"").replace(/^\s*/,"");
if(dojo.lang.has(_34d,name)){
_34d[name](node,_352);
}else{
node.style[dojo.style.toCamelCase(name)]=_352;
}
}
};
ds._toggle=function(node,_354,_355){
node=dojo.byId(node);
_355(node,!_354(node));
return _354(node);
};
ds.show=function(node){
node=dojo.byId(node);
if(ds.getStyleProperty(node,"display")=="none"){
ds.setStyle(node,"display",(node.dojoDisplayCache||""));
node.dojoDisplayCache=undefined;
}
};
ds.hide=function(node){
node=dojo.byId(node);
if(typeof node["dojoDisplayCache"]=="undefined"){
var d=ds.getStyleProperty(node,"display");
if(d!="none"){
node.dojoDisplayCache=d;
}
}
ds.setStyle(node,"display","none");
};
ds.setShowing=function(node,_35a){
ds[(_35a?"show":"hide")](node);
};
ds.isShowing=function(node){
return (ds.getStyleProperty(node,"display")!="none");
};
ds.toggleShowing=function(node){
return ds._toggle(node,ds.isShowing,ds.setShowing);
};
ds.displayMap={tr:"",td:"",th:"",img:"inline",span:"inline",input:"inline",button:"inline"};
ds.suggestDisplayByTagName=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
var tag=node.tagName.toLowerCase();
return (tag in ds.displayMap?ds.displayMap[tag]:"block");
}
};
ds.setDisplay=function(node,_360){
ds.setStyle(node,"display",(dojo.lang.isString(_360)?_360:(_360?ds.suggestDisplayByTagName(node):"none")));
};
ds.isDisplayed=function(node){
return (ds.getComputedStyle(node,"display")!="none");
};
ds.toggleDisplay=function(node){
return ds._toggle(node,ds.isDisplayed,ds.setDisplay);
};
ds.setVisibility=function(node,_364){
ds.setStyle(node,"visibility",(dojo.lang.isString(_364)?_364:(_364?"visible":"hidden")));
};
ds.isVisible=function(node){
return (ds.getComputedStyle(node,"visibility")!="hidden");
};
ds.toggleVisibility=function(node){
return ds._toggle(node,ds.isVisible,ds.setVisibility);
};
ds.toCoordinateArray=function(_367,_368){
if(dojo.lang.isArray(_367)){
while(_367.length<4){
_367.push(0);
}
while(_367.length>4){
_367.pop();
}
var ret=_367;
}else{
var node=dojo.byId(_367);
var pos=ds.getAbsolutePosition(node,_368);
var ret=[pos.x,pos.y,ds.getBorderBoxWidth(node),ds.getBorderBoxHeight(node)];
}
ret.x=ret[0];
ret.y=ret[1];
ret.w=ret[2];
ret.h=ret[3];
return ret;
};
})();
dojo.provide("dojo.html");
dojo.require("dojo.lang.func");
dojo.require("dojo.dom");
dojo.require("dojo.style");
dojo.require("dojo.string");
dojo.lang.mixin(dojo.html,dojo.dom);
dojo.lang.mixin(dojo.html,dojo.style);
dojo.html.clearSelection=function(){
try{
if(window["getSelection"]){
if(dojo.render.html.safari){
window.getSelection().collapse();
}else{
window.getSelection().removeAllRanges();
}
}else{
if(document.selection){
if(document.selection.empty){
document.selection.empty();
}else{
if(document.selection.clear){
document.selection.clear();
}
}
}
}
return true;
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.html.disableSelection=function(_36c){
_36c=dojo.byId(_36c)||document.body;
var h=dojo.render.html;
if(h.mozilla){
_36c.style.MozUserSelect="none";
}else{
if(h.safari){
_36c.style.KhtmlUserSelect="none";
}else{
if(h.ie){
_36c.unselectable="on";
}else{
return false;
}
}
}
return true;
};
dojo.html.enableSelection=function(_36e){
_36e=dojo.byId(_36e)||document.body;
var h=dojo.render.html;
if(h.mozilla){
_36e.style.MozUserSelect="";
}else{
if(h.safari){
_36e.style.KhtmlUserSelect="";
}else{
if(h.ie){
_36e.unselectable="off";
}else{
return false;
}
}
}
return true;
};
dojo.html.selectElement=function(_370){
_370=dojo.byId(_370);
if(document.selection&&document.body.createTextRange){
var _371=document.body.createTextRange();
_371.moveToElementText(_370);
_371.select();
}else{
if(window["getSelection"]){
var _372=window.getSelection();
if(_372["selectAllChildren"]){
_372.selectAllChildren(_370);
}
}
}
};
dojo.html.selectInputText=function(_373){
_373=dojo.byId(_373);
if(document.selection&&document.body.createTextRange){
var _374=_373.createTextRange();
_374.moveStart("character",0);
_374.moveEnd("character",_373.value.length);
_374.select();
}else{
if(window["getSelection"]){
var _375=window.getSelection();
_373.setSelectionRange(0,_373.value.length);
}
}
_373.focus();
};
dojo.html.isSelectionCollapsed=function(){
if(document["selection"]){
return document.selection.createRange().text=="";
}else{
if(window["getSelection"]){
var _376=window.getSelection();
if(dojo.lang.isString(_376)){
return _376=="";
}else{
return _376.isCollapsed;
}
}
}
};
dojo.html.getEventTarget=function(evt){
if(!evt){
evt=window.event||{};
}
var t=(evt.srcElement?evt.srcElement:(evt.target?evt.target:null));
while((t)&&(t.nodeType!=1)){
t=t.parentNode;
}
return t;
};
dojo.html.getDocumentWidth=function(){
dojo.deprecated("dojo.html.getDocument*","replaced by dojo.html.getViewport*","0.4");
return dojo.html.getViewportWidth();
};
dojo.html.getDocumentHeight=function(){
dojo.deprecated("dojo.html.getDocument*","replaced by dojo.html.getViewport*","0.4");
return dojo.html.getViewportHeight();
};
dojo.html.getDocumentSize=function(){
dojo.deprecated("dojo.html.getDocument*","replaced of dojo.html.getViewport*","0.4");
return dojo.html.getViewportSize();
};
dojo.html.getViewportWidth=function(){
var w=0;
if(window.innerWidth){
w=window.innerWidth;
}
if(dojo.exists(document,"documentElement.clientWidth")){
var w2=document.documentElement.clientWidth;
if(!w||w2&&w2<w){
w=w2;
}
return w;
}
if(document.body){
return document.body.clientWidth;
}
return 0;
};
dojo.html.getViewportHeight=function(){
if(window.innerHeight){
return window.innerHeight;
}
if(dojo.exists(document,"documentElement.clientHeight")){
return document.documentElement.clientHeight;
}
if(document.body){
return document.body.clientHeight;
}
return 0;
};
dojo.html.getViewportSize=function(){
var ret=[dojo.html.getViewportWidth(),dojo.html.getViewportHeight()];
ret.w=ret[0];
ret.h=ret[1];
return ret;
};
dojo.html.getScrollTop=function(){
return window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop||0;
};
dojo.html.getScrollLeft=function(){
return window.pageXOffset||document.documentElement.scrollLeft||document.body.scrollLeft||0;
};
dojo.html.getScrollOffset=function(){
var off=[dojo.html.getScrollLeft(),dojo.html.getScrollTop()];
off.x=off[0];
off.y=off[1];
return off;
};
dojo.html.getParentOfType=function(node,type){
dojo.deprecated("dojo.html.getParentOfType","replaced by dojo.html.getParentByType*","0.4");
return dojo.html.getParentByType(node,type);
};
dojo.html.getParentByType=function(node,type){
var _381=dojo.byId(node);
type=type.toLowerCase();
while((_381)&&(_381.nodeName.toLowerCase()!=type)){
if(_381==(document["body"]||document["documentElement"])){
return null;
}
_381=_381.parentNode;
}
return _381;
};
dojo.html.getAttribute=function(node,attr){
node=dojo.byId(node);
if((!node)||(!node.getAttribute)){
return null;
}
var ta=typeof attr=="string"?attr:new String(attr);
var v=node.getAttribute(ta.toUpperCase());
if((v)&&(typeof v=="string")&&(v!="")){
return v;
}
if(v&&v.value){
return v.value;
}
if((node.getAttributeNode)&&(node.getAttributeNode(ta))){
return (node.getAttributeNode(ta)).value;
}else{
if(node.getAttribute(ta)){
return node.getAttribute(ta);
}else{
if(node.getAttribute(ta.toLowerCase())){
return node.getAttribute(ta.toLowerCase());
}
}
}
return null;
};
dojo.html.hasAttribute=function(node,attr){
node=dojo.byId(node);
return dojo.html.getAttribute(node,attr)?true:false;
};
dojo.html.getClass=function(node){
node=dojo.byId(node);
if(!node){
return "";
}
var cs="";
if(node.className){
cs=node.className;
}else{
if(dojo.html.hasAttribute(node,"class")){
cs=dojo.html.getAttribute(node,"class");
}
}
return dojo.string.trim(cs);
};
dojo.html.getClasses=function(node){
var c=dojo.html.getClass(node);
return (c=="")?[]:c.split(/\s+/g);
};
dojo.html.hasClass=function(node,_38d){
return dojo.lang.inArray(dojo.html.getClasses(node),_38d);
};
dojo.html.prependClass=function(node,_38f){
_38f+=" "+dojo.html.getClass(node);
return dojo.html.setClass(node,_38f);
};
dojo.html.addClass=function(node,_391){
if(dojo.html.hasClass(node,_391)){
return false;
}
_391=dojo.string.trim(dojo.html.getClass(node)+" "+_391);
return dojo.html.setClass(node,_391);
};
dojo.html.setClass=function(node,_393){
node=dojo.byId(node);
var cs=new String(_393);
try{
if(typeof node.className=="string"){
node.className=cs;
}else{
if(node.setAttribute){
node.setAttribute("class",_393);
node.className=cs;
}else{
return false;
}
}
}
catch(e){
dojo.debug("dojo.html.setClass() failed",e);
}
return true;
};
dojo.html.removeClass=function(node,_396,_397){
var _396=dojo.string.trim(new String(_396));
try{
var cs=dojo.html.getClasses(node);
var nca=[];
if(_397){
for(var i=0;i<cs.length;i++){
if(cs[i].indexOf(_396)==-1){
nca.push(cs[i]);
}
}
}else{
for(var i=0;i<cs.length;i++){
if(cs[i]!=_396){
nca.push(cs[i]);
}
}
}
dojo.html.setClass(node,nca.join(" "));
}
catch(e){
dojo.debug("dojo.html.removeClass() failed",e);
}
return true;
};
dojo.html.replaceClass=function(node,_39c,_39d){
dojo.html.removeClass(node,_39d);
dojo.html.addClass(node,_39c);
};
dojo.html.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
dojo.html.getElementsByClass=function(_39e,_39f,_3a0,_3a1,_3a2){
_39f=dojo.byId(_39f)||document;
var _3a3=_39e.split(/\s+/g);
var _3a4=[];
if(_3a1!=1&&_3a1!=2){
_3a1=0;
}
var _3a5=new RegExp("(\\s|^)(("+_3a3.join(")|(")+"))(\\s|$)");
var _3a6=[];
if(!_3a2&&document.evaluate){
var _3a7="//"+(_3a0||"*")+"[contains(";
if(_3a1!=dojo.html.classMatchType.ContainsAny){
_3a7+="concat(' ',@class,' '), ' "+_3a3.join(" ') and contains(concat(' ',@class,' '), ' ")+" ')]";
}else{
_3a7+="concat(' ',@class,' '), ' "+_3a3.join(" ')) or contains(concat(' ',@class,' '), ' ")+" ')]";
}
var _3a8=document.evaluate(_3a7,_39f,null,XPathResult.ANY_TYPE,null);
var _3a9=_3a8.iterateNext();
while(_3a9){
try{
_3a6.push(_3a9);
_3a9=_3a8.iterateNext();
}
catch(e){
break;
}
}
return _3a6;
}else{
if(!_3a0){
_3a0="*";
}
_3a6=_39f.getElementsByTagName(_3a0);
var node,i=0;
outer:
while(node=_3a6[i++]){
var _3ab=dojo.html.getClasses(node);
if(_3ab.length==0){
continue outer;
}
var _3ac=0;
for(var j=0;j<_3ab.length;j++){
if(_3a5.test(_3ab[j])){
if(_3a1==dojo.html.classMatchType.ContainsAny){
_3a4.push(node);
continue outer;
}else{
_3ac++;
}
}else{
if(_3a1==dojo.html.classMatchType.IsOnly){
continue outer;
}
}
}
if(_3ac==_3a3.length){
if((_3a1==dojo.html.classMatchType.IsOnly)&&(_3ac==_3ab.length)){
_3a4.push(node);
}else{
if(_3a1==dojo.html.classMatchType.ContainsAll){
_3a4.push(node);
}
}
}
}
return _3a4;
}
};
dojo.html.getElementsByClassName=dojo.html.getElementsByClass;
dojo.html.getCursorPosition=function(e){
e=e||window.event;
var _3af={x:0,y:0};
if(e.pageX||e.pageY){
_3af.x=e.pageX;
_3af.y=e.pageY;
}else{
var de=document.documentElement;
var db=document.body;
_3af.x=e.clientX+((de||db)["scrollLeft"])-((de||db)["clientLeft"]);
_3af.y=e.clientY+((de||db)["scrollTop"])-((de||db)["clientTop"]);
}
return _3af;
};
dojo.html.overElement=function(_3b2,e){
_3b2=dojo.byId(_3b2);
var _3b4=dojo.html.getCursorPosition(e);
with(dojo.html){
var top=getAbsoluteY(_3b2,true);
var _3b6=top+getInnerHeight(_3b2);
var left=getAbsoluteX(_3b2,true);
var _3b8=left+getInnerWidth(_3b2);
}
return (_3b4.x>=left&&_3b4.x<=_3b8&&_3b4.y>=top&&_3b4.y<=_3b6);
};
dojo.html.setActiveStyleSheet=function(_3b9){
var i=0,a,els=document.getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")){
a.disabled=true;
if(a.getAttribute("title")==_3b9){
a.disabled=false;
}
}
}
};
dojo.html.getActiveStyleSheet=function(){
var i=0,a,els=document.getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")&&!a.disabled){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.getPreferredStyleSheet=function(){
var i=0,a,els=document.getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("rel").indexOf("alt")==-1&&a.getAttribute("title")){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.body=function(){
return document.body||document.getElementsByTagName("body")[0];
};
dojo.html.isTag=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
var arr=dojo.lang.map(dojo.lang.toArray(arguments,1),function(a){
return String(a).toLowerCase();
});
return arr[dojo.lang.find(node.tagName.toLowerCase(),arr)]||"";
}
return "";
};
dojo.html.copyStyle=function(_3c0,_3c1){
if(dojo.lang.isUndefined(_3c1.style.cssText)){
_3c0.setAttribute("style",_3c1.getAttribute("style"));
}else{
_3c0.style.cssText=_3c1.style.cssText;
}
dojo.html.addClass(_3c0,dojo.html.getClass(_3c1));
};
dojo.html._callExtrasDeprecated=function(_3c2,args){
var _3c4="dojo.html.extras";
dojo.deprecated("dojo.html."+_3c2,"moved to "+_3c4,"0.4");
dojo["require"](_3c4);
return dojo.html[_3c2].apply(dojo.html,args);
};
dojo.html.createNodesFromText=function(){
return dojo.html._callExtrasDeprecated("createNodesFromText",arguments);
};
dojo.html.gravity=function(){
return dojo.html._callExtrasDeprecated("gravity",arguments);
};
dojo.html.placeOnScreen=function(){
return dojo.html._callExtrasDeprecated("placeOnScreen",arguments);
};
dojo.html.placeOnScreenPoint=function(){
return dojo.html._callExtrasDeprecated("placeOnScreenPoint",arguments);
};
dojo.html.renderedTextContent=function(){
return dojo.html._callExtrasDeprecated("renderedTextContent",arguments);
};
dojo.html.BackgroundIframe=function(){
return dojo.html._callExtrasDeprecated("BackgroundIframe",arguments);
};

