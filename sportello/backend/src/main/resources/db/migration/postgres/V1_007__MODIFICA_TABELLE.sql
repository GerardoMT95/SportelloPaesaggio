alter table "presentazione_istanza"."tipi_e_qualificazioni"
drop column "zona";

alter table "presentazione_istanza"."tipi_e_qualificazioni"
add column "key" integer;

alter table "presentazione_istanza"."tipi_e_qualificazioni"
add column "dependency" integer;

alter table "presentazione_istanza"."tipi_e_qualificazioni"
add column "hasText" boolean; 

drop table "presentazione_istanza"."caratterizzazione_intervento_values";
drop table "presentazione_istanza"."qualificazione_31_2017_values";
drop table "presentazione_istanza"."qualificazione_42_2004_values";

create table "presentazione_istanza"."descrizione_scheda_tecnica_values"(
	"pratica_id" uuid
		references "presentazione_istanza"."pratica",
	"codice" varchar(50)
		references "presentazione_istanza"."tipi_e_qualificazioni",
	"text" varchar(1000),
	primary key("pratica_id", "codice")
);
COMMENT ON TABLE "presentazione_istanza"."descrizione_scheda_tecnica_values" 
IS 'Tabella in cui confluiscono tutti gli elementi che descrivono l''intervento';

create table "presentazione_istanza"."procedimento_qualificazioni"(
	"id_tipo_procedimento" integer not null 
		references "presentazione_istanza"."tipo_procedimento",
	"codice_tipi_e_qualificazioni" varchar(50) not null
		references "presentazione_istanza"."tipi_e_qualificazioni",
	primary key ("id_tipo_procedimento", "codice_tipi_e_qualificazioni")
);
COMMENT ON TABLE "presentazione_istanza"."procedimento_qualificazioni" 
IS 'Tabella che permette di ricavare, per ogni tipo di procedimento, le possibili descrizioni che il fascicolo può avere';

COMMENT ON TABLE "presentazione_istanza"."tipi_e_qualificazioni" 
IS 'Tabella contenente tutte le possibili descrizioni dell''intervento. Per ogni record sono presenti informazioni quali: codice (univoco per ogni record), label (descrizione del record che viene mostrata anche in FE), key (valore numerico che indica su quali record la selezione dell''elemento può avere effetto), dependency (se diverso da null vuol dire che deve essere stato selezionato un record con key = depedecy, altrimenti la scelta non è valida), stile (indica se la descrizione va mostrata come checkbox o come radiobutton in FE), hasText (true se può essere associata una stringa di dettaglio all''elemento)';

