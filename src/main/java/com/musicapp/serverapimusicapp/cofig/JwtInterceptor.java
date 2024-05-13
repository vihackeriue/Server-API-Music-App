package com.musicapp.serverapimusicapp.cofig;

import com.musicapp.serverapimusicapp.dto.BaseDTO;
import com.musicapp.serverapimusicapp.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String bearerToken = request.getHeader("token");

        System.out.println(bearerToken);
//        System.out.println(jwtConfig.extractUsername(bearerToken));
        if (bearerToken != null && !jwtConfig.isTokenExpired(bearerToken)) {
            // Token hợp lệ, cho phép yêu cầu tiếp tục
            String email = jwtConfig.extractUsername(bearerToken);
            if (email != null) {
                System.out.println(userService.isToken(bearerToken));
                if (userService.existsByEmail(email) && userService.isToken(bearerToken)){
                    // Thiết lập thông tin createdBy và updatedBy trong DTO
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        Method method = handlerMethod.getMethod();
                        // Kiểm tra nếu method được gọi là một endpoint đăng ký hoặc cập nhật
                        if (method.isAnnotationPresent(PostMapping.class) || method.isAnnotationPresent(PutMapping.class)) {
                            Object requestBody = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                            BaseDTO dto = new BaseDTO();
                            if (dto.getId() != null) {
                                // Nếu có id, đây là một yêu cầu cập nhật
                                dto.setUpdatedBy(email);
                            } else {
                                // Nếu không có id, đây là một yêu cầu đăng ký mới
                                dto.setCreatedBy(email);
                                dto.setUpdatedBy(email);
                            }
                            if (requestBody instanceof  BaseDTO) {
                            }
                            System.out.println(dto.getCreatedBy());
                            System.out.println("00000000");
                        }
                    }
                    System.out.println("1111");
                    return true;
                }
                System.out.println("222");
                return false;
            }
            System.out.println("333");
            return false;
        } else {
            // Token không hợp lệ, trả về lỗi 401 UNAUTHORIZED
            System.out.println("Quyền truy cập bị hạn chế");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Quyền truy cập bị hạn chế");
            return false;
        }
    }

//    private String extractTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
}

