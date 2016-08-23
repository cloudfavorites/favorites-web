/*---LEFT BAR ACCORDION----*/
var mainActiveId='home';
var firstUrl = null;//第一个页面
var secondUrl = null;//第二个页面
var flag = 1;
$(function() {
	loadFavorites();
	loadConfig();
	$("#pwderror").hide();
});

function loadFavorites(){
	$.ajax({
		async: false,
		type: 'POST',
		dataType: 'json',
		url: '/user/getFavorites',
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
		},
		success: function(favorites){
			$("#unread").html("");
			$("#favorites > li").each(function(i){ 
				if(i != 0 && i != 1){
					$(this).remove();
				}
			});
			$("#layoutFavoritesName").html("");
			initDatas(favorites);
		}
	});
}

function initDatas(favorites){
	for(var i=0;i<favorites.length;i++){
		var id = favorites[i].id ;
		var name = favorites[i].name;
		var count = favorites[i].count;
		var url ='/standard/'+ id;
		if(name=="未读列表"){
			var favorite="<a href=\"javascript:void(0);\" onclick=\"locationUrl('"+url+"','unread')\" title="+name+" >";
			if(count > 0){
				favorite=favorite+"<div class=\"label label-success pull-right\">"+count+"</div>";
			}
			favorite=favorite+"<em class=\"icon-paper-clip\"></em>";
			favorite=favorite+"<span>"+name+"</span>";
			favorite=favorite+"</a>";
			$("#unread").append(favorite)
		}else{
			var favorite="<li id="+id+">";
			favorite=favorite+"<a href=\"javascript:void(0);\" onclick=\"locationUrl('"+url+"','"+id+"')\" title="+name+" >";
			favorite=favorite+"<div class=\"text-muted mr pull-right\">"+count+"</div>";
			favorite=favorite+"<span>"+name+"</span>";
			favorite=favorite+"</a></li>";
			$("#newFavortes").after(favorite)
		}
		$("#layoutFavoritesName").append("<option value=\"" + id + "\">" + name + "</option>");
		
	}
}

function loadConfig(){
	$.ajax({
		async: false,
		type: 'POST',
		dataType: 'json',
		url: '/user/getConfig',
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
		},
		success: function(config){
			$("#defaultCollectType").html("");
			$("#defaultModel").html("");
			$("#defaultFavorites").html("");
			initConfigDatas(config);
			//设置默认选中收藏夹
			obj = document.getElementById("layoutFavoritesName");
			for(i=0;i<obj.length;i++){
			  if(obj[i].value == config.defaultFavorties){
			    obj[i].selected = true;
			  	$("#defaultFavorites").append("<strong>默认收藏夹(" +obj[i].text +")");
			  }
			}
		}
	});
}

function initConfigDatas(config){
	$("#defaultCollectType").append("<strong>默认"+config.collectTypeName+"收藏（点击切换）</strong>")
	$("#defaultModel").append("<strong>收藏时显示" +config.modelName+"模式</strong>");
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

/**
 * 后退
 */
function historyBack(){
	if(flag == 1){
		goUrl(firstUrl);
	}else{
		goUrl(secondUrl);
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

function handleServerResponse() {
	if (xmlhttp.readyState == 4) {
		//document.getElementById("mainSection").innerHTML =xmlhttp.responseText;
		$("#content").html(xmlhttp.responseText);
	}
}

function showContent(url){
	$.get("/html/"+url+".html", function(data){
		$("#main-content").html(data);
		initContentPage();
	},"html");
}

function updateFavorites(){
	var ok = $('#updateFavoritesForm').parsley().isValid({force: true});
	if(ok){
		$.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:$("#updateFavoritesForm").serialize(),
			url: '/favorites/update',
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(response){
				if(response.rspCode == '000000'){
					 loadFavorites();
					 locationUrl("/standard/" + $("#favoritesId").val(),$("#favoritesId").val());
					 $("#updateFavoritesBtn").attr("aria-hidden","true");
					 $("#updateFavoritesBtn").attr("data-dismiss","modal");
  	    	 	}else{
  	    	 		$("#updateErrorMsg").text(response.rspMsg);
  	    	 		$("#updateErrorMsg").show();
  	    	 }
			}
		});
	}
}

function delFavorites(){
	$.ajax({
		async: false,
		type: 'POST',
		dataType: 'json',
		data:"id=" + $("#favoritesId").val(),
		url: '/favorites/del',
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
		},
		success: function(response){
			locationUrl("/standard/my","home");
			loadFavorites();
			 $("#delFavoritesBtn").attr("aria-hidden","true");
			 $("#delFavoritesBtn").attr("data-dismiss","modal");
		}
	});
}

function updatePwd() {
    var ok = $('#updatePwdForm').parsley().isValid({force: true});
	if(!ok){
		return;
	}
	var url = '/user/updatePassword';
	$.ajax({
		async: false,
		url : url,
		data : 'oldPassword='+$("#oldPassword").val()+'&newPassword='+$("#newPassword").val(),
		type : 'POST',
		dataType : "json",
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		},
		success : function(data, textStatus) {
			if(data.rspCode == '000000'){
				$("#pwderror").hide();
				$("#updatePwdBtn").attr("aria-hidden","true");
				$("#updatePwdBtn").attr("data-dismiss","modal");
				$("#updatePwdForm")[0].reset();
  	    	}else{
  	    		$("#pwderror").show();
  	    		$("#updatePwdBtn").removeAttr("aria-hidden");
				$("#updatePwdBtn").removeAttr("data-dismiss");
  	    	}
		}
	});
}
