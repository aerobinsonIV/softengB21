create table project
(
    name     varchar(100)   not null primary key,
    archived boolean not null default false
);

create table task
(
    id      int                                       not null primary key auto_increment,
    name    varchar(100)                                         not null,
    parent  int                                          null,
    status  enum ('complete', 'in_progress', 'not_markable') not null default 'in_progress',
    project varchar(100)                                         not null,
    foreign key (parent) references task (id) on delete cascade on update cascade,
    foreign key (project) references project (name) on delete cascade on update cascade
);

create table teammate
(
    name    varchar(100) not null,
    project varchar(100) not null,
    primary key (name, project),
    foreign key (project) references project (name) on delete cascade on update cascade
);

create table task_assignments
(
    task             int  not null,
    teammate_name    varchar(100) not null,
    teammate_project varchar(100) not null,
    primary key (task, teammate_name, teammate_project),
    foreign key (task) references task (id) on delete cascade on update cascade,
    foreign key (teammate_name, teammate_project) references teammate (name, project) on delete cascade on update cascade
);
