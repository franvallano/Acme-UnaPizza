package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BossRepository;
import domain.Boss;

@Component
@Transactional
public class StringToBossConverter implements Converter<String, Boss> {

	@Autowired
	BossRepository bossRepository;

	@Override
	public Boss convert(String text) {
		Boss result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = bossRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
