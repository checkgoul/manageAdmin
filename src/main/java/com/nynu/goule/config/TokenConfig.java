package com.nynu.goule.config;

import com.nynu.goule.utils.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
/**
 * @author  goule
 * @date  2021/10/29 13:40
 */

@Configuration
public class TokenConfig {

    @Autowired
    private StringRedisTemplate template;

    private final static Logger logger = LoggerFactory.getLogger(TokenConfig.class);
    private final static String systemName = "newTest.GOUL";

    public String createToken(String username){
        String afterToken = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().replace("-","");
        String token = year+username+systemName+id;
        try {
            afterToken = Encrypt.AESencrypt(token);
        } catch (Exception e) {
            logger.info("token生成异常");
            e.printStackTrace();
        }
        return afterToken;
    }

    public boolean checkToken(String username, String severToken) throws Exception {
        String systemToken = template.opsForValue().get(username);
        String afterSystemToken = Encrypt.AESdecrypt(systemToken);
        String afterSeverToken = Encrypt.AESdecrypt(severToken);
        return afterSystemToken.equals(afterSeverToken);
    }
}
