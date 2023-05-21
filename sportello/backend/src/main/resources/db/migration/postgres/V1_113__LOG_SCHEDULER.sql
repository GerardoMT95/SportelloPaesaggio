CREATE TABLE presentazione_istanza.log_scheduler
(id bigserial NOT NULL
,pratica_id uuid NOT NULL 
,executor varchar(200) NOT NULL  
,executor_time timestamp NOT NULL default current_timestamp   
,primary key(id)
);

ALTER TABLE presentazione_istanza.log_scheduler  ADD CONSTRAINT scheduler_fk FOREIGN KEY (pratica_id) 
REFERENCES presentazione_istanza.pratica(id);

CREATE INDEX presentazione_istanza_log_scheduler_idx_1 on presentazione_istanza.log_scheduler(pratica_id);
CREATE INDEX presentazione_istanza_log_scheduler_idx_2 on presentazione_istanza.log_scheduler(executor);
CREATE INDEX presentazione_istanza_log_scheduler_idx_3 on presentazione_istanza.log_scheduler(executor_time);

COMMENT on TABLE presentazione_istanza.log_scheduler                is 'Log scheduler degli executor asincroni associati alla pratica';
COMMENT on COLUMN presentazione_istanza.log_scheduler.id            is 'Id serial';
COMMENT on COLUMN presentazione_istanza.log_scheduler.pratica_id      is 'Fk su pratica';
COMMENT on COLUMN presentazione_istanza.log_scheduler.executor      is 'Executor name';  
COMMENT on COLUMN presentazione_istanza.log_scheduler.executor_time is 'Data fine esecuzione con successo';   


