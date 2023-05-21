import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NG_VALUE_ACCESSOR, ValidationErrors } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { BaseControlValueAccessor } from './../../BaseControlValueAccessor';
import { ConfigOptionValue, HierarchicalOption, OptionNode } from './../../models/models';

@Component({
    selector: 'app-hierarchical-field',
    template: `
        <div class="form-group" *ngIf="container">
            <label style="padding-bottom: .5em">
                {{ translate ? (label | translate) : label }}
                <app-tooltip *ngIf="tooltip" [message]="tooltip"></app-tooltip>
            </label>
            <div class="main-container" [ngClass]="{ 'is-invalid': validation && isInvalid() }">
                <ng-container *ngFor="let option of options">
                    <div style='white-space: nowrap;'>
                        <p-checkbox *ngIf="container.type === 'checkbox'" [disabled]="disabled"
                            [name]="name" [value]="option.value" [(ngModel)]="dummyValues"
                            [label]="translate ? (option.label | translate) : option.label"
                            (onChange)="myChangeBindSelected()" (blur)="onTouched()"
                            [readonly]="option.disabled === true">
                        </p-checkbox>
                        <p-radioButton *ngIf="container.type === 'radiobutton'" [disabled]="disabled"
                            [name]="name" [value]="option.value" [(ngModel)]="dummyValues"
                            [label]="translate ? (option.label | translate) : option.label"
                            (onClick)="myChangeBindSelected()" (blur)="onTouched()"
                            [disabled]="option.disabled === true">
                        </p-radioButton>
                        <app-tooltip *ngIf="option.tooltip" [message]="option.tooltip|translate"></app-tooltip>
                    </div>
                    <app-text-field *ngIf="showTextInput(option)" [prepend]="('DESCRIPTION_TAB.SPECIFIC'|translate|uppercase)+'*'"
                        [validation]="validation" [errors]="getErrorForTextInput(option.value)" [(ngModel)]="dummyTexts[option.value]" 
                        (change)="myChangeBindText(option.value)" [disabled]="disabled">
                    </app-text-field>
                </ng-container>
            </div>
            <app-input-error [errors]="isInvalid() ? errors : null" [validation]="validation">
            </app-input-error>
            <div *ngIf="container.children" class='pl-4'>
                <!-- recursion -->
                <app-hierarchical-field #child [hidden]="hide && !activateChild" [disabled]="!activateChild || disabled" [(ngModel)]="value"
                    [validation]="validation" [errors]="errors" [options]="container.children">
                </app-hierarchical-field>
            </div>
        </div>`,
    styles: [
        `p-checkbox 
         {
             width: 90%;
             margin-bottom: 1rem;
             overflow-wrap: break-word;
             display: inline!important;
         }
         p-radioButton 
         {
             width: 90%;
             margin-bottom: 1rem;
             overflow-wrap: break-word;
             display: inline!important;
             word-break: break-all;
             white-space: break-spaces;
         }
         .main-container
         {
             padding-top: .6em;
             padding-bottom: .1em;
         }
         .main-container.is-invalid
         {
             padding-left: 1rem;
             border-left: solid .32rem;
             border-left-color: red;
         }
         .ui-chkbox-label 
         {
             vertical-align: middle!important;
             display: inherit!important;
         }
         `],
    providers: [{
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => HierarchicalFieldComponent),
            multi: true
        }]
})
export class HierarchicalFieldComponent extends BaseControlValueAccessor<ConfigOptionValue[]> implements OnChanges
{
    @ViewChild("child", { static: false }) child: HierarchicalFieldComponent;

    @Input("options") container: HierarchicalOption;
    @Input("hideDisabledChildren")hide: boolean = false;
    @Output("change") changeEvt: EventEmitter<ConfigOptionValue[]> = new EventEmitter<ConfigOptionValue[]>();

    protected dummyValues: any = [];
    protected dummyTexts: {[key: string]: string} = {};
    protected activateChild: boolean = false;

    constructor() { super(); }
    ngOnChanges(changes: SimpleChanges): void 
    { 
        if(this.container) 
        {
            this.applyContainersValue();
            this.activateChild = this.satisfiedDependency(); 
        }
    }

    public writeValue(value: ConfigOptionValue[]): void 
    {
        this.value = value;
        this.dummyValues = [];
        this.dummyTexts = {}; 
        if(value)
        {
            value.forEach(v =>
            {
                this.dummyTexts[v.value] = v.text ? v.text : "";
            });
        }
        this.applyContainersValue();
        this.activateChild = this.satisfiedDependency();
    }  

    protected myChangeBindSelected(): void
    {
        this.removeContainersValue();
        if(this.dummyValues instanceof Array)
            this.dummyValues.forEach(item => this.value.push({value: item, text: this.dummyTexts[item]}));
        else
            this.value.push({value: this.dummyValues, text: this.dummyTexts[this.dummyValues]})
        this.removeDuplicate(this.value);

        if(this.child && !this.satisfiedDependency())
            this.child.removeAll();
        this.activateChild = this.satisfiedDependency();
        this.changeEvt.emit(this.value);
        super._callTouchAndChange();
    }

    protected myChangeBindText(codice: string): void
    {
        let text = this.dummyTexts[codice];
        this.value.forEach((item, index) =>
        {
            if(item.value === codice)
                this.value[index].text = text;
        });
        this.changeEvt.emit(this.value);
        super._callTouchAndChange();
    }

    private removeContainersValue(): void
    {
        if(!isNullOrUndefined(this.options))
        {
            this.options.forEach(option =>
            {
                let index = this.value.map(m => m.value).indexOf(option.value);
                if(index != -1)
                    this.value.splice(index, 1);
            });
        }
    }
    private applyContainersValue(): void
    {
        if (this.container && this.value && this.value.length > 0)
        {
            this.dummyValues instanceof Array ? this.dummyValues.splice(0) : this.dummyValues = null;
            this.options.forEach(option =>
            {
                let index = this.value.map(m => m.value).indexOf(option.value);
                if (index !== -1)
                    this.dummyValues instanceof Array ? this.dummyValues.push(option.value) : this.dummyValues = option.value;
            });
        }
    }

    private removeDuplicate(values: ConfigOptionValue[]): ConfigOptionValue[]
    {
        let array: ConfigOptionValue[] = [];
        if(values)
        {
            values.forEach(v =>
            {
                if(!array.map(m => m.value).includes(v.value))
                    array.push(v);
            });
        }
        return array;
    }

    protected getErrorForTextInput(codice: string): ValidationErrors|null
    { 
        let errors: ValidationErrors|null = null;
        let textErrors: string[] = this.errors ? this.errors.textRequired : [];
        if(textErrors.includes(codice))
            errors = { textRequired: true };
        return errors;
    }

    protected removeAll(): void
    {
        this.dummyValues = [];
        this.removeContainersValue();
        if(this.child)
            this.removeAll();
    }


    protected showTextInput(option: OptionNode): boolean { return option.hasText && this.dummyValues.includes(option.value); }
    protected satisfiedDependency(): boolean { return this.container && this.container.children && this.container.options.some(v => this.value && this.value.some(val => val.value === v.value) && v.key === this.container.children.dependency); }
    protected isInvalid(): boolean { return this.errors && this.errors.valueRequired && this.container.options.every(e => this.errors.valueRequired.some(e1 => e1.value === e.value)); }
    get options(): OptionNode[] { return this.container ? this.container.options.sort((a1, a2) => a1.orderIndex - a2.orderIndex) : []; }

}