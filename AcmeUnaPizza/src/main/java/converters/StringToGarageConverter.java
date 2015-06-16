package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GarageRepository;
import domain.Garage;

@Component
@Transactional
public class StringToGarageConverter implements Converter<String, Garage> {

	@Autowired
	GarageRepository garageRepository;

	@Override
	public Garage convert(String text) {
		Garage result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = garageRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}