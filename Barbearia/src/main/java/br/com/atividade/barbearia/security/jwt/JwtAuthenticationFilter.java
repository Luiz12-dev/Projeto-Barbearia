package br.com.atividade.barbearia.security.jwt;

import br.com.atividade.barbearia.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter {
    @Autowired
    private final JwtUtils jwtUtils;
    @Autowired
    public final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try{
            String token = parseJwt(request);

            if(token != null && jwtUtils.validateJwtToken(token)){
                String username = jwtUtils.getUsernameFromJwtToken(token);

                UserDetails userDetails= userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken
                        (userDetails,null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(Exception e){

        }
        chain.doFilter(request, response);

    }


    private String parseJwt(HttpServletRequest req) {
        String headerAuth= req.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;

    }
}
