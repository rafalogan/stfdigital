/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var PrincipalPage = require('./pages/principal.page');
	
	var PeticionamentoPage = require('./pages/peticionamento.page');
	
	var RegistroPage = require('./pages/registro.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var PreautuacaoPage = require('./pages/preautuacao.page');
	
	var principalPage;
	
	var peticaoId;
	
	describe('Autuação de Petições Digitais Originárias:', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});

		it('Deveria navegar para a página de envio de petições digitais', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso('peticionamento', 'novoItemIcon');
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao/);
		});

		it('Deveria enviar uma nova petição digital', function() {
			var peticionamentoPage = new PeticionamentoPage();
			
			peticionamentoPage.classificarClasse('AP');
			
			peticionamentoPage.partePoloAtivo('João da Silva');
		    
			peticionamentoPage.partePoloPassivo('Maria da Silva');
			
			peticionamentoPage.uploadPecas();
			
			peticionamentoPage.selecionarTipoPeca('Ato coator');
		    
			peticionamentoPage.registrar();

			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			principalPage.login('autuador');
			
		    expect(principalPage.tarefas().count()).toEqual(6);
		    
		    var pos;
		    var res;
		    
		    
		    //peticaoId = compilarId(principalPage.tarefas().get(0).getText());
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	console.log(pos);
		    	res = text.substr(pos+1, text.length);
		    });
		    
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo #' + res );
		    
/*			compilarId = function(texto){
				var pos = texto.search("#");
				var res = texto.substr(pos+1, texto.length);
				return res;
			};*/
		});
		

		it('Deveria atuar como válida a petição recebida', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/144\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    principalPage.login('distribuidor');
		    
		    expect(principalPage.tarefas().count()).toEqual(2);
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #144');
		    
		});

		it('Deveria distribuir a petição autuada', function() {
			
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/144\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionar('Min. Roberto Barroso');
			
			distribuicaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			principalPage.login('recebedor');
		}); 
		


	});
	

})();