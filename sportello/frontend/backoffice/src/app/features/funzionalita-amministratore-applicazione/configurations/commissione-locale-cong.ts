import { IButton } from './../../../core/models/dialog.model';
import { CONST } from './../../../shared/constants';
import { DropdownOptions } from 'src/app/shared/components/form-search/model/form-search.model';
import { DateOptions, SearchFields } from 'src/app/shared/models/form-search.configurations.models';
import { TableConfig } from './../../../core/models/header.model';
import { InputType } from './../../../shared/models/form-search.configurations.models';

export class CommissioneLocaleConst
{
    private static _tableHeader: TableConfig[] = [
        {
            header: 'COMMISSIONE_LOCALE.THEAD.NAME',
            field: 'denominazione',
            orderable: true,     
        },
        {
            header: 'COMMISSIONE_LOCALE.THEAD.VALIDITY-START',
            field: 'dataCreazione',
            orderable: true,
        },
        {
            header: 'COMMISSIONE_LOCALE.THEAD.VALIDITY-END',
            field: 'dataTermine',
            orderable: false,
        }
    ];

    private static _searchConfiguration: SearchFields[] = [
        {
            label: 'COMMISSIONE_LOCALE.THEAD.NAME',
            formControlName: 'denominazione',
            type: InputType.text,
            className: 'form-control',
            extra: {}
        },
        {
            label: 'COMMISSIONE_LOCALE.THEAD.MEMBER',
            formControlName: 'username',
            type: InputType.dropdown,
            className: 'dflex',
            extra: <DropdownOptions>{
                defaultLabel: '-- Seleziona un utente --',
                filterPlaceholder: '-- Cerca utente --',
                filter: true,
                isGroup: false,
                optionLabel: 'description',
                options: []
            }
        },
        {
            label: 'COMMISSIONE_LOCALE.THEAD.SIMPLE-VALIDITY',
            formControlName: 'dataValidita',
            type: InputType.dateRange,
            className: 'form-control',
            extra: <DateOptions>{
                format: 'dd/mm/yy',
                locale: CONST.IT,
                maxDate: CONST.TODAY,
                yearNavigator: true,
                yearRangeStart: "1900:"+new Date().getFullYear(),
                yearRangeEnd  : "1900:"+(new Date().getFullYear() + 50),
                monthNavigator: false
            }
        }
    ];

    private static _headerUsersTable: TableConfig[] = [
        {
            header: 'COMMISSIONE_LOCALE.THEAD.MEMBERS',
            field: 'description',
            type: 'text'
        }
    ];

    private static _headerErrorsTable: TableConfig[] = [
        {
            header: 'COMMISSIONE_LOCALE.THEAD.NAME-ENTE',
            field: 'nomeEnte',
            type: 'text'
        },
        {
            header: 'COMMISSIONE_LOCALE.THEAD.NAME',
            field: 'nomeCommissione',
            type: 'text'
        },
        {
            header: 'COMMISSIONE_LOCALE.THEAD.VALIDITY-START',
            field: 'dataInizioVal',
            type: 'date'
        }
        ,
        {
            header: 'COMMISSIONE_LOCALE.THEAD.VALIDITY-END',
            field: 'dataTermineVal',
            type: 'date'
        }
    ]

    private static _customButton: IButton = 
    {
        id: 1,
        label: 'Elimina utente',
        icon: 'trash'
    };

    private static _customError: any =
    {
        duplicate: 'L\'utente è già associato a questa commissione locale'
    };

    static get tableHeader(): TableConfig[] { return this._tableHeader; }
    static get searchConfiguration(): SearchFields[] { return this._searchConfiguration; }
    static get headerUsersTable(): TableConfig[] { return this._headerUsersTable; }
    static get headerErrorsTable(): TableConfig[] { return this._headerErrorsTable; }
    static get customButton(): IButton { return this._customButton; }
    static get customError(): any { return this._customError; }
}