package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
