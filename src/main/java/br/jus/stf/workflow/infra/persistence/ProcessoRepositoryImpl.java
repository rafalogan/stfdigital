package br.jus.stf.workflow.infra.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.workflow.domain.model.ProcessoWorkflow;
import br.jus.stf.workflow.domain.model.ProcessoRepository;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository("processoWorkflowRepository")
public class ProcessoRepositoryImpl extends SimpleJpaRepository<ProcessoWorkflow, ProcessoWorkflowId> implements ProcessoRepository {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	public ProcessoRepositoryImpl(EntityManager entityManager) {
		super(ProcessoWorkflow.class, entityManager);
	}
	
	@Override
	public ProcessoWorkflowId criar(String mensagem, String status) {
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("status", status);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage(mensagem, variaveis);
		ProcessoWorkflow processo = new ProcessoWorkflow(processInstance, status);
		super.save(processo);
		return processo.id();
	}
	
	@Override
	public ProcessoWorkflow consultar(ProcessoWorkflowId id) {
		ProcessoWorkflow processo = super.findOne(id);
    	ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
    													.processInstanceId(id.toString())
    													.includeProcessVariables()
    													.singleResult();
    	processo.setInstance(processInstance);
    	return processo;
	}

	@Override
	public ProcessoWorkflow salvar(ProcessoWorkflow processo) {
		return super.save(processo);
	}
	
	@Override
	public void sinalizar(String sinal, String status) {
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("status", status);
		runtimeService.signalEventReceived(sinal, variaveis);
	}

}
