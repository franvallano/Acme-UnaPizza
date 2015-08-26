package services;

import java.util.Collection;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Repair;
import domain.Stuff;
import domain.WorkShop;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RepairServiceTestPositive extends AbstractTest {
	@Autowired
	private RepairService repairService;
	@Autowired
	private StuffService stuffService;
	@Autowired
	private WorkShopService workShopService;
	
	// Test listar reparaciones
	// Autenticado como Boss
	@Test
	public void testListAllRepairs() {
		authenticate("boss1");
		
		Collection<Repair> repairs;

		repairs = repairService.findAll();

		Assert.isTrue(repairs.size() == 3);
		
		unauthenticate();
	}
	
	// Test crear reparacion
	// Autenticado como Boss
	@Test
	public void testCreateRepair() {
		authenticate("boss1");
		
		Stuff stuff;
		Repair repair;
		WorkShop repairWorkshop;
		Integer totalBefore;
		
		repair = repairService.create();
		
		totalBefore = repairService.findAll().size();
		
		stuff = stuffService.findCheckMalfunction(71);
		
		repairWorkshop = workShopService.findOne(stuff.getWorkShop().getId());
		
		repair.setWorkShop(repairWorkshop);
		repair.setStuff(stuff);
		repair.setCost(60.0);
		repair.setMoment(new GregorianCalendar(2015, 7, 10).getTime());
		
		stuff = repair.getStuff();
		stuff.setStatus("REPAIRING");
		stuffService.save(stuff);
		
		repairService.save(repair);
		
		stuff = stuffService.findOne(71);
		
		Assert.isTrue(totalBefore + 1 == repairService.findAll().size() && stuff.getStatus().equals("REPAIRING"));
		
		unauthenticate();
	}
}
