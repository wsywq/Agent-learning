package com.agent.learning.controller;

import com.agent.learning.dto.AgentDto;
import com.agent.learning.dto.ApiResponse;
import com.agent.learning.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Agent控制器
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
@Slf4j
@RestController
@RequestMapping("/agents")
@Tag(name = "Agent管理", description = "Agent相关接口")
@Validated
public class AgentController {

    @Autowired
    private AgentService agentService;

    /**
     * 创建Agent
     */
    @PostMapping
    @Operation(summary = "创建Agent", description = "创建新的Agent")
    public ResponseEntity<ApiResponse<AgentDto>> createAgent(
            @Parameter(description = "Agent信息") @Valid @RequestBody AgentDto agentDto) {
        try {
            log.info("创建Agent请求: {}", agentDto.getName());
            AgentDto createdAgent = agentService.createAgent(agentDto);
            return ResponseEntity.ok(ApiResponse.success("Agent创建成功", createdAgent));
        } catch (Exception e) {
            log.error("创建Agent失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 根据ID获取Agent
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取Agent", description = "根据ID获取Agent详细信息")
    public ResponseEntity<ApiResponse<AgentDto>> getAgentById(
            @Parameter(description = "Agent ID") @PathVariable Long id) {
        try {
            log.info("获取Agent请求，ID: {}", id);
            AgentDto agent = agentService.getAgentById(id);
            return ResponseEntity.ok(ApiResponse.success(agent));
        } catch (Exception e) {
            log.error("获取Agent失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 根据名称获取Agent
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "根据名称获取Agent", description = "根据名称获取Agent详细信息")
    public ResponseEntity<ApiResponse<AgentDto>> getAgentByName(
            @Parameter(description = "Agent名称") @PathVariable String name) {
        try {
            log.info("根据名称获取Agent请求: {}", name);
            AgentDto agent = agentService.getAgentByName(name);
            return ResponseEntity.ok(ApiResponse.success(agent));
        } catch (Exception e) {
            log.error("根据名称获取Agent失败，名称: {}, 错误: {}", name, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取所有Agent
     */
    @GetMapping
    @Operation(summary = "获取所有Agent", description = "获取所有Agent列表")
    public ResponseEntity<ApiResponse<List<AgentDto>>> getAllAgents() {
        try {
            log.info("获取所有Agent请求");
            List<AgentDto> agents = agentService.getAllAgents();
            return ResponseEntity.ok(ApiResponse.success(agents));
        } catch (Exception e) {
            log.error("获取所有Agent失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 根据类型获取Agent列表
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "根据类型获取Agent列表", description = "根据类型获取Agent列表")
    public ResponseEntity<ApiResponse<List<AgentDto>>> getAgentsByType(
            @Parameter(description = "Agent类型") @PathVariable String type) {
        try {
            log.info("根据类型获取Agent列表请求: {}", type);
            List<AgentDto> agents = agentService.getAgentsByType(type);
            return ResponseEntity.ok(ApiResponse.success(agents));
        } catch (Exception e) {
            log.error("根据类型获取Agent列表失败，类型: {}, 错误: {}", type, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 根据状态获取Agent列表
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态获取Agent列表", description = "根据状态获取Agent列表")
    public ResponseEntity<ApiResponse<List<AgentDto>>> getAgentsByStatus(
            @Parameter(description = "Agent状态") @PathVariable String status) {
        try {
            log.info("根据状态获取Agent列表请求: {}", status);
            List<AgentDto> agents = agentService.getAgentsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(agents));
        } catch (Exception e) {
            log.error("根据状态获取Agent列表失败，状态: {}, 错误: {}", status, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 根据名称搜索Agent
     */
    @GetMapping("/search")
    @Operation(summary = "搜索Agent", description = "根据名称模糊搜索Agent")
    public ResponseEntity<ApiResponse<List<AgentDto>>> searchAgents(
            @Parameter(description = "搜索关键词") @RequestParam String name) {
        try {
            log.info("搜索Agent请求: {}", name);
            List<AgentDto> agents = agentService.searchAgentsByName(name);
            return ResponseEntity.ok(ApiResponse.success(agents));
        } catch (Exception e) {
            log.error("搜索Agent失败，关键词: {}, 错误: {}", name, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 更新Agent
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新Agent", description = "更新Agent信息")
    public ResponseEntity<ApiResponse<AgentDto>> updateAgent(
            @Parameter(description = "Agent ID") @PathVariable Long id,
            @Parameter(description = "Agent信息") @Valid @RequestBody AgentDto agentDto) {
        try {
            log.info("更新Agent请求，ID: {}", id);
            AgentDto updatedAgent = agentService.updateAgent(id, agentDto);
            return ResponseEntity.ok(ApiResponse.success("Agent更新成功", updatedAgent));
        } catch (Exception e) {
            log.error("更新Agent失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 删除Agent
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除Agent", description = "删除Agent（逻辑删除）")
    public ResponseEntity<ApiResponse<Void>> deleteAgent(
            @Parameter(description = "Agent ID") @PathVariable Long id) {
        try {
            log.info("删除Agent请求，ID: {}", id);
            agentService.deleteAgent(id);
            return ResponseEntity.ok(ApiResponse.success("Agent删除成功", null));
        } catch (Exception e) {
            log.error("删除Agent失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 激活Agent
     */
    @PutMapping("/{id}/activate")
    @Operation(summary = "激活Agent", description = "激活Agent")
    public ResponseEntity<ApiResponse<AgentDto>> activateAgent(
            @Parameter(description = "Agent ID") @PathVariable Long id) {
        try {
            log.info("激活Agent请求，ID: {}", id);
            AgentDto activatedAgent = agentService.activateAgent(id);
            return ResponseEntity.ok(ApiResponse.success("Agent激活成功", activatedAgent));
        } catch (Exception e) {
            log.error("激活Agent失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 停用Agent
     */
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "停用Agent", description = "停用Agent")
    public ResponseEntity<ApiResponse<AgentDto>> deactivateAgent(
            @Parameter(description = "Agent ID") @PathVariable Long id) {
        try {
            log.info("停用Agent请求，ID: {}", id);
            AgentDto deactivatedAgent = agentService.deactivateAgent(id);
            return ResponseEntity.ok(ApiResponse.success("Agent停用成功", deactivatedAgent));
        } catch (Exception e) {
            log.error("停用Agent失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 统计指定类型的Agent数量
     */
    @GetMapping("/count/type/{type}")
    @Operation(summary = "统计指定类型的Agent数量", description = "统计指定类型的Agent数量")
    public ResponseEntity<ApiResponse<Long>> countAgentsByType(
            @Parameter(description = "Agent类型") @PathVariable String type) {
        try {
            log.info("统计指定类型的Agent数量请求: {}", type);
            long count = agentService.countAgentsByType(type);
            return ResponseEntity.ok(ApiResponse.success(count));
        } catch (Exception e) {
            log.error("统计指定类型的Agent数量失败，类型: {}, 错误: {}", type, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 统计指定状态的Agent数量
     */
    @GetMapping("/count/status/{status}")
    @Operation(summary = "统计指定状态的Agent数量", description = "统计指定状态的Agent数量")
    public ResponseEntity<ApiResponse<Long>> countAgentsByStatus(
            @Parameter(description = "Agent状态") @PathVariable String status) {
        try {
            log.info("统计指定状态的Agent数量请求: {}", status);
            long count = agentService.countAgentsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(count));
        } catch (Exception e) {
            log.error("统计指定状态的Agent数量失败，状态: {}, 错误: {}", status, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
