package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Stuff;

@Repository
public interface StuffRepository extends JpaRepository<Stuff, Integer>{
	


}