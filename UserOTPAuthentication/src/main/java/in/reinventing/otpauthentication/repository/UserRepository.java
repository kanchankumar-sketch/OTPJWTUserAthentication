package in.reinventing.otpauthentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.reinventing.otpauthentication.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	void deleteByEmail(String email);

	Optional<User> findByEmail(String email);

}
