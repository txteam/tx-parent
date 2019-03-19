/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月28日
 * <修改描述:>
 */
package com.tx.component.security.jwthandler;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tx.component.security.SecurityConstants;
import com.tx.component.security.model.JwtSigningKey;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.UUIDUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;

/**
 * JwtToken处理器<br/>
 *  Header:
 *      在header中通常包含了两部分：
 *      token类型和采用的加密算法。{ "alg": "HS256", "typ": "JWT"} 
 *      接下来对这部分内容使用 Base64Url 编码组成了JWT结构的第一部分。
 *      iss(Issuser)：代表这个JWT的签发主体；（签发者）,
 *      aud(Audience)：代表这个JWT的接收对象；（接收方）, 
 *      exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；（过期时间戳）, 
 *      iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；（签发时间）。 
 *      nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
 *      
 *      sub(Subject)：代表这个JWT的主题,
 *      jti(JWT ID)：是JWT的唯一标识。
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
public abstract class AbstractJwtTokenHandler {
    
    /** signingKey解析器 */
    protected SigningKeyResolver signingKeyResolver = new AbstractSigningKeyResolver() {
        
        /**
         * @param header
         * @return
         */
        @SuppressWarnings("rawtypes")
        @Override
        protected String doGetBase64EncodedKeyBytes(JwsHeader header) {
            // TODO Auto-generated method stub
            return null;
        }
    };
    
    //签发者
    protected String issue;
    
    /** <默认构造函数> */
    public AbstractJwtTokenHandler() {
        super();
    }
    
    /** <默认构造函数> */
    public AbstractJwtTokenHandler(String issue) {
        super();
        this.issue = issue;
    }
    
    /**
     * 生成Token<br/>
     * <功能详细描述>
     * @param claims
     * @param subject
     * @param audience
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String generateToken(Map<String, Object> claims, String subject,
            String audience) {
        AssertUtils.notEmpty(subject, "subject is empty.");
        AssertUtils.notEmpty(audience, "audience is empty.");
        
        Date issuedAt = new Date();//签发时间
        Date notBefore = generateNotBefore(issuedAt, subject, audience);
        notBefore = (notBefore == null || notBefore.compareTo(issuedAt) < 0)
                ? issuedAt : notBefore;
        
        //获取最后一个有效的JwtSigningKey实现
        JwtSigningKey jsk = getSigningKey(subject, audience);
        AssertUtils.notNull(jsk,
                "jsk is null.subject:{} audience:{}",
                new Object[] { subject, audience });
        
        //生成失效时间
        Date expirationDate = generateExpirationDate(issuedAt, jsk);
        String signingKeyCode = jsk.getSigningKeyCode();
        String signingKey = jsk.getSigningKey();
        
        String token = Jwts.builder()
                .setHeaderParam(SecurityConstants.JWT_HEADER_SIGNING_KEY_CODE,
                        signingKeyCode)
                .setIssuer(this.issue) //签发者
                .setAudience(audience) //接收方 
                .setIssuedAt(issuedAt) //签发时间
                .setSubject(subject) //主题
                .setNotBefore(notBefore) //生效时间，生效时间>=签发时间
                .setId(generateJti(issuedAt, subject, audience))// 生成jwt唯一键
                .setExpiration(expirationDate) //过期时间
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
        return token;
    }
    
    protected JwtSigningKey getSigningKey(String subject, String audience) {
        return null;
    }
    
    /**
     * 生成生效时间<br/>
     *    默认为签发时间<br/>
     * <功能详细描述>
     * @param issuedAt
     * @param subject
     * @return [参数说明]
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String generateJti(Date issuedAt, String subject,
            String audience) {
        String jti = UUIDUtils.generateUUID();
        return jti;
    }
    
    /**
     * 生成生效时间<br/>
     *    默认为签发时间<br/>
     * <功能详细描述>
     * @param issuedAt
     * @param subject
     * @return [参数说明]
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected Date generateNotBefore(Date issuedAt, String subject,
            String audience) {
        return issuedAt;
    }
    
    public Date generateExpirationDate(Date issuedAt,
            JwtSigningKey jwtSigningKey) {
        return null;
    }
    
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
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        if (StringUtils.isBlank(token)) {
            return claims;
        }
        
        try {
            String signingKeyCode = String.valueOf(Jwts.parser()
                    .setSigningKeyResolver(signingKeyResolver)
                    .parseClaimsJws(token)
                    .getHeader()
                    .get("signingKeyCode"));
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
    
    /**
     * @return 返回 issue
     */
    public String getIssue() {
        return issue;
    }
    
    /**
     * @param 对issue进行赋值
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }
    
}
