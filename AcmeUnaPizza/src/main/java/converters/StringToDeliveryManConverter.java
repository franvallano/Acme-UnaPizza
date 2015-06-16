package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DeliveryManRepository;
import domain.DeliveryMan;

@Component
@Transactional
public class StringToDeliveryManConverter implements Converter<String, DeliveryMan> {

	@Autowired
	DeliveryManRepository deliveryManRepository;

	@Override
	public DeliveryMan convert(String text) {
		DeliveryMan result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = deliveryManRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}