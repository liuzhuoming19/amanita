create table if not exists bookmark
(
    id varchar
(
    36
) default gen_random_uuid
(
) not null
    constraint bookmark_pk
    primary key,
    url varchar
(
    255
) not null,
    title varchar
(
    255
) not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP,
    host varchar
(
    127
) not null,
    excerpt varchar
(
    500
),
    is_read integer default 0 not null,
    is_invalid integer default 0 not null,
    user_id varchar
(
    36
) not null,
    note varchar
(
    500
)
    );

comment
on table bookmark is '书签';

comment
on column bookmark.url is '书签地址';

comment
on column bookmark.title is '书签标题';

comment
on column bookmark.host is '书签地址域名';

comment
on column bookmark.excerpt is '页面摘要';

comment
on column bookmark.is_read is '是否已读 0否 1是';

comment
on column bookmark.is_invalid is '是否失效链接 0否 1是';

comment
on column bookmark.user_id is '数据绑定的用户id';

alter table bookmark
    owner to root;

create table if not exists "user"
(
    id varchar
(
    36
) default gen_random_uuid
(
) not null
    constraint user_pk
    primary key,
    username varchar
(
    15
) not null,
    email varchar
(
    50
) not null,
    password varchar
(
    100
) not null,
    enabled integer default 1,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP
    );

comment
on table "user" is '用户';

comment
on column "user".username is '用户名';

comment
on column "user".email is '邮箱地址';

comment
on column "user".password is '密码（加密后）';

comment
on column "user".enabled is '是否可用（0不可用 1可用）';

alter table "user"
    owner to root;

create unique index if not exists user_id_uindex
    on "user" (id);

create unique index if not exists user_email_uindex
    on "user" (email);

create unique index if not exists user_name_uindex
    on "user" (username);

create table if not exists user_access
(
    id varchar
(
    36
) default gen_random_uuid
(
) not null
    constraint user_access_pk
    primary key,
    user_id varchar
(
    36
) not null,
    access_key varchar
(
    50
) not null,
    secret_key varchar
(
    50
) not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP
    );

comment
on table user_access is '用户access';

comment
on column user_access.user_id is '用户id';

comment
on column user_access.access_key is 'AccessKey';

comment
on column user_access.secret_key is 'SecretKey';

alter table user_access
    owner to root;

create unique index if not exists user_access_id_uindex
    on user_access (id);

create table if not exists user_setting
(
    id varchar
(
    36
) default gen_random_uuid
(
) not null
    constraint user_setting_pk
    primary key,
    user_id varchar
(
    36
) not null,
    allow_excerpt_page_archive integer default 0 not null,
    allow_bookmark_change_history integer default 0 not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP
    );

comment
on table user_setting is '用户设置';

comment
on column user_setting.user_id is '用户id';

comment
on column user_setting.allow_excerpt_page_archive is '是否开启页面简述存档（0否 1是）';

comment
on column user_setting.allow_bookmark_change_history is '是否开启书签修改历史（开启后可回溯历史）(0否 1是)';

alter table user_setting
    owner to root;

create unique index if not exists user_setting_id_uindex
    on user_setting (id);

create unique index if not exists user_setting_user_id_uindex
    on user_setting (user_id);

create table if not exists tag
(
    id varchar
(
    36
) default gen_random_uuid
(
) not null
    constraint tag_pk
    primary key,
    name varchar
(
    15
) not null,
    color varchar
(
    15
) not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP
    );

comment
on table tag is '标签';

comment
on column tag.name is '标签名称';

comment
on column tag.color is '标签颜色';

alter table tag
    owner to root;

create unique index if not exists tag_id_uindex
    on tag (id);

create unique index if not exists tag_name_uindex
    on tag (name);

create table if not exists bookmark_tag
(
    id varchar
(
    36
) not null
    constraint bookmark_tag_pk
    primary key,
    bookmark_id varchar
(
    36
) not null,
    tag_id varchar
(
    36
) not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP
    );

comment
on table bookmark_tag is '书签标签';

comment
on column bookmark_tag.bookmark_id is '书签id';

comment
on column bookmark_tag.tag_id is '标签id';

alter table bookmark_tag
    owner to root;

create unique index if not exists bookmark_tag_id_uindex
    on bookmark_tag (id);

create table if not exists vip
(
    id varchar
(
    36
) default gen_random_uuid
(
) not null
    constraint vip_pk
    primary key,
    user_id varchar
(
    36
) not null,
    start_time timestamp not null,
    end_time timestamp not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP
    );

comment
on table vip is '会员';

comment
on column vip.user_id is '用户id';

comment
on column vip.start_time is '会员开始时间';

comment
on column vip.end_time is '会员结束时间';

alter table vip
    owner to root;

create unique index if not exists vip_id_uindex
    on vip (id);

create table if not exists vip_level
(
    id varchar
(
    36
) default gen_random_uuid
(
) not null
    constraint vip_level_pk
    primary key,
    user_id varchar
(
    36
) not null,
    level integer default 0 not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP
    );

comment
on table vip_level is '会员等级';

comment
on column vip_level.user_id is '用户id';

comment
on column vip_level.level is '会员等级';

alter table vip_level
    owner to root;

create unique index if not exists vip_level_id_uindex
    on vip_level (id);

create unique index if not exists vip_level_user_id_uindex
    on vip_level (user_id);

create table if not exists "order"
(
    id varchar
(
    36
) not null
    constraint order_pk
    primary key,
    order_no varchar
(
    20
) not null,
    user_id varchar
(
    36
) not null,
    amount numeric not null,
    discount numeric default 0,
    paid_amount numeric not null,
    transaction_no varchar
(
    50
),
    status integer default 0,
    order_info varchar
(
    1000
) not null,
    create_time timestamp default CURRENT_TIMESTAMP,
    update_time timestamp default CURRENT_TIMESTAMP,
    payment_type varchar
(
    15
) not null
    );

comment
on table "order" is '订单';

comment
on column "order".order_no is '订单号';

comment
on column "order".user_id is '用户id';

comment
on column "order".amount is '应付金额';

comment
on column "order".discount is '打折金额';

comment
on column "order".paid_amount is '实付金额';

comment
on column "order".transaction_no is '第三方订单号';

comment
on column "order".status is '订单状态 0创建订单 1已付款';

comment
on column "order".order_info is '支付信息（json）';

comment
on column "order".payment_type is '支付类型 ALIPAY支付宝 WXPAY微信支付 OTHER其他';

alter table "order"
    owner to root;

create unique index if not exists order_id_uindex
    on "order" (id);

create unique index if not exists order_order_no_uindex
    on "order" (order_no);

