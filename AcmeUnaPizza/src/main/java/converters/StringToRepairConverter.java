package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RepairRepository;
import domain.Repair;

@Component
@Transactional
public class StringToRepairConverter implements Converter<String, Repair> {

	@Autowired
	RepairRepository repairRepository;

	@Override
	public Repair convert(String text) {
		Repair result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = repairRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
