/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月28日
 * <修改描述:>
 */
package com.tx.component.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;

/**
 * JwtToken处理器<br/>
 *  Header:
 *      在header中通常包含了两部分：
 *      token类型和采用的加密算法。{ "alg": "HS256", "typ": "JWT"} 
 *      接下来对这部分内容使用 Base64Url 编码组成了JWT结构的第一部分。
 *      iss（签发者），exp（过期时间），sub（面向的用户），aud（接收方），iat（签发时间）
 *      
 *      iss(Issuser)：代表这个JWT的签发主体；（签发者）,
 *      sub(Subject)：代表这个JWT的主体，即它的所有人；（面向的用户）, 
 *      aud(Audience)：代表这个JWT的接收对象；（接收方）, 
 *      exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；（过期时间戳）, 
 *      nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
 *      iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；（签发时间）。 
 *      jti(JWT ID)：是JWT的唯一标识。
 *      
 *  Payload: 
 *      Token的第二部分是负载
 *      它包含了claim， Claim是一些实体（通常指的用户）的状态和额外的元数据，
 *      有三种类型的
 *      claim：reserved, public 和 private.Reserved 
 *      claims: 这些claim是JWT预先定义的，在JWT中并不会强制使用它们，而是推荐使用，
 *      Public claims：根据需要定义自己的字段，注意应该避免冲突 Private claims：
 *      这些是自定义的字段，可以用来在双方之间交换信息 负载使用的例子：
 *      { "sub": "1234567890", "name": "John Doe", "admin": true} 
 *      上述的负载需要经过Base64Url编码后作为JWT结构的第二部分。
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JwtTokenHandler {
    
    private String issuer = "cqtianxin";
    
    public Date generateExpirationDate(){
        return null;
    }
    
    public String generateToken(Map<String, Object> claims) {
        Date issuedAt = new Date();//签发时间
        
        String token = Jwts.builder()
                .setHeaderParam("signingKeyCode", "common")
                .setIssuer(issuer) //签发者
                .setIssuedAt(issuedAt) //签发时间
                .setAudience("inner") //接收方 
                .addClaims(claims)
                
                .setExpiration(generateExpirationDate())
                //.setSubject(sub) //面向的用户
                
                
                //.setSubject(sub)//
                
                //.setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, "123321qQ")
                .compact();
        return token;
    }
    
    //  private Date generateExpirationDate() {
    //  return new Date(System.currentTimeMillis() + expiration * 1000);
    //}
    
    //    String generateToken() {
    //        return 
    //    }
    
    private static SigningKeyResolver signingKeyResolver = new CustomizeSigningKeyResolver();
    
    /**
     * 从Token中获取声明<br/>
     * <功能详细描述>
     * @param secret token签名key
     * @param token token
     * 
     * @return Claims 可能会返回null
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        if (StringUtils.isBlank(token)) {
            return claims;
        }
        
        try {
            String signingKeyCode = String.valueOf(Jwts.parser()
                    .setSigningKeyResolver(signingKeyResolver)
                    .parseClaimsJws(token).getHeader().get("signingKeyCode"));
            System.out.println(signingKeyCode);
            
            claims = Jwts.parser()
                    .setSigningKeyResolver(signingKeyResolver)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
//            logger.warn("parse claims error.token:{} error:{}",
//                    token,
//                    e.getMessage());
            
            //当解析异常时直接返回null
            claims = null;
        }
        
        return claims;
    }
    
    public static void main(String[] args) {
        JwtTokenHandler handler = new JwtTokenHandler();
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "PengQingyang");
        claims.put("x1", "PengQingyang1");
        claims.put("x2", "PengQingyang2");
        claims.put("x3", "PengQingyang3");
        claims.put("x4", "PengQingyang4");
        claims.put("x5", "PengQingyang5");
        String token = handler.generateToken(claims);
        
        System.out.println(token);
        
        Claims c = getClaimsFromToken(token);
        System.out.println(c.get("sub"));
        
        for(Entry<String, Object> entry : c.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
