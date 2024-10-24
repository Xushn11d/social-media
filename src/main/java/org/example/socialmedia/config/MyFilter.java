package org.example.socialmedia.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class MyFilter extends OncePerRequestFilter {
    @Autowired
    @Lazy
    private  UserDetailsService userDetailsService;
    @Autowired
    private JwtProvider jwtProvider;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

        String authorization = request.getHeader("Authorization");


        if(authorization == null || authorization.equals("annonumous")){
            filterChain.doFilter(request, response);
            return;
        }
        if (authorization.startsWith("Basic ")) {
            authorization = authorization.substring(6);
            byte[] decode = Base64.getDecoder().decode(authorization);
            String auth = new String(decode);
            String[] split = auth.split(":");
            setAuthenticationToContext(split[0],split[1]);
        }
        if (authorization.startsWith("Bearer ")) {
            authorization = authorization.substring(7);
            String  username = jwtProvider.getSubject(authorization);
            System.out.println(username);
//            String username = "auth ichidagi username olinishi kerak!";
            setAuthenticationToContext(username);
        }

        }catch (Exception e){
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthenticationToContext(String  username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authUser =new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.isCredentialsNonExpired() &&
                userDetails.isAccountNonExpired() &&
                userDetails.isAccountNonLocked() &&
                userDetails.isEnabled(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder
                .getContext()
                .setAuthentication(authUser);
    }
    public void setAuthenticationToContext(String  username,String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        setAuthenticationToContext(username);
    }
}
