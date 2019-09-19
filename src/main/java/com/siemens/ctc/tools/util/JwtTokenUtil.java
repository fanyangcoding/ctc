package com.siemens.ctc.tools.util;

import com.siemens.ctc.dao.UserInfoMapper;
import com.siemens.ctc.model.UserInfo;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(JwtTokenUtil.class);

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String CLAIM_KEY_CREATED = "created";

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                LOGGER.error("Jwt Token claims null");
                throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
            }
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
//            LOGGER.error("username为空");
//            throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
        }
        return username;
    }

    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                LOGGER.error("Jwt Token claims null");
                throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
            }
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
//            throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                LOGGER.error("Jwt Token claims null");
                throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
            }
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
//            throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
//            throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                LOGGER.error("Jwt Token claims null");
                throw new BusinessException(ErrorCodeEnum.JWT_TOKEN_FAILURE.getErrorCode(), ErrorCodeEnum.JWT_TOKEN_FAILURE.getMsg());
            }
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        UserInfo user = userInfoMapper.findByUsername(userDetails.getUsername());
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
//        final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

}
