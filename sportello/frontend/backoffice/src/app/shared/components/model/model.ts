export interface Lang{
    code:string;
    name:string;
}

export interface Menu{
    name:string;
    link:string;
    icon?:string;
    target?:string;
    roles?:string[];//ruoli ammessi
    
}
export interface TipoSelect {
    label:string
    value:any 
}
  
export interface TipoAllegato {
    label:string
    items:TipoSelect[]
}

export interface Allegato {
    idAllegatiRichiesta?:number
    idAllegato?:number
    pathAlfresco?:string
    idAlfresco?:string
    idTipo?:string
    numero?:string
    dataRilascio?:Date
    dataScadenza?:Date
    enteRilascio?:string
    fileName?:string;
    descrizione?:string;
}
 
export interface BaseMap{
    thumbnail:string;
    name:string;
    url:string;
}

export const basemap:BaseMap[]=[
    {
        thumbnail:"http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2006.png",
        url:"http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2006/ImageServer",
        name:"Ortofoto 2006"
    },
    {
        thumbnail:"http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2010.png",
        url:"http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2010/ImageServer",
        name:"Ortofoto 2010"
    },
    {
        thumbnail:"http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2011.png",
        url:"http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2011/ImageServer",
        name:"Ortofoto 2011"
    },
    {
        thumbnail:"http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2013.png",
        url:"http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2013/ImageServer",
        name:"Ortofoto 2013"
    },
    {
        thumbnail:"http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/2013.png",
        url:"http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/Ortofoto2016/ImageServer",
        name:"Ortofoto 2016"
    },
    {
        thumbnail:"http://webapps.sit.puglia.it/EsriJsViewer/images/imgbasemap/trasp.png",
        url:"http://webapps.sit.puglia.it/arcgis/rest/services/BaseMaps/SfondoTrasp/MapServer",
        name:"Trasparente"
    }
];

export class defaultSelectModel{
    value:number;
    label:string;
}

//Base bean for paginated request
export interface PaginatedRequest{
    page:number;
    rowsOnPage:number;
    myFilter?: string;
    sortBy: string;
    sortOrder:string;
    sortOrderSql?:string;
}


export interface EnteCss{
    id:number;
    nome:string;
    footer:string;
    link:string;
    hasLogo:boolean;
  }

export interface SportelloConfigBean {
    esoneroOneriLabel: string;
    esoneroBolloLabel: string;
  }  

  export interface DownloadManualeBean {
    label?:string;
    url?:string;
}