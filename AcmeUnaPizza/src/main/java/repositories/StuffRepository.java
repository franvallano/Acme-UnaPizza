package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Stuff;

@Repository
public interface StuffRepository extends JpaRepository<Stuff, Integer>{
	

	@Query("select s from Stuff s where s.repairs.size = (select max(st.repairs.size) from Stuff st)")
	Collection<Stuff> findStuffMoreRepaired();
	
	//Finds all malfunction stuff that can be repaired at a specified workshop
	@Query("select s from Stuff s where s.status='MALFUNCTION' and s.workShop.id=?1")
	Collection<Stuff> findAllMalfunctionStuffByWorkshopId(int id);
	
	//Finds all malfunctioning stuff
	@Query("select s from Stuff s where s.status='MALFUNCTION'")
	Collection<Stuff> findAllMalfunctionStuff();
	
	//Finds all stuff being repaired 
	@Query("select s from Stuff s where s.status='REPAIRING'")
	Collection<Stuff> findAllRepairingStuff();
	
	//Finds all OK stuff 
	@Query("select s from Stuff s where s.status='OK'")
	Collection<Stuff> findAllOkStuff();
	
	@Query("select s from Stuff s where s.id = ?1 AND status = 'MALFUNCTION'")
	Stuff findCheckMalfunction(int stuffId);
}