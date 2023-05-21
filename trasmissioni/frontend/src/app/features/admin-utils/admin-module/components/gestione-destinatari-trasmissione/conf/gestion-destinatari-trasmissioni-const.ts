import { EmailOrganizzazioneSearch } from './gestione-destinatari-trasmissione-conf';
import { SelectItem } from 'primeng/primeng';
import { InputType, SearchFields } from 'src/app/components/model/form-search/formSearch.models';
import { TableHeader } from "src/shared/models/table-header";

export class ConstDestTrasm
{
    public static readonly columnsWithEnte: Array<TableHeader> = [
        {field: 'email'                      , header: 'Indirizzo email/PEC' },
        {field: 'pec'                        , header: 'Tipo indirizzo'},
        {field: 'denominazione'              , header: 'Denominazione'},
        {field: 'denominazioneOrganizzazione', header: 'Organizzazione'},
        {field: 'denominazioneEnte'          , header: 'Territorio associato'}
    ];

    public static readonly columnsWithoutEnte: Array<TableHeader> = [
        {field: 'email'                      , header: 'Indirizzo email/PEC' },
        {field: 'pec'                        , header: 'Tipo indirizzo'},
        {field: 'denominazione'              , header: 'Denominazione'},
        {field: 'denominazioneOrganizzazione', header: 'Organizzazione'}

    ];

    public static readonly initSearch: EmailOrganizzazioneSearch = {
        id                          : null,
        email                       : null,
        denominazione               : null,
        pec                         : null,
        organizzazioneId            : null,
        enteId                      : null,
        denominazioneEnte			: null,
	    denominazioneOrganizzazione	: null,
	    tipoOrganizzazione			: null,
        organizzazione              : null,

        page                        : 1,
        limit                       : 5,
        sortBy                      : 'denominazione',
        sortType                    : 'ASC'
    }

    public static getSearchConfigurationEmail(listEnti: Array<SelectItem>): Array<SearchFields>
    {
        return  [
            {
                label: "Organizzazione",
                formControlName: "organizzazioneId",
                className: "dflex",
                type: InputType.select,
                extra: {
                    options: listEnti,
                    filter: true,
                    optionLabel: "label",
                    defaultLabel: '-- Seleziona una organizzazione --'
                }
            },
            {
                label: "Email/PEC",
                formControlName: "email",
                className: "form-control",
                type: InputType.text,
                extra: { maxlength: 200 }
            },
            {
                label: "Denominazione",
                formControlName: "denominazione",
                className: "form-control",
                type: InputType.text,
                extra: {
                    maxlength: 200
                }
            },
       ];
    }

}