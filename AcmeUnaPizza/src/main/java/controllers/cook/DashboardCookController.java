package controllers.cook;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/dashboard/cook")
public class DashboardCookController {
	//Services--------------------------------------------------------
	
	//Constructor------------------------------------------------------
	public DashboardCookController(){
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
