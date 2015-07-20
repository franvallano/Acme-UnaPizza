package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class DrivingTimeForm {
	private int salesOrderId;
	private int drivingTime;

	@Min(1)
	public int getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(int drivingTime) {
		this.drivingTime = drivingTime;
	}

	public int getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(int salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

}
