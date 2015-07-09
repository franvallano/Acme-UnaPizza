package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer>{
	
	@Query("select o from Offer o where o.startDate <= CURRENT_TIMESTAMP AND (o.endDate is null OR o.endDate >= CURRENT_TIMESTAMP)")
	Collection<Offer> findCurrentOffers();
}