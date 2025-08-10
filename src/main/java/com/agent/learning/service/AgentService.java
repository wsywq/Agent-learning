package com.agent.learning.service;

import com.agent.learning.dto.AgentDto;

import java.util.List;

/**
 * Agent服务接口
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
public interface AgentService {

    /**
     * 创建Agent
     * 
     * @param agentDto Agent数据传输对象
     * @return 创建的Agent
     */
    AgentDto createAgent(AgentDto agentDto);

    /**
     * 根据ID获取Agent
     * 
     * @param id Agent ID
     * @return Agent数据传输对象
     */
    AgentDto getAgentById(Long id);

    /**
     * 根据名称获取Agent
     * 
     * @param name Agent名称
     * @return Agent数据传输对象
     */
    AgentDto getAgentByName(String name);

    /**
     * 获取所有Agent
     * 
     * @return Agent列表
     */
    List<AgentDto> getAllAgents();

    /**
     * 根据类型获取Agent列表
     * 
     * @param type Agent类型
     * @return Agent列表
     */
    List<AgentDto> getAgentsByType(String type);

    /**
     * 根据状态获取Agent列表
     * 
     * @param status Agent状态
     * @return Agent列表
     */
    List<AgentDto> getAgentsByStatus(String status);

    /**
     * 根据名称模糊查询Agent列表
     * 
     * @param name Agent名称（模糊匹配）
     * @return Agent列表
     */
    List<AgentDto> searchAgentsByName(String name);

    /**
     * 更新Agent
     * 
     * @param id       Agent ID
     * @param agentDto Agent数据传输对象
     * @return 更新后的Agent
     */
    AgentDto updateAgent(Long id, AgentDto agentDto);

    /**
     * 删除Agent（逻辑删除）
     * 
     * @param id Agent ID
     */
    void deleteAgent(Long id);

    /**
     * 激活Agent
     * 
     * @param id Agent ID
     * @return 更新后的Agent
     */
    AgentDto activateAgent(Long id);

    /**
     * 停用Agent
     * 
     * @param id Agent ID
     * @return 更新后的Agent
     */
    AgentDto deactivateAgent(Long id);

    /**
     * 统计指定类型的Agent数量
     * 
     * @param type Agent类型
     * @return Agent数量
     */
    long countAgentsByType(String type);

    /**
     * 统计指定状态的Agent数量
     * 
     * @param status Agent状态
     * @return Agent数量
     */
    long countAgentsByStatus(String status);
}
