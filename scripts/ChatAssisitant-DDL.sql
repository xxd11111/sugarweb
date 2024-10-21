drop table if exists `agent_info`;
create table `agent_info` (
                              `agent_id` varchar(255) not null comment '',
                              `agent_name` varchar(255) default null comment '',
                              `create_time` datetime default null comment '',
                              `update_time` datetime default null comment '',
                              PRIMARY KEY (`agent_id`)
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

drop table if exists `stage_info`;
create table `stage_info` (
                              `stage_id` varchar(255) not null comment '',
                              `stage_name` varchar(255) default null comment '',
                              `description` varchar(255) default null comment '',
                              `create_time` datetime default null comment '',
                              `update_time` datetime default null comment '',
                              PRIMARY KEY (`stage_id`)
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

drop table if exists `blbl_user`;
create table `blbl_user` (
                             `blbl_uid` varchar(255) not null comment '',
                             `username` varchar(255) default null comment '',
                             `create_time` datetime default null comment '',
                             `update_time` datetime default null comment '',
                             PRIMARY KEY (`blbl_uid`)
) engine = innodb comment = '';

drop table if exists `blbl_user_action`;
create table `blbl_user_action` (
                                    `action_id` varchar(255) not null comment '',
                                    `action_type` varchar(255) default null comment '',
                                    `blbl_uid` varchar(255) default null comment '',
                                    `username` varchar(255) default null comment '',
                                    `format_content` varchar(255) default null comment '',
                                    `create_time` datetime default null comment '',
                                    `update_time` datetime default null comment '',
                                    PRIMARY KEY (`action_id`)
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


drop table if exists `file_info`;
create table `file_info` (
                             `file_id` varchar(255) not null comment '',
                             `group_code` varchar(255) default null comment '',
                             `file_key` varchar(255) default null comment '',
                             `filename` varchar(255) default null comment '',
                             `file_type` varchar(255) default null comment '',
                             `content_type` varchar(255) default null comment '',
                             `file_size` bigint(11) default null comment '',
                             `create_time` datetime default null comment '',
                             `update_time` datetime default null comment '',
                             PRIMARY KEY (`file_id`)
) engine = innodb comment = '';

drop table if exists `api_call_log`;
create table `api_call_log` (
                                `log_id` varchar(255) not null comment '',
                                `api_name` varchar(255) default null comment '',
                                `user_id` varchar(255) default null comment '',
                                `username` varchar(255) default null comment '',
                                `request_ip` varchar(255) default null comment '',
                                `request_url` varchar(255) default null comment '',
                                `request_method` varchar(255) default null comment '',
                                `method_params` varchar(255) default null comment '',
                                `user_agent` varchar(255) default null comment '',
                                `start_time` datetime default null comment '',
                                `end_time` datetime default null comment '',
                                `cost_time` int(8) default null comment '',
                                `result_code` int(8) default null comment '',
                                `result_data` varchar(255) default null comment '',
                                `result_message` varchar(255) default null comment '',
                                `exception_message` varchar(255) default null comment '',
                                `exception_class_name` varchar(255) default null comment '',
                                `exception_stack_trace` varchar(255) default null comment '',
                                PRIMARY KEY (`log_id`)
) engine = innodb comment = '';

drop table if exists `task_trigger`;
create table `task_trigger` (
                                `trigger_id` varchar(255) not null comment '',
                                `task_id` varchar(255) default null comment '',
                                `trigger_code` varchar(255) default null comment '',
                                `trigger_name` varchar(255) default null comment '',
                                `cron` varchar(255) default null comment '',
                                `enabled` varchar(255) default null comment '',
                                `trigger_data` varchar(255) default null comment '',
                                `is_default` varchar(255) default null comment '',
                                PRIMARY KEY (`trigger_id`)
) engine = innodb comment = '';

drop table if exists `file_group`;
create table `file_group` (
                              `group_id` varchar(255) not null comment '',
                              `group_code` varchar(255) default null comment '',
                              `group_name` varchar(255) default null comment '',
                              `max_file_size` varchar(255) default null comment '',
                              `allow_file_type` varchar(255) default null comment '',
                              `deny_file_type` varchar(255) default null comment '',
                              PRIMARY KEY (`group_id`)
) engine = innodb comment = '';

drop table if exists `task_info`;
create table `task_info` (
                             `task_id` varchar(255) not null comment '',
                             `task_code` varchar(255) default null comment '',
                             `task_name` varchar(255) default null comment '',
                             `bean_name` varchar(255) default null comment '',
                             `enabled` varchar(255) default null comment '',
                             `task_data` varchar(255) default null comment '',
                             `is_default` varchar(255) default null comment '',
                             PRIMARY KEY (`task_id`)
) engine = innodb comment = '';

drop table if exists `file_link`;
create table `file_link` (
                             `link_id` varchar(255) not null comment '',
                             `file_id` varchar(255) default null comment '',
                             `biz_id` varchar(255) default null comment '',
                             `group_code` varchar(255) default null comment '',
                             PRIMARY KEY (`link_id`)
) engine = innodb comment = '';

drop table if exists `param`;
create table `param` (
                         `param_id` varchar(255) not null comment '',
                         `param_code` varchar(255) default null comment '',
                         `param_name` varchar(255) default null comment '',
                         `param_value` varchar(255) default null comment '',
                         `description` varchar(255) default null comment '',
                         PRIMARY KEY (`param_id`)
) engine = innodb comment = '';

drop table if exists `dict_group`;
create table `dict_group` (
                              `group_id` varchar(255) not null comment '',
                              `group_name` varchar(255) default null comment '',
                              `group_code` varchar(255) default null comment '',
                              `group_status` varchar(255) default null comment '',
                              PRIMARY KEY (`group_id`)
) engine = innodb comment = '';

drop table if exists `task_trigger_log`;
create table `task_trigger_log` (
                                    `log_id` varchar(255) not null comment '',
                                    `task_id` varchar(255) default null comment '',
                                    `task_code` varchar(255) default null comment '',
                                    `task_name` varchar(255) default null comment '',
                                    `bean_name` varchar(255) default null comment '',
                                    `trigger_id` varchar(255) default null comment '',
                                    `trigger_name` varchar(255) default null comment '',
                                    `cron` varchar(255) default null comment '',
                                    `start_time` datetime default null comment '',
                                    `end_time` datetime default null comment '',
                                    `cost_time` int(8) default null comment '',
                                    `execute_result` varchar(255) default null comment '',
                                    `exception_message` varchar(255) default null comment '',
                                    `trace_message` varchar(255) default null comment '',
                                    PRIMARY KEY (`log_id`)
) engine = innodb comment = '';

drop table if exists `dict_item`;
create table `dict_item` (
                             `dict_item_id` varchar(255) not null comment '',
                             `dict_group_id` varchar(255) default null comment '',
                             `dict_item_code` varchar(255) default null comment '',
                             `dict_item_name` varchar(255) default null comment '',
                             `dict_item_status` varchar(255) default null comment '',
                             PRIMARY KEY (`dict_item_id`)
) engine = innodb comment = '';