create or replace view v_assegnazione as(
select af.*,af2.username_utente as username_rup,af2.denominazione_utente as denominazione_rup from assegnamento_fascicolo af left 
join assegnamento_fascicolo af2 on af2.id_fascicolo =af.id_fascicolo and af2.id_organizzazione =af.id_organizzazione  
and af2.fase =af.fase and af2.rup =true where af.rup =false);     