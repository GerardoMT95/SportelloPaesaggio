import { FormBuilder } from "@angular/forms";
import { PlanFormField } from "src/shared/models/plan-form.model";


export class FormConfigConfigurazioni {

    static formConfig():PlanFormField[]
    {
        const esitoFields: PlanFormField[] =
        [
            {
                field: 'campionamentoAttivo',
                label: '',
                required:true,
            },
            {
                field: 'intervalloCampionamento',
                label: '',
            },
            {
                field: 'tipoCampionamentoSuccessivo',
                label: '',
            },
            {
                field: 'percentualeIstanze',
                label: '',
            },
            {
                field: 'esitoPubblico',
                label: '',
            },
            {
                field: 'applicaInCorso',
                label: '',
            },
            {
                field: 'giorniNotificaCampionamento',
                label: '',
            },
            {
                field: 'giorniNotificaVerifica',
                label: '',
            },
            {
                field: 'intervalloVerifica',
                label: '',
            },
            {
                field: 'comunicazioniAttivo',
                label: '',
                required:true,
            },
            {
                field: 'comunicazioni',
                label: '',
                required:false,
            },
            {
                field: 'osservazioniAttivo',
                label: '',
                required:true,
            },
            {
                field: 'osservazioni',
                label: '',
                required:false,
            },
            {
                field: 'documentazioneAttivo',
                label: '',
                required:true,
            },
            {
                field: 'documentazione',
                label: '',
                required:false,
            },
            {
                field: 'statoRegistrazione',
                label: '',
            },
            {
                field: 'esitoVerifica',
                label: '',
            }
        ];
        return esitoFields;
    }
}    