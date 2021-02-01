package com.nynu.goule.common.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.Serializable;

/**
 * @author  goule
 * @date  2021/1/29 10:20
 * 整个socket有关文件 都暂未使用
 */
@Configuration
public class WebSocketConfig implements Serializable {
    /**
     * ServerEndpointExporter 作用
     *
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     *
     * @return
     */

    private static final long serialVersionUID = 7600559593733357846L;


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
