drop table if exists `blbl_user`;
create table `blbl_user` (
`blbl_uid` varchar(255) not null comment '',
`username` varchar(255) default null comment '',
`level` varchar(255) default null comment '',
`avatar` varchar(255) default null comment '',
`remark` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`blbl_uid`)
) engine = innodb comment = '';

drop table if exists `blbl_user_action_log`;
create table `blbl_user_action_log` (
`action_id` varchar(255) not null comment '',
`action_type` varchar(255) default null comment '',
`blbl_uid` varchar(255) default null comment '',
`username` varchar(255) default null comment '',
`format_content` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`action_id`)
) engine = innodb comment = '';

drop table if exists `model`;
create table `model` (
`model_id` varchar(255) not null comment '',
`model_type` varchar(255) default null comment '',
`model_name` varchar(255) default null comment '',
`model_platform` varchar(255) default null comment '',
`base_url` varchar(255) default null comment '',
`api_key` varchar(255) default null comment '',
`dimension` int(8) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`model_id`)
) engine = innodb comment = '';

drop table if exists `stage_performance`;
create table `stage_performance` (
`performance_id` varchar(255) not null comment '',
`title` varchar(255) default null comment '',
`stage_id` varchar(255) default null comment '',
`stage_name` varchar(255) default null comment '',
`script_id` varchar(255) default null comment '',
`script_name` varchar(255) default null comment '',
`actor_id` varchar(255) default null comment '',
`actor_name` varchar(255) default null comment '',
`start_time` datetime default null comment '',
`end_time` datetime default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`performance_id`)
) engine = innodb comment = '';

drop table if exists `script_node`;
create table `script_node` (
`node_id` varchar(255) not null comment '',
`node_name` varchar(255) default null comment '',
`node_pid` varchar(255) default null comment '',
`node_index` varchar(255) default null comment '',
`script_id` varchar(255) default null comment '',
`description` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`node_id`)
) engine = innodb comment = '';

drop table if exists `dataset_document_segment`;
create table `dataset_document_segment` (
`segment_id` varchar(255) not null comment '',
`document_id` varchar(255) default null comment '',
`dataset_id` varchar(255) default null comment '',
`vector_id` varchar(255) default null comment '',
`content` varchar(255) default null comment '',
`position` int(8) default null comment '',
`status` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`segment_id`)
) engine = innodb comment = '';

drop table if exists `actor`;
create table `actor` (
`actor_id` varchar(255) not null comment '',
`actor_name` varchar(255) default null comment '',
`prompt_template` varchar(255) default null comment '',
`chat_model_id` varchar(255) default null comment '',
`chat_mode_config` varchar(255) default null comment '',
`tts_model_id` varchar(255) default null comment '',
`tts_model_config` varchar(255) default null comment '',
`dataset_id` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`actor_id`)
) engine = innodb comment = '';

drop table if exists `dataset_document`;
create table `dataset_document` (
`document_id` varchar(255) not null comment '',
`dataset_id` varchar(255) default null comment '',
`document_name` varchar(255) default null comment '',
`source_type` varchar(255) default null comment '',
`status` varchar(255) default null comment '',
`parse_status` varchar(255) default null comment '',
`error_msg` varchar(255) default null comment '',
`segment_count` int(8) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`document_id`)
) engine = innodb comment = '';

drop table if exists `stage_performance_msg`;
create table `stage_performance_msg` (
`msg_id` varchar(255) not null comment '',
`msg_pid` varchar(255) default null comment '',
`performance_id` varchar(255) default null comment '',
`stage_id` varchar(255) default null comment '',
`model_id` varchar(255) default null comment '',
`actor_id` varchar(255) default null comment '',
`system_msg` varchar(255) default null comment '',
`question` varchar(255) default null comment '',
`answer` varchar(255) default null comment '',
`history_msg` varchar(255) default null comment '',
`start_time` datetime default null comment '',
`end_time` datetime default null comment '',
`cost_time` int(8) default null comment '',
`msg_type` varchar(255) default null comment '',
`user_id` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`msg_id`)
) engine = innodb comment = '';

drop table if exists `dataset`;
create table `dataset` (
`dataset_id` varchar(255) not null comment '',
`dataset_name` varchar(255) default null comment '',
`collection_name` varchar(255) default null comment '',
`dimension` int(8) default null comment '',
`embedding_model_id` varchar(255) default null comment '',
`embedding_model_name` varchar(255) default null comment '',
`status` varchar(255) default null comment '',
`description` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`dataset_id`)
) engine = innodb comment = '';

drop table if exists `stage`;
create table `stage` (
`stage_id` varchar(255) not null comment '',
`stage_name` varchar(255) default null comment '',
`description` varchar(255) default null comment '',
`actor_id` varchar(255) default null comment '',
`script_id` varchar(255) default null comment '',
`status` varchar(255) default null comment '',
`performance_id` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`stage_id`)
) engine = innodb comment = '';

drop table if exists `script`;
create table `script` (
`script_id` varchar(255) not null comment '',
`script_name` varchar(255) default null comment '',
`description` varchar(255) default null comment '',
`prompt_template` varchar(255) default null comment '',
`create_time` datetime default null comment '',
`update_time` datetime default null comment '',
PRIMARY KEY (`script_id`)
) engine = innodb comment = '';