package services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DashboardAdministratorServiceTestPositive.class, DashboardBossServiceTestPositive.class,
	DashboardCookServiceTestPositive.class, DashboardCustomerServiceTestPositive.class,
	DashboardDeliveryManServiceTestPositive.class,
	ProductServiceTestPositive.class, ProductServiceTestNegative.class,
	ProviderServiceTestPositive.class, ProviderServiceTestNegative.class,
	MotorbikeServiceTestPositive.class, MotorbikeServiceTestNegative.class, 
	OfferServiceTestPositive.class, OfferServiceTestNegative.class,
	UserManagementServiceTestPositive.class, UserManagementServiceTestNegative.class,
	SalesOrderServiceTestPositive.class, SalesOrderServiceTestNegative.class,
	PurcharseOrderServiceTestPositive.class, PurcharseOrderServiceTestNegative.class,
	ComplaintServiceTestPositive.class, ComplaintServiceTestNegative.class,
	DiscussionMessageServiceTestPositive.class, DiscussionMessageServiceTestNegative.class,
	GarageServiceTestPositive.class, GarageServiceTestNegative.class,
	WorkShopServiceTestPositive.class, WorkShopServiceTestNegative.class,
	RepairServiceTestPositive.class, RepairServiceTestNegative.class,
	StuffServiceTestPositive.class, StuffServiceTestNegative.class})
public class AllTests {

}
