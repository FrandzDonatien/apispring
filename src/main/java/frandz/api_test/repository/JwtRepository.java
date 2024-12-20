package frandz.api_test.repository;

import frandz.api_test.model.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<Jwt, Long> {

    Optional<Jwt> findByValeur(String value);

    @Query("FROM Jwt j WHERE j.expire = :expire AND j.desactive = :desactive AND j.user.email = :email ")
    Optional<Jwt> findUserValidToken(String email, boolean desactive, boolean expire);
}