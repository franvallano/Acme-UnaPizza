package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.StaffRepository;
import domain.Staff;

@Component
@Transactional
public class StringToStaffConverter implements Converter<String, Staff> {

	@Autowired
	StaffRepository staffRepository;

	@Override
	public Staff convert(String text) {
		Staff result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = staffRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}