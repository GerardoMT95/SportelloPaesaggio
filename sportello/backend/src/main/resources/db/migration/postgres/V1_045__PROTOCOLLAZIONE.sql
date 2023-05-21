CREATE TABLE "protocollo"
(
    "id_protocollo"						uuid						not null				PRIMARY KEY,
    "codiceAmministrazione"				character varying(200),
    "codiceAOO"							character varying(200),
    "codiceRegistro"					character varying(200),
    "dataRegistrazione"					character varying(200),
    "numeroProtocollo"					character varying(100),
    "hostProxy"							character varying(100),
    "portProxy"							character varying(100),
    "usernameProxy"						character varying(100),
    "passwordProxy"						character varying(100),
    "urlNotProxy"						character varying(100),
    "clientProtocolloEndpoint"			character varying(100),
    "clientProtocolloAdministration"	character varying(100),
    "clientProtocolloAOO"				character varying(100),
    "clientProtocolloRegister"			character varying(100),
    "clientProtocolloUser"				character varying(100),
    "clientProtocolloPassword"			character varying(100),
    "clientProtocolloHashAlgorihtm"		character varying(100),
    "protocolObject"					character varying(100)
);
