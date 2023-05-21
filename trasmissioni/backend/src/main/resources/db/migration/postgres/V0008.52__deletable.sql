ALTER TABLE allegato ADD deletable boolean NULL DEFAULT true;

COMMENT on COLUMN allegato.deletable IS 'true indica che è cancellabile, false invece indica che non è cancellabile su alfresco';

CREATE TABLE doc_caricati_sportello (
	id bigserial primary key,
	id_doc_caricato UUID NOT NULL,
	date_insert timestamp NOT NULL DEFAULT current_timestamp,
	user_insert varchar(100),
	deleted boolean DEFAULT false,
	id_fascicolo bigint NOT NULL,
	date_updated timestamp,
	user_updated varchar(100),
	CONSTRAINT doc_caricati_sportello FOREIGN KEY (id_fascicolo) REFERENCES fascicolo(id)
);

COMMENT on TABLE doc_caricati_sportello IS 'operazioni di import da sportello ambiente a partire da doc caricato';
COMMENT on COLUMN doc_caricati_sportello.id_doc_caricato IS 'UUID del documento utilizzato per import';
COMMENT on COLUMN doc_caricati_sportello.id_fascicolo IS 'id del fascicolo in cui è stato effettuato import';
COMMENT on COLUMN doc_caricati_sportello.deleted IS 'true se dopo averlo utilizzato è stato successivamente cancellata la bozza del fascicolo e quindi è riutilizzabile lo stesso id_doc_caricato';

CREATE INDEX doc_caricati_sportello_id_doc_caricato ON  doc_caricati_sportello USING btree (deleted,id_doc_caricato);





 