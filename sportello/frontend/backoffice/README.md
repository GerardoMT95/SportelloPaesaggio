Progetto front-end per Microservizio sportello paesaggio per istruttoria autorizzazioni paesaggistiche WEB APP lato Autorità procedente
===

Il progetto è basato su Angular 7 e si interfaccia con 
- un backend (autpae) 
- con WSO2 IS per l'autenticazione
- con i servizi di mappa erogati da webapps.sit.puglia.it


Per installare localmente Node e npm:
 apriamo una finestra del terminale, portiamoci nella cartella del README.md e lanciamo

* mvn install

Si attenda che sia tutto terminato correttamente.

* dist: directory in cui sarà presente la distribuzione finale (ovvero tutto l’HTML e il CSS generato)

* node e node_modules: directory in cui sono installati node ed i relativi moduli

L'install si occuperà di scaricare le librerie presenti nel file package.json, nel nostro caso installa anche node.js e npm.

Settare nell'environment di sviluppo i percorsi all'npm e node locali al progetto:

**Per chi utilizza Windows:**

* set PATH=%cd%\node\node_modules\npm\node_modules\npm-lifecycle\node-gyp-bin;%cd%\node_modules\.bin;%cd%\node;%PATH%

**Per chi utilizza Linux:**

* export PATH="$(pwd)/node/node_modules/npm/node_modules/npm-lifecycle/node-gyp-bin:$(pwd)/node_modules/.bin:$(pwd)/node:$PATH"


Per avviare il progetto basterà digitare nella console di Visual Code

* npm start

Il nuovo progetto dovrebbe quindi essere disponibile all'indirizzo http://localhost:4200

