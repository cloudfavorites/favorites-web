var page=1;
var gconfig;
var gfollows;

//关注某人或取消关注某人
function changeFollow(followIdStr){
    var userId = document.getElementById("userId").value;
    var textStr = document.getElementById(followIdStr).innerText;
    var status = "";
    if(textStr == "关注"){
        status = "follow";
    }else if(textStr == "取消关注"){
        status = "unfollow";
    }
    var followId = followIdStr.substring(6);
    if(userId != "0"){
        $.ajax({
              async: false,
              type: 'POST',
              dataType: 'json',
              data:{'status':status,'userId':followId},
              url: '/follow/changeFollowStatus',
              error : function(XMLHttpRequest, textStatus, errorThrown) {
                  console.log(XMLHttpRequest);
                  console.log(textStatus);
                  console.log(errorThrown);
              },
              success: function(response){
                  if(response.rspCode == '000000'){
                      if(textStr == "关注"){
                          document.getElementById(followIdStr).innerText = "取消关注";
                      }else{
                          document.getElementById(followIdStr).innerText = "关注";
                      }
                  }
              }
        });
    }else{
        window.location.href="/login";
    }
}



function loadFollows(){
    $.ajax({
        async: false,
        type: 'POST',
        dataType: 'json',
        url: '/user/getFollows',
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest);
            console.log(textStatus);
            console.log(errorThrown);
        },
        success: function(follows){
            gfollows=follows;
            initFollows(follows);
        }
    });
}
function initFollows(follows){
    $("#friends").html("");
    var friends="";
    for(var i=0;i<follows.length;i++){
        var name="<a href=\"javascript:void(0);\" onclick=\"showAt('"+follows[i]+"')\" >"+follows[i]+"</a>";
        friends=friends+name;
    }
    $("#friends").append(friends);
}
function loadConfig(){
	$.ajax({
		async: true,
		type: 'POST',
		dataType: 'json',
		url: '/user/getConfig',
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
		},
		success: function(config){
		    gconfig=config;
			$("#defaultCollectType").html("");
			$("#defaultModel").html("");
			$("#defaultFavorites").html("");
            $("#defaultClear").html("");
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
    $("#defaultClear").append("<strong>"+config.clearName+"清除无效文章</strong>");
}


function loadFavorites(){
	$.ajax({
		async: false,
		type: 'POST',
		dataType: 'json',
		url: '/favorites/getFavorites/0',
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
		},
		success: function(favorites){
			$("#layoutFavoritesName").html("");
			initFavorites(favorites);
		}
	});
}
function initFavorites(favorites){
	$("#favoritesSelect").empty();
	for(var i=0;i<favorites.length;i++){
		var id = favorites[i].id ;
		var name = favorites[i].name;
		//collct favorites
		$("#favoritesSelect").append("<option value=\"" + id + "\">" + name + "</option>");
		$("#layoutFavoritesName").append("<option value=\"" + id + "\">" + name + "</option>");
	}
}