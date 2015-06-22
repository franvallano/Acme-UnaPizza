package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.WorkShop;

@Repository
public interface WorkShopRepository extends JpaRepository<WorkShop, Integer>{
	
}