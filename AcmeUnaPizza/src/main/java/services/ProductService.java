package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProductRepository;

@Service
@Transactional
public class ProductService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProductRepository productRepository;

	// Ancillary services -----------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public ProductService(){
		super();
	}

	
	// Ancillary methods ------------------------------------------------------

}