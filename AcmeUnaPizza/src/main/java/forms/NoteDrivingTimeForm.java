package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class NoteDrivingTimeForm {
	private int salesOrderId;
	private int drivingTime;
	private String cause;
	private String description;

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
	
	@NotBlank
	@Pattern(regexp = "^JOKE$|^CANCELLED$|^OTHER$")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
