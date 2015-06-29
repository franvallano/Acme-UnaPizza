package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Repair;


@Repository
public interface RepairRepository
	extends JpaRepository<Repair, Integer> {
	
	@Query("select SUM(r.cost) from Repair r")
	Double findTotalCostRepairs();
}
