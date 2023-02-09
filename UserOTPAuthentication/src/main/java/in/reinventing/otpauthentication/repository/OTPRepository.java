package in.reinventing.otpauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.reinventing.otpauthentication.entity.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP,Long> {

}
