package com.example.zinware.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Removes bearer from authorization
     * @param request authorization header value
     * @return jwt token
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasLength("headerAuth") && headerAuth.startsWith("Bearer")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    /**
     * Authenticates the user based on a JSON Web Token (JWT) that is provided in the request header
     * @param request the HTTP request object
     * @param response the HTTP response object
     * @param filterChain the filter chain to execute after authentication
     * @throws ServletException if there is an error in the servlet
     * @throws IOException  if there is an I/O error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            // check if the jwt key is valid and not null
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // if valid get user email from the key
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                // load user details from the key
                UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
                // set username and password authentication token from user user details
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // build request and get security content
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.info("Cannot set user authentication");
        }
        filterChain.doFilter(request, response);
    }
}
