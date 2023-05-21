alter table "common"."paesaggio_commissione_locale" drop constraint "paesaggio_commissione_locale_pkey";
alter table "common"."paesaggio_commissione_locale" drop column "id";
alter table "common"."paesaggio_commissione_locale" add primary key("id_ente_delegato", "id_commissione_locale");