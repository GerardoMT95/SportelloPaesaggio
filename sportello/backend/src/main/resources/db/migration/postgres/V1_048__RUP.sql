CREATE TABLE "rup"
(
    id_organizzazione int 		   NOT NULL,
    username 		  varchar(100) NOT NULL,
    denominazione 	  varchar(100) NOT NULL,
    data_scadenza 	  date,
	CONSTRAINT rup_pkey PRIMARY KEY (id_organizzazione, username)
);
