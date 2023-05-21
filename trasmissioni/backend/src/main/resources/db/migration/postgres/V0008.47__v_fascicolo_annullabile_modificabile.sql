create or replace view v_codice_fascicolo_annullabile_modificabile as 
select codice,
(
stato in ('TRANSMITTED','SELECTED')
or 
(stato in ('FINISHED') and 
 (esito_verifica in 
  ('NOT_SELECTED','NOT_SAMPLED','POSITIVE','NEGATIVE'))
 )
) 
and 
esito_verifica in  ('NOT_SELECTED','NOT_SAMPLED','POSITIVE','NEGATIVE','SAMPLING_PENDING','CHECK_IN_PROGRESS')
as is_annullabile,
(stato='TRANSMITTED' or 
 stato='FINISHED') 
 and 
esito_verifica in  ('NOT_SELECTED','NOT_SAMPLED','POSITIVE') 
as is_modificabile,
stato='ON_MODIFY'
as is_in_modifica
from fascicolo 
where 
deleted = false;    
