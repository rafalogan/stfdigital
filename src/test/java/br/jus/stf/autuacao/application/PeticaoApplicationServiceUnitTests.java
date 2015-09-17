package br.jus.stf.autuacao.application;

import java.util.LinkedList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import br.jus.stf.AbstractIntegrationTests;

import br.jus.stf.workflow.application.TarefaApplicationService;

/**
 * Teste do processo de peticionamento.
 * 
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 23.07.2015
 */
public class PeticaoApplicationServiceUnitTests extends AbstractIntegrationTests {
	
	@Autowired
	private PeticaoApplicationService peticaoApplicationService;
	
	@Autowired
	private TarefaApplicationService tarefaApllicationService;
	
	//private Peticao peticao;
	
	@Before
	public void iniciarPeticao() {
		/*
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
                
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
    
        this.peticao = new Peticao();
		this.peticao.setClasseSugerida(new ClasseProcessual("HC", "HC"));
		this.peticao.setClasse(new ClasseProcessual("HC", "HC"));
		this.peticao.setPoloAtivo(poloAtivo);
		this.peticao.setPoloPassivo(poloPassivo);
		this.peticao.setDocumentos(documentos);*/
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSeTipoRecebimentoNaoInformado(){
		/*
		String idPeticao = "";
		String tipoRecebimento = "";
		String classeProcessual = "HC";
		        		
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        
        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
        
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
        
        idPeticao = this.peticaoApplicationService.peticionar(tipoRecebimento, classeProcessual, poloAtivo, poloPassivo, documentos);
       
        Assert.assertEquals("4", idPeticao);*/
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSeClasseProcessualNaoInformada(){
		/*
		String idPeticao = "";
		String tipoRecebimento = "autuarOriginarios";
		        		
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        
        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
        
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
        
        idPeticao = this.peticaoApplicationService.peticionar(tipoRecebimento, null, poloAtivo, poloPassivo, documentos);
       
        Assert.assertEquals("4", idPeticao);*/
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSePoloAtivoNaoInformado(){
		/*
		String tipoRecebimento = "autuarOriginarios";
		String classeProcessual = "HC";
		
		List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        Polo poloAtivo = new Polo();

        List<Parte> partesPoloPassivo = new LinkedList<Parte>();
        partesPoloPassivo.add(new Parte("Beltrano Silva"));
        Polo poloPassivo = new Polo();
        poloPassivo.setPartes(partesPoloPassivo);
        
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
        
        this.peticaoApplicationService.peticionar(tipoRecebimento, classeProcessual, poloAtivo, poloPassivo, documentos);*/
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoSePoloPassivoNaoInformado(){
		/*
		String tipoRecebimento = "autuarOriginarios";
		String classeProcessual = "HC";
		
        List<Parte> partesPoloAtivo = new LinkedList<Parte>();
        partesPoloAtivo.add(new Parte("Fulano Silva"));
        Polo poloAtivo = new Polo();
        poloAtivo.setPartes(partesPoloAtivo);
        
        Polo poloPassivo = null;
                
        List<Documento> documentos = new LinkedList<Documento>();
        documentos.add(new Documento("Doc1"));
        documentos.add(new Documento("Doc2"));
                
        this.peticaoApplicationService.peticionar(tipoRecebimento, classeProcessual, poloAtivo, poloPassivo, documentos);*/
	}
	
	@Test
	public void registrarPeticao(){
		/*
		String idPeticao = "";
		String tipoRecebimento = "autuarOriginarios";
				
		idPeticao = this.peticaoApplicationService.peticionar(tipoRecebimento, this.peticao.getClasseSugerida().getSigla(), this.peticao.getPoloAtivo(), this.peticao.getPoloPassivo(), this.peticao.getDocumentos());
        
		Assert.assertTrue(!StringUtils.isEmpty(idPeticao));*/
	}
	
	@Test
	public void listarTarefasAutuador(){
		/*
		String tipoRecebimento = "autuarOriginarios";
		
		this.peticaoApplicationService.peticionar(tipoRecebimento, this.peticao.getClasse().getSigla(), this.peticao.getPoloAtivo(), this.peticao.getPoloPassivo(), this.peticao.getDocumentos());
		
		List<Task> tarefas = this.tarefaApllicationService.tarefas("autuador");
		
		Assert.assertTrue(tarefas.size() > 0);*/
	}
	
	@Test(expected=RuntimeException.class)
	public void capturarExcecaoAutuarPeticaoSeIdPeticaoNaoInformado(){
		/*
		String idPeticao = "";
		String classe = "";
		boolean peticaoValida = true;
		
		this.peticaoApplicationService.autuar(idPeticao, classe, peticaoValida, "");
		*/
	}
	
	@Ignore
	public void autuarPeticaoValida(){
		/*
		String idPeticao = "4";
		boolean peticaoValida = true;
				
		this.peticaoApplicationService.autuar(idPeticao, this.peticao.getClasse().getSigla(), peticaoValida, "");
		
		List<Task> tarefas = this.tarefaApllicationService.tarefas("distribuidor");
		
		Assert.assertTrue(tarefas.size() > 0);*/
	}
}
