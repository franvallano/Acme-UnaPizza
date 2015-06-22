package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Offer;

@Component
@Transactional
public class OfferToStringConverter implements Converter<Offer, String> {

	@Override
	public String convert(Offer entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}