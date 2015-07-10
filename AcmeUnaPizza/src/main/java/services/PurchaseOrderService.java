package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PurchaseOrderRepository;
import domain.PurchaseOrder;

@Service
@Transactional
public class PurchaseOrderService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	// Ancillary services -----------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public PurchaseOrderService(){
		super();
	}

	public Collection<PurchaseOrder> findAll() {
		Collection<PurchaseOrder> result;
		
		result = purchaseOrderRepository.findAll();
		
		Assert.notNull(result);
		
		return result;
	}
	
	public Double findInvestedMoney() {
		Double result;
		
		result = purchaseOrderRepository.findInvestedMoney();
		
		Assert.notNull(result);
		
		return result;
	}
	// Ancillary methods ------------------------------------------------------

}