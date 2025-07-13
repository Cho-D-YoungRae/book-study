create table dist_lock
(
    name   varchar(100) not null comment '락 이름. 개별 잠금을 구분하기 위한 값',
    owner  varchar(100) comment '락 소유자. 잠금 소유자를 구분하기 위한 값으로, 여러 스레드가 같은 이름의 잠금을 시도할 때 충돌을 처리',
    expiry datetime comment '락 만료 시간. 잠금 소유 만료 시간으로, 한 소유자가 오랜 시간 잠금을 소유하지 못하도록 함',
    primary key (name)
);
