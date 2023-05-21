update "presentazione_istanza"."ulteriori_contesti_paesaggistici"
set "label"='BP - Zone di interesse archeologico'
where "codice"='ZONE_INT_ARCH';

insert into "presentazione_istanza"."ucp_tipo_procedimento"("codice_ucp", "id_tipo_procedimento")
select "codice", '1' 
from "presentazione_istanza"."ulteriori_contesti_paesaggistici";

insert into "presentazione_istanza"."ucp_tipo_procedimento"("codice_ucp", "id_tipo_procedimento")
select "codice", '2' 
from "presentazione_istanza"."ulteriori_contesti_paesaggistici";

insert into "presentazione_istanza"."ucp_tipo_procedimento"("codice_ucp", "id_tipo_procedimento")
select "codice", '3'
from "presentazione_istanza"."ulteriori_contesti_paesaggistici";

insert into "presentazione_istanza"."ucp_tipo_procedimento"("codice_ucp", "id_tipo_procedimento")
select "codice", '4' 
from "presentazione_istanza"."ulteriori_contesti_paesaggistici"
where "codice" not in ('TERRITORI_COSTIERI', 'TERRITORI_LAGHI','ACQUE_PUBBLICHE', 
					   'BOSCHI', 'ZONE_UMIDE_RAMSAR', 'PARCHI_E_RISERVE',
					   'AREE_NOTEVOLE_INSTERESSE_PUBB', 'ZONE_USI_CIVICI','ZONE_INT_ARCH');

--select "codice" as "codice_ucp", '1' as "id_tipo_procedimento"
--into "presentazione_istanza"."ucp_tipo_procedimento"
--from "presentazione_istanza"."ulteriori_contesti_paesaggistici";

--select "codice" as "codice_ucp", '2' as "id_tipo_procedimento"
--into "presentazione_istanza"."ucp_tipo_procedimento"
--from "presentazione_istanza"."ulteriori_contesti_paesaggistici";

--select "codice" as "codice_ucp", '3' as "id_tipo_procedimento"
--into "presentazione_istanza"."ucp_tipo_procedimento"
--from "presentazione_istanza"."ulteriori_contesti_paesaggistici";

--select "codice" as "codice_ucp", '4' as "id_tipo_procedimento"
--into "presentazione_istanza"."ucp_tipo_procedimento"
--from "presentazione_istanza"."ulteriori_contesti_paesaggistici"
--where "codice" not in ('TERRITORI_COSTIERI', 'TERRITORI_LAGHI','ACQUE_PUBBLICHE', 
--					   'BOSCHI', 'ZONE_UMIDE_RAMSAR', 'PARCHI_E_RISERVE',
--					   'AREE_NOTEVOLE_INSTERESSE_PUBB', 'ZONE_USI_CIVICI','ZONE_INT_ARCH');