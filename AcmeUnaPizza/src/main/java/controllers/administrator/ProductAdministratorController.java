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

import services.ProductService;
import services.ProviderService;
import controllers.AbstractController;
import domain.Motorbike;
import domain.Product;
import domain.Provider;


@Controller
@RequestMapping("/product/administrator")
public class ProductAdministratorController extends AbstractController{
	//Services--------------------------------------------------------
	@Autowired
	private ProductService productService;
	@Autowired
	private ProviderService providerService;
	
	
	//Constructor------------------------------------------------------
	public ProductAdministratorController(){
		super();
	}
	
	// Creation ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam String productType) {
		ModelAndView result;
		Product product;
		Collection<Provider> availableProviders;

		product = productService.create();
		availableProviders = providerService.findAll();

		result = createEditModelAndView(product);
		result.addObject("register", true);
		result.addObject("availableProviders", availableProviders);
		
		if(productType.equals("PIZZA")){
			result.addObject("backURI", "listPizzas.do");
			product.setType(productType);
		} else if(productType.equals("DESSERT")){
			result.addObject("backURI", "listDesserts.do");
			product.setType(productType);
		} else if(productType.equals("COMPLEMENT")){
			result.addObject("backURI", "listComplements.do");
			product.setType(productType);
		} else if(productType.equals("DRINK")){
			result.addObject("backURI", "listDrinks.do");
			product.setType(productType);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");	

		return result;
	}
	
	//Listing----------------------------------------------------------
	@RequestMapping(value = "/listPizzas", method = RequestMethod.GET)
	public ModelAndView listPizzas(){
		ModelAndView result;
		
		Collection<Product> products;
		
		products = productService.findAllPizzas();
		
		result = new ModelAndView("product/list");
		result.addObject("products", products);
		
		return result;
	}
	
	@RequestMapping(value = "/listComplements", method = RequestMethod.GET)
	public ModelAndView listComplements(){
		ModelAndView result;
		
		Collection<Product> products;
		
		products = productService.findAllComplements();
		
		result = new ModelAndView("product/list");
		result.addObject("products", products);
		
		return result;
	}
	
	@RequestMapping(value = "/listDesserts", method = RequestMethod.GET)
	public ModelAndView listDesserts(){
		ModelAndView result;
		
		Collection<Product> products;
		
		products = productService.findAllDesserts();
		
		result = new ModelAndView("product/list");
		result.addObject("products", products);
		
		return result;
	}
	
	@RequestMapping(value = "/listDrinks", method = RequestMethod.GET)
	public ModelAndView listDrinks(){
		ModelAndView result;
		
		Collection<Product> products;
		
		products = productService.findAllDrinks();
		
		result = new ModelAndView("product/list");
		result.addObject("products", products);
		
		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int productId) {
		ModelAndView result;
		Product product;
		
		product = productService.findOne(productId);
		
		result = new ModelAndView("product/edit");
		result.addObject("product", product);
		result.addObject("details", true);
		
		if(product.getType().equals("PIZZA"))
			result.addObject("backURI", "listPizzas.do");
		else if(product.getType().equals("DESSERT"))
			result.addObject("backURI", "listDesserts.do");
		else if(product.getType().equals("COMPLEMENT"))
			result.addObject("backURI", "listComplements.do");
		else if(product.getType().equals("DRINK"))
			result.addObject("backURI", "listDrinks.do");
		
		return result;
	}
		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int productId) {
		ModelAndView result;
		Product product;
		Collection<Provider> availableProviders;
		
		product = productService.findOne(productId);
		availableProviders = providerService.findAll();
		
		result = createEditModelAndView(product);
		
		result.addObject("edit", true);
		
		if(product.getType().equals("PIZZA"))
			result.addObject("backURI", "listPizzas.do");
		else if(product.getType().equals("DESSERT"))
			result.addObject("backURI", "listDesserts.do");
		else if(product.getType().equals("COMPLEMENT"))
			result.addObject("backURI", "listComplements.do");
		else if(product.getType().equals("DRINK"))
			result.addObject("backURI", "listDrinks.do");
		
		result.addObject("availableProviders", availableProviders);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Product product, BindingResult binding) {
		ModelAndView result;
		Collection<Provider> availableProviders;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(product);
			result.addObject("edit", true);
			result.addObject("register", true);
			availableProviders = providerService.findAll();
			result.addObject("availableProviders", availableProviders);
		} else {
			try {
				productService.save(product);
				if(product.getType().equals("PIZZA"))
					result = new ModelAndView("redirect:/product/administrator/listPizzas.do");
				else if(product.getType().equals("DESSERT"))
					result = new ModelAndView("redirect:/product/administrator/listDesserts.do");
				else if(product.getType().equals("COMPLEMENT"))
					result = new ModelAndView("redirect:/product/administrator/listComplements.do");
				else if(product.getType().equals("DRINK"))
					result = new ModelAndView("redirect:/product/administrator/listDrinks.do");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
				
			} catch (Throwable oops) {
				result = createEditModelAndView(product, "product.commit.error");
				result.addObject("edit", true);
				result.addObject("register", true);
				availableProviders = providerService.findAll();
				result.addObject("availableProviders", availableProviders);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Product product, BindingResult binding) {
		ModelAndView result;
		Collection<Provider> availableProviders;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(product);
			result.addObject("edit", true);
			result.addObject("edit", true);
			availableProviders = providerService.findAll();
			result.addObject("availableProviders", availableProviders);
		} else {
			try {
				productService.save(product);
				if(product.getType().equals("PIZZA"))
					result = new ModelAndView("redirect:/product/administrator/listPizzas.do");
				else if(product.getType().equals("DESSERT"))
					result = new ModelAndView("redirect:/product/administrator/listDesserts.do");
				else if(product.getType().equals("COMPLEMENT"))
					result = new ModelAndView("redirect:/product/administrator/listComplements.do");
				else if(product.getType().equals("DRINK"))
					result = new ModelAndView("redirect:/product/administrator/listDrinks.do");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(product, "product.commit.error");
				result.addObject("edit", true);
				availableProviders = providerService.findAll();
				result.addObject("availableProviders", availableProviders);
			}
		}

		return result;
	}
	
	public ModelAndView createEditModelAndView(Product product){
		ModelAndView result;
		
		result = createEditModelAndView(product, null);
		
		return result;
	}

	public ModelAndView createEditModelAndView(Product product, String message){
		ModelAndView res;
		
		res = new ModelAndView("product/edit");
		res.addObject("product", product);
		res.addObject("message", message);
		res.addObject("requestURI", "product/administrator/edit.do");	
		res.addObject("edit", true);
	
		return res;
	}
}
