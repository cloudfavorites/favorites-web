var page = 1;

function loadMore(){
    $.ajax({
        async: false,
        type: 'POST',
        dataType: 'json',
        data:'page='+page,
        url: "/letter/getLetterList",
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest);
            console.log(textStatus);
            console.log(errorThrown);
        },
        success: function(letters){
            listLetter(letters);
            if(letters.length > 0){
                reload();
            }
            page++;
        }
    });
}

function listLetter(letters){
    var letterList = '';
    for(var i=0; i<letters.length; i++){
        var item =
            "<li>" +
                "<div class=\"timeline-panel\">"+
                "<div class=\"popover right\">"+
                "<div class=\"popover-content\">"+
                "<div class=\"table-grid table-grid-align-middle mb\">"+
                "<div class=\"col col-xxs\">"+
                "<a href=\"javascript:void(0);\" onclick=\"locationUrl(\'/user/"+ letters[i].sendUserId + "/0\',\'\');\">"+
                "<img src=\""+(letters[i].profilePicture==null?'img/favicon.png':letters[i].profilePicture) +"\" alt=\"\" class=\"media-object img-circle thumb48\" />"+
                "</a>"+
                "</div>"+
                "<div class=\"col\">"+
                "<p class=\"m0\">"+
                "<a href=\"javascript:void(0);\" class=\"text-muted\" onclick=\"locationUrl(\'/user/" + letters[i].sendUserId + "/0\',\'\');\">"+
                "<strong class=\"send-user-name\">"+letters[i].sendUserName+"</strong>"+
                "</a>"+
                "<small class=\"ml\">"+
                "<span>"+letters[i].createTime+"</span>"+
                "给我"+(letters[i].type == 'REPLY'?'回复':'发')+"了私信"+
                "</small>"+
                "</p>"+
                "</div>"+
                "<div class=\"col text-right\">"+
                "<!-- <div class=\"btn btn-default\" data-toggle=\"modal\" data-target=\"#modal-revoke\">撤销</div> -->"+
                "<div class=\"btn btn-default btn-reply\">回复</div>"+
                "</div>"+
                "</div>"+
                "<p>"+(letters[i].type == 'REPLY'?'回复：':'')+letters[i].content+"</p>"+
                "<div class=\"collapse\">"+
                "<div class=\"media\">"+
                "<div class=\"media-body\">"+
                "<form data-parsley-validate=\"true\" onsubmit=\"return false\">"+
                "<div class=\"input-group\">"+
                "<input type=\"text\" class=\"form-control\" required=\"required\" placeholder=\"输入回复...\" />"+
                "<span class=\"input-group-btn\">"+
                "<button type=\"submit\" class=\"btn btn-default\" onclick=\"sendLetter('reply','"+letters[i].id+"',this)\" value=\""+letters[i].id+"\">发送</button>"+
                "</span>"+
                "</div>"+
                "</form>"+
                "</div>"+
                "</div>"+
                "</div>"+
                "</div>"+
                "</div>"+
                "</div>"+
                "</li>";
        letterList = letterList + item;
    }
    $("#letterList").append(letterList);
}

// 发送私信
function sendLetter(type,pid,obj){
    var content = '';
    var receiveUserId = '';
    var ok = false;
    var msg = '';
    if("original" == type){
        content = $("#letterContent").val();
        receiveUserId = $("#userId").val();
        ok = $('#sendLetterForm').parsley().isValid({force: true});
        msg = '发送';
    }else{
        content = $(obj).parent().parent().children(0).val();
        var _this = $(obj).parent().parent().parent().parent().parent().parent();
        var _form = $(obj).parent().parent().parent();
        ok = _form.parsley().isValid({force: true});
        msg = '回复';
    }
    if(!ok){
        return;
    }
    var url = '/letter/sendLetter';
    $.ajax({
        url : url,
        data : {'content':content,'sendType':type,'receiveUserId':receiveUserId,'pid':pid},
        type : 'POST',
        dataType : "json",
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        },
        success : function(data) {
            if(data.rspCode == '000000'){
                if("original" == type){
                    $("#sendLetterBtn").attr("aria-hidden","true");
                    $("#sendLetterBtn").attr("data-dismiss","modal");
                    $("#sendLetterForm")[0].reset();
                }else{
                    _form[0].reset();
                    if( _this.hasClass('in')){
                        _this.removeClass('in');
                    }
                }
                toastr.success(msg+'私信成功', '操作成功');
            }else{
                toastr.error(data.rspMsg, '操作失败');
            }
        }
    });
}

