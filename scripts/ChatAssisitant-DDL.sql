drop table if exists `agent_info`;
create table `agent_info` (
                              `agent_id` varchar(255) not null comment '',
                              `agent_name` varchar(255) default null comment '',
                              `create_time` datetime default null comment '',
                              `update_time` datetime default null comment '',
                              PRIMARY KEY (`agent_id`)
) engine = innodb comment = '';

drop table if exists `knowledge_base`;
create table `knowledge_base` (
                                  `kb_id` varchar(255) not null comment '',
                                  `kb_name` varchar(255) default null comment '',
                                  `collection_name` varchar(255) default null comment '',
                                  `embedding_model` varchar(255) default null comment '',
                                  `dimension` varchar(255) default null comment '',
                                  `status` varchar(255) default null comment '',
                                  `description` varchar(255) default null comment '',
                                  `create_time` datetime default null comment '',
                                  `update_time` datetime default null comment '',
                                  PRIMARY KEY (`kb_id`)
) engine = innodb comment = '';

drop table if exists `stage_info`;
create table `stage_info` (
                              `stage_id` varchar(255) not null comment '',
                              `stage_name` varchar(255) default null comment '',
                              `description` varchar(255) default null comment '',
                              `create_time` datetime default null comment '',
                              `update_time` datetime default null comment '',
                              PRIMARY KEY (`stage_id`)
) engine = innodb comment = '';

drop table if exists `memory_info`;
create table `memory_info` (
                               `memory_id` varchar(255) not null comment '',
                               `agent_id` varchar(255) default null comment '',
                               `stage_id` varchar(255) default null comment '',
                               `scene_id` varchar(255) default null comment '',
                               `create_time` datetime default null comment '',
                               `update_time` datetime default null comment '',
                               PRIMARY KEY (`memory_id`)
) engine = innodb comment = '';

drop table if exists `blbl_user`;
create table `blbl_user` (
                             `blbl_uid` varchar(255) not null comment '',
                             `username` varchar(255) default null comment '',
                             `create_time` datetime default null comment '',
                             `update_time` datetime default null comment '',
                             PRIMARY KEY (`blbl_uid`)
) engine = innodb comment = '';

drop table if exists `chat_msg`;
create table `chat_msg` (
                            `msg_id` varchar(255) not null comment '',
                            `memory_id` varchar(255) default null comment '',
                            `content` varchar(255) default null comment '',
                            `chat_role` varchar(255) default null comment '',
                            `create_time` datetime default null comment '',
                            `update_time` datetime default null comment '',
                            `user_id` varchar(255) default null comment '',
                            PRIMARY KEY (`msg_id`)
) engine = innodb comment = '';

drop table if exists `prompt_template_variable_info`;
create table `prompt_template_variable_info` (
                                                 `variable_id` varchar(255) not null comment '',
                                                 `template_id` varchar(255) default null comment '',
                                                 `variable_code` varchar(255) default null comment '',
                                                 `variable_name` varchar(255) default null comment '',
                                                 `create_time` datetime default null comment '',
                                                 `update_time` datetime default null comment '',
                                                 PRIMARY KEY (`variable_id`)
) engine = innodb comment = '';

drop table if exists `doc_info`;
create table `doc_info` (
                            `doc_id` varchar(255) not null comment '',
                            `doc_name` varchar(255) default null comment '',
                            `doc_type` varchar(255) default null comment '',
                            `doc_size` varchar(255) default null comment '',
                            `doc_status` varchar(255) default null comment '',
                            `create_time` datetime default null comment '',
                            `update_time` datetime default null comment '',
                            PRIMARY KEY (`doc_id`)
) engine = innodb comment = '';

drop table if exists `scene_info`;
create table `scene_info` (
                              `scene_id` varchar(255) not null comment '',
                              `stage_id` varchar(255) default null comment '',
                              `scene_name` varchar(255) default null comment '',
                              `description` varchar(255) default null comment '',
                              `create_time` datetime default null comment '',
                              `update_time` datetime default null comment '',
                              PRIMARY KEY (`scene_id`)
) engine = innodb comment = '';

drop table if exists `doc_segment`;
create table `doc_segment` (
                               `segment_id` varchar(255) not null comment '',
                               `content` varchar(255) default null comment '',
                               `document_id` varchar(255) default null comment '',
                               `sync_status` varchar(255) default null comment '',
                               `create_time` datetime default null comment '',
                               `update_time` datetime default null comment '',
                               PRIMARY KEY (`segment_id`)
) engine = innodb comment = '';

drop table if exists `prompt_template_info`;
create table `prompt_template_info` (
                                        `prompt_id` varchar(255) not null comment '',
                                        `agent_id` varchar(255) default null comment '',
                                        `prompt_type` varchar(255) default null comment '',
                                        `content` varchar(255) default null comment '',
                                        `create_time` datetime default null comment '',
                                        `update_time` datetime default null comment '',
                                        PRIMARY KEY (`prompt_id`)
) engine = innodb comment = '';
