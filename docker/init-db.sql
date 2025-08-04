-- 档案数字化管理系统数据库初始化脚本
-- 创建日期: 2024-12-XX
-- 版本: v1.0

-- 设置字符集
SET client_encoding = 'UTF8';

-- 创建扩展
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- 用户表
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    avatar VARCHAR(200),
    status INTEGER DEFAULT 1, -- 1:启用 0:禁用
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    update_by BIGINT
);

-- 角色表
CREATE TABLE sys_role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    role_code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    update_by BIGINT
);

-- 权限表
CREATE TABLE sys_permission (
    id BIGSERIAL PRIMARY KEY,
    permission_name VARCHAR(50) NOT NULL,
    permission_code VARCHAR(50) NOT NULL UNIQUE,
    resource_type VARCHAR(20), -- menu, button, api
    parent_id BIGINT DEFAULT 0,
    path VARCHAR(200),
    icon VARCHAR(50),
    sort_order INTEGER DEFAULT 0,
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户角色关联表
CREATE TABLE sys_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES sys_role(id) ON DELETE CASCADE
);

-- 角色权限关联表
CREATE TABLE sys_role_permission (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES sys_role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES sys_permission(id) ON DELETE CASCADE
);

-- 档案分类表
CREATE TABLE archive_category (
    id BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    category_code VARCHAR(50) NOT NULL UNIQUE,
    parent_id BIGINT DEFAULT 0,
    level_num INTEGER DEFAULT 1,
    sort_order INTEGER DEFAULT 0,
    description VARCHAR(500),
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    update_by BIGINT
);

-- 档案主表
CREATE TABLE archive_main (
    id BIGSERIAL PRIMARY KEY,
    archive_no VARCHAR(100) NOT NULL UNIQUE, -- 档案号
    title VARCHAR(200) NOT NULL, -- 档案题名
    category_id BIGINT, -- 分类ID
    archive_date DATE, -- 档案形成时间
    archive_year INTEGER, -- 档案年度
    retention_period VARCHAR(20), -- 保管期限
    security_level VARCHAR(20), -- 密级
    page_count INTEGER DEFAULT 0, -- 页数
    file_format VARCHAR(50), -- 文件格式
    file_size BIGINT DEFAULT 0, -- 文件大小(字节)
    storage_location VARCHAR(200), -- 存储位置
    author VARCHAR(100), -- 责任者
    keywords VARCHAR(500), -- 关键词
    abstract TEXT, -- 摘要
    full_text TEXT, -- 全文内容(OCR提取)
    status INTEGER DEFAULT 1, -- 状态: 1-正常 2-借出 3-损坏 0-删除
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    update_by BIGINT,
    FOREIGN KEY (category_id) REFERENCES archive_category(id)
);

-- 档案文件表
CREATE TABLE archive_file (
    id BIGSERIAL PRIMARY KEY,
    archive_id BIGINT NOT NULL,
    file_name VARCHAR(200) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_url VARCHAR(500),
    file_size BIGINT DEFAULT 0,
    file_type VARCHAR(50),
    mime_type VARCHAR(100),
    md5_hash VARCHAR(32),
    page_num INTEGER DEFAULT 1,
    thumbnail_path VARCHAR(500), -- 缩略图路径
    is_main_file BOOLEAN DEFAULT FALSE, -- 是否主文件
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    FOREIGN KEY (archive_id) REFERENCES archive_main(id) ON DELETE CASCADE
);

-- 档案元数据表
CREATE TABLE archive_metadata (
    id BIGSERIAL PRIMARY KEY,
    archive_id BIGINT NOT NULL,
    metadata_key VARCHAR(100) NOT NULL,
    metadata_value TEXT,
    data_type VARCHAR(20) DEFAULT 'string', -- string, number, date, boolean
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (archive_id) REFERENCES archive_main(id) ON DELETE CASCADE
);

-- 档案借阅表
CREATE TABLE archive_borrow (
    id BIGSERIAL PRIMARY KEY,
    archive_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    borrower_name VARCHAR(100),
    borrow_date DATE NOT NULL,
    return_date DATE,
    planned_return_date DATE,
    borrow_reason VARCHAR(500),
    return_reason VARCHAR(500),
    status INTEGER DEFAULT 1, -- 1-借出 2-已归还 3-逾期
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    update_by BIGINT,
    FOREIGN KEY (archive_id) REFERENCES archive_main(id),
    FOREIGN KEY (borrower_id) REFERENCES sys_user(id)
);

-- 操作日志表
CREATE TABLE sys_operation_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    username VARCHAR(50),
    operation VARCHAR(100), -- 操作类型
    method VARCHAR(200), -- 请求方法
    params TEXT, -- 请求参数
    result TEXT, -- 操作结果
    ip_address VARCHAR(50),
    user_agent VARCHAR(500),
    execute_time INTEGER, -- 执行时间(毫秒)
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 系统配置表
CREATE TABLE sys_config (
    id BIGSERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT,
    config_type VARCHAR(20) DEFAULT 'string',
    description VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 数据字典表
CREATE TABLE sys_dict (
    id BIGSERIAL PRIMARY KEY,
    dict_type VARCHAR(50) NOT NULL,
    dict_label VARCHAR(100) NOT NULL,
    dict_value VARCHAR(100) NOT NULL,
    sort_order INTEGER DEFAULT 0,
    status INTEGER DEFAULT 1,
    description VARCHAR(200),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
CREATE INDEX idx_archive_main_archive_no ON archive_main(archive_no);
CREATE INDEX idx_archive_main_title ON archive_main(title);
CREATE INDEX idx_archive_main_category_id ON archive_main(category_id);
CREATE INDEX idx_archive_main_archive_date ON archive_main(archive_date);
CREATE INDEX idx_archive_main_status ON archive_main(status);
CREATE INDEX idx_archive_main_create_time ON archive_main(create_time);

CREATE INDEX idx_archive_file_archive_id ON archive_file(archive_id);
CREATE INDEX idx_archive_file_file_name ON archive_file(file_name);

CREATE INDEX idx_archive_metadata_archive_id ON archive_metadata(archive_id);
CREATE INDEX idx_archive_metadata_key ON archive_metadata(metadata_key);

CREATE INDEX idx_archive_borrow_archive_id ON archive_borrow(archive_id);
CREATE INDEX idx_archive_borrow_borrower_id ON archive_borrow(borrower_id);
CREATE INDEX idx_archive_borrow_status ON archive_borrow(status);

CREATE INDEX idx_sys_operation_log_user_id ON sys_operation_log(user_id);
CREATE INDEX idx_sys_operation_log_create_time ON sys_operation_log(create_time);

-- 全文检索索引
CREATE INDEX idx_archive_main_full_text_gin ON archive_main USING gin(to_tsvector('simple', coalesce(title, '') || ' ' || coalesce(full_text, '') || ' ' || coalesce(keywords, '')));

-- 插入初始数据
-- 默认管理员用户 (密码: admin123)
INSERT INTO sys_user (username, password, real_name, email, status) VALUES 
('admin', '$2a$10$7JB720yubVSOnGnEmSrwqe4MHBiKKH.h1KKJKSDcrOTKmTF7j5/jC', '系统管理员', 'admin@example.com', 1);

-- 默认角色
INSERT INTO sys_role (role_name, role_code, description) VALUES 
('超级管理员', 'SUPER_ADMIN', '拥有系统所有权限'),
('档案管理员', 'ARCHIVE_ADMIN', '档案管理相关权限'),
('普通用户', 'USER', '基础查看权限');

-- 默认权限
INSERT INTO sys_permission (permission_name, permission_code, resource_type, parent_id, path, icon, sort_order) VALUES 
('系统管理', 'sys', 'menu', 0, '/system', 'el-icon-setting', 1),
('用户管理', 'sys:user', 'menu', 1, '/system/user', 'el-icon-user', 1),
('角色管理', 'sys:role', 'menu', 1, '/system/role', 'el-icon-s-custom', 2),
('权限管理', 'sys:permission', 'menu', 1, '/system/permission', 'el-icon-key', 3),
('档案管理', 'archive', 'menu', 0, '/archive', 'el-icon-folder', 2),
('档案列表', 'archive:list', 'menu', 5, '/archive/list', 'el-icon-document', 1),
('档案分类', 'archive:category', 'menu', 5, '/archive/category', 'el-icon-menu', 2),
('档案借阅', 'archive:borrow', 'menu', 5, '/archive/borrow', 'el-icon-takeaway-box', 3);

-- 分配超级管理员角色
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 分配权限给超级管理员角色
INSERT INTO sys_role_permission (role_id, permission_id) 
SELECT 1, id FROM sys_permission;

-- 系统配置
INSERT INTO sys_config (config_key, config_value, description) VALUES 
('system.title', '档案数字化管理系统', '系统标题'),
('upload.max.size', '104857600', '最大上传文件大小(字节)'),
('upload.allowed.types', '.pdf,.doc,.docx,.jpg,.jpeg,.png,.tiff,.gif', '允许上传的文件类型'),
('archive.retention.periods', '永久,30年,10年,3年', '档案保管期限选项'),
('archive.security.levels', '公开,限制,秘密,机密', '档案密级选项');

-- 数据字典
INSERT INTO sys_dict (dict_type, dict_label, dict_value, sort_order) VALUES 
('archive_status', '正常', '1', 1),
('archive_status', '借出', '2', 2),
('archive_status', '损坏', '3', 3),
('user_status', '启用', '1', 1),
('user_status', '禁用', '0', 2);

-- 默认档案分类
INSERT INTO archive_category (category_name, category_code, parent_id, level_num, sort_order, description) VALUES 
('文书档案', 'WS', 0, 1, 1, '机关文书档案'),
('科技档案', 'KJ', 0, 1, 2, '科学技术档案'),
('会计档案', 'KJ', 0, 1, 3, '会计凭证档案'),
('人事档案', 'RS', 0, 1, 4, '人事管理档案'),
('基建档案', 'JJ', 0, 1, 5, '基本建设档案');

COMMIT;