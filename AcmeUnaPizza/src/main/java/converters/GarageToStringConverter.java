package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Garage;

@Component
@Transactional
public class GarageToStringConverter implements Converter<Garage, String> {

	@Override
	public String convert(Garage entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}