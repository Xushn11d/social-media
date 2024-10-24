package org.example.socialmedia.repository;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.example.socialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByPhoneNumber(String username);

}
