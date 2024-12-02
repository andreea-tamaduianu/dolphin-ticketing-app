sap.ui.define([
    "sap/ui/Device",
    "sap/ui/core/mvc/Controller",
    "sap/m/Menu",
    "sap/m/MenuItem",    
    "sap/ui/model/Filter",
    "sap/ui/model/Sorter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast",
    "sap/m/Button",
    "sap/m/Dialog",
    "sap/m/Label",
    "sap/m/MessageBox",
    "sap/m/Text",
    "sap/m/TextArea",
    "sap/ui/core/mvc/Controller",
    "sap/ui/layout/HorizontalLayout",
    "sap/ui/layout/VerticalLayout",
    "sap/m/library"
    
                
], function (Device, Controller, Filter, FilterOperator, JSONModel) {
    "use strict";
    
    var oStorage = jQuery.sap.storage(jQuery.sap.storage.Type.local);
//Sample: Table - Scroll To Index

    var SettingsDialogController = Controller.extend("sap.ui.dolphin.controller.TicheteInLucru", {

		onInit: function () {
			// Keeps reference to any of the created sap.m.ViewSettingsDialog-s in this sample

			// set explored app's demo model on this sample
			//var oModel = new JSONModel( sap.ui.require.toUrl("sap/ui/demo/mock/products.json") );                        
			//this.getView().setModel(oModel);
                        
                        var model = new sap.ui.model.json.JSONModel({
                                "ticheteInLucru": [],
                                "detaliiTichet": [],
                                "setulCurent":1,
                                "nrSeturi":0,
                                "nr_inregistrari":0,
                                "tichet_selectat":null,
                                "selPanel": "OBIECTE"
                         });

                        //this.getView().setModel(model, "ListaTichete");
                        //alert( "Altert 1" );
                        
                        this.getView().setModel(model);
                        
                        //sap.ui.getCore().setModel(new sap.ui.model.json.JSONModel(oData), "modelSource");
                        
                        //this.getView().setModel(oModel, "TicheteInLucruModel");
                        //alert( "Altert 2" );            
                        
                        
                        $.ajax({
                                method: "GET",
                                url: "rest/restConsultareTichete",                                
                                dataType: "JSON",
                                
                                data: {stare_tichet: "IN_LUCRU",
                                       filterScript: "", 
                                       orderScript: "",
                                       curentSet: "1",
                                       rowsNoPerSet: "20"
                                    }                 

                            }).done(function (data) {
                                //alert(" Nr total de inregistrari = " + data.rowsNo);
                                
                                var nr_inregistrari = data.rowsNo;
                                var nr_seturi = data.setsNo;
                                
                                //alert( model );
			        model.setProperty("/ticheteInLucru", data.listaTichete);
                                //"setulCurent":1,
                                //"nrSeturi":0,
                                model.setProperty("/nr_inregistrari", nr_inregistrari);
                                model.setProperty("/nrSeturi", nr_seturi);
                                
                                //alert( data.listaTichete );

                            });
                            //alert( "Altert 3" );            
                            
                            //this._mViewSettingsDialogs = {};

		},
                
                
                aduceInregistrari:function(set_navigare) {
                    var model = this.getView().getModel();
                    
                    $.ajax({
                                method: "GET",
                                url: "rest/restConsultareTichete",                                
                                dataType: "JSON",
                                
                                data: {stare_tichet: "IN_LUCRU",
                                       filterScript: "", 
                                       orderScript: "",
                                       curentSet: set_navigare,
                                       rowsNoPerSet: "20"
                                    }                 

                            }).done(function (data) {
                                //alert(" Nr total de inregistrari = " + data.rowsNo);
                                
                                var nr_inregistrari = data.rowsNo;
                                var nr_seturi = data.setsNo;
                                
                                //alert( model );
			        model.setProperty("/ticheteInLucru", data.listaTichete);
                                //"setulCurent":1,
                                //"nrSeturi":0,
                                model.setProperty("/nr_inregistrari", nr_inregistrari);
                                model.setProperty("/nrSeturi", nr_seturi);
                                
                                //alert( data.listaTichete );

                            });
                },
                
                
                seteazaStare: function (oEvent) {
                    
                  var obj = oEvent.getSource().getBindingContext().getObject();
                  //var model = this.getView().getModel("utilizatormodel");   
                  
                  var oDialog = new sap.m.Dialog({
                            title: 'Rezolvare Tichet',
                            type: 'Message',
                            content: [
                                    new sap.m.Label({ text: 'Confirmati Rezolvarea tichetului?', labelFor: 'confirmDialogTextarea' }),
                                    new sap.m.TextArea('confirmDialogTextarea', {
                                            width: '100%',
                                            placeholder: 'Add note (optional)'
                                    })
                            ],
                            beginButton: new sap.m.Button({
                                    type: sap.m.ButtonType.Emphasized,
                                    text: 'Confirmare',
                                    press: function () {
                                            var sText = sap.ui.getCore().byId('confirmDialogTextarea').getValue();
                                            //sap.m.MessageToast.show('Note is: ' + sText);
                                            //alert("--> Apel seteazaStare pentru id_tichet =  " + obj.id ) ;
                                            $.ajax({
                                                          method: "GET",
                                                          url: "rest/restSeteazaStareTichet",
                                                          dataType: "JSON",                                
                                                          data: {
                                                                 stare_tichet: "REZOLVAT",
                                                                 stare_tichet_ant: "IN_LUCRU",
                                                                 id_tichet: obj.id,
                                                                 nr_tichet: obj.nr_tichet,
                                                                 id_utiliz: oStorage.get("id_utiliz"),
                                                                 tip_oper:"TIPOPER1",
                                                                 overtime:0,
                                                                 descriere:sText
                                                              },                   

                                                      }).done(function (data) {
                                                          //alert(" Setare Stare = " + data.stare_modificata);
                                                          //alert(" mesaj = " + data.mesaj);

                                                          if( data.stare_modificata ) {
                                                             // Ar trebui sa intrebam daca doreste navigare in pagina cu TicheteInLucru  

                                                            sap.m.MessageBox.information("Tichetul cu numarul "+ obj.nr_tichet + " a fost rezolvat! ", {
                                                                    actions: [sap.m.MessageBox.Action.CLOSE],
                                                                    onClose: function (sAction) {
                                                                        window.location.reload();
                                                                    }
                                                            });
                                                            
                                                            
                                                          }   
                                                          else {
                                                            sap.m.MessageBox.error( data.mesaj );
                                                          }

                                                      });                  
                                            
                                            oDialog.close();
                                    }
                            }),
                            endButton: new sap.m.Button({
                                    text: 'Renunare',
                                    press: function () {
                                            oDialog.close();
                                    }
                            }),
                            afterClose: function () {
                                    oDialog.destroy();
                            }
                    });

                    oDialog.open();
                  
		},
                
               
                
                onPress: function (oEvent) {
			//var oItem = oEvent.getSource();                        
                        var obj = oEvent.getSource().getBindingContext().getObject();
                        var model = this.getView().getModel();
                        //alert("--> on press id_tichet =  " + obj.id ) ;                        
                        
                        model.setProperty("/tichet_selectat", obj.nr_tichet);
                        model.setProperty("/id_tichet_selectat", obj.id);
                        
                        
                          $.ajax({
                                method: "GET",
                                url: "rest/restDetaliiTichet",                                
                                dataType: "JSON",
                                
                                data: {id_tichet: obj.id,
                                       filterScript: "", 
                                       orderScript: "",
                                       curentSet: "1",
                                       rowsNoPerSet: "20"
                                    }                 

                            }).done(function (data) {
                                
			        model.setProperty("/detaliiTichet", data.detaliiTichet);                                
                            }); 
                
//                        $.ajax({
//                                method: "GET",
//                                url: "rest/restDetaliiTichet",                                
//                                dataType: "JSON",
//                                
//                                data: {id_tichet: obj.id,
//                                       filterScript: "", 
//                                       orderScript: "",
//                                       curentSet: "1",
//                                       rowsNoPerSet: "20"
//                                    }                 
//
//                            }).done(function (data) {
//                                
//			        model.setProperty("/detaliiTichet", data.detaliiTichet);                                
//                            });
                                                    
                            //alert("--> end nr_tichet =  " + obj.nr_tichet ) ;
                        
		},
                
                
                adaugaDetaliu: function () {
                    
                  //var obj = oEvent.getSource().getBindingContext().getObject();
                  //var model = this.getView().getModel();   
                  var model = this.getView().getModel();
                  
                  var oDialog = new sap.m.Dialog({
                            title: 'Adaugare detalii',
                            type: 'Message',
                            content: [
                                    new sap.m.Input('tip_detaliuInput', {
                                            width: '30%',
                                            placeholder: 'Adaugare detalii'
                                    }),
                                    
                                    new sap.m.Label({ text: 'Adaugaru detalii pentru modul de rezolvare al tichetului', labelFor: 'descriereDetaliuTextarea' }),                                    
                                    new sap.m.TextArea('descriereDetaliuTextarea', {
                                            width: '100%',
                                            placeholder: 'Adaugare detalii'
                                    })
                            ],
                            beginButton: new sap.m.Button({
                                    type: sap.m.ButtonType.Emphasized,
                                    text: 'Salvare',
                                    press: function () {
                                        
                                            var descriere_detaliu = sap.ui.getCore().byId('descriereDetaliuTextarea').getValue();
                                            var tip_detaliu = sap.ui.getCore().byId('tip_detaliuInput').getValue();
                                            
                                            
                                            
                                            $.ajax({
                                                          method: "GET",
                                                          url: "rest/restAdaugaDetaliu",
                                                          dataType: "JSON",                                
                                                          data: {
                                                                 id_tichet: model.setProperty("/id_tichet_selectat"),
                                                                 nr_tichet: model.setProperty("/tichet_selectat"),                                                                 
                                                                 id_utiliz: oStorage.get("id_utiliz"),
                                                                 tip_detaliu:tip_detaliu,
                                                                 descriere:descriere_detaliu
                                                              },                   

                                                      }).done(function (dataAdd) {
                                                          
                                                          if( dataAdd.stare_modificata ) {
                                                             // Ar trebui sa intrebam daca doreste navigare in pagina cu TicheteInLucru  
                                                              $.ajax({
                                                                    method: "GET",
                                                                    url: "rest/restDetaliiTichet",                                
                                                                    dataType: "JSON",

                                                                    data: {id_tichet: obj.id,
                                                                           filterScript: "", 
                                                                           orderScript: "",
                                                                           curentSet: "1",
                                                                           rowsNoPerSet: "20"
                                                                        }                 

                                                                }).done(function (data) {

                                                                    model.setProperty("/detaliiTichet", data.detaliiTichet);                                
                                                                }); 
                                                          }   
                                                          else {
                                                            sap.m.MessageBox.error( dataAdd.mesaj );
                                                          }

                                                      });                  
                                            
                                            oDialog.close();
                                    }
                            }),
                            endButton: new sap.m.Button({
                                    text: 'Renunare',
                                    press: function () {
                                            oDialog.close();
                                    }
                            }),
                            afterClose: function () {
                                    oDialog.destroy();
                            }
                    });

                    oDialog.open();
                  
		},
                
                

		onExit: function () {
			var oDialogKey,
				oDialogValue;

			for (oDialogKey in this._mViewSettingsDialogs) {
				oDialogValue = this._mViewSettingsDialogs[oDialogKey];

				if (oDialogValue) {
					oDialogValue.destroy();
				}
			}
		},

		createViewSettingsDialog: function (sDialogFragmentName) {
			var oDialog = this._mViewSettingsDialogs[sDialogFragmentName];

			if (!oDialog) {
				oDialog = sap.ui.xmlfragment(sDialogFragmentName, this);
				this._mViewSettingsDialogs[sDialogFragmentName] = oDialog;

				if (Device.system.desktop) {
					oDialog.addStyleClass("sapUiSizeCompact");
				}
			}
			return oDialog;
		},
                
                refreshButtonPressed: function () {
                    
                },

		handleSortButtonPressed: function () {
		  this.createViewSettingsDialog("sap.m.sample.TableViewSettingsDialog.SortDialog").open();
		},

		handleFilterButtonPressed: function () {
		  this.createViewSettingsDialog("sap.m.sample.TableViewSettingsDialog.FilterDialog").open();
		},

		handleGroupButtonPressed: function () {
		  this.createViewSettingsDialog("sap.m.sample.TableViewSettingsDialog.GroupDialog").open();
		},

		handleSortDialogConfirm: function (oEvent) {
			var oTable = this.byId("idTicheteInLucru"),
				mParams = oEvent.getParameters(),
				oBinding = oTable.getBinding("items"),
				sPath,
				bDescending,
				aSorters = [];

			sPath = mParams.sortItem.getKey();
			bDescending = mParams.sortDescending;
			aSorters.push(new Sorter(sPath, bDescending));

			// apply the selected sort and group settings
			oBinding.sort(aSorters);
		},

		handleFilterDialogConfirm: function (oEvent) {
			var oTable = this.byId("idTicheteInLucru"),
				mParams = oEvent.getParameters(),
				oBinding = oTable.getBinding("items"),
				aFilters = [];

			mParams.filterItems.forEach(function(oItem) {
				var aSplit = oItem.getKey().split("___"),
					sPath = aSplit[0],
					sOperator = aSplit[1],
					sValue1 = aSplit[2],
					sValue2 = aSplit[3],
					oFilter = new Filter(sPath, sOperator, sValue1, sValue2);
				aFilters.push(oFilter);
			});

			// apply filter settings
			oBinding.filter(aFilters);

			// update filter bar
			this.byId("vsdFilterBar").setVisible(aFilters.length > 0);
			this.byId("vsdFilterLabel").setText(mParams.filterString);
		},

		handleGroupDialogConfirm: function (oEvent) {
			var oTable = this.byId("idTicheteInLucru"),
				mParams = oEvent.getParameters(),
				oBinding = oTable.getBinding("items"),
				sPath,
				bDescending,
				vGroup,
				aGroups = [];

			if (mParams.groupItem) {
				sPath = mParams.groupItem.getKey();
				bDescending = mParams.groupDescending;
				vGroup = this.mGroupFunctions[sPath];
				aGroups.push(new Sorter(sPath, bDescending, vGroup));
				// apply the selected group settings
				oBinding.sort(aGroups);
			}
		},

		onToggleContextMenu: function (oEvent) {
			if (oEvent.getParameter("pressed")) {
				this.byId("idTicheteInLucru").setContextMenu(new Menu({
					items: [
						new MenuItem({text: "{Name}"}),
						new MenuItem({text: "{ProductId}"})
					]
				}));
			} else {
				this.byId("idTicheteInLucru").destroyContextMenu();
			}
		}
	});

	return SettingsDialogController;
});