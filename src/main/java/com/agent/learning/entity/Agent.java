package com.agent.learning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Agent实体类
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agents")
public class Agent {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Agent名称
     */
    @NotBlank(message = "Agent名称不能为空")
    @Size(max = 100, message = "Agent名称长度不能超过100个字符")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Agent描述
     */
    @Size(max = 500, message = "Agent描述长度不能超过500个字符")
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Agent类型
     */
    @NotBlank(message = "Agent类型不能为空")
    @Size(max = 50, message = "Agent类型长度不能超过50个字符")
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /**
     * Agent状态：ACTIVE, INACTIVE, TRAINING
     */
    @NotBlank(message = "Agent状态不能为空")
    @Size(max = 20, message = "Agent状态长度不能超过20个字符")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    /**
     * 配置参数（JSON格式）
     */
    @Column(name = "config", columnDefinition = "TEXT")
    private String config;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 创建者
     */
    @Size(max = 50, message = "创建者长度不能超过50个字符")
    @Column(name = "created_by", length = 50)
    private String createdBy;

    /**
     * 更新者
     */
    @Size(max = 50, message = "更新者长度不能超过50个字符")
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    /**
     * 版本号
     */
    @Version
    @Column(name = "version")
    private Long version;

    /**
     * 构造函数
     * 
     * @param name   Agent名称
     * @param type   Agent类型
     * @param status Agent状态
     */
    public Agent(String name, String type, String status) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    /**
     * 设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 设置更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
