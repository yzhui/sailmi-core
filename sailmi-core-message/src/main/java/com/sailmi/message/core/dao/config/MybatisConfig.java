package com.sailmi.message.core.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sailmi.message.dao.mapper")
public class MybatisConfig {

}
