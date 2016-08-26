$(function(){
	$("#changeModel1").click(function(){
		$("#show2").show();
		$("#model1").hide();
		$("#model2").show();
	});
	
	$("#changeModel2").click(function(){
		$("#show2").hide();
		$("#model2").hide();
		$("#model1").show();
	});
	
	$("#atshow").click(function(){
		if(gfollows.length > 0 && $("#atFriend").is(":hidden")){
			$("#atFriend").show();
		}else{
			$("#atFriend").hide();
		}
	});
	
    $('#atshow').bind('click', function(e) {  
    	if(e.stopPropagation){ 
            e.stopPropagation();
    	}else{ 
           e.cancelBubble = true;
     	} 
    }); 
	
	$("#ccollect").click(function(){
		 if($("#ctitle").val()==""){
			 $("#errorMsg").text("标题不能为空");
			 $("#errorMsg").show();
			 return;
		 }
		 if($("#clogoUrl").val() ==""){
			 $("#errorMsg").text("图片链接不能为空");
			 $("#errorMsg").show();
			 return;
		 }
		  $("#errorMsg").hide();
	  	  $.ajax({
	  	         type: "POST",
	  	         url:"/collect/collect",
	  	         data:$("#collect-form").serialize(),
	  	         success: function(response) { 
	  	        	 if(response.rspCode == '000000'){
	  	        		loadFavorites();
	  					$('#modal-changeSharing').modal('hide');
	  					locationUrl($("#forward").val(),"home");
	  	        	 }else{
	  	        		$("#errorMsg").text(response.rspMsg);
	 			 		$("#errorMsg").show();
	  	        	 }
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
		});
});


function showAt(name){
	var text = $("#remark").val();
	$("#remark").val(text + "@" +name + " ").focus();
}

function onCollect(id){
	 $('#modal-remove').modal('show');
	 $("#collectId").val(id);
}

function delCollect(){
	 $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:"",
			url: '/collect/delete/'+$("#collectId").val(),
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(response){
				locationUrl($("#forward").val(),"home");
				$('#modal-remove').modal('hide');
			}
		});
}


function getCollect(id){
	 $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:"",
			url: '/collect/detail/'+id,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(collect){
				$("#ctitle").val(collect.title);
				$("#clogoUrl").val(collect.logoUrl);
				$("#cremark").val(collect.remark);
				$("#ccollectId").val(collect.id);
				$('#modal-changeSharing').modal('show');
				$("#favoritesSelect").val(collect.favoritesId);
				$("#newFavorites").val("");
			}
		});
}


function changePrivacy(id,type){
	 $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:"",
			url: '/collect/changePrivacy/'+id+'/'+type,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(collect){
				if(type=='public'){
					$("#public"+id).hide();
					$("#private"+id).show();
				}else{
					$("#public"+id).show();
					$("#private"+id).hide();
				}
			}
		});
}


function changeLike(id){
	 $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:"",
			url: '/collect/like/'+id,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(like){
				if($("#like"+id).is(":hidden")){ 
					$("#like"+id).show();
					var praiseCount=parseInt($("#praiseC"+id).val())-1;
					$("#praiseC"+id).val(praiseCount);
					$("#likeS"+id).html("点赞("+praiseCount+")");
					$("#likel"+id).show();
					$("#unlike"+id).hide();
					$("#unlikel"+id).hide();
				}else{
					$("#like"+id).hide();
					$("#likel"+id).hide();
					$("#unlike"+id).show();
					$("#unlikel"+id).show();
					var praiseCount=parseInt($("#praiseC"+id).val())+1;
					$("#praiseC"+id).val(praiseCount);
					$("#UNlikeS"+id).html("取消点赞("+praiseCount+")");

				} 
			}
		});
}


function search(){
	 $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:"",
			url: '/collect/delete/'+$("#collectId").val(),
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(response){
				locationUrl($("#forward").val(),"home");
				$('#modal-remove').modal('hide');
			}
		});
}


function switchComment(collectId){
	 if($("#collapse"+collectId).hasClass('in')){
		 $("#collapse"+collectId).removeClass('in');
      }else{
    	  showComment(collectId);
      }
}

function showComment(collectId){
	  $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:'',
			url: '/comment/list/'+collectId,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(comments){
				initComment(comments,collectId);
	    	    $("#collapse"+collectId).addClass('in');
			}
		});
}

function initComment(comments,collectId){
	var comment='';
	 $("#commentList"+collectId).html("");
	for(var i=0;i<comments.length;i++){
		var item ='<div class=\"media bb p\"><small class=\"pull-right text-muted\">'+comments[i].commentTime+'</small>';
		item=item+'<div class=\"pull-left\"><img class=\"media-object img-circle thumb32\" src=\"'+comments[i].profilePicture+ '\" /></div> ';
		item=item+'<div class=\"media-body\">  <span class=\"media-heading\">  <p class=\"m0\"> '
		item=item+"<a href=\"javascript:void(0);\" onclick=\"locationUrl('/user/" + comments[i].userId + "')\">"+comments[i].userName+"</a>";
		item=item+'</p> <p class=\"m0 text-muted\">'+comments[i].content+'<small>';
		if(comments[i].userId==$("#userId").val()){
			item=item+"<a href=\"javascript:void(0);\" onclick=\"deleteComment('"+comments[i].id+"','"+collectId+"')\" >    删除</a>";
		}else{
			item=item+"<a href=\"javascript:void(0);\" onclick=\"replyComment('"+comments[i].userName+"','"+collectId+"')\" >    回复</a>";
		}
		item=item+'</small></p></span></div></div>';
		comment=comment+item;
	}
	 $("#commentList"+collectId).append(comment);
}


function comment(collectId){
	 $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:'collectId='+collectId+'&content='+$("#commentContent"+collectId).val(),
			url: '/comment/add',
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(response){
				var commentCount=parseInt($("#commentC"+collectId).val())+1;
				$("#commentC"+collectId).val(commentCount);
				$("#commentS"+collectId).html("评论("+commentCount+")");
				$("#commentContent"+collectId).val('');
				showComment(collectId);
			}
		});
}


function deleteComment(id,collectId){
	 $.ajax({
			async: false,
			type: 'POST',
			dataType: 'json',
			data:'',
			url: '/comment/delete/'+id,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
			},
			success: function(response){
				var commentCount=parseInt($("#commentC"+collectId).val())-1;
				$("#commentC"+collectId).val(commentCount);
				$("#commentS"+collectId).html("评论("+commentCount+")");
				showComment(collectId);
			}
		});
}

function replyComment(name,collectId){
	var text = $("#commentContent"+collectId).val();
	$("#commentContent"+collectId).val(text + "@" +name + " ").focus();
}
