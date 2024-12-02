sap.ui.define([
    "sap/ui/Device",
    "sap/ui/core/mvc/Controller",
    "sap/m/Menu",
    "sap/m/MenuItem",    
    "sap/ui/model/Filter",
    "sap/ui/model/Sorter",
    "sap/m/MessageBox",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast"
                
], function (Device, Controller, Filter, FilterOperator, JSONModel) {
    "use strict";
    
    var oStorage = jQuery.sap.storage(jQuery.sap.storage.Type.local);
//Sample: Table - Scroll To Index

    var SettingsDialogController = Controller.extend("sap.ui.dolphin.controller.TicheteInchise", {

		onInit: function () {
			// Keeps reference to any of the created sap.m.ViewSettingsDialog-s in this sample

			// set explored app's demo model on this sample
			//var oModel = new JSONModel( sap.ui.require.toUrl("sap/ui/demo/mock/products.json") );                        
			//this.getView().setModel(oModel);
                        
                        var model = new sap.ui.model.json.JSONModel({
                                "ticheteInchise": [],
                                "selPanel": "OBIECTE"
                         });

                        //this.getView().setModel(model, "ListaTichete");
                        //alert( "Altert 1" );
                        
                        this.getView().setModel(model);
                        
                        //sap.ui.getCore().setModel(new sap.ui.model.json.JSONModel(oData), "modelSource");
                        
                        //this.getView().setModel(oModel, "TicheteInchiseModel");
                        //alert( "Altert 2" );            
                        
                        
                        $.ajax({
                                method: "GET",
                                url: "rest/restConsultareTichete",                                
                                dataType: "JSON",
                                
                                data: {stare_tichet: "INCHIS",
                                       filterScript: "", 
                                       orderScript: "",
                                       curentSet: "1",
                                       rowsNoPerSet: "20"
                                    },                   

                            }).done(function (data) {
                                //alert(" Nr total de inregistrari = " + data.rowsNo);
                                
                                var nr_inregistrari = data.rowsNo;
                                
                                //alert( model );
			        model.setProperty("/ticheteInchise", data.listaTichete)
                                model.setProperty("/nr_inregistrari", nr_inregistrari)
                                
                                //alert( data.listaTichete );

                            });
                            //alert( "Altert 3" );            
                            
                            //this._mViewSettingsDialogs = {};

		},
                
                seteazaStare: function (oEvent) {
                    
                  var obj = oEvent.getSource().getBindingContext().getObject();
                  //var model = this.getView().getModel("utilizatormodel");   
                  
                  
                  //alert("--> Apel seteazaStare pentru id_tichet =  " + obj.id ) ;
                  $.ajax({
                                method: "GET",
                                url: "rest/restSeteazaStareTichet",
                                dataType: "JSON",                                
                                data: {
                                       stare_tichet: "IN_LUCRU",
                                       stare_tichet_ant: "DESCHIS",
                                       id_tichet: obj.id,
                                       nr_tichet: obj.nr_tichet,
                                       id_utiliz: oStorage.get("id_utiliz"),
                                       tip_oper:"TIPOPER1",
                                       overtime:0,
                                       descriere:""
                                    },                   
                                    
                            }).done(function (data) {
                                //alert(" Setare Stare = " + data.stare_modificata);
                                //alert(" mesaj = " + data.mesaj);
                                
                                if( data.stare_modificata ) {
                                   // Ar trebui sa intrebam daca doreste navigare in pagina cu TicheteInLucru  
                                  sap.m.MessageBox.success("Tichetul cu numarul "+ obj.nr_tichet + " a fost lansat in executie! ");                                  
                                }   
                                else {
                                  sap.m.MessageBox.error( data.mesaj );
                                }
                                
                                window.location.reload();   
                                
                            });
                  
                  
		},
                
                onPress: function (oEvent) {
			//var oItem = oEvent.getSource();
                        
                        var obj = oEvent.getSource().getBindingContext().getObject();
                        //var obj_cel = oEvent.getParameter("cellControl").getBindingContext().getObject()
                        
                        //alert("--> obj = " + obj ) ;
                        //alert("--> id_tichet =  " + obj.id ) ;
                        //alert("--> nr_tichet =  " + obj.nr_tichet ) ;
                       
//			if (oItem.getNavigated()) {
//				oItem.setNavigated(false);
//                                alert("--> oItem.setNavigated(false): " + oItem ) ;
//			} else {
//				oItem.setNavigated(true);
//                                alert("--> oItem.setNavigated(true): " + oItem ) ;
//			}
                        
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
			var oTable = this.byId("idTicheteInchise"),
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
			var oTable = this.byId("idTicheteInchise"),
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
			var oTable = this.byId("idTicheteInchise"),
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
				this.byId("idTicheteInchise").setContextMenu(new Menu({
					items: [
						new MenuItem({text: "{Name}"}),
						new MenuItem({text: "{ProductId}"})
					]
				}));
			} else {
				this.byId("idTicheteInchise").destroyContextMenu();
			}
		}
	});

	return SettingsDialogController;
});