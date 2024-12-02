sap.ui.define([
    "sap/ui/Device",
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel"
], function (Device, Controller, Filter, FilterOperator, JSONModel) {
    "use strict";
    var oStorage = jQuery.sap.storage(jQuery.sap.storage.Type.local);
    
    return Controller.extend("sap.ui.dolphin.controller.AdaugaTichet", {

        onInit: function () {

            this.aSearchFilters = [];
            this.aTabFilters = [];

            this.getView().setModel(new JSONModel({
                isMobile: Device.browser.mobile,
                filterText: undefined
            }), "view");
            
            
             var model = new JSONModel({
                
                "IDTichet": "",                
                "nr_tichet": "se va competa dupa salvare",
                "nume_alarma": "",
                "prioritate": "",
                "id_prioritate": "",
                "serviciu": "",
                "entitate_activa": "",
                "stare": "Tichet Deschis",
                "descriere": "",
                "componenta": "",
                "tichet_salvat":"NU",
                "afisare_mesaj":"",
                "addop":true,
                
                "stari":[],
                "componente":[],
                "servicii":[],
                "entitati":[],
                "prioritati":[]
                
            });

            this.getView().setModel(model, "tichetmodel");
            
            // restValoriLista                  

//          pstmt.setString(1, "PRIORITATI");
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "PRIORITATI"}
            }).done(function (data) {                
               model.setProperty("/prioritati", data.valoriLista);
            });
            //alert("setare prioritati");

//          pstmt.setString(2, "ENTITATI");
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "ENTITATI"}
            }).done(function (data) {                
               model.setProperty("/entitati", data.valoriLista);
            });
            //alert("setare entitati");
            
//          pstmt.setString(3, "SERVICII");
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "SERVICII"}
            }).done(function (data) {                
               model.setProperty("/servicii", data.valoriLista);
            });
            
//          pstmt.setString(4, "COMPONENTE");
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "COMPONENTE"}
            }).done(function (data) {                
               model.setProperty("/componente", data.valoriLista);
            });
          
//          pstmt.setString(5, "STARI");
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "STARI"}
            }).done(function (data) {                
               model.setProperty("/stari", data.valoriLista);
            });
            //alert("setare stari");
            
        },
        
        
        

        clickButtonSalvare: function (oEvent) {
//            MessageToast.show("Hello World!");
            var mm = this.getView().getModel("tichetmodel");
            $.ajax({
                method: "GET",
                url: "rest/restAdaugaTichet",
                dataType: "JSON",
                data: {nume_alarma: mm.getProperty("/nume_alarma"),
                       prioritate: mm.getProperty("/prioritate"), 
                       serviciu: mm.getProperty("/serviciu"),
                       entitate_activa: mm.getProperty("/entitate_activa"),
                       componenta: mm.getProperty("/componenta"),
                       descriere: mm.getProperty("/descriere"),
                   },
                   
            }).done(function (data) {
                
                mm.setProperty("/IDTichet", data.id);
                mm.setProperty("/nr_tichet", data.nr_tichet);
                mm.setProperty("/id_prioritate", data.id_prioritate);               
                mm.setProperty("/tichet_salvat", "DA");
                mm.setProperty("/afisare_mesaj", "Tichetul a fost adaugat. S-a generat numarul de tichet " + data.nr_tichet);
              
              //cum afisez valoarea in campurile de mai sus imi trebuie un refresh pentru ele  
              //   reload - implica apelul functiei OnInit 
              //window.location.reload();
              //alert("Dupa Salvare: " + data.id + " - " + data.nr_tichet + " - " + data.id_prioritate);
              
              mm.setProperty("/addop", false);
            });
            
        },
        
           
   });

});
