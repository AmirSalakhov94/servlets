package tech.itpark.proggerhub.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tech.itpark.proggerhub.dto.enums.TypeRestoreIssue;
import tech.itpark.proggerhub.exception.DataAccessException;
import tech.itpark.proggerhub.repository.model.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class AuthRepository {
    private static final String LOGIN = "login";
    private static final String TYPE_RESTORE_ISSUE = "type_restore_issue";
    private static final String VALUE_RESTORE_ISSUE = "value_restore_issue";
    private static final String ROLES = "roles";
    private static final String ID = "id";
    private static final String PASSWORD = "password";

    private final DataSource ds;

    public long save(UserModel model) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                    INSERT INTO users(login, password, type_restore_issue, value_restore_issue) 
                                    VALUES(?, ?, ?, ?) RETURNING id;
                                """
                )
        ) {
            var index = 0;
            stmt.setString(++index, model.getLogin());
            stmt.setString(++index, model.getHash());
            stmt.setString(++index, model.getTypeRestoreIssue().name());
            stmt.setString(++index, model.getValueOnRestoreIssue());
            try (
                    final var rs = stmt.executeQuery();
            ) {
                if (!rs.next()) {
                    throw new DataAccessException("no returning id");
                }
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public long replacePassword(String login, String newPassword) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """ 
                                UPDATE users SET password = ?, modified = CURRENT_TIMESTAMP 
                                WHERE login = ? RETURNING id;
                                """
                )
        ) {
            var index = 0;
            stmt.setString(++index, newPassword);
            stmt.setString(++index, login);
            try (
                    final var rs = stmt.executeQuery();
            ) {
                if (!rs.next()) {
                    throw new DataAccessException("no returning id");
                }
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Optional<UserWithIdModel> findByLogin(String login) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                SELECT id, login, password 
                                FROM users 
                                WHERE login = ?;
                                """)
        ) {
            var index = 0;
            stmt.setString(++index, login);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(
                    new UserWithIdModel(rs.getLong(ID), rs.getString(LOGIN), rs.getString(PASSWORD))
            ) : Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Optional<UserWithRestoreModel> findUserWithRestoreByLogin(String login) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                SELECT login, password, type_restore_issue, value_restore_issue 
                                FROM users 
                                WHERE login = ?;
                                """)
        ) {
            var index = 0;
            stmt.setString(++index, login);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(new UserWithRestoreModel(rs.getString(LOGIN),
                    TypeRestoreIssue.valueOf(rs.getString(TYPE_RESTORE_ISSUE)), rs.getString(VALUE_RESTORE_ISSUE)))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void save(UserTokenModel model) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        "INSERT INTO tokens(user_id, token) VALUES(?, ?);"
                )
        ) {
            var index = 0;
            stmt.setLong(++index, model.getId());
            stmt.setString(++index, model.getToken());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Optional<UserAuthModel> findByToken(String token) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        // FIXME: вопросы инвалидации (кто-то должен вычищать старые токены)
                        """
                                  SELECT u.id, u.roles
                                  FROM users u
                                  JOIN tokens t on u.id = t.user_id
                                  WHERE t.token = ?;
                                """
                )
        ) {
            int index = 0;
            stmt.setString(++index, token);
            try (
                    final var rs = stmt.executeQuery();
            ) {
                return !rs.next() ? Optional.empty() : Optional.of(
                        new UserAuthModel(
                                rs.getLong(ID),
                                Set.of((String[]) rs.getArray(ROLES).getArray())
                        )
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void saveRestoreKey(String login, String key) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                    INSERT INTO users_password_restore(login, key, is_valid) VALUES(?, ?, ?);
                                """
                )
        ) {
            var index = 0;
            stmt.setString(++index, login);
            stmt.setString(++index, key);
            stmt.setBoolean(++index, true);
            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public boolean restoreKeyExists(String login, String key) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                  SELECT 1 FROM users_password_restore
                                  WHERE login = ? AND key = ? AND is_valid IS TRUE;
                                """
                )
        ) {
            int index = 0;
            stmt.setString(++index, login);
            stmt.setString(++index, key);
            try (
                    final var rs = stmt.executeQuery();
            ) {
                return rs.next() && rs.getInt(1) == 1;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void updateRestoreKey(String login, String key, long interval) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """ 
                                UPDATE users_password_restore SET is_valid = false, modified = NOW()
                                WHERE login = ? AND (key = ? OR created < ?);
                                """
                )
        ) {
            Timestamp t = new Timestamp(System.currentTimeMillis() - interval);
            var index = 0;
            stmt.setString(++index, login);
            stmt.setString(++index, key);
            stmt.setTimestamp(++index, t);
            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Integer getUserNumberAttempts(String login) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                  SELECT num FROM user_number_attempts
                                  WHERE login = ?;
                                """
                )
        ) {
            int index = 0;
            stmt.setString(++index, login);
            try (
                    final var rs = stmt.executeQuery();
            ) {
                return rs.next() ? rs.getInt(1) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public int saveUserNumberAttempts(String login) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                    INSERT INTO user_number_attempts(login, num) VALUES(?, 1);
                                """
                )
        ) {
            int index = 0;
            stmt.setString(++index, login);
            stmt.execute();
            return 1;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void updateUserNumberAttempts(String login, Integer num) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """ 
                                UPDATE user_number_attempts SET num = ?, modified = NOW()
                                WHERE login = ?;
                                """
                )
        ) {
            var index = 0;
            stmt.setInt(++index, num);
            stmt.setString(++index, login);
            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void clearOldUserNumberAttempts(long interval) {
        try (
                final var conn = ds.getConnection();
                final var stmt = conn.prepareStatement(
                        """
                                DELETE FROM user_number_attempts WHERE modified < ?;
                                """
                )
        ) {
            Timestamp t = new Timestamp(System.currentTimeMillis() - interval);
            int index = 0;
            stmt.setTimestamp(++index, t);
            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
