update "presentazione_istanza"."template_email"
set "visibilita" = ',ED_,REG_,'
where "codice" in ('NOT_SOSP', 'NOT_ARC', 'NOT_ATT', 'REQ_ARC', 'REQ_ATT');
