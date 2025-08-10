package com.agent.learning.service.impl;

import com.agent.learning.dto.AgentDto;
import com.agent.learning.entity.Agent;
import com.agent.learning.exception.BusinessException;
import com.agent.learning.repository.AgentRepository;
import com.agent.learning.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Agent服务实现类
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
@Slf4j
@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public AgentDto createAgent(AgentDto agentDto) {
        log.info("创建Agent: {}", agentDto.getName());

        // 检查Agent名称是否已存在
        if (agentRepository.findByNameAndIsDeletedFalse(agentDto.getName()).isPresent()) {
            throw new BusinessException("Agent名称已存在: " + agentDto.getName());
        }

        // 创建Agent实体
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentDto, agent);
        agent.setStatus("ACTIVE");
        agent.setIsDeleted(false);

        // 保存Agent
        Agent savedAgent = agentRepository.save(agent);

        // 转换为DTO返回
        AgentDto result = new AgentDto();
        BeanUtils.copyProperties(savedAgent, result);

        log.info("Agent创建成功，ID: {}", savedAgent.getId());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public AgentDto getAgentById(Long id) {
        log.info("根据ID获取Agent: {}", id);

        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agent不存在，ID: " + id));

        if (agent.getIsDeleted()) {
            throw new BusinessException("Agent已删除，ID: " + id);
        }

        AgentDto agentDto = new AgentDto();
        BeanUtils.copyProperties(agent, agentDto);
        return agentDto;
    }

    @Override
    @Transactional(readOnly = true)
    public AgentDto getAgentByName(String name) {
        log.info("根据名称获取Agent: {}", name);

        Agent agent = agentRepository.findByNameAndIsDeletedFalse(name)
                .orElseThrow(() -> new BusinessException("Agent不存在，名称: " + name));

        AgentDto agentDto = new AgentDto();
        BeanUtils.copyProperties(agent, agentDto);
        return agentDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentDto> getAllAgents() {
        log.info("获取所有Agent");

        List<Agent> agents = agentRepository.findAllActive();
        return agents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentDto> getAgentsByType(String type) {
        log.info("根据类型获取Agent列表: {}", type);

        List<Agent> agents = agentRepository.findByTypeAndIsDeletedFalse(type);
        return agents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentDto> getAgentsByStatus(String status) {
        log.info("根据状态获取Agent列表: {}", status);

        List<Agent> agents = agentRepository.findByStatusAndIsDeletedFalse(status);
        return agents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentDto> searchAgentsByName(String name) {
        log.info("根据名称搜索Agent: {}", name);

        List<Agent> agents = agentRepository.findByNameContainingAndIsDeletedFalse(name);
        return agents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AgentDto updateAgent(Long id, AgentDto agentDto) {
        log.info("更新Agent，ID: {}", id);

        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agent不存在，ID: " + id));

        if (existingAgent.getIsDeleted()) {
            throw new BusinessException("Agent已删除，ID: " + id);
        }

        // 检查名称是否重复（排除当前Agent）
        if (!existingAgent.getName().equals(agentDto.getName())) {
            agentRepository.findByNameAndIsDeletedFalse(agentDto.getName())
                    .ifPresent(agent -> {
                        throw new BusinessException("Agent名称已存在: " + agentDto.getName());
                    });
        }

        // 更新Agent属性
        existingAgent.setName(agentDto.getName());
        existingAgent.setDescription(agentDto.getDescription());
        existingAgent.setType(agentDto.getType());
        existingAgent.setStatus(agentDto.getStatus());
        existingAgent.setConfig(agentDto.getConfig());
        existingAgent.setUpdatedBy(agentDto.getUpdatedBy());

        // 保存更新
        Agent updatedAgent = agentRepository.save(existingAgent);

        AgentDto result = new AgentDto();
        BeanUtils.copyProperties(updatedAgent, result);

        log.info("Agent更新成功，ID: {}", id);
        return result;
    }

    @Override
    public void deleteAgent(Long id) {
        log.info("删除Agent，ID: {}", id);

        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agent不存在，ID: " + id));

        if (agent.getIsDeleted()) {
            throw new BusinessException("Agent已删除，ID: " + id);
        }

        // 逻辑删除
        agent.setIsDeleted(true);
        agentRepository.save(agent);

        log.info("Agent删除成功，ID: {}", id);
    }

    @Override
    public AgentDto activateAgent(Long id) {
        log.info("激活Agent，ID: {}", id);

        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agent不存在，ID: " + id));

        if (agent.getIsDeleted()) {
            throw new BusinessException("Agent已删除，ID: " + id);
        }

        agent.setStatus("ACTIVE");
        Agent updatedAgent = agentRepository.save(agent);

        AgentDto result = new AgentDto();
        BeanUtils.copyProperties(updatedAgent, result);

        log.info("Agent激活成功，ID: {}", id);
        return result;
    }

    @Override
    public AgentDto deactivateAgent(Long id) {
        log.info("停用Agent，ID: {}", id);

        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agent不存在，ID: " + id));

        if (agent.getIsDeleted()) {
            throw new BusinessException("Agent已删除，ID: " + id);
        }

        agent.setStatus("INACTIVE");
        Agent updatedAgent = agentRepository.save(agent);

        AgentDto result = new AgentDto();
        BeanUtils.copyProperties(updatedAgent, result);

        log.info("Agent停用成功，ID: {}", id);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public long countAgentsByType(String type) {
        log.info("统计指定类型的Agent数量: {}", type);
        return agentRepository.countByTypeAndIsDeletedFalse(type);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAgentsByStatus(String status) {
        log.info("统计指定状态的Agent数量: {}", status);
        return agentRepository.countByStatusAndIsDeletedFalse(status);
    }

    /**
     * 将Agent实体转换为DTO
     * 
     * @param agent Agent实体
     * @return AgentDto
     */
    private AgentDto convertToDto(Agent agent) {
        AgentDto agentDto = new AgentDto();
        BeanUtils.copyProperties(agent, agentDto);
        return agentDto;
    }
}
