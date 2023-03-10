package com.uj15.timedeal.common.auth;

import com.uj15.timedeal.auth.SessionConst;
import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.common.auth.annotation.Authorization;
import com.uj15.timedeal.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authorization authorization = ((HandlerMethod) handler).getMethodAnnotation(Authorization.class);
        if (authorization == null) {
            return true;
        }
        HttpSession session = request.getSession();
        UserPrincipal principal = (UserPrincipal) session.getAttribute(SessionConst.KEY.name());
        Role role = principal.getRole();

        if (!Arrays.asList(authorization.role()).contains(role)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorize");
            return false;
        }

        return true;
    }
}
