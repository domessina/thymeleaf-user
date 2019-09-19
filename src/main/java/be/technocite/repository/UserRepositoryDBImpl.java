package be.technocite.repository;

import be.technocite.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryDBImpl implements UserRepository, RowMapper<User> {

    private final Logger logger = LoggerFactory.getLogger(UserRepositoryDBImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("select * from public.user where email = ?", new Object[]{email}, this);
        }catch (EmptyResultDataAccessException e) {
            logger.debug("No user found with email " + email);
            return null;
        }
    }

    @Override
    public User save(User user) {
        logger.debug("user to save " + user);

        Object[] params = new Object[]{
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword()
        };

        if(findByEmail(user.getEmail()) == null) {
            jdbcTemplate.update("insert into public.user (email, firstname, lastname, password)" +
                    "values (?,?,?,?)", params);
        }else {
            jdbcTemplate.update("update public.user set email = ?, firstname = ?, lastname = ?, password = ?" +
                    "where email = ?", params[0], params[1], params[2], params[3]);
        }

        //FIXME email is User id, dont let user change his email in this thymeleaf example app
        return findByEmail(user.getEmail());
    }

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(
                resultSet.getString("firstname"),
                resultSet.getString("lastname"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
