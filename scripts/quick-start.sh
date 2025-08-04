#!/bin/bash

# 档案数字化管理系统快速启动脚本
# 作者：Archives Digital Management System Team
# 版本：v1.0
# 创建日期：2024-12-XX

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 日志函数
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

log_step() {
    echo -e "${BLUE}[STEP]${NC} $1"
}

# 检查Docker是否安装
check_docker() {
    log_step "检查Docker环境..."
    
    if ! command -v docker &> /dev/null; then
        log_error "Docker未安装，请先安装Docker"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        log_error "Docker Compose未安装，请先安装Docker Compose"
        exit 1
    fi
    
    if ! docker info >/dev/null 2>&1; then
        log_error "Docker服务未启动，请启动Docker服务"
        exit 1
    fi
    
    log_info "Docker环境检查通过"
}

# 检查端口占用
check_ports() {
    log_step "检查端口占用情况..."
    
    ports=(3000 8080 5432 6379 9200 9000 9001 5601 9090 3001)
    occupied_ports=()
    
    for port in "${ports[@]}"; do
        if lsof -i :$port >/dev/null 2>&1; then
            occupied_ports+=($port)
        fi
    done
    
    if [ ${#occupied_ports[@]} -gt 0 ]; then
        log_warn "以下端口被占用: ${occupied_ports[*]}"
        read -p "是否继续启动？这可能导致服务冲突 (y/N): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            log_info "用户取消启动"
            exit 0
        fi
    else
        log_info "端口检查通过"
    fi
}

# 创建必要的目录
create_directories() {
    log_step "创建必要的目录结构..."
    
    directories=(
        "logs"
        "data/postgres"
        "data/redis"
        "data/elasticsearch"
        "data/minio"
        "data/prometheus"
        "data/grafana"
        "frontend/dist"
        "backend/target"
    )
    
    for dir in "${directories[@]}"; do
        if [ ! -d "$dir" ]; then
            mkdir -p "$dir"
            log_info "创建目录: $dir"
        fi
    done
}

# 设置环境变量
setup_environment() {
    log_step "设置环境变量..."
    
    if [ ! -f .env ]; then
        cat > .env << EOF
# 档案数字化管理系统环境配置
# 创建时间: $(date)

# 数据库配置
POSTGRES_DB=archives
POSTGRES_USER=admin
POSTGRES_PASSWORD=password123

# Redis配置
REDIS_PASSWORD=redis123

# MinIO配置
MINIO_ROOT_USER=minioadmin
MINIO_ROOT_PASSWORD=minioadmin123

# Grafana配置
GRAFANA_ADMIN_PASSWORD=admin123

# 系统配置
SYSTEM_ENV=production
LOG_LEVEL=info
EOF
        log_info "创建环境配置文件: .env"
    else
        log_info "环境配置文件已存在"
    fi
}

# 拉取Docker镜像
pull_images() {
    log_step "拉取Docker镜像..."
    
    images=(
        "nginx:alpine"
        "openjdk:17-jdk-slim"
        "postgres:15-alpine"
        "redis:7-alpine"
        "elasticsearch:8.8.0"
        "minio/minio:latest"
        "kibana:8.8.0"
        "prom/prometheus:latest"
        "grafana/grafana:latest"
    )
    
    for image in "${images[@]}"; do
        log_info "拉取镜像: $image"
        docker pull $image
    done
}

# 启动服务
start_services() {
    log_step "启动档案数字化管理系统..."
    
    # 启动基础服务
    log_info "启动数据库和基础服务..."
    docker-compose up -d postgres redis elasticsearch minio
    
    # 等待数据库启动
    log_info "等待数据库启动..."
    sleep 30
    
    # 检查数据库连接
    while ! docker-compose exec -T postgres pg_isready -U admin -d archives; do
        log_info "等待PostgreSQL启动..."
        sleep 5
    done
    
    # 启动应用服务
    log_info "启动应用服务..."
    docker-compose up -d backend frontend
    
    # 启动监控服务
    log_info "启动监控服务..."
    docker-compose up -d kibana prometheus grafana
    
    log_info "所有服务启动完成"
}

# 等待服务就绪
wait_for_services() {
    log_step "等待服务就绪..."
    
    services=(
        "http://localhost:3000|前端应用"
        "http://localhost:8080/actuator/health|后端应用"
        "http://localhost:9200/_cluster/health|Elasticsearch"
        "http://localhost:9001|MinIO控制台"
        "http://localhost:5601|Kibana"
        "http://localhost:9090|Prometheus"
        "http://localhost:3001|Grafana"
    )
    
    for service in "${services[@]}"; do
        IFS='|' read -ra ADDR <<< "$service"
        url=${ADDR[0]}
        name=${ADDR[1]}
        
        log_info "检查$name服务..."
        timeout=60
        count=0
        
        while [ $count -lt $timeout ]; do
            if curl -f -s "$url" >/dev/null 2>&1; then
                log_info "$name服务已就绪"
                break
            fi
            
            count=$((count + 1))
            sleep 1
        done
        
        if [ $count -ge $timeout ]; then
            log_warn "$name服务启动超时，请检查日志"
        fi
    done
}

# 显示访问信息
show_access_info() {
    log_step "服务访问信息"
    
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo -e "${GREEN}档案数字化管理系统启动成功！${NC}"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo ""
    echo "🌐 应用服务:"
    echo "   前端应用:     http://localhost:3000"
    echo "   后端API:      http://localhost:8080"
    echo "   API文档:      http://localhost:8080/swagger-ui.html"
    echo ""
    echo "🗄️ 存储服务:"
    echo "   PostgreSQL:   localhost:5432 (admin/password123)"
    echo "   Redis:        localhost:6379 (password: redis123)"
    echo "   MinIO控制台:  http://localhost:9001 (minioadmin/minioadmin123)"
    echo ""
    echo "🔍 搜索与监控:"
    echo "   Elasticsearch: http://localhost:9200"
    echo "   Kibana:       http://localhost:5601"
    echo "   Prometheus:   http://localhost:9090"
    echo "   Grafana:      http://localhost:3001 (admin/admin123)"
    echo ""
    echo "👤 默认登录账号:"
    echo "   用户名: admin"
    echo "   密码:   admin123"
    echo ""
    echo "📋 常用命令:"
    echo "   查看日志:     docker-compose logs -f [服务名]"
    echo "   停止服务:     docker-compose down"
    echo "   重启服务:     docker-compose restart [服务名]"
    echo "   查看状态:     docker-compose ps"
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
}

# 显示帮助信息
show_help() {
    echo "档案数字化管理系统快速启动脚本"
    echo ""
    echo "用法: $0 [选项]"
    echo ""
    echo "选项:"
    echo "  start    启动所有服务 (默认)"
    echo "  stop     停止所有服务"
    echo "  restart  重启所有服务"
    echo "  status   查看服务状态"
    echo "  logs     查看服务日志"
    echo "  clean    清理所有数据和容器"
    echo "  help     显示此帮助信息"
    echo ""
}

# 停止服务
stop_services() {
    log_step "停止档案数字化管理系统..."
    docker-compose down
    log_info "所有服务已停止"
}

# 重启服务
restart_services() {
    log_step "重启档案数字化管理系统..."
    docker-compose restart
    log_info "所有服务已重启"
}

# 查看服务状态
show_status() {
    log_step "查看服务状态..."
    docker-compose ps
}

# 查看日志
show_logs() {
    log_step "查看服务日志..."
    docker-compose logs -f
}

# 清理环境
clean_environment() {
    log_step "清理环境..."
    
    read -p "此操作将删除所有数据和容器，确认继续? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        docker-compose down -v
        docker system prune -f
        log_info "环境清理完成"
    else
        log_info "用户取消清理操作"
    fi
}

# 主函数
main() {
    cd "$(dirname "$0")/.."
    
    case "${1:-start}" in
        start)
            log_info "开始启动档案数字化管理系统..."
            check_docker
            check_ports
            create_directories
            setup_environment
            pull_images
            start_services
            wait_for_services
            show_access_info
            ;;
        stop)
            stop_services
            ;;
        restart)
            restart_services
            ;;
        status)
            show_status
            ;;
        logs)
            show_logs
            ;;
        clean)
            clean_environment
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            log_error "未知命令: $1"
            show_help
            exit 1
            ;;
    esac
}

# 脚本入口
main "$@"