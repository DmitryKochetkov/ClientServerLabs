package mirea.dimedrol.lab5.repos;

import mirea.dimedrol.lab5.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    void deleteById(Integer id);
}
