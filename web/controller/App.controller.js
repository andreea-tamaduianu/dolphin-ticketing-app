sap.ui.define([
    
    "sap/ui/Device",
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageBox"
    
], function (Device, Controller, Filter, FilterOperator, JSONModel) {
    "use strict";
    var oStorage = jQuery.sap.storage(jQuery.sap.storage.Type.local);
    
    return sap.ui.core.mvc.Controller.extend("sap.ui.dolphin.controller.App", {

        onInit: function () {

            this.aSearchFilters = [];
            this.aTabFilters = [];

            this.getView().setModel(new sap.ui.model.json.JSONModel({
                isMobile: Device.browser.mobile,
                filterText: undefined
            }), "view");
            
            this._initMainModel();

        },
        _initMainModel: function () {

//            oStorage = jQuery.sap.storage(jQuery.sap.storage.Type.local);
////Get data from Storage
//oStorage.get("myLocalData");
////Set data into Storage
//oStorage.put("myLocalData", oData);
////Clear Storage
//oStorage.clear();

            var model = new JSONModel({
                "produseDashList": [],
                "pageTitle": " pageTitle ",
                
                "Username": "ADMIN",
                "Password": "1234",
                
                "IDUser": oStorage.get("id_utiliz"),
                "selPanel": "OBIECTE",
                "utilizator_utentificat": "NU",
                "rol_utilizator": "",
                "nume_utilizator": "",
                "admin": 0,
                
                
            });

            this.getView().setModel(model, "mainmodel");
//            alert("Initializat model" + oStorage.get("id_utiliz"));
//            this.setModel(model);

        },
        
        InfoUser: function (oEvent) {
            var mm = this.getView().getModel("mainmodel");
            sap.m.MessageBox.information(" Utilizator Conectat: " + 
                    //mm.getProperty("mainmodel>/nume_utilizator") + "\n" +  
                    oStorage.get("nume_utilizator") + "\n" +  
                    " Rol utilizator: " + 
                    //mm.getProperty("mainmodel>/rol_utilizator") + "\n" +  
                    oStorage.get("rol_utiliz") + "\n" +  
                    " ID: " + mm.getProperty("/IDUser") );
            
        },
        
         onItemSelect: function (oEvent) {
            var item = oEvent.getParameter('item');
            var sKey = item.getKey();
//            var oRouter = this.getRouter();
            var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
            
          //  var oToolPage = this.byId("app");
            if (oRouter.getTargets().getTarget(sKey)!=null) {
                
                if(sKey === "utilizatori") {
                    if( oStorage.get("id_utiliz") === 1 ) {
                      this.getView().getModel("mainmodel").setProperty("/pageTitle", "Adauga Utilizator");
                      oRouter.navTo(sKey); 
                    }
                    else {
                        sap.m.MessageBox.error('Nu aveti drepturi de acces!');
                    }
                    
                }
                else if(sKey === "valoriListe") {
                    if( oStorage.get("id_utiliz") === 1 ) {
                      this.getView().getModel("mainmodel").setProperty("/pageTitle", "Valori LIste");
                      oRouter.navTo(sKey); 
                    }
                    else {
                        sap.m.MessageBox.error('Nu aveti drepturi de acces!');
                    }
                }
                else {
                    if(sKey === "tichete") {
                        this.getView().getModel("mainmodel").setProperty("/pageTitle", "Adaugare Tichete");
                    }
                    else if(sKey === "ticheteDeschise") {
                        this.getView().getModel("mainmodel").setProperty("/pageTitle", "Tichete Deschise");
                    }
                    else if(sKey === "ticheteInLucru") {
                        this.getView().getModel("mainmodel").setProperty("/pageTitle", "Tichete in Lucru");
                    }
                    else if(sKey === "ticheteRezolvate") {
                        this.getView().getModel("mainmodel").setProperty("/pageTitle", "Tichete Rezolvate");
                    }
                    else if(sKey === "ticheteInchise") {
                        this.getView().getModel("mainmodel").setProperty("/pageTitle", "Tichete Inchise");
                    }
                
                    else {
                        this.getView().getModel("mainmodel").setProperty("/pageTitle", "Nedefinit");
                    }
                    oRouter.navTo(sKey);
                }
                
                
                //this.getView().getModel("mainmodel").setProperty("/pageTitle", " - " + this.getView().getModel("i18n").getResourceBundle().getText(sKey));
//                oToolPage.setSideExpanded(false);
                
                //oRouter.navTo(sKey);
                
            } else {
                if(sKey === "logOff") {
                    //alert(sKey + " Iesire!");
                     
                    var mm = this.getView().getModel("mainmodel");
                    mm.setProperty("/IDUser", null);
                    oStorage.put("id_utiliz", null);
                    oStorage.put("rol_utiliz", null);
                    oStorage.put("nume_utilizator", null);
                    oStorage.put("admin", null);
                    oRouter.navTo("mainPage");
                     
                    //oRouter.navTo("");
                    window.location.reload();
                     
                }
                else {
                  sap.m.MessageBox.warning(sKey + " section is not implemented yet!");
                }
            }
        },
        
        
//        	onItemSelect: function (oEvent) {
//			var oItem = oEvent.getParameter("item");
//			this.byId("pageContainer").to(this.getView().createId(oItem.getKey()));
//		},

        clickButtonLogare: function (oEvent) {
            
//            MessageToast.show("Hello World!");
            var mm = this.getView().getModel("mainmodel");
            
            $.ajax({
                method: "GET",
                url: "rest/restLogare",
                dataType: "JSON",
                data: {name: mm.getProperty("/Username"), parola: mm.getProperty("/Password")},
            }).done(function (data) {
                
                
                //alert( data.utilizator_autentificat );
                
                if( data.utilizator_autentificat === "DA" ) {
                   mm.setProperty("/IDUser", data.id);
                   mm.setProperty("/utilizator_utentificat", data.utilizator_autentificat);
                   
                   mm.setProperty("/rol_utilizator", data.rol_utilizator);
                   mm.setProperty("mainmodel>/rol_utilizator", data.rol_utilizator);
                   
                   mm.setProperty("/nume_utilizator", data.nume_utilizator);
                   mm.setProperty("mainmodel>/nume_utilizator", data.nume_utilizator);
                   
                   oStorage.put("id_utiliz", data.id);
                   oStorage.put("rol_utiliz", data.rol_utilizator);
                   oStorage.put("nume_utilizator", data.nume_utilizator);
                   
                   //alert('data.rol_utilizator: ' + data.rol_utilizator);
                   if(data.rol_utilizator === "ADMIN") {
                     mm.setProperty("/admin", 1 );
                     oStorage.put("admin", 1);
                   }
                   else {
                     mm.setProperty("/admin", null);
                     oStorage.put("admin", null);
                   }
                   //alert('mm.getProperty: ' + mm.getProperty("/admin") );
                   
                   
//                   alert (" data.rol_utilizator = "+data.rol_utilizator + "\n  " +  
//                          " rol_utilizator = "+mm.getProperty("/rol_utilizator") + " \n "  +
//                          " admin = "+ mm.getProperty("/admin") );
                }
                else {
                   mm.setProperty("/IDUser", null);
                   
                   oStorage.put("id_utiliz", null); 
                   oStorage.put("admin", null);
                   oStorage.put("rol_utiliz", null);
                   oStorage.put("nume_utilizator", null);
                   
                   
                   sap.m.MessageBox.error( data.err_autentificare );
                }
              
              window.location.reload();
                //alert(data.id);
            });
            
        },
        /**
         * Adds a new todo item to the bottom of the list.
         */
        addTodo: function () {
            var oModel = this.getView().getModel();
            var aTodos = oModel.getProperty("/todos").map(function (oTodo) {
                return Object.assign({}, oTodo);
            });

            aTodos.push({
                title: oModel.getProperty("/newTodo"),
                completed: false
            });

            oModel.setProperty("/todos", aTodos);
            oModel.setProperty("/newTodo", "");
        },

        /**
         * Removes all completed items from the todo list.
         */
        clearCompleted: function () {
            var oModel = this.getView().getModel();
            var aTodos = oModel.getProperty("/todos").map(function (oTodo) {
                return Object.assign({}, oTodo);
            });

            var i = aTodos.length;
            while (i--) {
                var oTodo = aTodos[i];
                if (oTodo.completed) {
                    aTodos.splice(i, 1);
                }
            }

            oModel.setProperty("/todos", aTodos);
        },

        /**
         * Updates the number of items not yet completed
         */
        updateItemsLeftCount: function () {
            var oModel = this.getView().getModel();
            var aTodos = oModel.getProperty("/todos") || [];

            var iItemsLeft = aTodos.filter(function (oTodo) {
                return oTodo.completed !== true;
            }).length;

            oModel.setProperty("/itemsLeftCount", iItemsLeft);
        },

        /**
         * Trigger search for specific items. The removal of items is disable as long as the search is used.
         * @param {sap.ui.base.Event} oEvent Input changed event
         */
        onSearch: function (oEvent) {
            var oModel = this.getView().getModel();

            // First reset current filters
            this.aSearchFilters = [];

            // add filter for search
            this.sSearchQuery = oEvent.getSource().getValue();
            if (this.sSearchQuery && this.sSearchQuery.length > 0) {
                oModel.setProperty("/itemsRemovable", false);
                var filter = new Filter("title", FilterOperator.Contains, this.sSearchQuery);
                this.aSearchFilters.push(filter);
            } else {
                oModel.setProperty("/itemsRemovable", true);
            }

            this._applyListFilters();
        },

        onFilter: function (oEvent) {
            // First reset current filters
            this.aTabFilters = [];

            // add filter for search
            this.sFilterKey = oEvent.getParameter("item").getKey();

            // eslint-disable-line default-case
            switch (this.sFilterKey) {
                case "active":
                    this.aTabFilters.push(new Filter("completed", FilterOperator.EQ, false));
                    break;
                case "completed":
                    this.aTabFilters.push(new Filter("completed", FilterOperator.EQ, true));
                    break;
                case "all":
                default:
                // Don't use any filter
            }

            this._applyListFilters();
        },

        _applyListFilters: function () {
            var oList = this.byId("todoList");
            var oBinding = oList.getBinding("items");

            oBinding.filter(this.aSearchFilters.concat(this.aTabFilters), "todos");

            var sI18nKey;
            if (this.sFilterKey && this.sFilterKey !== "all") {
                if (this.sFilterKey === "active") {
                    sI18nKey = "ACTIVE_ITEMS";
                } else {
                    // completed items: sFilterKey = "completed"
                    sI18nKey = "COMPLETED_ITEMS";
                }
                if (this.sSearchQuery) {
                    sI18nKey += "_CONTAINING";
                }
            } else if (this.sSearchQuery) {
                sI18nKey = "ITEMS_CONTAINING";
            }

            var sFilterText;
            if (sI18nKey) {
                var oResourceBundle = this.getView().getModel("i18n").getResourceBundle();
                sFilterText = oResourceBundle.getText(sI18nKey, [this.sSearchQuery]);
            }

            this.getView().getModel("view").setProperty("/filterText", sFilterText);
        },

    });

});
