package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Staff;

@Component
@Transactional
public class StaffToStringConverter implements Converter<Staff, String> {

	@Override
	public String convert(Staff entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}