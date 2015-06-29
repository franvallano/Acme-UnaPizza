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
	
	//Pizza m�s y menos vendida.
	//Bebida m�s y menos vendida.
	//Postre m�s y menos vendido.
	//Complemento m�s y menos vendido.
	
	

}
