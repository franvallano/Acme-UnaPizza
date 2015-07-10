package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProductRepository;
import domain.Product;
import domain.Provider;

@Component
@Transactional
public class StringToProductConverter implements Converter<String, Product> {

	@Autowired
	ProductRepository productRepository;

	@Override
	public Product convert(String text) {
		Product result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = productRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
