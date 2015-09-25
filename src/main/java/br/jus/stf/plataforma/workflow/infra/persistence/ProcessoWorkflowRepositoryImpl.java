package br.jus.stf.plataforma.workflow.infra.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.plataforma.workflow.domain.model.ProcessoWokflowRepository;
import br.jus.stf.shared.ProcessoWorkflow;
import br.jus.stf.shared.ProcessoWorkflowId;

/**
 * @author Rodrigo Barreiros
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 26.06.2015
 */
@Repository
public class ProcessoWorkflowRepositoryImpl extends SimpleJpaRepository<ProcessoWorkflow, ProcessoWorkflowId> implements ProcessoWokflowRepository {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	public ProcessoWorkflowRepositoryImpl(EntityManager entityManager) {
		super(ProcessoWorkflow.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public ProcessoWorkflowId criar(Long informacao, String mensagem, String status) {
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("status", status);
		variaveis.put("informacao", informacao);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage(mensagem, variaveis);
		ProcessoWorkflowId id = new ProcessoWorkflowId(Long.parseLong(processInstance.getId()));
		ProcessoWorkflow processo = new ProcessoWorkflow(id, status);
		super.save(processo);
		return id;
	}

	@Override
	public void sinalizar(String sinal, String status) {
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("status", status);
		runtimeService.signalEventReceived(sinal, variaveis);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProcessoWorkflow save(ProcessoWorkflow processo) {
		return super.save(processo);
	}
	
	@Override
	public void updateStatus(ProcessoWorkflowId processoWorkflowId, String status) {
		Query query = entityManager.createQuery("UPDATE ProcessoWorkflow pw SET pw.status = :status WHERE pw.id = :id");
		query.setParameter("status", status);
		query.setParameter("id", processoWorkflowId);
		query.executeUpdate();
	}

}