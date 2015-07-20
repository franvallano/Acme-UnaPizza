package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{
	
	@Query("select s from Staff s where s.userAccount.id = ?1")
	Staff findByPrincipal(int userAccountId);
	
	@Query("select s from Staff s where s.userAccount.id = ?1")
	Staff findOneByUserAccount(int userAccountId);
	
	@Query("select s from Staff s where s.userAccount.id = ?1 AND (s.contractEndDate is null OR s.contractEndDate >= CURRENT_TIMESTAMP)")
	Staff checkHasContract(int userAccountId);
}