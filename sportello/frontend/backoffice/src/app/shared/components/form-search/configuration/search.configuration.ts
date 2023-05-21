import { SelectItem } from 'primeng/components/common/selectitem';
import { DateOptions, DropdownOptions, InputType } from 'src/app/shared/models/form-search.configurations.models';
import { CONST } from './../../../constants';
import { SearchFields } from './../../../models/form-search.configurations.models';
import { GroupType, ContainerInitSearch, GroupRole } from './../../../models/models';


export class SearchConfiguration
{

    public static getConfigurazioneRicerca(tipoGruppo: GroupType, container: ContainerInitSearch, ruolo?: GroupRole): Array<SearchFields>
    {
        let conf: SearchFields[] = [
            {
                label: "FASCIOLO_FIELDS.FILE_CODE",
                labelClass: "uppercase",
                className: "form-control",
                formControlName: 'codicePraticaAppptr',
                type: InputType.text,
                extra: {}
            },
            {
                label: "FASCIOLO_FIELDS.PROCEDURE_TYPE",
                labelClass: "uppercase",
                className: "dflex",
                formControlName: 'tipoProcedimento',
                type: InputType.dropdown,
                extra: <DropdownOptions>{
                    defaultLabel: "-- Seleziona una voce --",
                    filter: false,
                    isGroup: false,
                    filterPlaceholder: null,
                    options: CONST.mappingTipiProcedimento,
                    optionLabel: 'description'
                }
            },
            {
                label: 'FASCIOLO_FIELDS.COMMON',
                labelClass: "uppercase",
                className: "dflex",
                formControlName: "comune",
                type: InputType.dropdown,
                extra: <DropdownOptions>{
                    options: container.comuni,
                    defaultLabel: '-- Seleziona un comune --',
                    filter: true,
                    filterPlaceholder: 'cerca comune',
                    isGroup: false,
                    optionLabel: 'description'
                }
            },
            {
                label: "FASCIOLO_FIELDS.OBJECT",
                labelClass: "uppercase",
                className: "form-control",
                formControlName: 'oggetto',
                type: InputType.textarea,
                extra: {}
            }
        ];
        if ([GroupType.EnteDelegato, GroupType.Regione, GroupType.CommissioneLocale, GroupType.Soprintendenza].includes(tipoGruppo))
        {
            conf = [...conf,
            {
                label: "FASCIOLO_FIELDS.STATE",
                labelClass: "uppercase",
                className: "dflex",
                formControlName: 'stato',
                type: InputType.dropdown,
                extra: <DropdownOptions>{
                    defaultLabel: "-- Seleziona una voce --",
                    filter: false,
                    isGroup: false,
                    filterPlaceholder: null,
                    options: CONST.statiLabelValue,
                    optionLabel: 'description'
                }
            }];
        }
        if ([GroupType.Soprintendenza, GroupType.Amministratore].includes(tipoGruppo))
        {
            conf = [...conf,
            {
                label: "FASCIOLO_FIELDS.ENTE_DELEGATO",
                labelClass: "uppercase",
                className: "dflex",
                formControlName: 'enteDelegato',
                type: InputType.dropdown,
                extra: <DropdownOptions>{
                    defaultLabel: "-- Seleziona una voce --",
                    filter: true,
                    isGroup: false,
                    filterPlaceholder: null,
                    options: container.enti,
                    optionLabel: 'description'
                }
            }];
        }
        if ([GroupType.EnteDelegato, GroupType.EnteTerritoriale, GroupType.Soprintendenza].includes(tipoGruppo) &&
            (ruolo && ruolo != GroupRole.F))
        {
            conf = [...conf,
            {
                label: "FASCIOLO_FIELDS.ASSEGNATO_A",
                labelClass: "uppercase",
                className: "dflex",
                formControlName: 'userAssegnatario',
                type: InputType.dropdown,
                extra: <DropdownOptions>{
                    defaultLabel: "-- Seleziona una voce --",
                    filter: true,
                    isGroup: false,
                    filterPlaceholder: null,
                    options: container.funzionari,
                    optionLabel: 'description'
                }
            }];
        }
        conf = [...conf,
        {
            label: "FASCIOLO_FIELDS.RICHIEDENTE",
            labelClass: "uppercase",
            className: "dflex",
            formControlName: 'userId',
            type: InputType.dropdown,
            extra: <DropdownOptions>{
                defaultLabel: "-- Seleziona una voce --",
                filter: true,
                isGroup: false,
                filterPlaceholder: null,
                options: container.richiedenti,
                optionLabel: 'description'
            }
        }];
        return conf;
    }

    public static getRubricaSearchFields(listaTipologie: SelectItem[]): SearchFields[]
    {
        return [
            {
                label: 'Tipologia ente',
                labelClass: "uppercase",
                className: "dflex",
                formControlName: "tipoEnte",
                type: InputType.dropdown,
                extra: <DropdownOptions>{
                    options: listaTipologie,
                    defaultLabel: '-- Seleziona una tipologia --',
                    filter: false,
                    isGroup: false,
                    optionLabel: 'label'
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

    public static getTemplateEmailSearchFields(): SearchFields[]
    {
        return [
            {
                label: "nome",
                formControlName: "nome",
                className: "form-control",
                type: InputType.text,
                extra: {}
            },
            {
                label: "descrizione",
                formControlName: "descrizione",
                className: "form-control",
                type: InputType.text,
                extra: {}
            },
            {
                label: "oggetto",
                formControlName: "oggetto",
                className: "form-control",
                type: InputType.text,
                extra: {}
            },
        ]
    }

    public static getConfigurazioneReport(): Array<SearchFields> {
        return [
          {
            label: "report.date",
            labelClass: "uppercase",
            className: "form-control",
            formControlName: "data",
            type: InputType.dateRange,
            extra: <DateOptions>{
              format: "dd/mm/yy",
              locale: CONST.IT,
              maxDate: CONST.TODAY,
              yearNavigator: true,
              yearRange: "2022:" + new Date().getFullYear(),
              monthNavigator: true,
            },
          },
        ];
      }
}