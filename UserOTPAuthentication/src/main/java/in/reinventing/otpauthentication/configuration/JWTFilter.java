package in.reinventing.otpauthentication.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.reinventing.otpauthentication.externalservice.JWTService;
import in.reinventing.otpauthentication.externalservice.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String username=null;
		String JWTToken=null;
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
			JWTToken=requestTokenHeader.substring(7);
			try {
				username=this.jwtService.getUsernameFromToken(JWTToken);
			}catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			try {
				UserDetails userDetails=this.myUserDetailsService.loadUserByUsername(username);
				if(this.jwtService.validateToken(JWTToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
