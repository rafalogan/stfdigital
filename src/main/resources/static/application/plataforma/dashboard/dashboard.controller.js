// TODO Remover essa controller quando começar a utilizar o mecanismo de dashlets
/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('DashboardController', function (data, $scope, $http, properties, PeticaoService) {
		
		$scope.tarefas = data.data;
		
	});
	
})();

