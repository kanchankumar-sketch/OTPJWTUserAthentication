package in.reinventing.otpauthentication.entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OTP {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String otp;
	private Instant generatedAt=Instant.now();
	private Instant expireAt=generatedAt.plus(5L,ChronoUnit.MINUTES);
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
}
