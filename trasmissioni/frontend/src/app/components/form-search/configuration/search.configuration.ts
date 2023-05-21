import { SearchFields, MultiselectOptions, DateOptions, DropdownOptions } from './../../model/form-search/formSearch.models';
import { InputType } from "../../model/form-search/formSearch.models";
import { CONST } from 'src/shared/constants';
import { SelectItem } from 'primeng/components/common/selectitem';

export class SearchConfiguration
{
    public static getUlterioreDocumentazioneSearchFields(): SearchFields[]
    {
        return [
        {
            label: "Titolo documento",
            formControlName: "titolo",
            type: InputType.text,
            className: "form-control",
            extra: {
                maxlength: CONST.MAX_LEN_TITOLO_DOCUMENTO
            }
        },
        {
            label: "Descrizione contenuto",
            formControlName: "descrizione",
            type: InputType.textarea,
            className: "form-control",
            extra: {
                maxlength: CONST.MAX_LEN_DESCRIZIONE_DOCUMENTO
            }
        },
        {
            label: "Destinatari",
            formControlName: "destinatario",
            type: InputType.text,
            className: "form-control",
            extra: {
                maxlength: CONST.MAX_LEN_DESTINATARIO_EMAIL
            }
        },
        {
            label: "Visibile A",
            formControlName: "visibileA",
            type: InputType.multiselect,
            className: "dflex",
            extra: <MultiselectOptions>{
                filter: false,
                options: CONST.entiTemplate,
                defaultLabel: "-- Seleziona una voce --",
                optionLabel: "label",
                filterPlaceholder: null
            }
        },
        {
            label: "Inserito da",
            formControlName: "inseritoDa",
            type: InputType.text,
            className: "form-control",
            extra: {
                maxlength: CONST.MAX_LEN_NOME+CONST.MAX_LEN_COGNOME
            }
        },
        {
            label: "Data condivisione",
            formControlName: "dataCondivisione",
            type: InputType.dateRange,
            className: "form-control nop",
            extra: <DateOptions>{
                format: "dd/mm/yy",
                locale: CONST.IT,
                minDate: null,
                maxDate: null,
                monthNavigator: true,
                yearNavigator: true,
                yearRange: "1950:" + CONST.NOW_YEAR
            }
        }];
    }

    public static getCorrispondenzaSearchFields(): SearchFields[]
    {
        return [
            {
                label: "Mittente",
                formControlName: "mittente",
                className: "form-control",
                type: InputType.text,
                extra: {
                    maxlength: CONST.MAX_LEN_EMAIL_PEC
                }
            },
            {
                label: "Destinatario",
                formControlName: "destinatario",
                className: "form-control",
                type: InputType.text,
                extra: {
                    maxlength: CONST.MAX_LEN_DESTINATARIO_EMAIL
                }
            },
            {
                label: "Oggetto",
                formControlName: "oggetto",
                className: "form-control",
                type: InputType.text,
                extra: {
                    maxlength: CONST.MAX_LEN_OGGETTO_EMAIL
                }
            },
            {
                label: "Data",
                formControlName: "dataInvio",
                className: "form-control",
                type: InputType.dateRange,
                extra:<DateOptions> {
                    format: "dd/mm/yy",
                    locale: CONST.IT,
                    maxDate: null,
                    minDate: null,
                    monthNavigator: true,
                    yearNavigator: true,
                    yearRange: "1950:" + CONST.NOW_YEAR
                }
            },
        ]
    }

    public static getRubricaSearchFields(listaTipologie: SelectItem[]): SearchFields[]
    {
        return [
            {
                label: "Tipologia ente",
                formControlName: "tipoEnte",
                className: "dflex",
                type: InputType.select,
                extra: <DropdownOptions>{
                    filterPlaceholder: null,
                    filter: false,
                    isGroup: false,
                    options: listaTipologie,
                    defaultLabel: "Seleziona una voce"
                }
            },
            {
                label: "Denominazione ente",
                formControlName: "nome",
                className: "form-control",
                type: InputType.text,
                extra: {
                    maxlength: CONST.MAX_LEN_DENOMINAZIONE_ENTE
                }
            },
        ]
    }

    public static importSportelloSearch(): SearchFields[]
    {
        return [
        {
            label: "Codice trasmissione",
            formControlName: "codiceTrasmissione",
            type: InputType.text,
            className: "form-control",
            extra: {
                maxlength: CONST.MAX_LEN_TITOLO_DOCUMENTO
            }
        },
        ];
    }
}