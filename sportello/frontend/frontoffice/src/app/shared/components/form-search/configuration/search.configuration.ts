
import { SelectItem } from 'primeng/components/common/selectitem';
import { SearchFields, InputType, MultiselectOptions, DateOptions, DropdownOptions } from '../model/form-search.model';
import { CONST } from 'src/app/shared/constants';

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
            extra: {}
        },
        {
            label: "Descrizione contenuto",
            formControlName: "descrizione",
            type: InputType.textarea,
            className: "form-control",
            extra: {}
        },
        {
            label: "Destinatari",
            formControlName: "destinatario",
            type: InputType.text,
            className: "form-control",
            extra: {}
        },
        {
            label: "Visibile A",
            formControlName: "visibileA",
            type: InputType.multiselect,
            className: "dflex",
            extra: <MultiselectOptions>{
                filter: false,
                options: []/* CONST.entiTemplate */,
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
            extra: {}
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
                extra: {}
            },
            {
                label: "Destinatario",
                formControlName: "destinatario",
                className: "form-control",
                type: InputType.text,
                extra: {}
            },
            {
                label: "Oggetto",
                formControlName: "oggetto",
                className: "form-control",
                type: InputType.text,
                extra: {}
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
                extra: {}
            },
        ]
    }
}