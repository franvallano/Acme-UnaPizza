package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PurchaseOrderRepository;
import domain.PurchaseOrder;

@Component
@Transactional
public class StringToPurchaseOrderConverter implements Converter<String, PurchaseOrder> {

	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;

	@Override
	public PurchaseOrder convert(String text) {
		PurchaseOrder result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = purchaseOrderRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
