package com.demo_bank_v1.interceptors;

import com.demo_bank_v1.models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AppInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("In Pre Handle Interceptor Method");
        // TODO: 3/16/2022 REQUEST URI:
        if (request.getRequestURI().startsWith("/app")) {
            // Get Session:
            HttpSession session = request.getSession();

            // TODO: Get Token Stored In Session:
            String token = (String) session.getAttribute("token");
            System.out.println(token);
            // TODO: Get Use Object Stored In Session:
            User user = (User) session.getAttribute("user");
            // TODO: CHECK IF AUTHENTICATED:
//            boolean isAuthenticated = (boolean) session.getAttribute("authenticated");

            // TODO: VALIDATE SESSION ATTRIBUTES / OBJECTS:
            if (token == null || user == null) {
                response.sendRedirect("/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("In Post Handle Interceptor Method");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("In After Completion Interceptor Method");
    }
}
