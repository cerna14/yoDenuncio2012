Ext.define('MyApp.controller.ControladorMap', {
	extend: 'Ext.app.Controller',
	views: ['MyPanel'],
	models: ['ModelMap'],
	stores: ['MyStore'],
	refs: [{
		ref: 'mapa',
		selector: '#mapa'
	}],
	init: function() {
		var me = this;
		console.log('Initialized Users! This happens before the Application launch function is called');
		console.log(Ext.query('#refrescarMapa'));
		this.control({
			'#refrescarMapa': {
				click: function() {
					console.log("refrescar MAPA ");
					//console.log(me.getMapa());

					var store = Ext.getStore('MyStore');
					
					store.each(function(record) {
						console.log(record.get("lat"));
						console.log(record.get("lng"));
						console.log(record.get("descripcion"));
						console.log(record.get("nombreArchivo"));
						console.log(record.get("fecha"));
						console.log(record.get("direccion"));
						this.getMapa().addMarker({
							lat: record.get("lat"),
							lng: record.get("lng"),
							title: 'PROBLEMA\n Haga click para ver',
							listeners: {
								mouseup: function(e) {
									window.win.close();
								},
								mousedown: function(e) {
									window.win = Ext.create('MyApp.view.MyWindowImage', {
										title: record.get("fecha"),
										height: 216,
										width: 249,
										imagen: 'images/' + record.get("nombreArchivo"),
										descripcion: record.get("descripcion"),
										direccion:record.get("direccion")
									});
									win.setVisible(true);
									win.show();
								},
								dblclick: function(e) {
									window.win = Ext.create('MyApp.view.MyWindowImage', {
										title: record.get("fecha"),
										direccion:record.get("direccion"),
										height: 466,
										width: 449,
										imagen: 'images/' + record.get("nombreArchivo"),
										descripcion: record.get("descripcion"),
										modal: true,
										maximizable: true,
										closable: true
									});
									win.setVisible(true);
									win.show();
								},
							}
						});
					}, this);




				}
			}
		});
		/*var User = this.getUserModel(),
		 allUsers = this.getAllUsersStore();
		 
		 var ed = new User({name: 'Ed'});
		 allUsers.add(ed);*/
	}
});