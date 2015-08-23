package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProviderService;
import controllers.AbstractController;
import domain.Product;
import domain.Provider;

@Controller
@RequestMapping("/provider/administrator")
public class ProviderAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ProviderService providerService;
	
	// Constructors -----------------------------------------------------------
	
	public ProviderAdministratorController() {
		super();
	}
	
	// Creation ----------------------------------------------------------------
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Provider> providers;
		String uri;
		
		providers = providerService.findAll();
		
		uri = "provider/administrator/list.do";
		result = new ModelAndView("provider/list");
		result.addObject("requestURI", uri);
		result.addObject("providers", providers);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int providerId) {
		ModelAndView result;
		Provider provider;
		
		provider = providerService.findOne(providerId);
		
		result = new ModelAndView("provider/edit");
		result.addObject("provider", provider);
		result.addObject("details", true);
		
		return result;
	}
	
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int providerId) {
		ModelAndView result;
		Provider provider;
		
		provider = providerService.findOne(providerId);
		
		result = createEditModelAndView(provider);
		result.addObject("edit", true);

		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Provider provider, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(provider);
		} else {
			try {
				providerService.save(provider);
				result = new ModelAndView("redirect:/provider/administrator/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(provider, "provider.commit.error");
			}
		}

		return result;
	}
	
	
	public ModelAndView createEditModelAndView(Provider provider){
		ModelAndView result;
		
		result = createEditModelAndView(provider, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(Provider provider, String message){
		ModelAndView res;
		
		res = new ModelAndView("provider/edit");
		res.addObject("provider", provider);
		res.addObject("message", message);
		res.addObject("requestURI", "provider/administrator/edit.do");	
		res.addObject("edit", true);
	
		return res;
	}

	
	// Edition ----------------------------------------------------------------
	
}
