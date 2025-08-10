package com.agent.learning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Agent数据传输对象
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
@Data
public class AgentDto {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * Agent名称
     */
    @NotBlank(message = "Agent名称不能为空")
    @Size(max = 100, message = "Agent名称长度不能超过100个字符")
    private String name;

    /**
     * Agent描述
     */
    @Size(max = 500, message = "Agent描述长度不能超过500个字符")
    private String description;

    /**
     * Agent类型
     */
    @NotBlank(message = "Agent类型不能为空")
    @Size(max = 50, message = "Agent类型长度不能超过50个字符")
    private String type;

    /**
     * Agent状态：ACTIVE, INACTIVE, TRAINING
     */
    @NotBlank(message = "Agent状态不能为空")
    @Size(max = 20, message = "Agent状态长度不能超过20个字符")
    private String status;

    /**
     * 配置参数（JSON格式）
     */
    private String config;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 创建者
     */
    @Size(max = 50, message = "创建者长度不能超过50个字符")
    private String createdBy;

    /**
     * 更新者
     */
    @Size(max = 50, message = "更新者长度不能超过50个字符")
    private String updatedBy;

    /**
     * 版本号
     */
    private Long version;
}
