package be.technocite.repository;


import be.technocite.model.User;

public interface UserRepository {

    User findByEmail(String email);
    User save(User user);
}
