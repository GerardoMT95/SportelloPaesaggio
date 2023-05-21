export enum IntervalloCampionamento {
    predefinito,
	succesivo
}

export class CampionaturaDTO {
    id: number;
	campionamentoAttivo: boolean;
	campionamentoSuccessivo: number;
	campionamentoPubblico: boolean;
	intervalloCampionamento: IntervalloCampionamento;
}