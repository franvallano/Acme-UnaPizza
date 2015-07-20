/* LoginService.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.StaffService;

@Service
@Transactional
public class LoginService implements UserDetailsService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired 
	StaffService staffService;
	
	// Business methods -------------------------------------------------------

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Assert.notNull(username);
		UserAccount userAccount;
		List<Authority> staffAuthorities;
		Authority boss, cook, deliveryMan;

		UserDetails result;
		
		userAccount = userRepository.findByUsername(username);
		
		Assert.notNull(userAccount);
		
		staffAuthorities = new ArrayList<Authority>();
		boss = new Authority();
		cook = new Authority();
		deliveryMan = new Authority();
		
		boss.setAuthority(Authority.BOSS);
		cook.setAuthority(Authority.COOK);
		deliveryMan.setAuthority(Authority.DELIVERY_MAN);
		
		staffAuthorities.add(boss);
		staffAuthorities.add(cook);
		staffAuthorities.add(deliveryMan);
		
		// Si es miembro del staff, comprobamos si le ha caducado el contrato para desactivar su cuenta
		if(userAccount.getAuthorities().contains(staffAuthorities.get(0)) || userAccount.getAuthorities().contains(staffAuthorities.get(1)) || 
				userAccount.getAuthorities().contains(staffAuthorities.get(2)))
			staffService.checkHasContract(userAccount.getId());
		
		result = userAccount;

		Assert.notNull(result);		
		// WARNING: The following sentences prevent lazy initialisation problems!
		Assert.notNull(result.getAuthorities());
		Assert.isTrue(result.isEnabled());
		result.getAuthorities().size();

		return result;
	}

	public static UserAccount getPrincipal() {
		UserAccount result;
		SecurityContext context;
		Authentication authentication;
		Object principal;

		// If the asserts in this method fail, then you're
		// likely to have your Tomcat's working directory
		// corrupt. Please, clear your browser's cache, stop
		// Tomcat, update your Maven's project configuration,
		// clean your project, clean Tomcat's working directory,
		// republish your project, and start it over.

		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();
		Assert.notNull(authentication);
		principal = authentication.getPrincipal();
		Assert.isTrue(principal instanceof UserAccount);
		result = (UserAccount) principal;
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);

		return result;
	}

}
