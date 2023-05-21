import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Input, Output, EventEmitter, Renderer2 } from '@angular/core';
import { loadModules } from 'esri-loader';
//import { BaseMap, basemap, BaseResponse } from 'src/app/shared/models';
//import { BaseMap,  BaseResponse } from 'src/app/shared/models';
import { CONST } from 'src/app/shared/constants';
import { HttpClient } from '@angular/common/http';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { environment } from 'src/environments/environment';
//import { AllegatiService } from '../../services/allegati.service';
import { TranslateService } from '@ngx-translate/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { BaseResponse } from 'src/app/shared/components/model/base-response';
import { BaseMap, basemap, Allegato } from 'src/app/shared/components/model/model';
import { CookieStorage } from 'cookie-storage';
import { InfoAndGeoJson } from '../../models/comp-istanza.model';

@Component({
  selector: 'app-esri-map',
  templateUrl: './esri-map.component.html',
  styleUrls: ['./esri-map.component.css']
})

export class EsriMapComponent implements OnInit, OnDestroy {
  public error: string = '';
  public editorWidget: any;
  public pFloatingPane: any;
  public toolbar: any;
  public graphicLayer: any;
  public map: any;
  public polygon: any;
  public qtPolygon: any;
  public qPolygon: any;
  public hasGraphic: boolean = true;
  public hasLoaded: boolean = false;
  public basemap: BaseMap[] = basemap;
  public baseMapLayers: any[] = new Array();
  public errorLayer: boolean = false;
  public errorLayerMessage: string = "";
  public isDraw: boolean = false;
  public display: boolean = false;
  public typ: string = "info";
  public content: string = "";
  public title: string = "Informazioni";
  public hasEditLayer = environment.webgis.hasEditLayer;
  public enableApply:boolean =true;// environment.webgis.enableApply;
  /*public polygonIdentify:any;
  public identifyParams:any;
  public layerOption:any;*/
  @Input() geometryZoom;
  @Input('praticaId') idPiano: string;
  @Input() idLabel: string;
  @Input() user: string;
  @Input() externalQuery: string;
  @Input() hasEditor: boolean;
  @Input() isDettaglio:boolean;
  @Input() callbackOnEditsComplete=(event:any)=>{};
  @Input() listaAllegati:Allegato[]=[];
  //@Input() idAllegatiRichiesta:number=0;
  @Output() result = new EventEmitter<any>();
  @Output() refresh = new EventEmitter<boolean>();
  @Output() closeMap = new EventEmitter<boolean>();
  @Output() popolaTabella: EventEmitter<any> = new EventEmitter<any>(); 
  @Output() deleteShape = new EventEmitter<any>();
  @Output() aggiuntoShapeFile=new EventEmitter<Allegato>();//serve a far refreshare la listaAllegati dal component padre
  @ViewChild('calcolaButton',{static:false}) public calcolaButton: ElementRef;
  @ViewChild('cancellaButton',{static:false}) public cancellaButton: ElementRef;
  @ViewChild('editorContainer',{static:false}) private editorContainer: ElementRef;
  @ViewChild('mapViewNode',{static:false}) private mapViewEl: ElementRef;
  @ViewChild('editorDiv',{static:false}) private editorDiv: ElementRef;
/*******NEW */
  public _tipoSelezioneLocalizzazione:string='';
  public configFunzionalita={
    enableUploadShape:true,
    enableEditingManuale:true,
    enableSelezioneParticelle:true,
    enableCalcolaParticelle:false
  };

  private applicaConfigFunzionalita(tipoSelezioneLocalizzazione: string) {
    //disabilito eventuali funzionalità
    let titolo="Editing";
    if (tipoSelezioneLocalizzazione == 'PTC') {
      this.configFunzionalita.enableUploadShape = false;
      this.configFunzionalita.enableEditingManuale = false;
      this.configFunzionalita.enableSelezioneParticelle = true;
      titolo="Selezione particelle";
    } else if (tipoSelezioneLocalizzazione == 'SHPD') {
      this.configFunzionalita.enableUploadShape = false;
      this.configFunzionalita.enableEditingManuale = true;
      this.configFunzionalita.enableSelezioneParticelle = false;
      titolo="Editing area";
    } else if (tipoSelezioneLocalizzazione == 'SHPF') {
      this.configFunzionalita.enableUploadShape = true;
      this.configFunzionalita.enableEditingManuale = false;
      this.configFunzionalita.enableSelezioneParticelle = false;
      titolo="Caricamento shape file";
    }
    if(this.pFloatingPane && this.pFloatingPane.titleNode){
      this.pFloatingPane.titleNode.innerText=titolo;
    }
    if (this.editorWidget) {
      if (!this.configFunzionalita.enableEditingManuale) {
        this.editorWidget.templatePicker.domNode.style.display = 'none';
      } else {
        this.editorWidget.templatePicker.domNode.style.display = 'block';
      }
      if(this.configFunzionalita.enableUploadShape){
        //disattivo la cancellazione da mappa
        this.editorWidget.domNode.style.display = 'none'
      }else{
        this.editorWidget.domNode.style.display = 'block'
      }
    }
  }

  @Input()
  set tipoSelezioneLocalizzazione(val: string) {
    //qui gestisco la disattivazione di alcune funzionalità
    this._tipoSelezioneLocalizzazione = val;
    this.applicaConfigFunzionalita(val);
  }

  @Input()
  set aggiornaMappa(val: number) {
    //qui gestisco la disattivazione di alcune funzionalità
    if(this.map){
      this.map.getLayer('EditPolygon').refresh();  
      if(this.geometryZoom!=null){
        this.map.centerAndZoom(this.geometryZoom, 14);
        this.geometryZoom=null;
      }
    }
    
  }
  numeroAllegatiShape(){
    return this.listaAllegati.length;
  }
  /**** end NEW */

  private env = environment;
  private unsubscribe$ = new Subject<void>();

  constructor(private renderer: Renderer2,
    private httpClient: HttpClient,
    private loadingSvc: LoadingService,
    //private allegatiService: AllegatiService,
    private translateService: TranslateService) { }



  public ngOnDestroy() {
    if (this.editorWidget != null) { this.editorWidget.destroy(); }
    if (this.pFloatingPane != null) { this.pFloatingPane.destroy(); }
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  public emitPopolaTabella(container: any): void {
    this.popolaTabella.emit(container);
  }

  public ngOnInit() {

    const options = {
      url: 'https://js.arcgis.com/3.26/'
    };

    loadModules([
      'esri/map',
      'esri/renderers/SimpleRenderer',
      'esri/layers/FeatureLayer',
      'esri/tasks/QueryTask',
      'esri/tasks/query',
      'esri/tasks/IdentifyTask',
      'esri/tasks/IdentifyParameters',
      'esri/layers/ArcGISTiledMapServiceLayer',
      'esri/layers/ArcGISDynamicMapServiceLayer',
      'esri/geometry/Polygon',
      'esri/SpatialReference',
      'esri/geometry/Extent',
      'esri/layers/GraphicsLayer',
      'esri/symbols/SimpleFillSymbol',
      'esri/symbols/SimpleLineSymbol',
      'esri/Color',
      'esri/toolbars/draw',
      'esri/geometry/jsonUtils',
      'esri/graphic',
	    'esri/geometry/Point',
      'esri/dijit/editing/Editor',
      'dojox/layout/FloatingPane',
      'dojo/_base/array',
      'esri/config',
      "dojo/dom",
      "dojo/json",
      "dojo/on",
      "dojo/parser",
      "dojo/sniff",
      "esri/request",
      "esri/geometry/scaleUtils",
      "dojo/_base/lang",
      'dojo/i18n!esri/nls/jsapi'
    ], options).then(([
      EsriMap,
      SimpleRenderer,
      FeatureLayer,
      QueryTask,
      Query,
      IdentifyTask,
      IdentifyParameters,
      ArcGISTiledMapServiceLayer,
      ArcGISDynamicMapServiceLayer,
      Polygon,
      SpatialReference,
      Extent,
      GraphicsLayer,
      SimpleFillSymbol,
      SimpleLineSymbol,
      Color,
      Draw,
      jsonUtils,
      Graphic,
	    Point,
      Editor,
      FloatingPane,
      arrayUtils,
      esriConfig,
      dom,
      JSON,
      on,
      parser,
      sniff,
      esriRequest,
      scaleUtils,
      lang,
	  dojoI18n
    ]) => {

      //per ogni chiamata verso un layer, aggiungo il token
      esriRequest.setRequestPreCallback(function(ioArgs){       
        try{
          if(ioArgs.url.indexOf(urlLayer) >= 0 && environment.developer){
            let cookieStorage  : CookieStorage = new CookieStorage({path:"/",secure:!CONST.DEVELOPER});           
            ioArgs.headers['Authorization'] = "Bearer " + cookieStorage.getItem("access_token");
          }
        }catch(e){
          console.log(e)
        }
        return ioArgs;
      });
      // CREO PANNELLO PER DRAG EDITING WIDGET
      if (this.hasEditor) {
        this.pFloatingPane = new FloatingPane({
          title: this.hasEditLayer?"Editing":"Selezione particelle",
          resizable: false,
          dockable: false,
          style: "position:absolute;top:20px;right:40px;width:275px;height:390px;visibility:visible;corner:round",
          id: "pFloatingPane"
        }, this.editorContainer.nativeElement);
        this.pFloatingPane.startup();
      }

      var urlLayer = "";
      // CREO PANNELLO PER DRAG EDITING WIDGET
      // se si è in fase di editing mostro il layer di editing
      // altrimenti verifico se l'uente è stato passato allora uso lo stesso il layer dell'editing (ma senza la possibilitò di poterlo modificare)
      // se invece non sto in editor e l'utente è null allora uso il layer di visualizzazione
      if (this.hasEditor) {
        urlLayer = CONST.EDIT_LAYER;
      }
      else {
        if (this.user == null || this.user == '') {
          urlLayer = CONST.VIEW_LAYER;
        }
        else {
          urlLayer = CONST.EDIT_LAYER;
        }
      }
      // INIZIALIZZAZIONE VARIABILI GLOBALI
      esriConfig.defaults.map.logoLink = "http://www.sit.puglia.it";
      //esriConfig.defaults.io.proxyUrl = environment.webgis.urlOrtofoto + "/EsriJsViewer/" + "ProxyUtil/proxy.ashx";
      esriConfig.defaults.io.proxyUrl = environment.webgis.urlProxy;
      esriConfig.defaults.io.alwaysUseProxy = false;
      esriConfig.defaults.io.corsEnabledServers.push("webadaptor.tz.it:6443");
      esriConfig.defaults.io.corsEnabledServers.push("webadaptor.tz.it");
      let urlBackEnd=new URL(environment.webgis.urlLocale);
      esriConfig.defaults.io.corsEnabledServers.push(urlBackEnd.host);
      //var graphicLayerParticella;
      var _self = this;
      var identifyTask;
      var identifyParams;

      /************************************ */
      if (this.hasEditor) {
        on(dom.byId("uploadShapeForm"), "change", function (event) {
          var fileName = event.target.value.toLowerCase();
          //filename is full path in IE so extract the file name
          if (sniff("ie")) {
            var arr = fileName.split("\\");
            fileName = arr[arr.length - 1];
          }
          if (event.target.files[0].size > _self.env.maxSizeUpload) {
            let err = 'La dimensione del file(' +
              Math.round(event.target.files[0].size / 1024 / 1024) + ' MB) supera i limiti ammessi (' +
              Math.round(_self.env.maxSizeUpload / 1024 / 1024) + ' MB)';
            dom.byId('upload-status').innerHTML = '<p style="color:red">' + err + '</p>';
            return;
          }
          // CONTROLLO CHE SIA UNO ZIP
          if (fileName.indexOf(".zip") !== -1) {
            _self.hasLoaded = false;
            generateFeatureCollection(fileName, event.target.files);
          }
          else {
            dom.byId('upload-status').innerHTML = '<p style="color:red">Estensione file attesa: .zip </p>';
          }
        });
      }


      async function generateFeatureCollection(fileName, files) {
        var name = fileName.split(".");
        name = name[0].replace("c:\\fakepath\\", "");
        dom.byId('upload-status').innerHTML = '<b>Loading… </b>' + name;

        //Define the input params for generate see the rest doc for details
        //http://www.arcgis.com/apidocs/rest/index.html?generate.html
        var params: any = {
          'name': name,
          'targetSR': _self.map.spatialReference,
          'maxRecordCount': 1000,
          'enforceInputFileSizeLimit': true,
          'enforceOutputJsonSizeLimit': true
        };

        //generalize features for display Here we generalize at 1:40,000 which is approx 10 meters
        //This should work well when using web mercator.
        var extent = scaleUtils.getExtentForScale(_self.map, 40000);
        var resolution = extent.getWidth() / _self.map.width;
        params.generalize = true;
        params.maxAllowableOffset = resolution;
        params.reducePrecision = true;
        params.numberOfDigitsAfterDecimal = 0;


        let fileToUpload = files.item(0);
        let formData = new FormData();
        formData.append('file', fileToUpload, fileToUpload.name);
        formData.append('idPiano', _self.idPiano);
        _self.loadingSvc.emitLoading(true);
        _self.hasLoaded = false;
        try {
          let response = await
            _self.httpClient.post<BaseResponse<InfoAndGeoJson>>(`${_self.env.baseUrl}/geoutil/shape2geoJSON`, formData)
              .pipe(takeUntil(_self.unsubscribe$))
              .toPromise();
          _self.loadingSvc.emitLoading(false);
          if (response.payload && response.payload.fc) {
            addGeoGsonToMap(response.payload.fc);
            //TODO 
            //_self.allegatiService.uploadedShape$.next(true);//mando il segnale al tab allegati per refreshare la lista
            //let container: any = {
            //  nomeFile: fileToUpload.name,
            //  idFile: response.payload.idAllegato
            //}
            //_self.emitPopolaTabella(container);
          }
          if (response.payload.codeValidazione != 0) {
            _self.display = true;
            _self.typ = "warning";
            _self.content = response.payload.messaggioValidazione;
            _self.hasLoaded = true;
          } else {
            _self.display = true;
            _self.typ = "success";
            _self.content = response.payload.messaggioValidazione || _self.translateService.instant('LOCALIZZAZIONE.CORRECTLY_UPLOAD_SHAPE');
            _self.hasLoaded = true;
            _self.aggiuntoShapeFile.emit(response.payload.allegatoDto);
          }
        } catch (error) {
          lang.hitch(this, errorHandler);
        }
        dom.byId('upload-status').innerHTML = '';
        //questa istruzione scazza tutta la mappa refreshata 
        //dom.byId("uploadShapeForm").reset();
        dom.byId("inFile").value="";

        /*  OLD STYLE
        //test brutale
        //console.log('aggiungo il sample')
        //addGeoGsonToMap(CONST.sampleGeoJSON);
        //return;
        //use the rest generate operation to generate a feature collection from the zipped shapefile
        var myContent = {
          'filetype': 'shapefile',
          'publishParameters': JSON.stringify(params),
          'f': 'json',
          'callback.html': 'textarea'
        };
        request({
          //http://localhost:8081
          url: '/geoutil/shape2geoJSON',
          content: myContent,
          form: dom.byId('uploadShapeForm'),
          handleAs: 'json',
          useProxy: 'false',
          load: lang.hitch(this, function (response) {
            if (response.error) {
              errorHandler(response.error);
              return;
            }
            _self.hasLoaded = false;
            console.log("response:"+response);
            //var layerName = response.featureCollection.layers[0].layerDefinition.name;
            //dom.byId('upload-status').innerHTML = '<b>Loaded: </b>' + layerName;
            //need to convert geogson to esri feature
            addGeoGsonToMap(response);
          }),
          error: lang.hitch(this, errorHandler)
        },{usePost: 'false'});
        */
      }

      function errorHandler(error) {
        _self.hasLoaded = true;
        dom.byId('upload-status').innerHTML = "<p style='color:red'>" + error.message + "</p>";
      }

      function addGeoGsonToMap(fc) {
        // First we create an empty feature collection:
        var featureCollection = {
          "layerDefinition": null,
          "featureSet": {
            "features": [],
            "geometryType": "esriGeometryPolygon",
            /*"extent": {
              "xmin": -231444,
              "ymin": 3901142,
              "xmax": 853081,
              "ymax": 5252194,
              "spatialReference": {
                "wkid": 32633,
                "latestWkid": 32633
              }
            }*/
          }
        };

        //give the feature collection a layer definition:
        featureCollection.layerDefinition = {
          "currentVersion": 10.7,
          "id": 0,
          "name": "Reg_2016_WGS84_g",
          "type": "Feature Layer",
          "displayField": "",
          "description": "",
          "copyrightText": "",
          "defaultVisibility": true,
          "relationships": [],
          "isDataVersioned": false,
          "supportsAppend": true,
          "supportsCalculate": true,
          "supportsASyncCalculate": true,
          "supportsTruncate": false,
          "supportsAttachmentsByUploadId": true,
          "supportsAttachmentsResizing": true,
          "supportsRollbackOnFailureParameter": true,
          "supportsStatistics": true,
          "supportsExceedsLimitStatistics": true,
          "supportsAdvancedQueries": true,
          "supportsValidateSql": true,
          "supportsCoordinatesQuantization": true,
          "supportsFieldDescriptionProperty": true,
          "supportsQuantizationEditMode": true,
          "supportsApplyEditsWithGlobalIds": false,
          "supportsMultiScaleGeometry": true,
          "supportsReturningQueryGeometry": true,
          "advancedQueryCapabilities": {
            "supportsPagination": true,
            "supportsPaginationOnAggregatedQueries": true,
            "supportsQueryRelatedPagination": true,
            "supportsQueryWithDistance": true,
            "supportsReturningQueryExtent": true,
            "supportsStatistics": true,
            "supportsOrderBy": true,
            "supportsDistinct": true,
            "supportsQueryWithResultType": true,
            "supportsSqlExpression": true,
            "supportsAdvancedQueryRelated": true,
            "supportsCountDistinct": true,
            "supportsPercentileStatistics": true,
            "supportsReturningGeometryCentroid": true,
            "supportsReturningGeometryProperties": true,
            "supportsQueryWithDatumTransformation": true,
            "supportsHavingClause": true,
            "supportsOutFieldSQLExpression": true,
            "supportsMaxRecordCountFactor": true,
            "supportsTopFeaturesQuery": true,
            "supportsDisjointSpatialRel": true,
            "supportsQueryWithCacheHint": true
          },
          "useStandardizedQueries": false,
          "geometryType": "esriGeometryPolygon",
          "minScale": 0,
          "maxScale": 0,
          /*"extent": {
            "xmin": -231444,
            "ymin": 3901142,
            "xmax": 853081,
            "ymax": 5252194,
            "spatialReference": {
              "wkid": 32633,
              "latestWkid": 32633
            }
          },*/
          "drawingInfo": {
            "renderer": {
              "type": "simple",
              "symbol": {
                "type": "esriSFS",
                "style": "esriSFSSolid",
                "color": [
                  76,
                  129,
                  205,
                  191
                ],
                "outline": {
                  "type": "esriSLS",
                  "style": "esriSLSSolid",
                  "color": [
                    120,
                    120,
                    120,
                    255
                  ],
                  "width": 0.75
                }
              }
            },
            "transparency": 0,
            "labelingInfo": null
          },
          "allowGeometryUpdates": true,
          "hasAttachments": false,
          "htmlPopupType": "",
          "hasM": false,
          "hasZ": false,
          "objectIdField": "FID",
          "globalIdField": "",
          "typeIdField": "",
          "fields": [
            {
              "name": "FID",
              "type": "esriFieldTypeOID",
              "alias": "FID",
              "sqlType": "sqlTypeOther",
              "nullable": false,
              "editable": false,
              "domain": null,
              "defaultValue": null
            },
            {
              "name": "COD_REG",
              "type": "esriFieldTypeInteger",
              "alias": "COD_REG",
              "sqlType": "sqlTypeOther",
              "nullable": true,
              "editable": true,
              "domain": null,
              "defaultValue": null
            },
            {
              "name": "REGIONE",
              "type": "esriFieldTypeString",
              "alias": "REGIONE",
              "sqlType": "sqlTypeOther",
              "length": 50,
              "nullable": true,
              "editable": true,
              "domain": null,
              "defaultValue": null
            },
            {
              "name": "SHAPE_Leng",
              "type": "esriFieldTypeDouble",
              "alias": "SHAPE_Leng",
              "sqlType": "sqlTypeOther",
              "nullable": true,
              "editable": true,
              "domain": null,
              "defaultValue": null
            },
            {
              "name": "SHAPE_Area",
              "type": "esriFieldTypeDouble",
              "alias": "SHAPE_Area",
              "sqlType": "sqlTypeOther",
              "nullable": true,
              "editable": true,
              "domain": null,
              "defaultValue": null
            }
          ],
          "indexes": [],
          "types": [],
          "templates": [
            {
              "name": "New Feature",
              "description": "",
              "drawingTool": "esriFeatureEditToolPolygon",
              "prototype": {
                "attributes": {
                  "COD_REG": null,
                  "REGIONE": null,
                  "SHAPE_Leng": null,
                  "SHAPE_Area": null
                }
              }
            }
          ],
          "supportedQueryFormats": "JSON, geoJSON",
          "hasStaticData": false,
          "maxRecordCount": -1,
          "standardMaxRecordCount": 4000,
          "standardMaxRecordCountNoGeometry": 32000,
          "tileMaxRecordCount": 4000,
          "maxRecordCountFactor": 1,
          "capabilities": "Create,Delete,Query,Update,Editing"
        };
        //creo il layer
        var featureLayer = new FeatureLayer(featureCollection);
        // _self.map.addLayer(featureLayer);
        var fullExtent;
        fullExtent = fullExtent ? fullExtent.union(featureLayer.fullExtent) : featureLayer.fullExtent;
        //var layers = [];
        /* for(let i in featureCollection.featureSet.features){
           var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([250,0,33]), 2), new Color([253,150,44,0.25]));
           var attr = featureCollection.featureSet.features[i].attributes;
           attr.is_custom = 1;
           attr[_self.idLabel] = _self.id;
           
           // var graphic = new Graphic(layer.featureSet.features[i].geometry,symbol,attr);
           // if (_self.graphicLayer != null){
           //   //_self.graphicLayer.clear();
           // }else {
           //   _self.graphicLayer = new GraphicsLayer({opacity:1,id:'bufferGraphicLayerQueryIni'});
           //   _self.map.addLayer(_self.graphicLayer);
           // }
           // _self.graphicLayer.add(graphic);
           // _self.hasGraphic = true;
         }*/
        //loop through the items and add to the feature layer
        ////var features = [];
        //arrayUtils.forEach(fc.features, function(item) {
        let extent: any;
        let initPoint;
        fc.features.forEach((item) => {
          var attributes = {};
          var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([250, 0, 33]), 2), new Color([253, 150, 44, 0.25]));
          //attr.is_custom = 1;
          //attr[_self.idLabel] = _self.id;
          //pull in any additional attributes if required
          attributes["FID"] = item.id;
          attributes["COD_REG"] = "COD_" + item.id;
          attributes["REGIONE"] = "REG_" + item.id;
          attributes["SHAPE_Leng"] = item.id;
          attributes["SHAPE_Area"] = item.id;
          attributes["is_custom"] =1;
          attributes["id_fascicolo"] = _self.idPiano;
          //var coordinates=[[[549013,4613947],[668425,4585173],[619509,4542012],[531748,4523309],[405143,4582296],[412336,4672934],[549013,4613947]]];
          var geometry = new Polygon(
            { rings: item.geometry.coordinates[0], "spatialReference": { "wkid": 32633 } });
          initPoint= new Point(item.geometry.coordinates[0][0][0][0],item.geometry.coordinates[0][0][0][1],new SpatialReference({ wkid: 32633 })) ; 
          // featureCollection.featureSet.features.push({attributes,geometry});
          //var graphic = new Graphic(geometry,symbol,attr);
          var graphic = new Graphic(geometry, symbol);
          graphic.setAttributes(attributes);
          //console.log('aggiungendo graphic:{}',graphic);
          //qui calcolo l'estensione per ogni poligono
          extent = extent ? extent.union(geometry.getExtent()) : geometry.getExtent();
          if (_self.graphicLayer != null) {
            // _self.graphicLayer.clear();
          } else {
            _self.graphicLayer = new GraphicsLayer({ opacity: 0.5, id: 'bufferGraphicLayerQueryIni' });
            _self.map.addLayer(_self.graphicLayer);
          }
          _self.graphicLayer.add(graphic);
          //features.push(graphic);
          _self.hasGraphic = true;
        });
        //var featureLayer = new FeatureLayer(featureCollection);
        //fullExtent = fullExtent ? fullExtent.union(featureLayer.fullExtent) : featureLayer.fullExtent;
        //_self.map.graphicLayer=featureLayer;
        //featureLayer.applyEdits(features, null, null);
        _self.map.addLayer(featureLayer);
        //_self.map.setExtent(fullExtent.expand(1.25), true);
        
        _self.hasLoaded = true;
        //in questo caso simulo il conferma area 
        _self.map.centerAndZoom(initPoint, 14).then(async ()=>{
        
          await _self.addPolygon();
        
          _self.map.setExtent(extent.expand(1.25), true);

          //e' come se ha cliccato conferma area
          if(dom.byId('upload-status')){
            dom.byId('upload-status').innerHTML = "";
          }
        });
      }

      /************************************ */

      // CARICO LA PARTICELLA RICHIESTA
      if (this.externalQuery != null) {
        var queryTask = new QueryTask(CONST.CATASTO_LAYER + "/2");
        var query = new Query();
        query.where = "(" + this.externalQuery + ")";
        query.returnGeometry = true;
        query.outFields = ["*"];
        queryTask.execute(query, ShowResults, ErrorCallback);
      }
      else {
        ShowResults(null);
      }

      function ErrorCallback(error) {
        _self.hasLoaded = true;
        _self.hasGraphic = false;
        if (_self.hasEditor) {
          _self.renderer.addClass(_self.editorContainer.nativeElement, 'hidden');
        }
        _self.error = "Errore nella creazione della mappa, per piacere riprova fra un minuto " + error;
      }

      // FUNZIONE PER CARICARE I SERVIZI
      function CaricaServizi(customExtentAndSR: any, results?: any, polygon?: any): void {
        _self.map = new EsriMap(_self.mapViewEl.nativeElement, {
          extent: customExtentAndSR,
          //center: [-122.45, 37.75],
          //zoom: 13,
          showLabels: true
        });
        if (_self.hasEditor) {
          _self.map.on("layers-add-result", initEditor);
        }
        _self.map.on("update-start", function () {
          _self.hasLoaded = false;
        });
        _self.map.on("update-end", function () {
          _self.hasLoaded = true;
        });
        _self.map.on("load", initToolbar);

        //BaseMap
        var baseMap2006 = new ArcGISTiledMapServiceLayer(environment.webgis.urlOrtofoto + "/arcgis/rest/services/BaseMaps/Ortofoto2006/ImageServer");
        baseMap2006.hide();
        _self.map.addLayer(baseMap2006);
        var baseMap2010 = new ArcGISTiledMapServiceLayer(environment.webgis.urlOrtofoto + "/arcgis/rest/services/BaseMaps/Ortofoto2010/ImageServer");
        baseMap2010.hide();
        _self.map.addLayer(baseMap2010);
        var baseMap2011 = new ArcGISTiledMapServiceLayer(environment.webgis.urlOrtofoto + "/arcgis/rest/services/BaseMaps/Ortofoto2011/ImageServer");
        baseMap2011.hide();
        _self.map.addLayer(baseMap2011);
        var baseMap2013 = new ArcGISTiledMapServiceLayer(environment.webgis.urlOrtofoto + "/arcgis/rest/services/BaseMaps/Ortofoto2013/ImageServer");
        baseMap2013.hide();
        _self.map.addLayer(baseMap2013);
        var baseMap2016 = new ArcGISTiledMapServiceLayer(environment.webgis.urlOrtofoto + "/arcgis/rest/services/BaseMaps/Ortofoto2016/ImageServer");
        baseMap2016.hide();
        _self.map.addLayer(baseMap2016);
        var baseMapTrasparente = new ArcGISDynamicMapServiceLayer(environment.webgis.urlOrtofoto + "/arcgis/rest/services/BaseMaps/SfondoTrasp/MapServer");
        _self.map.addLayer(baseMapTrasparente);

        _self.baseMapLayers.push(baseMap2006, baseMap2010, baseMap2011, baseMap2013, baseMap2016, baseMapTrasparente);

        var opLayer1 = new ArcGISDynamicMapServiceLayer(CONST.SFONDO_LAYER);
        _self.map.addLayer(opLayer1);

        var opLayer2 = new ArcGISDynamicMapServiceLayer(CONST.CATASTO_LAYER);
        _self.map.addLayer(opLayer2);

        console.log(results, polygon);

        if (results) {
          if (results.features.length != 0) {
            _self.graphicLayer = new GraphicsLayer({ opacity: 1, id: 'bufferGraphicLayerQueryIni' });
            _self.map.addLayer(_self.graphicLayer);

            if (results.geometryType == "esriGeometryPolygon") {
              for (let i in results.features) {
                var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([250, 0, 33]), 2), new Color([253, 246, 44, 0.25]));
                var attr = results.features[i].attributes;
                attr.id_fascicolo = _self.idPiano;
                attr.user_id = _self.user;
                attr.is_particella = true;
                attr.is_custom = 0;
                var graphic = new Graphic(results.features[i].geometry, symbol, attr);
                _self.graphicLayer.add(graphic);
                //graphicLayerParticella = graphic;
              }
            }

            //_self.graphicLayer.add(graphic);
          }
        }

        var pugliaRenderer = new SimpleRenderer(
          new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 128, 0]), 2), new Color([76, 175, 80, 0.45]))
        );
        if (_self.hasEditLayer) {
          var editLayerPolygon = new FeatureLayer(urlLayer, {
            outFields: ['*'],
            mode: FeatureLayer.MODE_ONDEMAND,
            visible: true,
            id: 'EditPolygon',
          });
          editLayerPolygon.allowGeometryUpdates=false;
          editLayerPolygon.setRenderer(pugliaRenderer);
          if (_self.user != null)
            //editLayerPolygon.setDefinitionExpression("(id_fascicolo='" + _self.idPiano + "' AND user_id='" + _self.user + "')");
            editLayerPolygon.setDefinitionExpression("(id_fascicolo='" + _self.idPiano + "')");
          else
            editLayerPolygon.setDefinitionExpression("(id_fascicolo='" + _self.idPiano + "')");

          _self.map.addLayers([editLayerPolygon]);
          editLayerPolygon.on("load", function (event) {
            _self.errorLayer = false;
            _self.errorLayerMessage = "";
            if ((results == undefined || results == null) || !_self.hasEditor) {
              var query = new Query();
              query.where = "1=1";
              query.outSpatialReference = new SpatialReference(32633);

              editLayerPolygon.queryExtent(query, function (response) {
                if (response != null && response.count != null && response.count > 0) {
                  var extent = response.extent;
                  _self.map.setExtent(extent, true);
                }
              });
            }
          });
          editLayerPolygon.on("error", function (error) {
            if( error.error.message!="Request canceled")
            _self.errorLayer = true;
            _self.errorLayerMessage = error.error.message;
            console.log("errore", error);
          });
          
          let  geometryAddsApply;
          editLayerPolygon.on("before-apply-edits", function (geometry) {
            //aggiungo i metadati per ogni poligono... importante l'id_fascicolo per capire se posso aggiornare ....
            let operazioni=['adds','updates'];
            this.geometryAddsApply= new Array();
            operazioni.forEach(operazione=>{
              if (geometry != null && geometry[operazione] != null) {
                
                var geometryAdds = geometry[operazione];
                this.geometryAddsApply=geometryAdds;
                for (let i in geometryAdds) {
                  //addParticella(geometryAdds[i],true)
                  geometryAdds[i].attributes['id_fascicolo'] = _self.idPiano;
                  geometryAdds[i].attributes['user_id'] = _self.user;
                  if (geometryAdds[i].attributes['is_custom'] != null && geometryAdds[i].attributes['is_particella'] != null && geometryAdds[i].attributes['is_particella'])
                    geometryAdds[i].attributes['is_custom'] = geometryAdds[i].attributes['is_custom'];
                  else
                    geometryAdds[i].attributes['is_custom'] = 1;
                };
              }
            });
          });

          editLayerPolygon.on("edits-complete", function (event) {
            console.log("edits-complete",event);
            if(_self.callbackOnEditsComplete){
              _self.callbackOnEditsComplete(event);
            }
            if(event.adds || event.updates){
              for (let i in this.geometryAddsApply) {
                if(event.updates.length>0){
                  _self.deleteShape.emit(event.updates[0].objectId)
                }
                addParticella(this.geometryAddsApply[i],true);
              }
            }
            if(event.deletes &&  Array.isArray(event.deletes) && event.deletes.length>0)
            {
              _self.deleteShape.emit(event.deletes[0].objectId)
            }
          });

          editLayerPolygon.on("mouse-drag", function (event) {
            console.log("mouse-drag",event);
            
          });

          editLayerPolygon.on("update-start", function (event) {
            if(_self._tipoSelezioneLocalizzazione=="PTC" || _self._tipoSelezioneLocalizzazione=="SHPF" ){
              editLayerPolygon.allowGeometryUpdates=false;
            }else{
              editLayerPolygon.allowGeometryUpdates=true;
            }
            
          });
        }
      }

      // INIT TOOLBAR
      function initToolbar() {
        _self.toolbar = new Draw(_self.map);
        _self.isDraw = true;
        _self.toolbar.on("draw-end", addParticella);
        _self.hasLoaded = true;
      }
      /*_self.polygonIdentify = new Polygon(new SpatialReference({wkid:32633}));
      _self.identifyParams = new IdentifyParameters();
      _self.layerOption = IdentifyParameters.LAYER_OPTION_VISIBLE;*/
      if (this.hasEditor && this.hasEditLayer) {
        _self.qtPolygon = new QueryTask(urlLayer);
        _self.qPolygon = new Query();
        var calcola = _self.calcolaButton.nativeElement;
        calcola.onclick = function () {
          _self.hasLoaded = false;
          //if(_self.graphicLayer!=null)
          //  _self.graphicLayer.clear();
          _self.qPolygon.where = "(id_fascicolo='" + _self.idPiano + "' AND is_custom=1)";
          _self.qPolygon.returnGeometry = true;
          _self.qPolygon.spatialRelationship = Query.SPATIAL_REL_INTERSECTS;
          _self.qPolygon.outFields = ["*"];
          //_self.qPolygon.distances = [-1.1] ;
          _self.qtPolygon.execute(_self.qPolygon, function (results) {
            var polygonIdentify = new Polygon(new SpatialReference({ wkid: 32633 }));
            if (results.features.length > 0) {
              for (let i in results.features) {
                polygonIdentify.addRing(results.features[i].geometry.rings[0]);
              }
              addParticella(polygonIdentify, true);
            } else {
              _self.display = true;
              _self.typ = "info";
              _self.content = "Nessuna particella da aggiungere";
              _self.hasLoaded = true;
            }
          }, function (error) {
            console.log("Errore in Calcola Particelle", error);
            _self.display = true;
            _self.typ = "danger";
            _self.content = "Errore in Calcola Particelle";
            _self.hasLoaded = true;
          });
        }
      }
      if (this.hasEditor){
      var cancella = _self.cancellaButton.nativeElement;
        cancella.onclick = function () {
          if (_self.hasEditLayer) {
            //let selectedGraphic =_self.map.getSelectedFeatures();
            //console.log("selected graphic",selectedGraphic);
            _self.map.getLayer("EditPolygon").clearSelection();
            _self.map.getLayer("EditPolygon").refresh();
          }
          //console.log("elimina")
          _self.map.infoWindow.hide();
          if (_self.graphicLayer != null)
            _self.graphicLayer.clear();
          _self.hasGraphic = false;
          var i = _self.polygon.rings.length - 1;
          while (i >= 0) {
            //console.log("i prima:" + i);
            _self.polygon.removeRing(i);
            i--;
            //console.log("i dopo:" + i);
          }
          _self.toolbar.deactivate();
          /*debugger;
          var i = _self.polygon.rings.length - 1;
          _self.polygon.removeRing(i);

          var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([250,0,33]), 2), new Color([253,150,44,0.25]));
          var attr = {};
          var graphic = new Graphic(jsonUtils.fromJson(_self.polygon),symbol,attr);

          _self.graphicLayer.add(graphic);*/
        };
      }

      // ADD PARTICELLA
      function addParticella(geometry, callback) {
        _self.hasLoaded = false;
        if (geometry.geometry != null && geometry.geometry.type != "point") {
          _self.toolbar.deactivate();
        }
        //Pulisce il graphicLayer
        //if (_self.graphicLayer != null) _self.graphicLayer.clear();

        //create identify tasks and setup parameters
        identifyTask = new IdentifyTask(CONST.CATASTO_LAYER);

        identifyParams = new IdentifyParameters();
        identifyParams.tolerance = 0;
        identifyParams.returnGeometry = true;
        identifyParams.layerIds = [2];
        if(_self.tipoSelezioneLocalizzazione=='PTC'){
          identifyParams.layerOption = IdentifyParameters.LAYER_OPTION_VISIBLE;//LAYER_OPTION_ALL;
        }else{
          identifyParams.layerOption = IdentifyParameters.LAYER_OPTION_ALL;
        }
        
        identifyParams.width = _self.map.width;
        identifyParams.height = _self.map.height;
        identifyParams.geometry = geometry.geometry != null ? geometry.geometry : geometry;
        identifyParams.mapExtent = _self.map.extent;
        identifyParams.spatialReference = new SpatialReference({ wkid: 32633 });

        identifyTask.execute(identifyParams, function (results) {
          _self.hasLoaded = true;
          if (_self.polygon == null) _self.polygon = new Polygon(new SpatialReference({ wkid: 32633 }));
          if(results.length>200){
            _self.display = true;
            _self.typ = "danger";
            _self.content = "L'area selezionata è troppo grande";
           
            _self.map.getLayer("EditPolygon").applyEdits(null,null,[geometry],null);
            return null
            //_self.map.getLayer("EditPolygon").clearSelection();
           
          }else{
            if (callback || !_self.enableApply) {
              if (results && results.length == 0) {
                _self.display = true;
                _self.typ = "warning";
                _self.content = "Nessuna particella rintracciata, prova ad ingrandire fino a vedere il layer delle particelle.";
              } else {
                if (_self._tipoSelezioneLocalizzazione != "PTC") {
                  results.forEach(element => {
                    element.oid == null ? element.feature.attributes['oid'] = geometry.attributes.oid : null;
                  });
                  _self.result.emit(results);
                }
              }
            }
            if (callback == null || !callback) {
              for (var i = 0; i < results.length; i++) {
                //_self.polygon.addRing(results[i].feature.geometry.rings[0]);
                var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([250, 0, 33]), 2), new Color([253, 150, 44, 0.25]));
                var attr = results[i].feature.attributes;
                //debugger;
                if (_self.isDraw) {
                  attr.is_custom = 0;
                  attr.is_particella = true;
                }
                var graphic = new Graphic(results[i].feature.geometry, symbol, attr);
                if (_self.graphicLayer != null) {
                  //_self.graphicLayer.clear();
                } else {
                  _self.graphicLayer = new GraphicsLayer({ opacity: 1, id: 'bufferGraphicLayerQueryIni' });
                  _self.map.addLayer(_self.graphicLayer);
                }
                _self.graphicLayer.add(graphic);
                _self.hasGraphic = true;

              }
              //_self.isDraw = false;
              /*if (_self.polygon.rings.length > 0 ) {
                var symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([250,0,33]), 2), new Color([253,150,44,0.25]));
                debugger;
                var attr = {"USERID":null,"ID_DOMANDA":null,"FROM_CT":"1" };
                var graphic = new Graphic(jsonUtils.fromJson(_self.polygon),symbol,attr);
  
                if (_self.graphicLayer != null) _self.graphicLayer.clear();
                else {
                  _self.graphicLayer = new GraphicsLayer({opacity:1,id:'bufferGraphicLayerQueryIni'});
                  _self.map.addLayer(_self.graphicLayer);
                }
  
                _self.graphicLayer.add(graphic);
              }*/
            }
         }
        }, function (error) {
          console.log("Errore nel recupero della particella", error);
          _self.isDraw = false;
          _self.hasLoaded = true;
          _self.display = true;
          _self.typ = "danger";
          _self.content = "Errore nel recupero della particella";
        });
      }

      function initEditor(event) {
        var featureLayerInfos = arrayUtils.map(event.layers, function (layer) {
          return {
            "featureLayer": layer.layer,
            'isEditable': false,
            'showDeleteButton': false,
            'disableAttributeUpdate': true
          };
        });

        var settings = {
          map: _self.map,
          layerInfos: featureLayerInfos,
          toolbarVisible: true
        };
        var params = {
          settings: settings
        };
        _self.editorWidget = new Editor(params, _self.editorDiv.nativeElement);
        _self.editorWidget.startup();
        _self.map.enableSnapping();
        if(_self._tipoSelezioneLocalizzazione){
          //nel caso in cui il component non era ancora pronto...
          _self.applicaConfigFunzionalita(_self._tipoSelezioneLocalizzazione);
        }
      }

      // TROVATA LA PARTICELLA CALCOLA L'EXTENT
      function ShowResults(results): void {
        var tempExtent: any = [];

        _self.polygon = new Polygon(new SpatialReference({ wkid: 32633 }));
        if (results != null && results.features.length != 0) {
          for (var i = 0; i < results.features.length; i++) {
            if (tempExtent == "") tempExtent = results.features[i].geometry.getExtent();
            else tempExtent = tempExtent.union(results.features[i].geometry.getExtent());

            _self.polygon.addRing(results.features[i].geometry.rings[0]);
          }
          var xmin = tempExtent.xmin;
          var ymin = tempExtent.ymin;
          var xmax = tempExtent.xmax;
          var ymax = tempExtent.ymax;

          var customExtentAndSR = new Extent(xmin, ymin, xmax, ymax, new SpatialReference({ "wkid": 32633 }));
          CaricaServizi(customExtentAndSR, results, _self.polygon);
        } else {
          var customExtentAndSR = new Extent(450000, 4400000, 840000, 4700000, new SpatialReference({ "wkid": 32633 }));
          _self.hasGraphic = false;
          CaricaServizi(customExtentAndSR);
        }
      }
    })
      .catch(err => {
        console.error(err);
      });
  } // ngOnInit

  public close() {
    this.closeMap.emit(true)
  }

  public changeBaseMap(item: any, indice: number): void {
    for (var i = 0; i < this.baseMapLayers.length; i++) {
      this.baseMapLayers[i].hide();
    }
    //console.log(this.baseMapLayers[indice]);
    this.baseMapLayers[indice].show();
  }

  public draw(type: string): void {
    this.isDraw = true;
    this.toolbar.activate(type);
  }

  /*public addParticelle():void{
    var _self=this;
    this.qPolygon.where = "( * )";
    this.qtPolygon.execute(this.qPolygon,function(results){
      _self.polygonIdentify.addRing(results.features[0].geometry.rings[0]);
      _self.identifyParams.tolerance = 0;
      _self.identifyParams.returnGeometry = false;
      _self.identifyParams.layerIds = [2];
      _self.identifyParams.layerOption = _self.layerOption;
      _self.identifyParams.width  = _self.map.width;
      _self.identifyParams.height = _self.map.height;
      _self.identifyParams.mapExtent = _self.map.extent;
      _self.identifyParams.geometry = _self.polygonIdentify;

      var identifyTask = new esri.tasks.IdentifyTask('http://webapps.sit.puglia.it/arcgis/rest/services/Background/Catasto/MapServer');


      identifyTask.execute(identifyParams,dojo.hitch( this, function(listObjectId, idResults){
        var returnWebGis = "";

        for (var i = 0; i < idResults.length; i++) {
          if (i != (idResults.length-1)) {
              returnWebGis += idResults[i].feature.attributes.COMUNE+";"+idResults[i].feature.attributes.SEZIONE+";"+idResults[i].feature.attributes.FOGLIO+";"+idResults[i].feature.attributes.NUMERO+"#";
          } else {
              returnWebGis += idResults[i].feature.attributes.COMUNE+";"+idResults[i].feature.attributes.SEZIONE+";"+idResults[i].feature.attributes.FOGLIO+";"+idResults[i].feature.attributes.NUMERO;
          }
        }
        returnString = listObjectId + ';' + returnWebGis;

        console.log("Stringa TEMPORANEA sul parent:" + returnString);
        parent.postMessage(returnString,"*");

      }, listObjectId) );
    });
    console.log(this.map.getLayer("EditPolygon"));
  }*/

  public ricarica(): void {
    this.refresh.emit(true);
  }

  public callbackMappa(event: any) {
    this.display = false;
  }

  public refreshMap(){
    this.map.getLayer("EditPolygon").refresh();
  }

  public addPolygon(): void {
    var _self = this;
    if (this.graphicLayer != null && this.graphicLayer.graphics.length > 0) {
      var arrayGraphics: any = []; 
      for (let i = 0; i < this.graphicLayer.graphics.length; i++) {
        arrayGraphics.push(this.graphicLayer.graphics[i]);
      }
      this.map.getLayer("EditPolygon").applyEdits(arrayGraphics, null, null, function (event) {
        if (event && event[0].success) {
          var tmp = new Array();
          for (let i = 0; i < arrayGraphics.length; i++) {
            tmp.push({ feature: arrayGraphics[i] })
          }
          _self.isDraw = false;
          _self.result.emit(tmp);
          //_self.display = true;
          //_self.typ = "success";
          //_self.content = "Particella aggiunta con successo!";
          _self.map.getLayer("EditPolygon").clearSelection();
          //_self.map.getLayer("EditPolygon").refresh();
          _self.map.infoWindow.hide();
          if (_self.graphicLayer != null)
            _self.graphicLayer.clear();
          _self.hasGraphic = false;
          var i = _self.polygon.rings.length - 1;
          while (i >= 0) {
            //console.log("i prima:" + i);
            _self.polygon.removeRing(i);
            i--;
            //console.log("i dopo:" + i);
          }
          _self.toolbar.deactivate();
        } else {
          console.log(event);
          console.log("Errore inserimento");
          _self.display = true;
          _self.content = "Errore durante l'operazione, si prega di riprovare";
          _self.typ = "danger";
          _self.toolbar.deactivate();
          _self.isDraw = false;
        }
      }, function (event) {
        console.log(event);
        console.log("Errore inserimento");
        _self.display = true;
        _self.content = "Errore durante l'operazione, si prega di riprovare";
        _self.typ = "danger";
        _self.toolbar.deactivate();
        _self.isDraw = false;
      });
    } else {
      //alert('Nessuna particella selezionata!');
      _self.display = true;
      _self.typ = "info";
      _self.content = "Nessuna particella risulta da aggiungere!";
    }
  }

}