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
	
	describe('Autuação de Originários Distribuídos', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});

		it('Deveria navegar para a página de envio de petições digitais', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcessoDigital();
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao/);
		});

		it('Deveria enviar uma nova petição digital', function() {
			var peticionamentoPage = new PeticionamentoPage();
			
			peticionamentoPage.classificar('AP');
			
			peticionamentoPage.partePoloAtivo('João da Silva');
		    
			peticionamentoPage.partePoloPassivo('Maria da Silva');
		    
			peticionamentoPage.registrar();

			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			//Seta o papel autuador
			principalPage.login('autuador');
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo #2');
		});
		

		it('Deveria atuar como válida a petição recebida', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/2\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    principalPage.login('distribuidor');
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #2');
		    
		});

		it('Deveria distribuir a petição autuada', function() {
			
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/2\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionar('Min. Roberto Barroso');
			
			distribuicaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			//Seta o papel recebedor
			principalPage.login('recebedor');
		}); 
		
		it('Deveria navegar para a página de envio de petições físicas', function() {
			
			principalPage = new PrincipalPage();
			
			// Iniciando o Processo de Remessa Físca
			principalPage.iniciarProcessoFisico();
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/fisica/);
		});
		
		it('Deveria registrar uma petição física', function(){
			var registroPage = new RegistroPage();
			
			registroPage.qtdVolumes(2);
			
			registroPage.qtdApensos(2);
			
			registroPage.classificarTipoRecebimento('Sedex');
			
			registroPage.numSedex(2);
			
			registroPage.registrar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			//Seta o papel preautuador
			principalPage.login('preautuador');
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		   expect(principalPage.tarefas().get(0).getText()).toEqual('Pré-autuar Processo #2');
		});
		

		it('Deveria pré-atuar como válida a petição recebida', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/2\/preautuacao/);
		    
			var preautuacaoPage = new PreautuacaoPage();
			
			preautuacaoPage.classificar('AP');
			
			preautuacaoPage.finalizar();
			
			//Seta o papel autuador
			principalPage.login('autuador');
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		    
		});
		
		it('Deveria atuar como válida a petição física recebida', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/2\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    principalPage.login('distribuidor');
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #2');
		});
		
		it('Deveria distribuir a petição física autuada', function() {
			
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/2\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionar('Min. Roberto Barroso');
			
			distribuicaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			//Seta o papel recebedor
			principalPage.login('recebedor');
		}); 
		

	});
})();
