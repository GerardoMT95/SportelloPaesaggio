import { FormGroup } from '@angular/forms';
import { Input } from '@angular/core';
import { Fascicolo } from 'src/app/shared/models/models';

export abstract class AbstractTab
{
    @Input("mainForm") mainForm: FormGroup;
    @Input("fascicolo") fascicolo: Fascicolo;
    @Input("validation") validation: boolean = false;
}