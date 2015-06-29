package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Customer;


@Repository
public interface AdministratorRepository
	extends JpaRepository<Administrator, Integer> {
	
	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int userAccountId);
	
	//Pizza más y menos vendida.
	//Bebida más y menos vendida.
	//Postre más y menos vendido.
	//Complemento más y menos vendido.
	
	

}
