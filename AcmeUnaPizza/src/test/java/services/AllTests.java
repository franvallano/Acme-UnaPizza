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
	MotorbikeServiceTestPositive.class, MotorbikeServiceTestNegative.class})
public class AllTests {

}
