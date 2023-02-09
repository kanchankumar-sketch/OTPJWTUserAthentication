package in.reinventing.otpauthentication.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
public class UserDto {
	
	@NotBlank(message="Name should not be balnk.")
	private String name;
	
	@Email(message = "Please fill valid email.")
	private String email;
}
