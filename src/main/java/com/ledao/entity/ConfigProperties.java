package com.ledao.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author LeDao
 * @company
 * @create 2022-03-03 3:59
 */
@Data
@Component
public class ConfigProperties {

    /**
     * IP地址
     */
    @Value("${spring.redis.host}")
    private String host;
    /**
     * 端口
     */
    @Value("${spring.redis.port}")
    private Integer port;
    /**
     * 密码
     */
    @Value("${spring.redis.password}")
    private String password;

    @Value("${wantToBuyId}")
    private Integer wantToBuyId;

    @Value("${spring.mail.username}")
    private String sendMailPerson;

    @Value("${userImageFilePath}")
    private String userImageFilePath;
}
