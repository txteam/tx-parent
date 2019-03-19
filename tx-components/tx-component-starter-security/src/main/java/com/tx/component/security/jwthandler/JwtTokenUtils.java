/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月15日
 * <修改描述:>
 */
package com.tx.component.security.jwthandler;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JwtToken类<br/>
 * JWT包含了使用.分隔的三部分： Header_头部;Payload_负载;Signature_签名
 *  其结构看起来是这样的Header.Payload.Signature
 *  Header:
 *      在header中通常包含了两部分：
 *      token类型和采用的加密算法。{ "alg": "HS256", "typ": "JWT"} 
 *      接下来对这部分内容使用 Base64Url 编码组成了JWT结构的第一部分。
 *      iss（签发者），exp（过期时间），sub（面向的用户），aud（接收方），iat（签发时间）
 *  Payload: 
 *      Token的第二部分是负载
 *      它包含了claim， Claim是一些实体（通常指的用户）的状态和额外的元数据，
 *      有三种类型的
 *      claim：reserved, public 和 private.Reserved 
 *      claims: 这些claim是JWT预先定义的，在JWT中并不会强制使用它们，而是推荐使用，
 *      常用的有 iss（签发者）,
 *      exp（过期时间戳）, 
 *      sub（面向的用户）, 
 *      aud（接收方）, 
 *      iat（签发时间）。 
 *      Public claims：根据需要定义自己的字段，注意应该避免冲突 Private claims：
 *      这些是自定义的字段，可以用来在双方之间交换信息 负载使用的例子：
 *      { "sub": "1234567890", "name": "John Doe", "admin": true} 
 *      上述的负载需要经过Base64Url编码后作为JWT结构的第二部分。
 *  Signature:
 *      创建签名需要使用编码后的header和payload以及一个秘钥，
 *      使用header中指定签名算法进行签名。例如如果希望使用HMAC SHA256算法，
 *      那么签名应该使用下列方式创建： 
 *      HMACSHA256( base64UrlEncode(header) + "." + base64UrlEncode(payload), secret) 
 *      签名用于验证消息的发送者以及消息是没有经过篡改的。 
 *      完整的JWT 完整的JWT格式的输出是以. 
 *      分隔的三段Base64编码，与SAML等基于XML的标准相比，
 *      JWT在HTTP和HTML环境中更容易传递。 下列的JWT展示了一个完整的JWT格式，
 *      它拼接了之前的Header， Payload以及秘钥签名：
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JwtTokenUtils implements Serializable {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
    
    /** 注释内容 */
    private static final long serialVersionUID = -8544917813800113932L;
    
    private static final String CLAIM_KEY_USERNAME = "sub";
    
    private static final String CLAIM_KEY_CREATED = "created";
    
    //    /**
    //     * 从Token中获取声明<br/>
    //     * <功能详细描述>
    //     * @param secret token签名key
    //     * @param token token
    //     * 
    //     * @return Claims 可能会返回null
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static Claims getClaimsFromToken(String token) {
    //        Claims claims = null;
    //        if (StringUtils.isBlank(token)) {
    //            return claims;
    //        }
    //        
    //        try {
    //            claims = Jwts.parser()
    //                    .setSigningKeyResolver(signingKeyResolver)
    //                    .parseClaimsJws(token)
    //                    .getBody();
    //        } catch (Exception e) {
    //            //ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
    //            logger.warn("parse claims error.token:{} error:{}",
    //                    token,
    //                    e.getMessage());
    //            
    //            //当解析异常时直接返回null
    //            claims = null;
    //        }
    //        
    //        return claims;
    //    }
    //    
    //    /**
    //     * 从Token中获取声明<br/>
    //     * <功能详细描述>
    //     * @param secret token签名key
    //     * @param token token
    //     * 
    //     * @return Claims 可能会返回null
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static Claims getClaimsFromToken(String secret, String token) {
    //        AssertUtils.notEmpty(secret, "secret is empty.");
    //        
    //        Claims claims = null;
    //        if (StringUtils.isBlank(token)) {
    //            return claims;
    //        }
    //        
    //        try {
    //            claims = Jwts.parser()
    //                    .setSigningKey(secret)
    //                    .parseClaimsJws(token)
    //                    .getBody();
    //        } catch (Exception e) {
    //            //ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
    //            logger.warn("parse claims error.token:{} error:{}",
    //                    token,
    //                    e.getMessage());
    //            
    //            //当解析异常时直接返回null
    //            claims = null;
    //        }
    //        
    //        return claims;
    //    }
    //    
    //    public String generateToken(UserDetails userDetails) {
    //        Map<String, Object> claims = new HashMap<>();
    //        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
    //        claims.put(CLAIM_KEY_CREATED, new Date());
    //        
    //        return generateToken(claims);
    //    }
    //    
    //    String generateToken(Map<String, Object> claims) {
    //        return Jwts.builder()
    //                .setClaims(claims)
    //                .setExpiration(generateExpirationDate())
    //                .signWith(SignatureAlgorithm.HS512, secret)
    //                .compact();
    //    }
    //    
    //    public Boolean validateToken(String token, UserDetails userDetails) {
    //        final String username = getUsernameFromToken(token);
    //        final Date created = getCreatedDateFromToken(token);
    //        
    //        //        final Date expiration = getExpirationDateFromToken(token);  
    //        return (username.equals(user.getUsername()) && !isTokenExpired(token)
    //                && !isCreatedBeforeLastPasswordReset(created,
    //                        user.getLastPasswordResetDate()));
    //    }
    //    
    //    public String getUsernameFromToken(String token) {
    //        String username;
    //        try {
    //            final Claims claims = getClaimsFromToken(token);
    //            username = claims.getSubject();
    //        } catch (Exception e) {
    //            username = null;
    //        }
    //        return username;
    //    }
    //    
    //    public Date getCreatedDateFromToken(String token) {
    //        Date created;
    //        try {
    //            final Claims claims = getClaimsFromToken(token);
    //            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
    //        } catch (Exception e) {
    //            created = null;
    //        }
    //        return created;
    //    }
    //    
    //    public Date getExpirationDateFromToken(String token) {
    //        Date expiration;
    //        try {
    //            final Claims claims = getClaimsFromToken(token);
    //            expiration = claims.getExpiration();
    //        } catch (Exception e) {
    //            expiration = null;
    //        }
    //        return expiration;
    //    }
    //    
    //    private Date generateExpirationDate() {
    //        return new Date(System.currentTimeMillis() + expiration * 1000);
    //    }
    //    
    //    private Boolean isTokenExpired(String token) {
    //        final Date expiration = getExpirationDateFromToken(token);
    //        return expiration.before(new Date());
    //    }
    //    
    //    private Boolean isCreatedBeforeLastPasswordReset(Date created,
    //            Date lastPasswordReset) {
    //        return (lastPasswordReset != null && created.before(lastPasswordReset));
    //    }
    //    
    //    
    //    
    //    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
    //        final Date created = getCreatedDateFromToken(token);
    //        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
    //                && !isTokenExpired(token);
    //    }
    //    
    //    public String refreshToken(String token) {
    //        String refreshedToken;
    //        try {
    //            final Claims claims = getClaimsFromToken(token);
    //            claims.put(CLAIM_KEY_CREATED, new Date());
    //            refreshedToken = generateToken(claims);
    //        } catch (Exception e) {
    //            refreshedToken = null;
    //        }
    //        return refreshedToken;
    //    }
    
    
    //    public static void main(String[] args) {
    //        JwtTokenHandler handler = new JwtTokenHandler();
    //        
    //        Map<String, Object> claims = new HashMap<>();
    //        claims.put("sub", "PengQingyang");
    //        claims.put("x1", "PengQingyang1");
    //        claims.put("x2", "PengQingyang2");
    //        claims.put("x3", "PengQingyang3");
    //        claims.put("x4", "PengQingyang4");
    //        claims.put("x5", "PengQingyang5");
    //        String token = handler.generateToken(claims);
    //        
    //        System.out.println(token);
    //        
    //        Claims c = getClaimsFromToken(token);
    //        System.out.println(c.get("sub"));
    //        
    //        for (Entry<String, Object> entry : c.entrySet()) {
    //            System.out.println(entry.getKey() + " : " + entry.getValue());
    //        }
    //    }
}
