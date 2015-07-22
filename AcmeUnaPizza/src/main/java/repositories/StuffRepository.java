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
	
	@Query("select s from Stuff s where s.status='MALFUNCTION' and s.workShop.id=?1")
	Collection<Stuff> findAllMalfunctionStuffByWorkshopId(int id);
}