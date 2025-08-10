package com.agent.learning.repository;

import com.agent.learning.entity.Agent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Agent数据访问层
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
@Repository
@Mapper
public interface AgentRepository extends JpaRepository<Agent, Long> {

    /**
     * 根据名称查找Agent
     * 
     * @param name Agent名称
     * @return Agent对象
     */
    Optional<Agent> findByNameAndIsDeletedFalse(String name);

    /**
     * 根据类型查找Agent列表
     * 
     * @param type Agent类型
     * @return Agent列表
     */
    List<Agent> findByTypeAndIsDeletedFalse(String type);

    /**
     * 根据状态查找Agent列表
     * 
     * @param status Agent状态
     * @return Agent列表
     */
    List<Agent> findByStatusAndIsDeletedFalse(String status);

    /**
     * 根据名称和类型查找Agent
     * 
     * @param name Agent名称
     * @param type Agent类型
     * @return Agent对象
     */
    Optional<Agent> findByNameAndTypeAndIsDeletedFalse(String name, String type);

    /**
     * 查找所有未删除的Agent
     * 
     * @return Agent列表
     */
    @Query("SELECT a FROM Agent a WHERE a.isDeleted = false ORDER BY a.createdAt DESC")
    List<Agent> findAllActive();

    /**
     * 根据名称模糊查询Agent列表
     * 
     * @param name Agent名称（模糊匹配）
     * @return Agent列表
     */
    @Query("SELECT a FROM Agent a WHERE a.isDeleted = false AND a.name LIKE %:name% ORDER BY a.createdAt DESC")
    List<Agent> findByNameContainingAndIsDeletedFalse(@Param("name") String name);

    /**
     * 根据类型和状态查找Agent列表
     * 
     * @param type   Agent类型
     * @param status Agent状态
     * @return Agent列表
     */
    List<Agent> findByTypeAndStatusAndIsDeletedFalse(String type, String status);

    /**
     * 统计指定类型的Agent数量
     * 
     * @param type Agent类型
     * @return Agent数量
     */
    long countByTypeAndIsDeletedFalse(String type);

    /**
     * 统计指定状态的Agent数量
     * 
     * @param status Agent状态
     * @return Agent数量
     */
    long countByStatusAndIsDeletedFalse(String status);
}
