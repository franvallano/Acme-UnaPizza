package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import controllers.AbstractController;
import domain.Offer;


@Controller
@RequestMapping("/offer/administrator")
public class OfferAdministratorController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private OfferService offerService;
	
	//Constructor------------------------------------------------------
	public OfferAdministratorController(){
		super();
	}
	
	// Creation ----------------------------------------------------------------

	
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPizzas(){
		ModelAndView result;
		
		Collection<Offer> offers;
		
		offers = offerService.findAll();
		
		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		
		return result;
	}
}
