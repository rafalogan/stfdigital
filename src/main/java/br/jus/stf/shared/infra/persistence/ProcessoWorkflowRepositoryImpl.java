/**
 * 
 */
package br.jus.stf.shared.infra.persistence;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.jus.stf.shared.domain.model.ProcessoWorkflowId;
import br.jus.stf.shared.domain.model.ProcessoWorkflowRepository;

/**
 * @author Lucas.Rodrigues
 *
 */
@Repository
public class ProcessoWorkflowRepositoryImpl implements ProcessoWorkflowRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public String statusById(ProcessoWorkflowId id) {
		String sql = new StringBuilder("SELECT pw.DSC_STATUS_PROCESS_INSTANCE ")
			.append("FROM PLATAFORMA.PROCESSO_WORKFLOW pw ")
			.append("WHERE pw.NUM_PROCESS_INSTANCE = :id").toString();
		TypedQuery<String> query = (TypedQuery<String>) entityManager.createNativeQuery(sql, String.class);
		query.setParameter("id", id.toLong());
		return query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessoWorkflowId> findByStatus(String status) {
		String sql = new StringBuilder("SELECT pw.NUM_PROCESS_INSTANCE ")
			.append("FROM PLATAFORMA.PROCESSO_WORKFLOW pw ")
			.append("WHERE pw.DSC_STATUS_PROCESS_INSTANCE = :status").toString();
		TypedQuery<Long> query = (TypedQuery<Long>) entityManager.createNativeQuery(sql, Long.class);
		query.setParameter("status", status);
		return query.getResultList().stream()
					.map(id -> new ProcessoWorkflowId(id))
					.collect(Collectors.toList());
	}

}
