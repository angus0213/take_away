package com.ccg.reggie.filter;

import com.alibaba.fastjson.JSON;

import com.ccg.reggie.common.R;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.ccg.reggie.common.baseContext;

import static com.sun.prism.impl.BaseContext.*;

/**
 * æ£€æŸ¥ç”¨æˆ·æ˜¯å¦å·²ç»å®Œæˆç™»å½•
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter{
    //è·¯å¾„åŒ¹é…å™¨ï¼Œæ”¯æŒé€šé…ç¬¦
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1ã€èŽ·å–æœ¬æ¬¡è¯·æ±‚çš„URI
        String requestURI = request.getRequestURI();// /backend/index.html

        log.info("æ‹¦æˆªåˆ°è¯·æ±‚ï¼š{}",requestURI);

        //å®šä¹‰ä¸éœ€è¦å¤„ç†çš„è¯·æ±‚è·¯å¾„
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/frontend/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        //2ã€åˆ¤æ–­æœ¬æ¬¡è¯·æ±‚æ˜¯å¦éœ€è¦å¤„ç†
        boolean check = check(urls, requestURI);

        //3ã€å¦‚æžœä¸éœ€è¦å¤„ç†ï¼Œåˆ™ç›´æŽ¥æ”¾è¡Œ
        if(check){
            log.info("æœ¬æ¬¡è¯·æ±‚{}ä¸éœ€è¦å¤„ç†",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4-1ã€åˆ¤æ–­ç™»å½•çŠ¶æ€ï¼Œå¦‚æžœå·²ç™»å½•ï¼Œåˆ™ç›´æŽ¥æ”¾è¡Œ
        if(request.getSession().getAttribute("employee") != null){
            log.info("ç”¨æˆ·å·²ç™»å½•ï¼Œç”¨æˆ·idä¸ºï¼š{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            baseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
        }

        //4-2ã€åˆ¤æ–­ç™»å½•çŠ¶æ€ï¼Œå¦‚æžœå·²ç™»å½•ï¼Œåˆ™ç›´æŽ¥æ”¾è¡Œ
        if(request.getSession().getAttribute("user") != null){
            log.info("ç”¨æˆ·å·²ç™»å½•ï¼Œç”¨æˆ·idä¸ºï¼š{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            baseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("ç”¨æˆ·æœªç™»å½•");
        //5ã€å¦‚æžœæœªç™»å½•åˆ™è¿”å›žæœªç™»å½•ç»“æžœï¼Œé€šè¿‡è¾“å‡ºæµæ–¹å¼å‘å®¢æˆ·ç«¯é¡µé¢å“åº”æ•°æ®
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * è·¯å¾„åŒ¹é…ï¼Œæ£€æŸ¥æœ¬æ¬¡è¯·æ±‚æ˜¯å¦éœ€è¦æ”¾è¡Œ
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
