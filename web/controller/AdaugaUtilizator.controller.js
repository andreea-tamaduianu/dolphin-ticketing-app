sap.ui.define([
    "sap/ui/Device",
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel"
], function (Device, Controller, Filter, FilterOperator, JSONModel) {
    "use strict";
    var oStorage = jQuery.sap.storage(jQuery.sap.storage.Type.local);
    
    return Controller.extend("sap.ui.dolphin.controller.AdaugaUtilizator", {

        onInit: function () {

            this.aSearchFilters = [];
            this.aTabFilters = [];

            this.getView().setModel(new JSONModel({
                isMobile: Device.browser.mobile,
                filterText: undefined
            }), "view");
            
            
             var model = new sap.ui.model.json.JSONModel({
                
                "IDUtilizator": "",                
                "id_utiliz":"se va completa dupa salvare",
                "nume_utiliz": "",
                "prenume_utiliz": "",
                "cod_utiliz": "",
                "parola": "",
                "echipa": "",
                "rol":"",
                "stare": "",
                "user_salvat":"NU",
                "afisare_mesaj":"",
                "addop":false,
                "email":false,
                
                "listaUtilizatori":[],
                "nr_inregistrari":0,
                
                "echipe":[],
                "roluri":[],
                "stari":[]
                
            });

            this.getView().setModel(model, "utilizatormodel");
           
                            
            $.ajax({
                method: "GET",
                url: "rest/restConsultareUtilizatori",
                dataType: "JSON",
                data: {filterScript: "", 
                       orderScript: "",
                       curentSet: "1",
                       rowsNoPerSet: "20"
                       }
            }).done(function (data) {                
               
               model.setProperty("/listaUtilizatori", data.listaUtilizatori)
               model.setProperty("/nr_inregistrari", data.rowsNo)
               
            });
            
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "ECHIPE"}
            }).done(function (data) {                
               model.setProperty("/echipe", data.valoriLista);
            });
            
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "ROLURI"}
            }).done(function (data) {
               model.setProperty("/roluri", data.valoriLista);               
            });
            
            $.ajax({
                method: "GET",
                url: "rest/restValoriLista",
                dataType: "JSON",
                data: {tip_lista: "STARI_U"}
            }).done(function (data) {
                model.setProperty("/stari", data.valoriLista);               
            });

        },
        
        
        adaugaUtilizator: function (oEvent) {

            var mm = this.getView().getModel("utilizatormodel");                
            mm.setProperty("/IDUtilizator", null);
            mm.setProperty("/id_utiliz", null);                
            mm.setProperty("/utilizator_salvat", "NU");
            mm.setProperty("/afisare_mesaj", "");
            
            mm.setProperty("/stare", "ACTIV");
            mm.setProperty("/nume_utiliz", "");
            mm.setProperty("/prenume_utiliz", "");
            mm.setProperty("/cod_utiliz", "");
            mm.setProperty("/parola", "");
            mm.setProperty("/echipa", "");
            mm.setProperty("/rol", "");
            mm.setProperty("/email", "");
            
            mm.setProperty("/addop", true);
            
        },
        
        onPress: function (oEvent) {
           //var oItem = oEvent.getSource();
                        
            var obj = oEvent.getSource().getBindingContext("utilizatormodel").getObject();
            //var obj_cel = oEvent.getParameter("cellControl").getBindingContext().getObject()

            //alert("--> obj = " + obj ) ;
            //alert("--> id_utiliz =  " + obj.id_utiliz + " - "  +obj.stare + " - "  + obj.echipa  + " - "  + obj.rol_utiliz ) ;
            //alert("--> nr_tichet =  " + obj.nr_tichet ) ;
          
            var mm = this.getView().getModel("utilizatormodel");                
            mm.setProperty("/IDUtilizator", obj.id_utiliz );
            mm.setProperty("/id_utiliz", obj.id_utiliz );                
            
            mm.setProperty("/utilizator_salvat", "DA");
            mm.setProperty("/afisare_mesaj", "");
            
            mm.setProperty("/nume_utiliz", obj.nume_utiliz);
            mm.setProperty("/prenume_utiliz", obj.prenume_utiliz);
            mm.setProperty("/cod_utiliz", obj.cod_utiliz);
            mm.setProperty("/parola", "");
            
            mm.setProperty("/stare", obj.stare);
            mm.setProperty("/echipa", obj.echipa);
            mm.setProperty("/rol", obj.rol_utiliz);
            
            mm.setProperty("/email", obj.email);
                        
	},
                
        
        clickButtonAnulare: function (oEvent) {
            var mm = this.getView().getModel("utilizatormodel");                
            mm.setProperty("/IDUtilizator", null);
            mm.setProperty("/id_utiliz", null);                
            mm.setProperty("/utilizator_salvat", "NU");
            mm.setProperty("/afisare_mesaj", "");
            
            mm.setProperty("/stare", "");
            mm.setProperty("/nume_utiliz", "");
            mm.setProperty("/prenume_utiliz", "");
            mm.setProperty("/cod_utiliz", "");
            mm.setProperty("/parola", "");
            mm.setProperty("/echipa", "");
            mm.setProperty("/rol", "");
            mm.setProperty("/email", "");
            
            mm.setProperty("/addop", false);
        },

        clickButtonSalvare: function (oEvent) {
//            MessageToast.show("Hello World!");
            var mm = this.getView().getModel("utilizatormodel");
            $.ajax({
                method: "GET",
                url: "rest/restAdaugaUtilizator",
                dataType: "JSON",
                data: {nume_utiliz: mm.getProperty("/nume_utiliz"),
                       prenume_utiliz: mm.getProperty("/prenume_utiliz"), 
                       cod_utiliz: mm.getProperty("/cod_utiliz"),
                       parola: mm.getProperty("/parola"),
                       echipa: mm.getProperty("/echipa"),
                       rol: mm.getProperty("/rol"),
                       stare: mm.getProperty("/stare"),
                       email: mm.getProperty("/email"),

                   },
                   
            }).done(function (data) {
                
                mm.setProperty("/IDUtilizator", data.id);
                mm.setProperty("/id_utiliz", data.id_utiliz);
               // mm.setProperty("/id_prioritate", data.id_prioritate);     
                
                mm.setProperty("/utilizator_salvat", "DA");
                mm.setProperty("/afisare_mesaj", "Utilizatorul a fost adaugat. S-a generat numarul de utilizator " + data.id_utiliz);
              
              //cum afisez valoarea in campurile de mai sus imi trebuie un refresh pentru ele  
              //   reload - implica apelul functiei OnInit 
              //window.location.reload();
              //alert("Dupa Salvare: " + data.id + " - " + data.nr_tichet + " - " + data.id_prioritate);
              
              mm.setProperty("/addop", false);
            });
            
        },
        
           
   });

});
