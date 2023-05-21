import { FormArray, FormBuilder, Validators } from "@angular/forms";
import { CONST } from "src/shared/constants";
import { PlanFormField } from "src/shared/models/plan-form.model";
import { TableHeader } from "src/shared/models/table-header";
import { TipiEQualificazioniDTO } from './../model/entity/intervento.models';
import { requiredLength, validDate, requiredDependsOn, minAge } from "../validators/customValidator";


export class FascicoloFormConfig
{
    static esitoFormField(formBuilder: FormBuilder): PlanFormField[]
    {
        const esitoFields: PlanFormField[] = 
        [
            {
                field: 'dataVerifica',
                label: '',
                required: true,
                validator: [Validators.required, validDate(null, CONST.NOW_DATE)],
                isDate: true,
            },
            {
                field: 'esitoVerifica',
                label: '',
                required: true,
                validator: Validators.required
            },
            {
                field: 'noteVerifica',
                label: '',
                required: true,
                validator: Validators.required
            },
        ];
        return esitoFields;
    }


    static particellaFormField(formBuilder: FormBuilder): PlanFormField[]
    {
        const particellaFields: PlanFormField[] = 
        [
            {
                field: 'id',
                label: ''
            },
            {
                field: 'type',
                label: ''
            },
            {
                field: 'ente',
                label: '',
                required: true,
            },
            {
                field: 'foglio',
                label: '',
                required: true,
                validator: Validators.pattern('[1-9]+[0-9]*'),
            },
            {
                field: 'sezione',
                label: '',
            },
            {
                field: 'particella',
                label: '',
                required: true,
                validator: Validators.pattern('[1-9]+[0-9]*'),
            },
            {
                field: 'sub',
                label: '',
            },
            {
                field: 'codiceEnte',
                label: '',
            }
        ];
        return particellaFields;
    }

    /**
     * dismesso
     */
    static localizzazioneFormField(formBuilder: FormBuilder): PlanFormField[]
    {
        const localizzazioneFields: PlanFormField[] = 
        [
            {
                field: 'particelle',
                label: '',
                children: formBuilder.array([])
            },
            {
                field: 'tipoSelezioneLocalizzazione',
                label: ''
            },
            {
                field: 'localizzazioneComuni',
                label: ''
            },
            {
                field: 'localizzazioneProvince',
                label: ''
            },
            {
                field: 'bpImmobiliAreeInteresse',
                label: 'fascicolo.localizzazioneSection.altreInfo.bpImmobiliAree',
            },
            {
                field: 'bpPaesaggioRurale',
                label: 'fascicolo.localizzazioneSection.altreInfo.bpPaesaggioRurale',
            },
            {
                field: 'bpParchiRiserve',
                label: 'fascicolo.localizzazioneSection.altreInfo.bpParchiRiserve',
            },
        ];
        return localizzazioneFields;
    }

    public static interventoFormField(formBuilder: FormBuilder): PlanFormField[]
    {
        let interventoFormField =
        [
            //okkio, i field con nome di 1 carattere vengono gestiti ad hoc con una meccanica che li 
            // mappa in oggetto ad hoc nel salva
            {
                field: "a",
                label: "",
            },
            {
                field: "b",
                label: "",
            },
            {
                field: "c",
                label: "",
            },
            {
                field: "d",
                label: "",
            },
            {
                field: "i",
                label: "",
            },
            {
                field: "e",
                label: "",
            },
            {
                field: "f",
                label: "",
            },
            {
                field: "g",
                label: "",
            },
            {
                field: "h",
                label: "",
            },
            {
                field: "j",
                label: ""
            },
            {
                field: "p",
                label: ""
            },
            {
                field: "interventoDettaglio", //usato anche per pareri in altro:
                label: ""
            },
            {
                field: "interventoSpecifica",
                label: ""
            },
            //per pareri:
            
        ];
        if(CONST.isPareri()){
            let aggiuntiviPareri=[
                {
                    field: "dataDelibera",
                    label: "fascicolo.sedutaDel",
                    isDate:true
                },
                {
                    field: "deliberaNum",
                    label: "fascicolo.deliberaNum",
                },
                {
                    field: "oggettoDelibera",
                    label: "fascicolo.oggettoDelibera",
                },
                {
                    field: "infoDeliberePrecedenti",
                    label: "fascicolo.infoPrecedenti",
                },
                {
                    field: "descrizioneIntervento",
                    label: "fascicolo.descrInterventoProp",
                },
            ];
            aggiuntiviPareri.forEach(el=>interventoFormField.push(el));
        }
        return interventoFormField;
    }

    static interventoFormMaker(formBuilder: FormBuilder, interventoTab: TipiEQualificazioniDTO[]): PlanFormField[]
    {
        let interventoFields: PlanFormField[] = [];
        if (interventoTab && interventoTab.length > 0)
        {
            interventoTab.forEach(intervento =>
            {
                if (!interventoFields.map(p => p.field).includes(intervento.raggruppamento))
                {
                    /*
                        Inserisco il raggruppamento nel menu a tendina.
                    */
                    let row = new PlanFormField();
                    row.field = intervento.raggruppamento;
                    row.label = "";
                    row.required = true;
                    interventoFields.push()
                }
            });
            interventoFields.push(new PlanFormField("interventoDettaglio", "dettagliare"));
            interventoFields.push(new PlanFormField("interventoSpecifica", "specificare"));
        }
        return interventoFields;
    }

    /* static interventoFormField(formBuilder: FormBuilder): PlanFormField[]
    {
        const interventoFields: PlanFormField[] = 
        [
            {
                field: 'idTipoIntervento',
                label: 'fascicolo.interventoSection.titoloTipo', //radio
                required: true,
                validator: Validators.required
            },
            {
                field: 'caratterizzazione',
                label: 'fascicolo.interventoSection.caratterizzazioneIntervento',
                required: true,
                validator: Validators.required
            },
            {
                field: 'rimessaInRipristinoDettaglio',
                label: 'fascicolo.interventoSection.dettagliare',
                required: false
            },
            {
                field: 'altroSpecificare',
                label: 'fascicolo.interventoSection.specificare',
                required: false
            },
            {
                field: 'idTipoInterventoTempo',
                label: 'fascicolo.interventoSection.caratterizzazioneIntervento',
                required: true,
                validator: Validators.required
            },
            {
                field: 'idQualificazioneDPR31_2017',
                label: 'fascicolo.interventoSection.qualificazioneIntervento',
                required: false
            },
            {
                field: 'idQualificazioneDPR139_2010',
                label: 'fascicolo.interventoSection.qualificazioneIntervento',
                required: false
            },
            {
                field: 'qualDPR31_2017',
                label: 'fascicolo.interventoSection.qualificazioneIntervento',
                required: false
            },
            {
                field: 'qualDLGS42_2004',
                label: 'fascicolo.interventoSection.qualificazioneIntervento',
                required: false
            },
            {
                field: 'qualDPR139_2010',
                label: 'fascicolo.interventoSection.qualificazioneIntervento',
                required: false
            },
            {
                field: 'qualDPCM_2005',
                label: 'fascicolo.interventoSection.qualificazioneIntervento',
                required: false
            },
            {
                field: 'istanzaRinnovo',
                label: 'fascicolo.interventoSection.qualificazioneIntervento',
                required: false
            },
        ];
        return interventoFields;
    } */


    static richiedenteFormField(formBuilder: FormBuilder): PlanFormField[]
    {
        const richiedenteFields: PlanFormField[] = 
        [
            {
                field: 'id',
                label: ""
            },
            {
                field: 'cognome',
                label: 'fascicolo.richiedente.cognome',
                required: true,
                validator: Validators.required
            },
            {
                field: 'nome',
                label: 'fascicolo.richiedente.nome',
                required: true,
                validator: Validators.required
            },
            {
                field: 'codiceFiscale',
                label: 'fascicolo.richiedente.codiceFiscale',
                validator: [Validators.required, Validators.pattern('^[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}$')],
                required: true
            },
            {
                field: 'sesso',
                label: 'fascicolo.richiedente.sesso',
                required: false
            },

            {
                field: 'dataNascita',
                label: 'fascicolo.richiedente.natoIl',
                required: false,
                isDate: true,
                validator: [validDate(null, CONST.NOW_DATE), minAge(18)]

            },
            {
                field: 'nazionalitaNascitaName',
                label: 'fascicolo.richiedente.stato',
                required: false
            },
            {
                field: 'comuneNascitaName',
                label: 'fascicolo.richiedente.comune',
                required: false
            },
            {
                field: 'provinciaNascitaName',
                label: 'fascicolo.richiedente.prov',
                required: false
            },
            {
                field: 'comuneNascitaEstero',
                label: 'fascicolo.richiedente.comune',
                required: false
            },
            {
                field: 'nazionalitaResidenzaName',
                label: 'fascicolo.richiedente.stato',
                required: false
            },
            {
                field: 'comuneResidenzaName',
                label: 'fascicolo.richiedente.comune',
                required: false
            },
            {
                field: 'comuneResidenzaEstero',
                label: 'fascicolo.richiedente.comune',
                required: false
            },
            {
                field: 'provinciaResidenzaName',
                label: 'fascicolo.richiedente.prov',
                required: false
            },
            {
                field: 'viaResidenza',
                label: 'fascicolo.richiedente.via',
                required: false
            },
            {
                field: 'numeroResidenza',
                label: 'fascicolo.richiedente.civico',
                required: false
            },
            {
                field: 'cap',
                label: 'fascicolo.richiedente.cap',
                validator: Validators.pattern('[0-9]{5}'),
                required: false
            },
            {
                field: 'telefono',
                label: 'fascicolo.richiedente.telefono',
                validator: Validators.pattern(CONST.PHONE_PATTERN),
                required: false
            },
            {
                field: 'email',
                label: 'fascicolo.richiedente.email',
                validator: [Validators.email, Validators.pattern('^\\s*?(.+)@(.+?)\\s*$')],
                required: false
            },
            {
                field: 'pec',
                label: 'fascicolo.richiedente.pec',
                validator: [Validators.required, Validators.email],
                required: true
            },
            {
                field: 'ditta',
                label: '',
                required: false
            },
            {
                field: 'dittaSocieta',
                label: '',
                required: false
            },
            {
                field: 'dittaInQualitaDi',
                label: 'fascicolo.richiedente.inQualitaDi',
                required: false
            },
            {
                field: 'dittaQualitaAltro',
                label: 'fascicolo.richiedente.descrAltro',
                required: false
            },
            {
                field: 'dittaCodiceFiscale',
                label: 'fascicolo.richiedente.cfDitta',
                required: false,
                validator: Validators.pattern('^[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}$')
            },
            {
                field: 'dittaPartitaIva',
                label: 'fascicolo.richiedente.pivaDitta',
                required: false
            },
        ];
        if(CONST.isPareri()){
            richiedenteFields.push({
                field: 'responsabile',
                label: '',
                nestedForm: this.responsabileFormField(formBuilder),
                formBuilder:formBuilder,
            });
        }
        return richiedenteFields;
    }

    //usato solo in pareri....continuare ad elencare i campi della sottosezione responsabile comunale...
    static responsabileFormField(formBuilder: FormBuilder): PlanFormField[]
    {
        const fields: PlanFormField[] = 
        [
            {
                field: 'id',
                label: ""
            },
            {
                field: 'cognome',
                label: 'fascicolo.richiedente.labelCognome',
                required: true,
                validator: Validators.required
            },
            {
                field: 'nome',
                label: 'fascicolo.responsabile.labelNome',
                required: true,
                validator: Validators.required
            }, 
            {
                field: 'inQualitaDi',
                label: 'fascicolo.responsabile.labelQualita',
                required: false
            },
            {
                field: 'servizioSettoreUfficio',
                label: 'fascicolo.responsabile.labelTipoServizio',
                required: false
            },
            {
                field: 'pec',
                label: 'fascicolo.responsabile.labelPec',
                required: true,
                validator: Validators.required
            },
            {
                field: 'mail',
                label: 'fascicolo.responsabile.labelPostaElettronica',
                required: false
            },
            {
                field: 'telefono',
                label: 'fascicolo.responsabile.labelTelefono',
                required: false
            },
            {
                field: 'riconoscimentoTipo',
                label: 'fascicolo.responsabile.labelTipo',
                required: true,
                validator: Validators.required
            },
            {
                field: 'riconoscimentoNumero',
                label: 'fascicolo.responsabile.labelNumero',
                required: true,
                validator: Validators.required
            },
            {
                field: 'riconoscimentoDataRilascio',
                label: 'fascicolo.responsabile.labelRilasciato',
                required: true,
                isDate:true,
                validator: [Validators.required, validDate(null, new Date())]
            },
            {
                field: 'riconoscimentoDataScadenza',
                label: 'fascicolo.responsabile.labelScadenza',
                required: true,
                isDate:true,
                validator: [Validators.required, validDate(new Date())]
            },
            {
                field: 'riconoscimentoRilasciatoDa',
                label: 'fascicolo.responsabile.labelDa',
                required: true,
                validator: Validators.required
            }
        ]
        return fields;
    }


    static pulisciParticelleVuote(locationFormArray: FormArray)
    {
        if (locationFormArray && locationFormArray.controls)
        {
            locationFormArray.controls.forEach((element, index) =>
            {
                if (!element.get('section').value &&
                    !element.get('fog').value &&
                    !element.get('part').value &&
                    !element.get('sub').value)
                {
                    locationFormArray.removeAt(index);
                }
            });
            if (locationFormArray.controls.length == 0)
            {
                locationFormArray.clearValidators();
                locationFormArray.updateValueAndValidity();
            }
        }   
    }


    static planFormConfig(formBuilder: FormBuilder)
    {
        const planFormConfig: PlanFormField[] = [
            {
                field: 'id',
                label: ''
            },
            {
                field: 'codice',
                label: '',
            },
            {
                field: 'ufficio',
                label: 'fascicolo.ufficio',
                required: true,
                validator: Validators.required
            },
            {
                field: 'tipoProcedimento',
                label: 'fascicolo.tipoProcedimento',
                required: true,
                validator: Validators.required
            },
            {
                field: 'sanatoria',
                label: 'fascicolo.inSanatoria',
            },
            {
                field: 'oggettoIntervento',
                label: 'fascicolo.oggettoIntervento',
                required: true,
                validator: Validators.required
            },
            {
                field: 'codiceInternoEnte',
                label: 'fascicolo.codiceInternoEnte',
                required: false
            },
            {
                field: 'numeroInternoEnte',
                label: 'fascicolo.numeroInternoEnte',
                required: false
            },
            {
                field: 'protocollo',
                label: 'fascicolo.protocolloInternoEnte',
                required: false
            },
            {
                field: 'dataProtocollo',
                label: 'fascicolo.dataProtocolloInternoEnte',
                required: false,
                validator: validDate(null, CONST.NOW_DATE),
                isDate: true
            },
            {
                field: 'note',
                label: 'fascicolo.note',
            },
            {
                field: 'tipoSelezioneLocalizzazione',
                label: '',
                required: true,
                validator: Validators.required,
            },
            {
                field: 'hasShape', //hidden indica se ci sono shape sul layer
                label: '',
            }
        ];
        if(CONST.isPutt()){
            planFormConfig.push({
                field: 'dataDelibera',
                label: 'fascicolo.dataPresentazione',
                required: true,
                isDate:true,
                validator: Validators.required
            });
        }

        return planFormConfig;
    }

    static provvedimentoFormField(formBuilder: FormBuilder)
    {
        const fields: PlanFormField[] = 
        [
            {
                field: 'numeroProvvedimento',
                label: 'fascicolo.provvedimentoSection.numeroProvv',
                required: true,
                validator: Validators.required
            },
            {
                field: 'dataRilascioAutorizzazione',
                label: 'fascicolo.provvedimentoSection.dataProvv',
                validator: [Validators.required, validDate(null, CONST.NOW_DATE)],
                required: true,
                isDate: true
            },
            {
                field: 'esito',
                label: 'fascicolo.provvedimentoSection.esito',
                required: true,
                validator: Validators.required
            },
            {
                field: 'rup',
                label: 'fascicolo.provvedimentoSection.rup',
                required: true,
                validator: Validators.required
            }
        ]
        return fields;
    }

    static prepareFormBuilder(fields: any, defaults: any) {
        const form = {};
        if (fields) {
            fields.forEach(item => {
                if (item.nestedForm) {
                    //ricorsione...
                    form[item.field] = item.formBuilder.group(this.prepareFormBuilder(item.nestedForm, defaults[item.field]));
                }
                else {
                    if (defaults) {
                        //console.log('defaults['+item.field+']= {}', defaults[item.field]);
                        defaults[item.field] =
                            item.isDate ?
                                (defaults[item.field] !== null && defaults[item.field] !== undefined) ?
                                    new Date(defaults[item.field]) :
                                    defaults[item.field]

                                : defaults[item.field];
                    }
                    return (form[item.field] =
                        item.children ? item.children : [defaults ? defaults[item.field] : item.field=="riconoscimentoTipo"?null:'' , item.validator]);
                }
            });
            return form;
        }
    }

    static prepareIntervento(intervento: TipiEQualificazioniDTO[], selected: number[], 
                            dettagliare: string, specificare: string): any
    {
        let defaults = {};
        if (selected)
        {
            selected.forEach(n =>
            {
                let type = intervento.find(f => f.id === n);
                if (type)
                {
                    if (!defaults[type.raggruppamento])
                        defaults[type.raggruppamento] = [];
                    defaults[type.raggruppamento].push(type.id.toString());
                }
            });
        }
        defaults["interventoDettaglio"] = dettagliare ? dettagliare : null;
        defaults["interventoSpecifica"] = specificare ? specificare : null;
        return defaults;
    }

    static allegatiUploadTableHeaders()
    {
        const allegatiUploadTableHeaders: TableHeader[] = 
        [
            {
                field: 'descrizione',
                header: 'Tipo File'
            },
            {
                field: 'nome',
                header: 'Nome file'
            },
            {
                field: 'checksum',
                header: 'Impronta hash(SHA256)'
            },
            {
                field: 'data',
                header: 'Data upload'
            }
        ];
        return allegatiUploadTableHeaders;
    }


}

