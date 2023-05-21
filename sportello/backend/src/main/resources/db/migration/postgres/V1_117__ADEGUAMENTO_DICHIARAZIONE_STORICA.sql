DROP TABLE presentazione_istanza.dichiarazione;

CREATE TABLE presentazione_istanza.dichiarazione (
	id bigserial primary key,
	testo text NOT NULL,
	label_cb text NOT NULL,
	id_procedimento int not null,
	data_inizio timestamp NOT NULL DEFAULT current_timestamp,
	data_fine timestamp NULL,
	CONSTRAINT id_procedimento_fkey FOREIGN KEY (id_procedimento) REFERENCES presentazione_istanza.tipo_procedimento(id)
);

INSERT INTO presentazione_istanza.dichiarazione (id_procedimento,testo, label_cb,data_inizio)
SELECT id,
'Il richiedente e/o il tecnico di riferimento per l''istanza, consapevole/i delle penalità previste in caso di false attestazioni, dichiarazioni mendaci o che affermano fatti non conformi al vero, ai sensi dell’articolo 76 del d.P.R. 28 dicembre 2000, n. 445 e degli artt. 483,495 e 496 del Codice Penale, sotto la propria responsabilità,', 
'DICHIARA/DICHIARANO quanto specificato nella scheda tecnica',
'2000-01-01 00:00:00'
FROM presentazione_istanza.tipo_procedimento;
