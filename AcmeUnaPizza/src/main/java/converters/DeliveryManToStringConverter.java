package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.DeliveryMan;

@Component
@Transactional
public class DeliveryManToStringConverter implements Converter<DeliveryMan, String> {

	@Override
	public String convert(DeliveryMan entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}