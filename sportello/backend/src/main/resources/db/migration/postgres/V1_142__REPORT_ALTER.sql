ALTER TABLE presentazione_istanza.report
ADD COLUMN mail varchar(200);

comment on column presentazione_istanza.report.mail is 'Mail Utente del report';