sap.ui.define(["sap/ui/core/UIComponent", "sap/ui/core/ComponentSupport"], function(UIComponent) {
	"use strict";
        
	return UIComponent.extend("ro.ase.dolphin.Component", {
		metadata: {
			manifest: "json"
		},
                		init : function () {
			UIComponent.prototype.init.apply(this, arguments);

			// Parse the current url and display the targets of the route that matches the hash
			this.getRouter().initialize();
		}
	});
});
