export type AutocompleteFn = (query: string, suggestions: string[]) => void
export enum InputType
{
    text = "text",
    checkbox = "checkbox",
    radio = "radio",
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
export type DateOptions =
{
    minDate?: Date,
    maxDate?: Date,
    locale?: any,
    yearNavigator?: boolean,
    monthNavigator?: boolean,
    yearRange?: string,
    format?: string
}
export type ListInputsOptions = baseInputOpt &
{
    filter?: boolean,
    defaultLabel: string,
    filterPlaceholder: string // nella multiselect è emptyFilterMessage,

}
export type DropdownOptions = ListInputsOptions &
{
    isGroup?: boolean
}
export type MultiselectOptions = ListInputsOptions &
{
    showToggleAll?: boolean,
    maxSelectedLabels: number
}
export type checkBox = baseInputOpt &
{
    labelStyleClass: string,
    valueLabel: string
}
export type autocomplete = baseInputOpt&
{    
    endpoint: string,
    forceSelection: boolean,
    uppercase: boolean,
    otherFields?: any,
    function?: AutocompleteFn
}

export interface SearchFields
{
    formControlName: string;
    label: string;
    labelClass?: string
    type: InputType;
    extra: any;
    className: string;
}

export interface Initialization
{
    formControlName: string;
    defaultValue: any;
}