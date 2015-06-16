package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PurchaseOrder;

@Component
@Transactional
public class PurchaseOrderToStringConverter implements Converter<PurchaseOrder, String> {

	@Override
	public String convert(PurchaseOrder entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}