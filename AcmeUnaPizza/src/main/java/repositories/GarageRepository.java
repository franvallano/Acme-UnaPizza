package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Garage;


@Repository
public interface GarageRepository
	extends JpaRepository<Garage, Integer> {
	
	@Query("select g from Garage g where g.size > g.motorbikes.size")
	Collection<Garage> findFreeGarages();
}
