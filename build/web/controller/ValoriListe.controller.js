                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             sap.ui.define([
    "sap/ui/Device",
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel"
], function (Device, Controller, Filter, FilterOperator, JSONModel) {
    "use strict";
    var oStorage = jQuery.sap.storage(jQuery.sap.storage.Type.local);
    
    return Controller.extend("sap.ui.dolphin.controller.ValoriListe", {

        onInit: function () {

            this.aSearchFilters = [];
            this.aTabFilters = [];

            this.getView().setModel(new JSONModel({
                isMobile: Device.browser.mobile,
                filterText: undefined
            }), "view");
            
            
             var model = new sap.ui.model.json.JSONModel({
                
                           
                "tip_lista":"",
                "tip_lista_def": "",
                "cod_lista": "",
                "den_lista": "",
                "lista_salvata":"NU",
                "afisare_mesaj":"",
                "addop":false,
               
                
                "tipuri_lista":[],
                "tipuri_lista_def":[],
                
                                
            });

            this.getView().setModel(model, "valorilistemodel");
           
                            
                       
            $.ajax({
                method: "GET",
                url: "rest/restAdaugaValoriListaDef",
                dataType: "JSON",
                data: {tip_lista: "TIPURI_LISTA"}
            }).done(function (data) {                
               model.setProperty("/tipuri_lista", data.valoriLista);
            });
            
            $.ajax({
                method: "GET",
                url: "rest/restAdaugaValoriListaDef",
                dataType: "JSON",
                data: {tip_lista: "TIPURI_LISTA_DEF"}
            }).done(function (data) {
               model.setProperty("/tipuri_lista_def", data.valoriLista);               
            });
            
           

        },
                
        clickButtonAnulare: function (oEvent) {
            var mm = this.getView().getModel("valorilistemodel");                
            mm.setProperty("/tip_lista_def", null);
            mm.setProperty("/tip_lista", null);                
            mm.setProperty("/lista_salvata", "NU");
            mm.setProperty("/afisare_mesaj", "");
            
            mm.setProperty("/cod_lista", "");
            mm.setProperty("/den_lista", "");
                        
            mm.setProperty("/addop", false);
        },

        clickButtonSalvare: function (oEvent) {
//            MessageToast.show("Hello World!");
            var mm = this.getView().getModel("valorilistemodel");
            $.ajax({
                method: "GET",
                url: "rest/restAdaugaValoriListaDef",
                dataType: "JSON",
                data: {tip_lista_def: mm.getProperty("/tip_lista_def"),
                       tip_lista: mm.getProperty("/tip_lista"), 
                       cod_lista: mm.getProperty("/cod_lista"),
                       den_lista: mm.getProperty("/den_lista"),
                       
                   },
                   
            }).done(function (data) {
                
                //mm.setProperty("/IDUtilizator", data.id);
                
                   
                
                mm.setProperty("/lista_salvata", "DA");
                mm.setProperty("/afisare_mesaj", "Lista a fost adaugata.");
              
              //cum afisez valoarea in campurile de mai sus imi trebuie un refresh pentru ele  
              //   reload - implica apelul functiei OnInit 
              //window.location.reload();
              //alert("Dupa Salvare: " + data.id + " - " + data.nr_tichet + " - " + data.id_prioritate);
              
              mm.setProperty("/addop", false);
            });
            
        },
        
           
   });

});
