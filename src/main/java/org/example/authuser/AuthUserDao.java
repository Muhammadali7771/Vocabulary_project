package org.example.authuser;

import lombok.NonNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long save(@NonNull AuthUser authUser) {
        var sql = "insert into authuser(username, password, role) values (:username, :password, :role)";
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("username", authUser.getUsername())
                .addValue("password", authUser.getPassword())
                .addValue("role", authUser.getRole());
        var keyholder = new GeneratedKeyHolder();
        
        namedParameterJdbcTemplate.update(sql, paramSource, keyholder, new String[]{"id"});
        return (Long) keyholder.getKeys().get("id");
    }

    public Optional<AuthUser> findByUsername(@NonNull String username) {
        var sql = "select * from authuser t where t.username = :username";
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("username", username);
        BeanPropertyRowMapper<AuthUser> rowMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);
        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, paramSource, rowMapper);
            return Optional.of(authUser);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
