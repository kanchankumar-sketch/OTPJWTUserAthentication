package in.reinventing.otpauthentication.dtos;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmailDto {

	@Email(message = "Please fill valid email.")
	private String email;
}
