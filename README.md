# 档案数字化管理系统

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue.svg)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue.svg)](https://www.typescriptlang.org/)

## 📖 项目简介

档案数字化管理系统是一个专门用于档案整理、数字化加工和管理的现代化后台管理系统。系统采用前后端分离架构，提供完整的档案管理流程，包括档案接收、整理、扫描、图像处理、质量检查、数据导出等功能。

## ✨ 功能特性

### 🗂️ 档案管理
- **档案接收登记**: 档案接收、状态跟踪、接收确认
- **档案整理**: 档案分类整理、排序、完整性检查
- **档案编目**: 档案信息录入、目录编制、元数据管理
- **档案状态流转**: 完整的档案处理状态管理

### 📷 图像处理
- **批量扫描**: 支持批量档案扫描，进度监控
- **图像优化**: 自动图像清晰度优化、对比度调整、去噪处理
- **图像质检**: 图像质量评估、质量指标检测、质量报告生成
- **图像补扫**: 不合格图像重新扫描、补扫任务管理

### 🔍 质量控制
- **数据校对**: 扫描图像与数据信息链接准确性检查
- **终检**: 最终质量检查、验收标准验证
- **质量跟踪**: 完整的质量检查流程和记录

### 📊 数据管理
- **数据对接**: 目录数据与档案图像一一对应关系建立
- **数据导出**: 支持多种格式的数据导出
- **数据备份**: 完善的数据备份和恢复机制

### 🔐 系统安全
- **用户认证**: JWT令牌认证机制
- **权限管理**: 基于角色的访问控制
- **操作日志**: 完整的操作审计日志
- **数据加密**: 敏感数据加密存储

## 🏗️ 技术架构

### 前端技术栈
- **框架**: React 18 + TypeScript
- **UI组件库**: Ant Design Pro
- **状态管理**: Redux Toolkit
- **路由**: React Router v6
- **HTTP客户端**: Axios
- **构建工具**: Vite
- **代码规范**: ESLint + Prettier

### 后端技术栈
- **框架**: Spring Boot 3.x
- **语言**: Java 17
- **微服务**: Spring Cloud
- **安全**: Spring Security + JWT
- **数据库**: MySQL 8.0 + Redis 7.x
- **文件存储**: MinIO / 阿里云OSS
- **搜索引擎**: Elasticsearch 8.x
- **消息队列**: RabbitMQ
- **容器化**: Docker + Docker Compose

### 基础设施
- **部署**: Kubernetes / Docker Swarm
- **监控**: Prometheus + Grafana
- **日志**: ELK Stack
- **CI/CD**: GitHub Actions

## 🚀 快速开始

### 环境要求
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- MySQL 8.0+
- Redis 7.x+

### 本地开发环境搭建

1. **克隆项目**
```bash
git clone https://github.com/wsywq/digital-archive-management.git
cd digital-archive-management
```

2. **启动基础设施服务**
```bash
docker-compose up -d mysql redis minio elasticsearch
```

3. **启动后端服务**
```bash
# 进入后端目录
cd archive-service
./mvnw spring-boot:run
```

4. **启动前端应用**
```bash
# 进入前端目录
cd frontend
npm install
npm run dev
```

5. **访问应用**
- 前端应用: http://localhost:3000
- 后端API: http://localhost:8080
- MinIO控制台: http://localhost:9001
- Elasticsearch: http://localhost:9200

### Docker部署

```bash
# 构建并启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f
```

## 📁 项目结构

```
digital-archive-management/
├── archive-service/              # 档案管理服务
│   ├── src/main/java/
│   │   ├── controller/          # 控制器层
│   │   ├── service/            # 服务层
│   │   ├── repository/         # 数据访问层
│   │   ├── entity/             # 实体类
│   │   └── config/             # 配置类
│   └── src/main/resources/
│       ├── application.yml      # 应用配置
│       └── db/migration/       # 数据库迁移脚本
├── image-processing-service/     # 图像处理服务
├── file-storage-service/        # 文件存储服务
├── quality-check-service/       # 质量检查服务
├── export-service/              # 数据导出服务
├── user-service/                # 用户管理服务
├── gateway-service/             # API网关服务
├── frontend/                    # 前端应用
│   ├── src/
│   │   ├── components/         # 组件
│   │   ├── pages/             # 页面
│   │   ├── services/          # API服务
│   │   └── utils/             # 工具函数
│   └── public/                # 静态资源
├── docs/                       # 文档
│   ├── 需求文档.md
│   └── 技术选型和解决方案.md
├── docker-compose.yml          # Docker编排文件
└── README.md                   # 项目说明
```

## 🔧 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/archive_management
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### MinIO配置
```yaml
minio:
  endpoint: http://localhost:9000
  accessKey: minioadmin
  secretKey: minioadmin
  bucket: archive-images
```

### JWT配置
```yaml
jwt:
  secret: your-jwt-secret-key
  expiration: 86400000  # 24小时
```

## 📊 监控和日志

### 应用监控
- **Prometheus**: 指标收集
- **Grafana**: 监控面板
- **健康检查**: `/actuator/health`

### 日志管理
- **日志级别**: INFO, WARN, ERROR
- **日志格式**: JSON格式
- **日志轮转**: 按日期轮转

## 🧪 测试

### 单元测试
```bash
# 运行单元测试
./mvnw test

# 生成测试报告
./mvnw jacoco:report
```

### 集成测试
```bash
# 运行集成测试
./mvnw verify
```

### 前端测试
```bash
cd frontend
npm test
```

## 📈 性能优化

### 缓存策略
- **Redis缓存**: 热点数据缓存
- **本地缓存**: Caffeine本地缓存
- **CDN加速**: 静态资源CDN

### 异步处理
- **消息队列**: RabbitMQ异步处理
- **线程池**: 自定义线程池配置
- **批量处理**: 批量操作优化

## 🔒 安全措施

### 身份认证
- JWT令牌认证
- 密码加密存储
- 会话管理

### 权限控制
- 基于角色的访问控制(RBAC)
- API权限验证
- 数据权限控制

### 数据安全
- 敏感数据加密
- SQL注入防护
- XSS攻击防护

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📝 开发规范

### 代码规范
- 遵循阿里巴巴Java开发手册
- 使用ESLint和Prettier进行代码格式化
- 提交前进行代码审查

### 提交规范
```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 代码重构
test: 测试相关
chore: 构建过程或辅助工具的变动
```

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- 项目维护者: [您的姓名]
- 邮箱: [您的邮箱]
- 项目地址: https://github.com/wsywq/digital-archive-management

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者和用户。

---

**注意**: 这是一个正在开发中的项目，功能可能会有所变化。请关注项目的更新日志。