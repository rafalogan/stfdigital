package br.jus.stf.plataforma.dashboards.domain.model;

import java.util.List;

/**
 * Entidade Dashboard. É composta por um conjunto de Dashlets.
 * 
 * @author Tomas.Godoi
 *
 */
public class Dashboard {

	private List<Dashlet> dashlets;

	public List<Dashlet> getDashlets() {
		return dashlets;
	}

	public void setDashlets(List<Dashlet> dashlets) {
		this.dashlets = dashlets;
	}

}
