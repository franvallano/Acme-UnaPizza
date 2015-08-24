package services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DashboardAdministratorServiceTestPositive.class, DashboardBossServiceTestPositive.class,
	DashboardCookServiceTestPositive.class, DashboardCustomerServiceTestPositive.class,
	DashboardDeliveryManServiceTestPositive.class,
	ProductServiceTestPositive.class, ProductServiceTestNegative.class,
	ProviderServiceTestPositive.class, ProductServiceTestNegative.class,
	MotorbikeServiceTestPositive.class, MotorbikeServiceTestNegative.class, 
	OfferServiceTestPositive.class, OfferServiceTestNegative.class,
	UserManagementServiceTestPositive.class, UserManagementServiceTestNegative.class,
	SalesOrderServiceTestPositive.class, SalesOrderServiceTestNegative.class,
	PurcharseOrderServiceTestPositive.class, PurcharseOrderServiceTestNegative.class})
public class AllTests {

}
