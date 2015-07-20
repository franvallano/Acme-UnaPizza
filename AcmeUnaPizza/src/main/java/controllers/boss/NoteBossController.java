package controllers.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SalesOrderService;
import controllers.AbstractController;
import domain.Note;


@Controller
@RequestMapping("/note/boss")
public class NoteBossController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private SalesOrderService salesOrderService;
	
	//Constructor------------------------------------------------------
	public NoteBossController(){
		super();
	}
	
	@RequestMapping(value = "/note", method = RequestMethod.GET)
	public ModelAndView note(@RequestParam int salesOrderId) {
		ModelAndView result;
		Note note;
		
		note = salesOrderService.note(salesOrderId);
		
		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		
		return result;
	}
}
