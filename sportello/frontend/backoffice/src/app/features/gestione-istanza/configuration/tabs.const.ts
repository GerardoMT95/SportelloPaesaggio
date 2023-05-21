export class IstanzaFascicolo
{
    private static _tabs =
    {
        protocollazione: "istanza-protocollazione",
        presentata: "istanza-presentata",
        localizzazione: "istanza-localizzazione",
        storicoStato: "istanza-storico-stato",
        verbale: "istanza-verbale",
        relazioneEnte: "istanza-relazione-ente",
        parere: "istanza-parere",
        comunicazioni: "istanza-comunicazioni",
        conferenzaServizi: "istanza-conferenza",
        ulterioreDocumentazione: "istanza-documentazione",
        elaborazione: "istanza-passa",
        trasmissione: "trasmissione-provvedimento-finale"
    }

    static get tabs(): any { return IstanzaFascicolo._tabs; }
}