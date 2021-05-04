
create table cities
(
    id          int auto_increment primary key,
    name        varchar(255) not null,
    postcode    varchar(10)  not null,
    constraint cities_postcode_u_index unique (postcode)
) charset = utf8mb4 auto_increment = 3;

create table roles
(
    id      int auto_increment primary key,
    name    varchar(30) not null,
    enabled tinyint(1)  not null,
    constraint roles_name_u_index
        unique (name)
) charset = utf8mb4;

create table permissions
(
    id          int auto_increment primary key,
    code        varchar(30)  not null,
    description varchar(255) not null,
    constraint permissions_code_u_index
        unique (code)
) charset = utf8mb4;

create table role_has_permission
(
    role_id       int not null,
    permission_id int not null,
    constraint role_has_permission_role_id_permission_id_u_index
        unique (role_id, permission_id),
    constraint role_has_permission_permissions_id_fk
        foreign key (permission_id) references permissions (id)
            on delete cascade,
    constraint role_has_permission_roles_id_fk
        foreign key (role_id) references roles (id)
            on delete cascade
) charset = utf8mb4;

create table users
(
    id       int auto_increment primary key,
    username varchar(20)       not null,
    email    varchar(100)      not null,
    password varchar(255)      not null,
    state    tinyint default 0 not null,
    role_id  int               not null,
    constraint users_email_u_index
        unique (email),
    constraint users_username_u_index
        unique (username),
    constraint users_roles_id_fk
        foreign key (role_id) references roles (id)
) charset = utf8mb4;

create table persons
(
    id         int auto_increment
        primary key,
    is_natural tinyint(1) not null
) charset = utf8mb4;

create table legal_persons
(
    person_id int          not null primary key,
    cuit      varchar(14)  not null,
    name      varchar(255) not null,
    constraint legal_persons_cuit_u_index
        unique (cuit),
    constraint legal_persons_persons_id_fk
        foreign key (person_id) references persons (id)
            on delete cascade
) charset = utf8mb4;

create table natural_persons
(
    person_id  int          not null primary key,
    cuil       varchar(14)  not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    birthdate  date         not null,
    constraint natural_persons_cuil_u_index
        unique (cuil),
    constraint natural_persons_persons_id_fk
        foreign key (person_id) references persons (id)
            on delete cascade
) charset = utf8mb4;

create table citizens
(
    id                int auto_increment primary key,
    natural_person_id int not null,
    user_id           int not null,
    constraint citizens_natural_person_id_u_index
        unique (natural_person_id),
    constraint citizens_user_id_u_index
        unique (user_id),
    constraint cities_natural_persons_person_id_fk
        foreign key (natural_person_id) references natural_persons (person_id)
            on delete cascade,
    constraint cities_users_id_fk
        foreign key (user_id) references users (id)
            on delete cascade
) charset = utf8mb4;

create table space_campaigns
(
    id      int auto_increment primary key,
    message text not null
) charset = utf8mb4;

create table space_categories
(
    id          int auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255) null,
    constraint space_categories_name_u_index
        unique (name)
) charset = utf8mb4;

create table spaces
(
    id                int auto_increment primary key,
    name              varchar(255)   not null,
    available         tinyint(1)     not null,
    is_closed         tinyint(1)     not null,
    capacity          int            not null,
    latitude          double         not null,
    longitude         double         not null,
    radius            float unsigned not null,
    category_id       int            not null,
    entry_campaign_id int            not null,
    exit_campaign_id  int            not null,
    constraint spaces_name_u_index
        unique (name),
    constraint spaces_space_campaigns_id_fk
        foreign key (entry_campaign_id) references space_campaigns (id),
    constraint spaces_space_campaigns_id_fk_2
        foreign key (exit_campaign_id) references space_campaigns (id),
    constraint spaces_space_categories_id_fk
        foreign key (category_id) references space_categories (id)
) charset = utf8mb4;

create table closed_spaces
(
    space_id int auto_increment primary key,
    street   varchar(255) not null,
    city_id  int          not null,
    constraint closed_spaces_street_u_index
        unique (street),
    constraint closed_spaces_spaces_id_fk
        foreign key (space_id) references spaces (id)
            on delete cascade
) charset = utf8mb4;

create table responsible
(
    closed_space_id int      not null,
    person_id       int      not null,
    start_date      datetime not null,
    end_date        datetime null,
    constraint responsible_closed_spaces_space_id_fk
        foreign key (closed_space_id) references closed_spaces (space_id)
            on delete cascade,
    constraint responsible_persons_id_fk
        foreign key (person_id) references persons (id)
) charset = utf8mb4;

create table device_brands
(
    id   int auto_increment primary key,
    name varchar(255) not null,
    constraint device_brands_name_u_index
        unique (name)
) charset = utf8mb4;

create table device_companies
(
    id   int auto_increment primary key,
    name varchar(255) not null,
    constraint device_companies_name_u_index
        unique (name)
) charset = utf8mb4;

create table device_models
(
    id              int auto_increment primary key,
    name            varchar(255) not null,
    device_brand_id int          not null,
    constraint device_models_name_u_index
        unique (name),
    constraint device_models_device_brands_id_fk
        foreign key (device_brand_id) references device_brands (id)
) charset = utf8mb4;

create table devices
(
    id                int auto_increment primary key,
    number            varchar(255)                        not null,
    citizen_id        int                                 not null,
    device_company_id int                                 not null,
    device_model_id   int                                 not null,
    start_date        timestamp default CURRENT_TIMESTAMP null,
    end_date          timestamp                           null,
    constraint devices_citizens_id_fk
        foreign key (citizen_id) references citizens (id)
            on delete cascade,
    constraint devices_device_companies_id_fk
        foreign key (device_company_id) references device_companies (id),
    constraint devices_device_models_id_fk
        foreign key (device_model_id) references device_models (id)
) charset = utf8mb4;

create table notifications
(
    id        int auto_increment primary key,
    device_id int      not null,
    message   text     not null,
    date      datetime not null,
    date_read datetime null,
    constraint notifications_devices_id_fk
        foreign key (device_id) references devices (id)
            on delete cascade
) charset = utf8mb4;

create table movements
(
    id         int auto_increment primary key,
    device_id  int                                 not null,
    space_id   int                                 not null,
    amount     int                                 not null,
    entry_date timestamp default CURRENT_TIMESTAMP null,
    exit_date  timestamp                           null,
    constraint movements_devices_id_fk
        foreign key (device_id) references devices (id)
            on delete cascade,
    constraint movements_spaces_id_fk
        foreign key (space_id) references spaces (id)
            on delete cascade
) charset = utf8mb4;