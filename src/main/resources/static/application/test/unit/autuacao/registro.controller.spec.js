/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.00
 * @since 01.09.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Registro Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $window, $log, properties, TipoRecebimentoService) {
			$window.sessionStorage.papel = JSON.stringify('recebedor');
			
			$httpBackend.expectGET(properties.apiUrl + '/tiporecebimentos').respond([{sigla : '2', nome: 'Sedex'}, {sigla : '3', nome: 'Malote'}]);
			$httpBackend.expectGET(properties.apiUrl + '/tarefas').respond([{}]);
			
			stateParams = {idTarefa: 4};
			
			
			controller = $controller('RegistroPeticaoFisicaController', {
				$stateParams : stateParams,
				$log: $log
			});
			$httpBackend.flush();
		}));
		it ('Deveria carregar a lista de forma de envio do controlador', function(){
			expect(controller.tipoRecebimentos.length).toEqual(2);
		})
	});
})();