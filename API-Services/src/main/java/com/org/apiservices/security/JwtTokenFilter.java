/*
 * package com.org.apiservices.security;
 * 
 * import java.io.IOException; import java.util.HashMap; import java.util.Map;
 * 
 * import javax.servlet.FilterChain; import javax.servlet.ServletException;
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.filter.OncePerRequestFilter;
 * 
 * import io.jsonwebtoken.ExpiredJwtException; import io.jsonwebtoken.Jwts;
 * import io.jsonwebtoken.MalformedJwtException; import
 * io.jsonwebtoken.UnsupportedJwtException;
 * 
 * @Component public class JwtTokenFilter extends OncePerRequestFilter {
 * 
 * @Autowired private JwtTokenUtil jwtTokenUtil;
 * 
 * @Autowired private JwtConfig jwtconfig;
 * 
 * private static final String[] JWT_URI = { "/S-DetailsDis/Login" };
 * 
 * private boolean isMatching(String[] values, String input) { for (String value
 * : values) { if (value.equals(input)) { return true; // Match found } } return
 * false; // No match found }
 * 
 * private Map<String, Object> createErrorDetails(String message) { Map<String,
 * Object> errorDetails = new HashMap<>(); errorDetails.put("statusCode",
 * "200"); errorDetails.put("errorCode", "500"); errorDetails.put("status", "");
 * errorDetails.put("successMessage", "null"); errorDetails.put("errorMessage",
 * message); return errorDetails; }
 * 
 * @Override protected void
 * doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
 * jakarta.servlet.http.HttpServletResponse response,
 * jakarta.servlet.FilterChain filterChain) throws
 * jakarta.servlet.ServletException, IOException { String token =
 * request.getHeader("Authorization"); String requestUri =
 * request.getRequestURI(); if (token != null && token.startsWith("Bearer ")) {
 * Map<String, Object> result = new HashMap<>(); token = token.substring(7); //
 * System.out.println(token); try {
 * Jwts.parser().setSigningKey(jwtTokenUtil.getSecretKey()).parseClaimsJws(token
 * );
 * 
 * } catch (ExpiredJwtException e) {
 * 
 * // String mobNum = jwtTokenUtil.extractmobileNumber(token);
 * System.out.println(token); if (isMatching(JWT_URI, requestUri)) {
 * 
 * Map<String, Object> extractedClaims = jwtTokenUtil.extractClaims(token,
 * jwtconfig.secretKey);
 * 
 * String mobNum = (String) extractedClaims.get("mobNumber"); String pin =
 * (String) extractedClaims.get("pin");
 * 
 * System.out.println(mobNum); System.out.println(pin);
 * 
 * } else { createErrorDetails("Token is expired"); } //
 * handleTokenExpiration(request, response); } catch (UnsupportedJwtException e)
 * { response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
 * response.getWriter().write("Token is invalid"); response.getWriter().flush();
 * 
 * } catch (MalformedJwtException e) {
 * response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
 * response.getWriter().write("Token is malformed");
 * response.getWriter().flush(); } } else {
 * response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 * response.getWriter().write("Token is missing"); response.getWriter().flush();
 * }
 * 
 * filterChain.doFilter(request, response);
 * 
 * }
 * 
 * private void handleTokenExpiration(jakarta.servlet.http.HttpServletRequest
 * request, jakarta.servlet.http.HttpServletResponse response) { // TODO
 * Auto-generated method stub }
 * 
 * 
 * }
 * 
 */