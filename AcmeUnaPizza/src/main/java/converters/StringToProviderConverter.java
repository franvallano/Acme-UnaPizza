package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProviderRepository;
import domain.Provider;

@Component
@Transactional
public class StringToProviderConverter implements Converter<String, Provider> {

	@Autowired
	ProviderRepository providerRepository;

	@Override
	public Provider convert(String text) {
		Provider result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = providerRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
