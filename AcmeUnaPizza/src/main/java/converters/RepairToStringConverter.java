package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Repair;

@Component
@Transactional
public class RepairToStringConverter implements Converter<Repair, String> {

	@Override
	public String convert(Repair entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}