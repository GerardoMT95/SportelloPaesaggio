ALTER TABLE presentazione_istanza.tipo_contenuto ADD 
check_pagamento bpchar(2) NULL;

COMMENT ON column presentazione_istanza.tipo_contenuto.check_pagamento is 'BL=Scansione marca da bollo RO=Ricevuta oneri';

INSERT INTO presentazione_istanza.tipo_contenuto 
(id, descrizione, descrizione_estesa, sezione, multiple,check_pagamento) 
VALUES(20, 'Scansione Marca da bollo', 'Scansione Marca da bollo', 'DOC_AMMINISTRATIVA_E', true,'BL');

update presentazione_istanza.tipo_contenuto set check_pagamento='RO' where 
descrizione ='Ricevuta di pagamento oneri istruttori';

ALTER TABLE presentazione_istanza.referenti ALTER COLUMN ditta_ente TYPE varchar(400) USING ditta_ente::varchar;

ALTER TABLE presentazione_istanza.pratica ADD esonero_oneri boolean NULL;
COMMENT ON column presentazione_istanza.pratica.esonero_oneri is 'true se dichiara di essere esonerato dal pagamento degli oneri';
ALTER TABLE presentazione_istanza.pratica ADD esonero_bollo boolean NULL;
COMMENT ON column presentazione_istanza.pratica.esonero_bollo is 'true se dichiara di essere esonerato dal pagamento del bollo';
