
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
