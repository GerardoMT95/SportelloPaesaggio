import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';

export class SchedaTecnicaConst
{
    private static _tabFormNames: string[] =
    [
        'descrizione',
        'destinazione',
        'eventualiProcedimenti',
        'effetiMitigazione',
        'inquadramento',
        'dichiara',
        'legittimita',
        'parreriEAtti',
        'pptr',
        'procedureEdilizie',
        'vincolistica'
    ];

    private static _casellaDiControllo: { [key: string]: Partial<SelectOption>[] } =
    {
        vincolistica: 
        [
            {
                value: 1,
                label: "vincolo monumentale diretto (art. 10 del D.Lgs. n. 42/2004)"
            },
            {
                value: 2,
                label: "vincolo monumentale indiretto (art. 45 del D.lgs. n. 42/2004)"
            },
            {
                value: 3,
                label: "vincolo archeologico diretto (art. 10 del D.lgs. n. 42/2004)"
            },
            {
                value: 4,
                label: "vincolo archeologico indiretto (art. 45 del D.lgs. n. 42/2004)"
            }
        ]
    };

    static get tabFormNames(): string[] { return this._tabFormNames; }
    static get casellaDiControllo(): { [key: string]: Partial<SelectOption>[] } { return this._casellaDiControllo; }
}