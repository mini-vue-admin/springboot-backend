INSERT INTO sys_menu (id, create_by, create_time, update_by, update_time, parent_id, menu_title, menu_name, menu_type,
                      order_num, path, component, query, icon, status)
VALUES ('01924292-b086-7095-8c2b-85b51d04d2e9', 'admin', NOW(), 'admin', NOW(), '0', '首页', 'index', 'C', 1,
        '', 'index', NULL, '#', '1');

INSERT INTO sys_menu (id, create_by, create_time, update_by, update_time, parent_id, menu_title, menu_name, menu_type,
                      order_num, path, component, query, icon, status)
VALUES ('019240ba-634f-7214-b08f-36da372c821b', 'admin', NOW(), 'admin', NOW(), '0', '系统管理', 'system', 'M', 999,
        '/system', NULL, NULL, '#', '1');

-- 插入用户管理菜单
INSERT INTO sys_menu (id, create_by, create_time, update_by, update_time, parent_id, menu_title, menu_name, menu_type,
                      order_num, path, component, query, icon, status)
VALUES ('019240ba-634f-7214-b08f-36da380ce9bb', 'admin', NOW(), 'admin', NOW(), '019240ba-634f-7214-b08f-36da372c821b',
        '用户管理', 'user', 'C', 1, '/system/user', 'system/user/index', NULL, '#', '1');

-- 插入角色管理菜单
INSERT INTO sys_menu (id, create_by, create_time, update_by, update_time, parent_id, menu_title, menu_name, menu_type,
                      order_num, path, component, query, icon, status)
VALUES ('019240ba-634f-7214-b08f-36da382281aa', 'admin', NOW(), 'admin', NOW(), '019240ba-634f-7214-b08f-36da372c821b',
        '角色管理', 'role', 'C', 2, '/system/role', 'system/role/index', NULL, '#', '1');

-- 插入菜单管理菜单
INSERT INTO sys_menu (id, create_by, create_time, update_by, update_time, parent_id, menu_title, menu_name, menu_type,
                      order_num, path, component, query, icon, status)
VALUES ('019240ba-634f-7214-b08f-36da38969642', 'admin', NOW(), 'admin', NOW(), '019240ba-634f-7214-b08f-36da372c821b',
        '菜单管理', 'menu', 'C', 3, '/system/menu', 'system/menu/index', NULL, '#', '1');

-- 插入字典管理菜单
INSERT INTO sys_menu (id, create_by, create_time, update_by, update_time, parent_id, menu_title, menu_name, menu_type,
                      order_num, path, component, query, icon, status)
VALUES ('019240ba-634f-7214-b08f-36da38f86c03', 'admin', NOW(), 'admin', NOW(), '019240ba-634f-7214-b08f-36da372c821b',
        '字典管理', 'dict', 'C', 4, '/system/dict', 'system/dict/index', NULL, '#', '1');

-- 插入配置管理菜单
INSERT INTO sys_menu (id, create_by, create_time, update_by, update_time, parent_id, menu_title, menu_name, menu_type,
                      order_num, path, component, query, icon, status)
VALUES ('019240ba-c13e-72c8-8796-27dbaa41106a', 'admin', NOW(), 'admin', NOW(), '019240ba-634f-7214-b08f-36da372c821b',
        '配置管理', 'config', 'C', 5, '/system/config', 'system/config/index', NULL, '#', '1');