package com.agent.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Agent Learning 应用启动类
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
@SpringBootApplication
@MapperScan("com.agent.learning.repository")
public class AgentLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentLearningApplication.class, args);
        System.out.println("🚀 Agent Learning 应用启动成功!");
        System.out.println("📖 API文档地址: http://localhost:8080/swagger-ui.html");
        System.out.println("🔍 健康检查地址: http://localhost:8080/actuator/health");
    }
}
