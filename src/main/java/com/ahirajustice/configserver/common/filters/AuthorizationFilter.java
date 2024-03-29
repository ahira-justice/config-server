package com.ahirajustice.configserver.common.filters;

import com.ahirajustice.configserver.common.constants.KeyConstants;
import com.ahirajustice.configserver.common.constants.SecurityConstants;
import com.ahirajustice.configserver.common.error.ErrorResponse;
import com.ahirajustice.configserver.common.exceptions.UnauthorizedException;
import com.ahirajustice.configserver.common.properties.AppProperties;
import com.ahirajustice.configserver.common.repositories.MicroserviceRepository;
import com.ahirajustice.configserver.common.repositories.UserRepository;
import com.ahirajustice.configserver.common.utils.AuthUtils;
import com.ahirajustice.configserver.modules.auth.dtos.AuthToken;
import com.ahirajustice.configserver.modules.auth.services.AuthDecodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends GenericFilterBean {

    private final AuthDecodeService authDecodeService;
    private final UserRepository userRepository;
    private final MicroserviceRepository microserviceRepository;
    private final AppProperties appProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        else {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Max-Age", "10");
        }

        if (!excludeFromAuth(request.getRequestURI(), request.getMethod())) {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            if (StringUtils.isBlank(header)) {
                writeErrorToResponse("Missing authorization header", response);
                return;
            }

            String[] splitHeader = header.split(" ");

            if (malformedAuthHeader(splitHeader)) {
                writeErrorToResponse("Malformed authorization header", response);
                return;
            }

            String scheme = splitHeader[0];
            String token = splitHeader[1];

            if (!StringUtils.lowerCase(scheme).equals("bearer")) {
                writeErrorToResponse("Invalid authentication scheme", response);
                return;
            }

            AuthToken authToken = authDecodeService.decodeJwt(token);

            if (isSecretKeyAuthToken(token)) {
                if (!microserviceExists(token)) {
                    writeErrorToResponse("Invalid secret key", response);
                    return;
                }
            }
            else {
                if (!userExists(authToken) || isExpired(authToken)) {
                    writeErrorToResponse("Invalid or expired token", response);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean excludeFromAuth(String requestURI, String requestMethod) {
        for (String url : SecurityConstants.EXCLUDE_FROM_AUTH_URLS) {
            String excludeURI = url.split(", ")[0];
            String excludeMethod = url.split(", ")[1];

            if (excludeURI.equals(requestURI) && excludeMethod.equals(requestMethod)){
                return true;
            }

            if (excludeURI.endsWith("/**") && requestURI.startsWith(excludeURI.replace("/**", "")) && excludeMethod.equals(requestMethod)){
                return true;
            }
        }

        return false;
    }

    private boolean malformedAuthHeader(String[] splitHeader) {
        return splitHeader.length != 2 ||
                StringUtils.isBlank(splitHeader[0]) ||
                StringUtils.isBlank(splitHeader[1]);
    }

    private boolean isSecretKeyAuthToken(String token) {
        return token.startsWith(KeyConstants.SECRET_KEY_PREFIX);
    }

    private boolean microserviceExists(String secretKey) {
        String hashedSecretKey = AuthUtils.getSha256Hash(secretKey);
        return microserviceRepository.existsByHashedSecretKey(hashedSecretKey);
    }

    private boolean userExists(AuthToken token) {
        return userRepository.existsByUsername(token.getUsername());
    }

    private boolean isExpired(AuthToken token) {
        return Instant.now().isAfter(token.getExpiry().toInstant());
    }

    private void writeErrorToResponse(String message, HttpServletResponse response) throws IOException {
        UnauthorizedException ex = new UnauthorizedException(message);
        ErrorResponse errorResponse = ex.toErrorResponse();

        ObjectMapper mapper = new ObjectMapper();
        String errorResponseBody = mapper.writeValueAsString(errorResponse);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ex.getStatusCode());
        writer.print(errorResponseBody);
        writer.flush();
    }
}
