package ga.tianyuge.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.Signer;
import org.primeframework.jwt.hmac.HMACSigner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/18 13:58
 * @Email: guoqing.chen01@hand-china.com
 */
public class CreateOnlyOfficeToken {
    /**
     * 生成JWT TOKEN
     *
     * @param id 这里加密数据id为用户id
     * @return
     */
    /*public String generateToken(Long id) {
        *//**将token设置为jwt格式*//*
        String baseToken = UUID.randomUUID().toString();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LocalDateTime localDateTimeExpire = localDateTimeNow.plusSeconds(EXPIRE_SECONDS);
        Date from = Date.from(localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
        //token过期时间
        Date expire = Date.from(localDateTimeExpire.atZone(ZoneId.systemDefault()).toInstant());
        Claims jwtClaims = Jwts.claims().setSubject(baseToken);
        jwtClaims.put("", id);
        String compactJws = Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
        return compactJws;
    }*/
}
