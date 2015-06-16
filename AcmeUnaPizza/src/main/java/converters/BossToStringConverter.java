package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Boss;

@Component
@Transactional
public class BossToStringConverter implements Converter<Boss, String> {

	@Override
	public String convert(Boss entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}