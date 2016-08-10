
package com.favorites.domain.result;

public enum ExceptionMsg {
	SUCCESS("000000", "操作成功"),
	FAILED("999999","操作失败"),
    ParamError("000001", "参数错误！"),
    
    LoginNameOrPassWordError("000100", "用户名或者密码错误！"),
    EmailUsed("000101","该邮箱已被注册"),
    UserNameUsed("000102","该登陆名称已存在")

    ;
   private ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;
    
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

    
}

