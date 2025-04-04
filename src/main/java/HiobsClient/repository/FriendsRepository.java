package HiobsClient.repository;

import HiobsClient.model.Friends;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Den 2.02.2024
 */

@Repository
public interface FriendsRepository extends CrudRepository<Friends, Integer> {

    /**
     * Meinen Token finden
     *
     * @param token
     * @return
     */
    Friends findByMeinentoken(String token);

    // mach was
}
