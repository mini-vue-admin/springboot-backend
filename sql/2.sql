INSERT INTO sys_menu (id,create_by,create_time,update_by,update_time,parent_id,menu_title,menu_name,menu_type,order_num,`path`,component,query,icon,status) VALUES
('1ef920cb-99f5-6ec2-a841-5600051f1387','admin',NOW(),'admin',NOW(),'0','首页','home','C',1,'/','index',NULL,'pi-home','1'),
('1ef920cb-99f6-6660-be02-5600051f1387','admin',NOW(),'admin',NOW(),'0','系统管理','system','M',999,'/system',NULL,NULL,'pi-cog','1'),
('1ef920cb-99f6-66f6-9f55-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','用户管理','user','C',1,'/system/user','system/user/index',NULL,'pi-user','1'),
('1ef920cb-99f6-6764-8e68-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','角色管理','role','C',2,'/system/role','system/role/index',NULL,'pi-users','1'),
('1ef920cb-99f6-67be-8d41-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','菜单管理','menu','C',3,'/system/menu','system/menu/index',NULL,'pi-list','1'),
('1ef920cb-99f6-6818-870b-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','字典管理','dict','C',4,'/system/dict','system/dict/index',NULL,'pi-book','1'),
('1ef920cb-99f6-6872-89bf-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','配置管理','config','C',5,'/system/config','system/config/index',NULL,'pi-file-edit','1'),
('1ef920cb-99f6-68cc-8fbf-5600051f1387','admin',NOW(),'admin',NOW(),'1ef920cb-99f6-6660-be02-5600051f1387','代码生成','genCode','C',999,'/system/genCode','system/genCode/index',NULL,'pi-code','1');

INSERT INTO sys_dict_data (id,create_by,create_time,update_by,update_time,dict_type,dict_label,dict_value,order_num,css_class,as_default,status) VALUES
('1ef8f8f2-ef01-66b0-b70d-5600051f1387','admin', NOW(), 'admin', NOW(),'sys.user.gender','男','male',1,NULL,'0','1'),
('1ef8f8f2-ef01-676e-b755-5600051f1387','admin', NOW(), 'admin', NOW(),'sys.user.gender','女','female',2,NULL,'0','1'),
('1ef8f8f2-ef01-67dc-9848-5600051f1387','admin', NOW(), 'admin', NOW(),'sys.user.gender','未知','unknown',3,NULL,'1','1'),
('1ef8f916-5a22-6390-bbce-5600051f1387','admin', NOW(), 'admin', NOW(),'sys.status','启用','enabled',1,NULL,'1','1'),
('1ef8f916-5a22-6b06-926f-5600051f1387','admin', NOW(), 'admin', NOW(),'sys.status','禁用','disabled',2,'danger','0','1');
