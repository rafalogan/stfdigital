package br.jus.stf.plataforma.dashboards.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.dashboards.interfaces.dto.DashboardDto;
import br.jus.stf.plataforma.dashboards.interfaces.facade.DashboardServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Api REST para recuperar dashboards.
 * 
 * @author Tomas.Godoi
 */
@RestController
@RequestMapping("/api/dashboards")
public class DashboardRestResource {

	@Autowired
	private DashboardServiceFacade dashboardServiceFacade;

	@ApiOperation("Recupera o dashboard padrão para o papel recebido")
	@RequestMapping(value = "/padrao", method = RequestMethod.GET)
	public DashboardDto recuperarPadrao(@RequestHeader("papel") String papel) {
		DashboardDto dashboard = dashboardServiceFacade.recuperarPadrao(papel);
		return dashboard;
	}

}
