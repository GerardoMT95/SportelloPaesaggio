-- Creo il campo booleano "isPec" --
alter table "template_destinatario" add column "isPec" boolean default false;
alter table "template_destinatario_default" add column "isPec" boolean default false;

-- Riporto "pec" in "email" --
update "template_destinatario"
set "isPec" = ("pec" is not null and trim("pec") not like ''),
	"email" = "pec"
where ("email" is null or trim("email") like '');

update "template_destinatario_default"
set "isPec" = ("pec" is not null and trim("pec") not like ''),
	"email" = "pec"
where ("email" is null or trim("email") like '');

-- Droppo la colonna pec --
alter table "template_destinatario" drop column "pec";
alter table "template_destinatario_default" drop column "pec";

-- Rinomino la colonna isPec in pec --
alter table "template_destinatario" rename column "isPec" to "pec";
alter table "template_destinatario_default" rename column "isPec" to "pec";