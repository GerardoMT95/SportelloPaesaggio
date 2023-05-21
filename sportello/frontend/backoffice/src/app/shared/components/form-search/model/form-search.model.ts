export type AutocompleFn = (query: string) => string[];

export enum InputType
{
    text = "text",
    checkbox = "checkbox",
    radio = "radio",
    select = "select",
    dropdown = "dropdown",
    multiselect = "multiselect",
    date = "date",
    dateRange = "dateRange",
    textarea = "textarea",
    autocomplete = "autocomplete"
}

type baseInputOpt =
{
    options?: any[],
    optionLabel?: string,
}
export type ExtraGeneric =
{
    maxlength: number
}

export type ListInputsOptions = baseInputOpt&
{
    filter?: boolean,
    defaultLabel: string,
    filterPlaceholder: string // nella multiselect è emptyFilterMessage,

}
export type DropdownOptions = ListInputsOptions&
{
    isGroup?: boolean
}
export type MultiselectOptions = ListInputsOptions&
{
    showToggleAll?: boolean,
    maxSelectedLabels: number
}
export type checkBox = baseInputOpt&
{
    labelStyleClass: string,
    valueLabel: string,
}
export type autocomplete = baseInputOpt&
{
    function: AutocompleFn,
    forceSelection: boolean
} 

export interface SearchFieldsOld
{
    formControlName: string;
    label: string;
    type: InputType;
    extra: any;
    className: string;
}