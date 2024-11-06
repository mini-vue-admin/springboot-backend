insert into sys_user  (id,create_by,create_time,update_by,update_time, username, nickname, email, phone, gender, avatar, password, status)
values ('019240b6-44e3-72e8-ab14-bc50df7253b9', 'admin', NOW(), 'admin', NOW(), 'admin', '系统管理员', null, null, 'U', null, 'admin', 1);

insert into sys_role (id,create_by,create_time,update_by,update_time,role_name, role_key, remark)
values ('019240b6-44e3-72e8-ab14-bc50dff40c8a', 'admin', NOW(), 'admin', NOW(), '管理员', 'admin', '管理员角色');

insert into sys_role_user (user_id, role_id)
values ('019240b6-44e3-72e8-ab14-bc50df7253b9', '019240b6-44e3-72e8-ab14-bc50dff40c8a');

INSERT INTO sys_menu (id,create_by,create_time,update_by,update_time,parent_id,menu_title,menu_name,menu_type,order_num,`path`,component,query,icon,status) VALUES
('1ef920cb-99f5-6ec2-a841-5600051f1387','admin',NOW(),'admin',NOW(),'0','首页','home','C',1,'/','index',NULL,'pi-home','1'),
('1ef920cb-99f6-6660-be02-5600051f1387','admin',NOW(),'admin',NOW(),'0','系统管理','system','M',999,'/system',NULL,NULL,'pi-cog','1'),
('1ef920cb-99f6-66f6-9f55-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','用户管理','user','C',1,'/system/user','system/user/index',NULL,'pi-user','1'),
('1ef920cb-99f6-6764-8e68-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','角色管理','role','C',2,'/system/role','system/role/index',NULL,'pi-users','1'),
('1ef920cb-99f6-67be-8d41-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','菜单管理','menu','C',3,'/system/menu','system/menu/index',NULL,'pi-list','1'),
('1ef920cb-99f6-6818-870b-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','字典管理','dict','C',4,'/system/dictType','system/dictType/index',NULL,'pi-book','1'),
('1ef920cb-99f6-6872-89bf-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','配置管理','config','C',5,'/system/config','system/config/index',NULL,'pi-file-edit','1'),
('1ef920cb-99f6-68cc-8fbf-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','代码生成','genCode','C',999,'/system/genCode','system/genCode/index',NULL,'pi-code','1');

INSERT INTO sys_dict_type (id,create_by,create_time,update_by,update_time,dict_name,dict_type,remark) VALUES
('1ef9b3be-a604-66b0-b6d5-5600051f1387','admin',NOW(),'admin',NOW(),'通用状态','sys_common_status',NULL),
('1ef9b3be-a604-6728-915d-5600051f1387','admin',NOW(),'admin',NOW(),'用户性别','sys_user_gender',NULL),
('1ef9b3be-a604-678c-8d71-5600051f1387','admin',NOW(),'admin',NOW(),'配置类型','sys_config_type',NULL),
('1ef9b55a-235d-6566-bb70-5600051f1387','admin',NOW(),'admin',NOW(),'菜单类型','sys_menu_type',NULL);


INSERT INTO sys_dict_data (id,create_by,create_time,update_by,update_time,dict_type,dict_label,dict_value,order_num,css_class,as_default,status) VALUES
('1ef8f8f2-ef01-66b0-b70d-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_user_gender','男','male',1,'primary',0,'1'),
('1ef8f8f2-ef01-676e-b755-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_user_gender','女','female',2,'primary',0,'1'),
('1ef8f8f2-ef01-67dc-9848-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_user_gender','未知','unknown',3,'primary',1,'1'),
('1ef8f916-5a22-6390-bbce-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_common_status','启用','enabled',1,'success',1,'1'),
('1ef8f916-5a22-6b06-926f-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_common_status','禁用','disabled',2,'danger',0,'1'),
('1ef9b3c2-07d0-6df0-8078-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_config_type','系统配置','system',1,'primary',0,'1'),
('1ef9b3c2-07d1-6836-8f07-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_config_type','用户配置','custom',2,'contrast',1,'1'),
('1ef9b55a-2360-69e6-87ae-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_menu_type','目录','dir',1,'info',0,'1'),
('1ef9b55a-2360-6aa4-898b-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_menu_type','菜单','menu',2,'success',0,'1'),
('1ef9b55a-2360-6b1c-b942-5600051f1387','admin', NOW(), 'admin', NOW(),'sys_menu_type','按钮','button',3,'warn',0,'1');

INSERT INTO sys_config (id,create_by,create_time,update_by,update_time,config_name,remark,config_key,config_value,config_type) VALUES
('0192fb46-98e4-7b98-9a8f-3eb59280bb9d','admin',NOW(),'admin',NOW(),'初始化密码','用户默认初始化密码','sys_init_password','111111','0');
