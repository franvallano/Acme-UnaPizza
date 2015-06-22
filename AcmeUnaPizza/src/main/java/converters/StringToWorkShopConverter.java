package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WorkShopRepository;
import domain.WorkShop;

@Component
@Transactional
public class StringToWorkShopConverter implements Converter<String, WorkShop> {

	@Autowired
	WorkShopRepository workShopRepository;

	@Override
	public WorkShop convert(String text) {
		WorkShop result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = workShopRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
