var mainActiveId='home';
var firstUrl = null;//第一个页面
var secondUrl = null;//第二个页面

function UrlSearch() {
    var name,value;
    var str=location.href; //取得整个地址栏
    var num=str.indexOf("?")
    str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
	str = str.replace("&amp;","&");
    var arr=str.split("&"); //各个参数放到数组里
    for(var i=0;i < arr.length;i++){
        num=arr[i].indexOf("=");
        if(num>0){
            name=arr[i].substring(0,num);
            value=arr[i].substr(num+1);
            this[name]=value;
        }
    }
}


function replaceEmpty(str) {
	if(isEmpty(str)){
		return '';
	}
	return str;
}

function isEmpty(str) {
	if(str=="undefined" || str==null || str.length == 0) {
		return true;
	}
	return false;
}



function addCookie(name,value,expiresHours){ 
	var cookieString=name+"="+escape(value)+";path=/"; 
	//判断是否设置过期时间 
	if(expiresHours>0){ 
		var date=new Date(); 
		date.setTime(date.getTime+expiresHours*3600*1000); 
		cookieString=cookieString+";expires="+date.toGMTString()+";path=/"; 
	}
	document.cookie=cookieString; 
}

function getCookie(name){ 
	var strCookie=document.cookie; 
	var arrCookie=strCookie.split("; "); 
	for(var i=0;i<arrCookie.length;i++){ 
		var arr=arrCookie[i].split("="); 
		if(arr[0]==name)return arr[1]; 
	} 
	return "";
}

function deleteCookie(name){ 
	var date=new Date(); 
	date.setTime(date.getTime()-10000); 
	document.cookie=name+"=v; expires="+date.toGMTString(); 
}

function getFileName(param){
	　　var myFile = document.getElementById(param).value;
	　　var length = myFile.length;
	　　var x = myFile.lastIndexOf("\\");
	　　x++;
	　　var fileName = myFile.substring(x,length);
		$("#"+param + "Name").val(fileName);
	　　}

function locationUrl(url,activeId){
	if(mainActiveId != null && mainActiveId != "" && activeId != null && activeId != ""){
		$("#"+mainActiveId).removeAttr("class");
		$("#"+activeId).attr("class", "active");
		mainActiveId = activeId;
	}
	goUrl(url,null);
}

var xmlhttp = new getXMLObject();
function goUrl(url,params) {
	fixUrl(url,params);
	if(xmlhttp) {
		//var params = "";
		xmlhttp.open("POST",url,true);
		xmlhttp.onreadystatechange = handleServerResponse;
		xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');
		xmlhttp.send(params);
	}
}

//XML OBJECT
function getXMLObject() {
	var xmlHttp = false;
	try {
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP") // For Old Microsoft
														// Browsers
	} catch (e) {
		try {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP") // For Microsoft
																// IE 6.0+
		} catch (e2) {
			xmlHttp = false // No Browser accepts the XMLHTTP Object then false
		}
	}
	if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
		xmlHttp = new XMLHttpRequest(); // For Mozilla, Opera Browsers
	}
	return xmlHttp; // Mandatory Statement returning the ajax object created
}

function fixUrl(url, params){
	if(params != null){
		url = url + "?" + params;
	}
	if(firstUrl == null){
		firstUrl = url;
	}else if(secondUrl == null){
		secondUrl = url;
	}else{
		if(flag == 1){
			firstUrl = url;
			flag = 2;
		}else{
			secondUrl = url;
			flag = 1;
		}
	}
}

function handleServerResponse() {
	if (xmlhttp.readyState == 4) {
		//document.getElementById("mainSection").innerHTML =xmlhttp.responseText;
		var text = xmlhttp.responseText;
		if(text.indexOf("<title>Favorites error Page</title>") >= 0){
			window.location.href="/error.html";
		}else{
			$("#content").html(xmlhttp.responseText);
		}
	}
}

function smartFavoritesFun(title,description,selectLabelName){
    $.ajax({
        type: "POST",
        url:"/collect/getFavoriteResult",
        data:{'title':title,'description':description},
        success: function(maps) {
            var favoritesStr = "";
            for(var i = 0; i < maps.favoritesList.length; i++){
                if(maps.favoritesList[i].id == maps.favoritesResult){
                    favoritesStr += "<a id=\"smartFavorites" + maps.favoritesList[i].id + "\""
                        + " href=\"javascript:void(0);\" class=\"active\" onclick=\"switchFavorites(this.id,\'" + selectLabelName + "\'" + ")\">"
                        + maps.favoritesList[i].name + "</a>";
                }else{
                    favoritesStr += "<a id=\"smartFavorites" + maps.favoritesList[i].id + "\""
                        + " href=\"javascript:void(0);\" onclick=\"switchFavorites(this.id,\'" + selectLabelName + "\'" + ")\">"
                        + maps.favoritesList[i].name + "</a>";
                }
            }
            $("#smartFavoritesList").append(favoritesStr);
            $("#"+selectLabelName).val(maps.favoritesResult);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.responseText);
            console.log(jqXHR.status);
            console.log(jqXHR.readyState);
            console.log(jqXHR.statusText);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}
function switchFavorites(id,selectLabelName){
    var favoritesId = id.substring(14);
    $("#"+selectLabelName).val(favoritesId);
}