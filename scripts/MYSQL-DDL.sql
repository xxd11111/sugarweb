drop table if exists `role_mapper`;
create table `role_mapper`
(

) engine = innodb comment = '';

drop table if exists `role_menu`;
create table `role_menu`
(
    `role_id`        varchar(255) default null comment '',
    `menu_id`        varchar(255) default null comment '',
    `api_permission` varchar(255) default null comment ''
) engine = innodb comment = '';

drop table if exists `user_mapper`;
create table `user_mapper`
(

) engine = innodb comment = '';

drop table if exists `api_call_log`;
create table `api_call_log`
(
    `log_id`                varchar(255) not null comment '',
    `api_name`              varchar(255) default null comment '',
    `user_id`               varchar(255) default null comment '',
    `username`              varchar(255) default null comment '',
    `request_ip`            varchar(255) default null comment '',
    `request_url`           varchar(255) default null comment '',
    `request_method`        varchar(255) default null comment '',
    `method_params`         varchar(255) default null comment '',
    `user_agent`            varchar(255) default null comment '',
    `start_time`            datetime     default null comment '',
    `end_time`              datetime     default null comment '',
    `cost_time`             int(8) default null comment '',
    `result_code`           int(8) default null comment '',
    `result_data`           varchar(255) default null comment '',
    `result_message`        varchar(255) default null comment '',
    `exception_message`     varchar(255) default null comment '',
    `exception_class_name`  varchar(255) default null comment '',
    `exception_stack_trace` varchar(255) default null comment '',
    PRIMARY KEY (`log_id`)
) engine = innodb comment = '';

drop table if exists `menu_mapper`;
create table `menu_mapper`
(

) engine = innodb comment = '';

drop table if exists `dict_group`;
create table `dict_group`
(
    `group_id`     varchar(255) not null comment '',
    `group_name`   varchar(255) default null comment '',
    `group_code`   varchar(255) default null comment '',
    `group_status` varchar(255) default null comment '',
    PRIMARY KEY (`group_id`)
) engine = innodb comment = '';

drop table if exists `task_trigger`;
create table `task_trigger`
(
    `trigger_id`   varchar(255) not null comment '',
    `task_id`      varchar(255) default null comment '',
    `trigger_code` varchar(255) default null comment '',
    `trigger_name` varchar(255) default null comment '',
    `cron`         varchar(255) default null comment '',
    `enabled`      varchar(255) default null comment '',
    `trigger_data` varchar(255) default null comment '',
    `is_default`   varchar(255) default null comment '',
    PRIMARY KEY (`trigger_id`)
) engine = innodb comment = '';

drop table if exists `task_trigger_log`;
create table `task_trigger_log`
(
    `log_id`            varchar(255) not null comment '',
    `task_id`           varchar(255) default null comment '',
    `task_code`         varchar(255) default null comment '',
    `task_name`         varchar(255) default null comment '',
    `bean_name`         varchar(255) default null comment '',
    `trigger_id`        varchar(255) default null comment '',
    `trigger_name`      varchar(255) default null comment '',
    `cron`              varchar(255) default null comment '',
    `start_time`        datetime     default null comment '',
    `end_time`          datetime     default null comment '',
    `cost_time`         int(8) default null comment '',
    `execute_result`    varchar(255) default null comment '',
    `exception_message` varchar(255) default null comment '',
    `trace_message`     varchar(255) default null comment '',
    PRIMARY KEY (`log_id`)
) engine = innodb comment = '';

drop table if exists `task_trigger_mapper`;
create table `task_trigger_mapper`
(

) engine = innodb comment = '';

drop table if exists `param_mapper`;
create table `param_mapper`
(

) engine = innodb comment = '';

drop table if exists `task_trigger_log_mapper`;
create table `task_trigger_log_mapper`
(

) engine = innodb comment = '';

drop table if exists `file_group_mapper`;
create table `file_group_mapper`
(

) engine = innodb comment = '';

drop table if exists `user`;
create table `user`
(
    `id`           varchar(255) not null comment '',
    `username`     varchar(255) default null comment '',
    `email`        varchar(255) default null comment '',
    `mobile_phone` varchar(255) default null comment '',
    `nick_name`    varchar(255) default null comment '',
    `password`     varchar(255) default null comment '',
    `enabled`      varchar(255) default null comment '',
    PRIMARY KEY (`id`)
) engine = innodb comment = '';

drop table if exists `task_info_mapper`;
create table `task_info_mapper`
(

) engine = innodb comment = '';

drop table if exists `user_role_mapper`;
create table `user_role_mapper`
(

) engine = innodb comment = '';

drop table if exists `menu`;
create table `menu`
(
    `id`             varchar(255) not null comment '',
    `pid`            varchar(255) default null comment '',
    `menu_code`      varchar(255) default null comment '',
    `menu_name`      varchar(255) default null comment '',
    `menu_type`      varchar(255) default null comment '',
    `menu_url_type`  varchar(255) default null comment '',
    `menu_url`       varchar(255) default null comment '',
    `menu_icon`      varchar(255) default null comment '',
    `menu_order`     int(8) default null comment '',
    `api_permission` varchar(255) default null comment '',
    `enabled`        varchar(255) default null comment '',
    PRIMARY KEY (`id`)
) engine = innodb comment = '';

drop table if exists `file_info`;
create table `file_info`
(
    `file_id`      varchar(255) not null comment '',
    `group_code`   varchar(255) default null comment '',
    `file_key`     varchar(255) default null comment '',
    `filename`     varchar(255) default null comment '',
    `file_type`    varchar(255) default null comment '',
    `content_type` varchar(255) default null comment '',
    `file_size`    bigint(11) default null comment '',
    `upload_time`  datetime     default null comment '',
    PRIMARY KEY (`file_id`)
) engine = innodb comment = '';

drop table if exists `file_link_mapper`;
create table `file_link_mapper`
(

) engine = innodb comment = '';

drop table if exists `dict_item`;
create table `dict_item`
(
    `dict_item_id`     varchar(255) not null comment '',
    `dict_group_id`    varchar(255) default null comment '',
    `dict_item_code`   varchar(255) default null comment '',
    `dict_item_name`   varchar(255) default null comment '',
    `dict_item_status` varchar(255) default null comment '',
    PRIMARY KEY (`dict_item_id`)
) engine = innodb comment = '';

drop table if exists `dict_group_mapper`;
create table `dict_group_mapper`
(

) engine = innodb comment = '';

drop table if exists `dict_item`;
create table `dict_item`
(
    `dict_item_id`     varchar(255) not null comment '',
    `dict_group_id`    varchar(255) default null comment '',
    `dict_item_code`   varchar(255) default null comment '',
    `dict_item_name`   varchar(255) default null comment '',
    `dict_item_status` varchar(255) default null comment '',
    PRIMARY KEY (`dict_item_id`)
) engine = innodb comment = '';

drop table if exists `file_group`;
create table `file_group`
(
    `group_id`        varchar(255) not null comment '',
    `group_code`      varchar(255) default null comment '',
    `group_name`      varchar(255) default null comment '',
    `max_file_size`   varchar(255) default null comment '',
    `allow_file_type` varchar(255) default null comment '',
    `deny_file_type`  varchar(255) default null comment '',
    PRIMARY KEY (`group_id`)
) engine = innodb comment = '';

drop table if exists `param`;
create table `param`
(
    `param_id`      varchar(255) not null comment '',
    `param_code`    varchar(255) default null comment '',
    `param_name`    varchar(255) default null comment '',
    `param_value`   varchar(255) default null comment '',
    `param_comment` varchar(255) default null comment '',
    PRIMARY KEY (`param_id`)
) engine = innodb comment = '';

drop table if exists `role`;
create table `role`
(
    `id`        varchar(255) not null comment '',
    `role_code` varchar(255) default null comment '',
    `role_name` varchar(255) default null comment '',
    `enabled`   varchar(255) default null comment '',
    PRIMARY KEY (`id`)
) engine = innodb comment = '';

drop table if exists `dict_group`;
create table `dict_group`
(
    `group_id`     varchar(255) not null comment '',
    `group_name`   varchar(255) default null comment '',
    `group_code`   varchar(255) default null comment '',
    `group_status` varchar(255) default null comment '',
    PRIMARY KEY (`group_id`)
) engine = innodb comment = '';

drop table if exists `user_role`;
create table `user_role`
(
    `user_id`   varchar(255) default null comment '',
    `role_id`   varchar(255) default null comment '',
    `role_code` varchar(255) default null comment ''
) engine = innodb comment = '';

drop table if exists `dict_item_mapper`;
create table `dict_item_mapper`
(

) engine = innodb comment = '';

drop table if exists `file_info_mapper`;
create table `file_info_mapper`
(

) engine = innodb comment = '';

drop table if exists `api_call_log_mapper`;
create table `api_call_log_mapper`
(

) engine = innodb comment = '';

drop table if exists `role_menu_mapper`;
create table `role_menu_mapper`
(

) engine = innodb comment = '';

drop table if exists `file_link`;
create table `file_link`
(
    `file_id`    varchar(255) not null comment '',
    `biz_id`     varchar(255) default null comment '',
    `group_code` varchar(255) default null comment '',
    PRIMARY KEY (`file_id`)
) engine = innodb comment = '';

drop table if exists `task_info`;
create table `task_info`
(
    `task_id`    varchar(255) not null comment '',
    `task_code`  varchar(255) default null comment '',
    `task_name`  varchar(255) default null comment '',
    `bean_name`  varchar(255) default null comment '',
    `enabled`    varchar(255) default null comment '',
    `task_data`  varchar(255) default null comment '',
    `is_default` varchar(255) default null comment '',
    PRIMARY KEY (`task_id`)
) engine = innodb comment = '';

