#!/usr/bin/env python3
"""
AI Agent 应用主入口
"""
import os
import sys
from pathlib import Path

# 添加项目根目录到Python路径
project_root = Path(__file__).parent
sys.path.insert(0, str(project_root))

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import uvicorn

from utils.config import config
from utils.logger import logger
from api.routes import router

def create_app() -> FastAPI:
    """创建FastAPI应用"""
    app = FastAPI(
        title="AI Agent API",
        description="基于Langchain的智能AI Agent系统",
        version="1.0.0",
        docs_url="/docs",
        redoc_url="/redoc"
    )
    
    # 配置CORS
    app.add_middleware(
        CORSMiddleware,
        allow_origins=["*"],
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )
    
    # 注册路由
    app.include_router(router, prefix="/api/v1")
    
    @app.on_event("startup")
    async def startup_event():
        """应用启动事件"""
        logger.info("AI Agent 应用启动中...")
        try:
            # 验证配置
            config.validate()
            logger.info("配置验证通过")
            
            # 初始化Agent
            from agent.core import agent
            health_check = agent.health_check()
            logger.info(f"Agent健康检查: {health_check['status']}")
            
        except Exception as e:
            logger.error(f"应用启动失败: {str(e)}")
            raise
    
    @app.on_event("shutdown")
    async def shutdown_event():
        """应用关闭事件"""
        logger.info("AI Agent 应用关闭中...")
    
    @app.get("/")
    async def root():
        """根路径"""
        return {
            "message": "AI Agent API",
            "version": "1.0.0",
            "status": "running"
        }
    
    @app.get("/health")
    async def health_check():
        """健康检查"""
        try:
            from agent.core import agent
            return agent.health_check()
        except Exception as e:
            logger.error(f"健康检查失败: {str(e)}")
            return {
                "status": "unhealthy",
                "error": str(e)
            }
    
    return app

def main():
    """主函数"""
    # 创建应用
    app = create_app()
    
    # 启动服务器
    uvicorn.run(
        app,
        host=config.APP_HOST,
        port=config.APP_PORT,
        reload=config.DEBUG,
        log_level="info"
    )

if __name__ == "__main__":
    main()
