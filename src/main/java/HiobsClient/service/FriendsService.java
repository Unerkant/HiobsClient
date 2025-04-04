package HiobsClient.service;

import HiobsClient.model.Friends;
import HiobsClient.repository.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Den 2.02.2024
 */

@Service
public class FriendsService {

    @Autowired
    private FriendsRepository freundeRepository;

    /**
     * Alle meine Freunde Laden
     * benutzt: IndexController
     * @return  null oder array
     */
    public Friends freundeLaden() {

        Iterable<Friends> result = freundeRepository.findAll();
        if (!result.iterator().hasNext()) {
            return null;
        }
        return (Friends) result;
    }

    /**
     * ???
     * @param token
     * @return
     */
    public Friends findeFreund(String token) {

        return freundeRepository.findByMeinentoken(token);
    }

  /*  Iterable<Token> result = tokenRepository.findAll();

        if (!result.iterator().hasNext()){
        return null;
    }
        return result.iterator().next().getMytoken();*/
}
