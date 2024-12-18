use skyprobe;

-- ----------------------------
-- 用户表
-- ----------------------------
drop table if exists sys_user;
create table sys_user
(
    id          char(36)  not null comment 'ID',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    username    varchar(30) not null comment '用户账号',
    nickname    varchar(30) not null comment '用户昵称',
    email       varchar(50) comment '用户邮箱',
    phone       varchar(11) comment '手机号码',
    gender      char(1)      default 'U' comment '用户性别(男:M, 女:F, 未知:U)',
    avatar      varchar(100) default '' comment '用户头像',
    password    varchar(100) default '' comment '密码',
    status      char(1)   default '1' comment '帐号状态(停用:0, 正常:1)',
    primary key (id)
) engine = innodb comment = '用户表';

-- ----------------------------
-- 角色表
-- ----------------------------
drop table if exists sys_role;
create table sys_role
(
    id          char(36)  not null comment 'ID',
    create_by   varchar(64) default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64) default '' comment '更新者',
    update_time datetime comment '更新时间',
    role_name   varchar(30) not null comment '角色名称',
    role_key    varchar(64) not null comment '角色',
    remark      varchar(255) comment '备注',
    primary key (id)
) engine = innodb comment = '角色表';


-- ----------------------------
-- 角色用户表
-- ----------------------------
drop table if exists sys_role_user;
create table sys_role_user
(
    user_id char(36) not null comment '用户id',
    role_id char(36) not null comment '角色id',
    primary key (user_id, role_id)
) engine = innodb comment = '角色用户表';

insert into sys_role_user
values ("019240b6-44e3-72e8-ab14-bc50df7253b9", "019240b6-44e3-72e8-ab14-bc50dff40c8a");

-- ----------------------------
-- 菜单表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu
(
    id          char(36)  not null comment 'ID',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    parent_id   varchar(36)  not null comment '父菜单ID',
    menu_title  varchar(50) not null comment '菜单标题',
    menu_name   varchar(50) not null comment '菜单名称',
    menu_type   char(1)      default 'M' comment '菜单类型(目录:M, 菜单:C, 按钮:F)',
    order_num   int(4)       default 1 comment '显示排序',
    path        varchar(255) comment '路由地址',
    component   varchar(255) comment '组件路径',
    query       varchar(255) comment '路由参数',
    icon        varchar(100) default '#' comment '菜单图标',
    status      char(1)      default '1' comment '菜单状态(停用:0, 正常:1)',
    primary key (id)
) engine = innodb comment = '菜单表';

-- ----------------------------
-- 角色才菜单表
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu
(
    menu_id char(36) not null comment '菜单id',
    role_id char(36) not null comment '角色id',
    primary key (menu_id, role_id)
) engine = innodb comment = '角色菜单表';


-- ----------------------------
-- 字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
    id          char(36) not null comment 'ID',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    dict_name   varchar(100) default '' comment '字典名称',
    dict_type   varchar(100) default '' comment '字典类型',
    remark      varchar(255) comment '备注',
    primary key (id)
) engine = innodb comment = '字典类型表';

-- ----------------------------
-- 字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
    id          char(36) not null comment 'ID',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    dict_type   varchar(100) default '' comment '字典类型',
    dict_label  varchar(100) default '' comment '字典标签',
    dict_value  varchar(100) default '' comment '字典键值',
    order_num   int(4)       default 1 comment '字典排序',
    css_class   varchar(100) comment '样式属性',
    as_default  bit(1)   default 0 comment '是否默认(否:0, 是:1)',
    status      char(1)      default '1' comment '状态(停用:0, 正常:1)',
    primary key (id)
) engine = innodb comment = '字典数据表';

-- ----------------------------
-- 系统配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config
(
    id           char(36) not null comment 'ID',
    create_by    varchar(64)  default '' comment '创建者',
    create_time  datetime comment '创建时间',
    update_by    varchar(64)  default '' comment '更新者',
    update_time  datetime comment '更新时间',
    config_name  varchar(100) default '' comment '配置名称',
    remark       varchar(255) comment '备注',
    config_key   varchar(100) default '' comment '配置键名',
    config_value varchar(500) default '' comment '配置键值',
    config_type  char(1)      default '1' comment '配置类型(系统内置:0, 用户定义:1)',
    primary key (id)
) engine = innodb comment = '系统配置表';


-- ----------------------------
-- 审计日志表
-- ----------------------------
drop table if exists sys_log;
create table sys_log
(
    id           char(36) not null comment 'ID',
    create_by    varchar(64)  default '' comment '创建者',
    create_time  datetime comment '创建时间',
    update_by    varchar(64)  default '' comment '更新者',
    update_time  datetime comment '更新时间',
    msg  varchar(255) default '' comment '操作描述',
    level char(1) default '1' comment '危险级别(正常:1, 警告:2, 危险:3)',
    type char(1) default '1' comment '操作类型(认证操作:1, 系统操作:2)',
    username    varchar(30)  comment '用户账号',
    nickname    varchar(30)  comment '用户昵称',
    result_status char(1) comment '操作结果状态(失败:0, 成功:1)',
    failed_reason varchar(1024) comment '失败原因',
    request_uri varchar(1024) comment '请求路径',
    parameters varchar(1024) comment '操作参数',
    result varchar(1024) comment '操作结果',
    primary key (id)
) engine = innodb comment = '系统审计日志表';