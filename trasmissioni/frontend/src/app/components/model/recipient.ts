import { Correspondence } from "./correspondence.model";
export enum RecipientType {
	TO, 
	CC
}
export class Recipient {
    id?: number;
    typeKey?: RecipientType;
    email?: string;
    accepted?: boolean;
    delivered?: boolean;
    correspondence?: Correspondence;
}