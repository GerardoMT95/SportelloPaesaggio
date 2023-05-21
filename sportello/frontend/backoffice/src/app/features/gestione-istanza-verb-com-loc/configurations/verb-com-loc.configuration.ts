import { TableConfig } from "src/app/core/models/header.model";

export class ConfVerbComLoc
{
    static get headers(): TableConfig[] 
    {
        let headers: TableConfig[] = [
            {
                field: "tipoAllegato",
                header: "Tipologia",
                type: "type"
            },
            {
                field: "nome",
                header: "Nome file",
                type: "text"
            },
            {
                field: "data",
                header: "Data caricamento",
                type: "date"
            },
            {
                field: "numeroIstanze",
                header: "fascicolo.verb_com_loc.dettaglio",
                type: "counter",
                eventId: "SHOW",
                width: 3
            }
        ]

        return headers;
    }
}