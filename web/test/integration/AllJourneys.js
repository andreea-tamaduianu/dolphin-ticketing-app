sap.ui.define([
	"sap/ui/test/Opa5",
	"sap/ui/dolphin/test/integration/arrangements/Startup",
	"sap/ui/dolphin/test/integration/TodoListJourney",
	"sap/ui/dolphin/test/integration/SearchJourney",
	"sap/ui/dolphin/test/integration/FilterJourney"
], function(Opa5, Startup) {
	"use strict";

	Opa5.extendConfig({
		arrangements: new Startup(),
		pollingInterval: 1
	});

});
