create table expensive_things (
    expense_id varchar(36) not null primary key,
    expense_name varchar(110) not null,
    expense_description varchar(255) null,
    expense_date timestamp not null default now()
);
