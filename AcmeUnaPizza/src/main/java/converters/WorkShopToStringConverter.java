package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorkShop;

@Component
@Transactional
public class WorkShopToStringConverter implements Converter<WorkShop, String> {

	@Override
	public String convert(WorkShop entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}