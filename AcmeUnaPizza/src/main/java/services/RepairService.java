package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BossRepository;
import repositories.RepairRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.PasswordCode;
import domain.Boss;

@Service
@Transactional
public class RepairService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RepairRepository repairRepository;

	// Ancillary services -----------------------------------------------------
	
	// Constructor ------------------------------------------------------------
	public RepairService(){
		super();
	}

	public Double findTotalCostRepairs() {
		Double result;
		
		result = repairRepository.findTotalCostRepairs();
		
		Assert.notNull(result);
		
		return result;
	}
	// Ancillary methods ------------------------------------------------------

}