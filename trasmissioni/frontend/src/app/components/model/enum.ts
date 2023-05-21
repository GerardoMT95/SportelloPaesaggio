export enum UploadType {
    PE_ATTACHMENT = 10,
    MAIL_ATTACHMENT = 20,
    CONCLUSION_ATTACHMENT = 30
}

export enum GroupType
{
    ED = "Ente delegato",
    SBAP = "Soprintendenza",
    REG = "Regione puglia",
    EPA = "Ente parco", //in teoria non serve
    ETI = "Ente territorialmente interessato",
    ADMIN = "Super admin"
}

export enum GroupRole
{
    F = "Funzionario",
    D = "Dirigente",
    A = "Amministratore"
}

export enum Projects
{
    AUTPAE='autpae',
    PARERI='pareri',
    PUTT='putt'
}