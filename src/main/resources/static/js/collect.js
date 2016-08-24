$(function(){
	if("private"== gconfig.defaultCollectType){
		$("#type").attr("checked","checked");
	}
	if("simple" == gconfig.defaultModel){
		$("#show2").hide();
		$("#model2").hide();
		$("#model1").show();
	}else{
		$("#show2").show();
		$("#model1").hide();
		$("#model2").show();
	}
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
	 if($("#title").val()==""){
		 $("#errorMsg").text("标题不能为空");
		 $("#errorMsg").show();
		 return;
	 }
	 if($("#logoUrl").val() ==""){
		 $("#errorMsg").text("图片链接不能为空");
		 $("#errorMsg").show();
		 return;
	 }
	  $("#errorMsg").hide();
  	  $.ajax({
  	         type: "POST",
  	         url:"/user/collect",
  	         data:$("#collect-form").serialize(),
  	         success: function(response) { 
  	        	 if(response.rspCode == '000000'){
  	        		window.location="/";
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
				locationUrl("/standard/my","home");
				loadFavorites();
				$("#delCollect").attr("aria-hidden","true");
				$("#delCollect").attr("data-dismiss","modal");
			}
		});
}


function modifyCollect(id){
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
			}
		});
}

