package controllers.boss;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/dashboard/boss")
public class DashboardBossController {
	//Services--------------------------------------------------------
	
	//Constructor------------------------------------------------------
	public DashboardBossController(){
		super();
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listDashboardAdmin(){
		ModelAndView result;
		
		result = new ModelAndView("dashboard/list");
		
		return result;
	}
}
