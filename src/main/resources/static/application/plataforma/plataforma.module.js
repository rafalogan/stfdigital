/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 09.07.2015
 */ 
(function() {
	'use strict';
	
	angular.plataforma = angular.module('plataforma', ['ui.router', 'ct.ui.router.extras.sticky', 'ct.ui.router.extras.previous']);

	angular.plataforma.config(function($stateProvider) {
		$stateProvider.state('dashboard', {
			url : '/dashboard',
			templateUrl : 'application/plataforma/dashboard/dashboard.tpl.html',
			controller : 'DashboardController',
			resolve : {
				data : function(TarefaService) {
					return TarefaService.listar();
				}
			}
		}).state('erro', {
			url : '/erro',
			templateUrl : 'application/plataforma/support/error-handling/error.tpl.html'
		});
	});

})();

