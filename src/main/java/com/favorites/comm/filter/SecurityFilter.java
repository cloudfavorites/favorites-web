package com.favorites.comm.filter;

import com.favorites.comm.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityFilter implements Filter {

    protected Logger logger = Logger.getLogger(this.getClass());
	private static Set<String> GreenUrlSet = new HashSet<String>();

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		GreenUrlSet.add(Const.BASE_PATH + "/");
		GreenUrlSet.add(Const.BASE_PATH + "/login");
		GreenUrlSet.add(Const.BASE_PATH + "/register");
		GreenUrlSet.add(Const.BASE_PATH + "/index");
		GreenUrlSet.add(Const.BASE_PATH + "/forgotPassword");
		GreenUrlSet.add(Const.BASE_PATH + "/newPassword");
	}
	
	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) srequest;
		String uri = request.getRequestURI();
		if (request.getSession().getAttribute(Const.LOGIN_SESSION_KEY) == null) {
		    if (containsSuffix(uri) || GreenUrlSet.contains(uri) || containsKey(uri)) {
	            logger.debug("don't check  url , " + request.getRequestURI());
	            filterChain.doFilter(srequest, sresponse);
	            return;
	        }else{
	        	//跳转到登陆页面
	        	String referer = "";
				String param = this.codeToString(request.getQueryString());
				if(StringUtils.isNotBlank(request.getContextPath())){
					referer = referer + request.getContextPath();
				}
	        	if(StringUtils.isNotBlank(request.getServletPath())){
					referer = referer + request.getServletPath();
				}
				if(StringUtils.isNotBlank(param)){
					referer = referer + "?" + param;
				}
	        	request.getSession().setAttribute(Const.LAST_REFERER, referer);
	        	logger.debug("security filter, deney, " + request.getRequestURI());
				String html = "";
				if(0 > referer.indexOf("/collect?")){
					html = "<script type=\"text/javascript\">window.location.href=\"_BP_index\"</script>";
				}else{
					html = "<script type=\"text/javascript\">window.location.href=\"_BP_login\"</script>";
				}
				html = html.replace("_BP_", Const.BASE_PATH);
				sresponse.getWriter().write(html);
	        }
		}else{
			filterChain.doFilter(srequest, sresponse);
		}
	}
	
	
	
    /**
     * @param url
     * @return
     * @author neo
     * @date 2016-5-4
     */
    private boolean containsSuffix(String url) {
        if (url.endsWith(".js")
                || url.endsWith(".css")
                || url.endsWith(".jpg")
                || url.endsWith(".gif")
                || url.endsWith(".png")
                || url.endsWith(".html")
                || url.endsWith(".eot")
                || url.endsWith(".svg")
                || url.endsWith(".ttf")
                || url.endsWith(".woff")
                || url.endsWith(".ico")
                || url.endsWith(".woff2")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @param url
     * @return
     * @author neo
     * @date 2016-5-4
     */
    private boolean containsKey(String url) {
        if (url.contains("/media/")
                || url.contains("/login")||url.contains("/user/login")
                || url.contains("/register")||url.contains("/user/regist")||url.contains("/index")
                || url.contains("/forgotPassword")||url.contains("/user/sendForgotPasswordEmail")
                || url.contains("/newPassword")||url.contains("/user/setNewPassword")) {
            return true;
        } else {
            return false;
        }
    }

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public  String codeToString(String str) {
        String strString = str;
        try {
            byte tempB[] = strString.getBytes("ISO-8859-1");
            strString = new String(tempB);
            return strString;
        } catch (Exception e) {
            return strString;
        }
    } 
}