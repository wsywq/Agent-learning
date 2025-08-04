# 档案数字化管理系统

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/your-username/archives-digital-management-system.svg)](https://github.com/your-username/archives-digital-management-system/stargazers)

一个现代化的档案数字化管理系统，提供完整的档案数字化处理、存储、管理和检索解决方案。

## 📖 项目简介

档案数字化管理系统是一个基于现代Web技术的后台管理系统，旨在为机构提供完整的档案数字化处理、存储、管理和检索解决方案。系统严格遵循《文书档案目录数据交换格式与著录细则》(DB35/T161-2002) 等相关标准，确保档案管理的规范性和标准化。

### 🎯 核心特性

- **📁 档案整理及数字化加工**：完整的档案数字化处理流程
- **🔍 智能检索系统**：基于Elasticsearch的全文检索
- **📊 元数据管理**：标准化的档案元数据管理
- **🖼️ 图像处理**：自动图像优化和OCR文字识别
- **👥 权限管理**：基于RBAC的细粒度权限控制
- **📱 响应式设计**：支持多设备访问
- **🌐 国际化支持**：多语言界面支持
- **🔒 安全保障**：数据加密和安全审计

## 🏗️ 技术架构

### 前端技术栈
- **Vue.js 3** - 渐进式JavaScript框架
- **TypeScript** - 类型安全的JavaScript超集
- **Element Plus** - Vue 3桌面端组件库
- **Pinia** - Vue状态管理库
- **Vite** - 新一代前端构建工具

### 后端技术栈
- **Spring Boot** - Java企业级应用框架
- **Spring Security** - 认证和授权框架
- **Spring Data JPA** - 数据访问层框架
- **PostgreSQL** - 企业级关系数据库
- **Redis** - 内存数据库，用于缓存
- **Elasticsearch** - 全文搜索引擎

### 基础设施
- **Docker** - 容器化部署
- **MinIO** - 对象存储服务
- **Nginx** - Web服务器和反向代理
- **Jenkins** - CI/CD自动化

## 🚀 快速开始

### 环境要求

- Node.js 16.0+
- Java 17+
- Docker & Docker Compose
- PostgreSQL 13+
- Redis 6.0+

### 使用Docker快速部署

1. **克隆项目**
```bash
git clone https://github.com/your-username/archives-digital-management-system.git
cd archives-digital-management-system
```

2. **一键启动（推荐）**
```bash
./scripts/quick-start.sh
```

3. **手动启动**
```bash
docker-compose up -d
```

4. **访问应用**
- 前端界面：http://localhost:3000
- 后端API：http://localhost:8080
- API文档：http://localhost:8080/swagger-ui.html
- MinIO控制台：http://localhost:9001 (minioadmin/minioadmin123)
- Kibana：http://localhost:5601
- Prometheus：http://localhost:9090
- Grafana：http://localhost:3001 (admin/admin123)

5. **默认登录账号**
- 用户名：admin
- 密码：admin123

### 本地开发环境

#### 前端开发
```bash
cd frontend
npm install
npm run dev
```

#### 后端开发
```bash
cd backend
./mvnw spring-boot:run
```

## 📚 文档

- [需求文档](./需求文档.md) - 详细的功能需求和业务流程
- [技术选型和解决方案](./技术选型和解决方案.md) - 技术架构和实现方案
- [API文档](http://localhost:8080/swagger-ui.html) - 后端API接口文档
- [部署指南](./docs/deployment.md) - 生产环境部署指南
- [开发规范](./docs/development.md) - 代码开发规范

## 🗂️ 项目结构

```
archives-digital-management-system/
├── frontend/                 # 前端Vue.js应用
│   ├── src/
│   │   ├── components/      # 公共组件
│   │   ├── views/          # 页面组件
│   │   ├── store/          # 状态管理
│   │   ├── router/         # 路由配置
│   │   └── utils/          # 工具函数
│   └── package.json
├── backend/                 # 后端Spring Boot应用
│   ├── src/main/java/
│   │   ├── controller/     # 控制器层
│   │   ├── service/        # 业务逻辑层
│   │   ├── repository/     # 数据访问层
│   │   ├── entity/         # 实体类
│   │   └── config/         # 配置类
│   └── pom.xml
├── docker/                  # Docker配置文件
├── docs/                    # 项目文档
├── scripts/                 # 部署脚本
├── docker-compose.yml       # Docker Compose配置
└── README.md               # 项目说明
```

## 🔧 核心功能模块

### 1. 档案管理
- 档案信息录入和编辑
- 档案分类和层级管理
- 档案状态跟踪
- 批量操作支持

### 2. 数字化处理
- 文件上传和格式转换
- 图像质量检测和优化
- OCR文字识别
- 缩略图生成

### 3. 检索系统
- 全文检索
- 高级搜索
- 分类浏览
- 搜索结果高亮

### 4. 权限管理
- 用户管理
- 角色权限配置
- 操作日志审计
- 数据权限控制

### 5. 系统管理
- 系统配置
- 数据字典
- 报表统计
- 监控告警

## 📊 功能截图

### 主界面
![主界面](./docs/images/dashboard.png)

### 档案管理
![档案管理](./docs/images/archives-management.png)

### 检索界面
![检索界面](./docs/images/search.png)

## 🤝 贡献指南

我们欢迎所有形式的贡献，包括但不限于：

- 🐛 Bug报告
- 💡 功能建议
- 📝 文档改进
- 🔧 代码贡献

### 提交流程

1. Fork本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

### 代码规范

- 前端：遵循Vue.js官方风格指南
- 后端：遵循Google Java代码规范
- 提交信息：使用Conventional Commits规范

## 📄 开源协议

本项目采用 [MIT](LICENSE) 开源协议。

## 🙏 致谢

本项目参考了以下开源项目：

- [RODA](https://github.com/keeps/roda) - 数字档案管理系统
- [VITAM](https://github.com/ProgrammeVitam/vitam) - 法国政府数字档案系统
- [Archipelago](https://github.com/esmero/archipelago-documentation) - 数字对象仓库

## 📞 联系我们

- 项目主页：https://github.com/your-username/archives-digital-management-system
- 问题反馈：https://github.com/your-username/archives-digital-management-system/issues
- 邮箱：your-email@example.com

## 🔖 版本历史

### v1.0.0 (2024-12-XX)
- 🎉 初始版本发布
- ✨ 档案管理核心功能
- 🔍 全文检索系统
- 👥 权限管理系统
- 📱 响应式用户界面

---

⭐ 如果这个项目对您有帮助，请给我们一个Star！