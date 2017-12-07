package com.hisen.jars.jsonwebtokenjjwt;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;

public class JsonWebTokenJJWT {
    // 密钥 通过各种加密方式也ok
    public static final String key = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545df>?N<:{LWPW_hisen";

    public static void main(String[] args) {
        // 生成 token
        String compactJws = Jwts.builder()
                // JWT 所面向的用户
                .setSubject("hisen")
                // 设置密钥和加密算法
                .signWith(SignatureAlgorithm.HS512, key)
                // 设置签发时间
                .setIssuedAt(new DateTime().toDate())
                // 设置生效时间
                .setNotBefore(new DateTime(System.currentTimeMillis() + 10000).toDate())
                // 设置过期时间
                .setExpiration(new DateTime(System.currentTimeMillis() + 600000).toDate())
                // 存放各种业务数据 KV形式，类型不限
                .claim("hisenK", "hisenV")
                .compact();
        // 输出token
        System.out.println(compactJws);
        // 串改后的token
        String compactJws1 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoaXNlbiIsImlhdCI6MTUxMjYyODUzNiwibmJmIjoxNTEyNjI4NTQ2LCJleHAiOjE1MTI2MjkxMzYsImhpc2VuSyI6Imhpc2VuViJ9.JKxvvYQyIJEN8Eg_6gN5NlnDokqVNApSd7eg3QGpjBfBARzx4ip4WRzSa0Ul2ScpdierKi9WxF1iTUdoHNRiaA";
        try {
            // 解析token
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws1);
            // 验证 Subject
            boolean hisen = claimsJws.getBody().getSubject().equals("hisen");
            System.out.println(hisen);
            // 获取业务数据
            String hisenV = (String) claimsJws.getBody().get("hisenK");
            System.out.println(hisenV);
        } catch (SignatureException e) {
            // 被串改或者超时，会抛出异常
            System.out.println(e);
        }
    }
}
