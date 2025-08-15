# AI Agent with Langchain

基于Langchain构建的智能AI Agent系统，实现了感知-决策-执行的完整工作流程。

## 🚀 功能特性

- 🤖 **智能对话**：基于GPT-4的自然语言理解
- 🛠️ **工具集成**：支持多种外部工具调用
- 📚 **知识库**：向量数据库存储和检索
- 🔄 **状态管理**：Redis缓存会话状态
- 📊 **监控系统**：性能监控和错误追踪

## 🏗️ 技术架构

### 核心组件
- **感知层**：输入解析、意图识别、上下文管理
- **决策层**：LLM推理、工具选择、执行计划
- **执行层**：工具管理、结果处理、错误恢复
- **输出层**：响应生成、状态更新、反馈收集

### 技术栈
- **框架**: Langchain 0.1.0
- **LLM**: OpenAI GPT-4
- **向量数据库**: ChromaDB
- **缓存**: Redis
- **API**: FastAPI
- **部署**: Docker + Kubernetes

## 📦 快速开始

### 环境要求
- Python 3.8+
- Redis 7.x+
- 8GB+ RAM
- 稳定的网络连接

### 1. 环境准备
```bash
# 克隆项目
git clone <your-repo-url>
cd Agent-learning

# 创建虚拟环境
python -m venv venv

# 激活虚拟环境
# Windows
venv\Scripts\activate
# Linux/Mac
source venv/bin/activate

# 安装依赖
pip install -r requirements.txt
```

### 2. 配置环境变量
```bash
# 复制环境变量模板
cp env_example.txt .env

# 编辑.env文件，填入你的API密钥
OPENAI_API_KEY=your_openai_api_key_here
REDIS_URL=redis://localhost:6379
```

### 3. 启动服务
```bash
# 启动Redis（确保已安装）
redis-server

# 启动应用
python main.py
```

### 4. 访问应用
- API文档: http://localhost:8000/docs
- 健康检查: http://localhost:8000/health
- 应用主页: http://localhost:8000/

## 📁 项目结构

```
Agent-learning/
├── agent/                 # Agent核心模块
│   ├── __init__.py
│   ├── core.py           # 核心Agent类
│   ├── tools.py          # 工具定义
│   └── memory.py         # 记忆管理
├── knowledge/            # 知识库模块
│   ├── __init__.py
│   ├── vector_store.py   # 向量数据库
│   └── embeddings.py     # 嵌入模型
├── api/                  # API接口
│   ├── __init__.py
│   ├── routes.py         # 路由定义
│   └── models.py         # 数据模型
├── utils/                # 工具函数
│   ├── __init__.py
│   ├── config.py         # 配置管理
│   └── logger.py         # 日志工具
├── tests/                # 测试文件
├── docs/                 # 文档
├── diagrams/             # 架构图
├── main.py               # 应用入口
├── requirements.txt      # 依赖列表
├── env_example.txt       # 环境变量模板
└── README.md            # 项目说明
```

## 🔧 开发指南

### 添加新工具
1. 在`agent/tools.py`中定义新工具类
2. 实现`_run`方法
3. 在Agent中注册工具

### 扩展知识库
1. 准备文档数据
2. 使用`knowledge/vector_store.py`进行向量化
3. 配置检索策略

### 自定义Agent行为
1. 修改`agent/core.py`中的处理逻辑
2. 调整提示词模板
3. 优化决策策略

## 🧪 测试

### 运行测试
```bash
# 运行所有测试
pytest

# 运行特定测试
pytest tests/test_agent.py

# 生成覆盖率报告
pytest --cov=agent --cov-report=html
```

### 代码质量检查
```bash
# 代码格式化
black .

# 代码检查
flake8 .

# 类型检查
mypy .
```

## 🚀 部署

### Docker部署
```bash
# 构建镜像
docker build -t ai-agent .

# 运行容器
docker run -p 8000:8000 ai-agent
```

### Docker Compose
```bash
# 启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f
```

## 📊 监控和日志

### 性能指标
- 响应时间、成功率、错误率
- CPU、内存、API调用次数
- 用户行为、工具使用频率

### 日志管理
- 结构化日志记录
- 日志轮转和归档
- 错误追踪和告警

## 🤝 贡献指南

1. Fork项目
2. 创建功能分支 (`git checkout -b feature/NewFeature`)
3. 提交更改 (`git commit -m 'Add NewFeature'`)
4. 推送到分支 (`git push origin feature/NewFeature`)
5. 创建Pull Request

## 📝 开发规范

### 代码规范
- 遵循PEP 8代码风格
- 使用类型注解
- 完整的文档字符串
- 单元测试覆盖

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

## 📚 参考资料

### 官方文档
- [Langchain官方文档](https://python.langchain.com/)
- [OpenAI API文档](https://platform.openai.com/docs)
- [FastAPI文档](https://fastapi.tiangolo.com/)
- [Redis文档](https://redis.io/documentation)

### 开发文档
- [AI Agent开发指导文档](AI_Agent_Development_Guide.md)
- [项目架构图](diagrams/)

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- 项目维护者: [您的姓名]
- 邮箱: [您的邮箱]
- 项目地址: https://github.com/your-username/Agent-learning

---

**注意**: 这是一个持续开发中的项目，功能会不断更新和完善。欢迎提出建议和贡献代码。