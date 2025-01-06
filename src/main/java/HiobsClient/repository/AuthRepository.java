package HiobsClient.repository;

import HiobsClient.model.Auth;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Den 31.10.2024
 */

@Repository
public interface AuthRepository extends CrudRepository<Auth, Integer> {

    /**
     *  Benutzt von SperreController/@DeleteMapping("/sperreDelete")
     *  bei entsperrung das Account die spalte 'sperrdatum' auf NULL setzen
     *
     *  ACHTUNG: sperrdata → wird als NULL zugesendet
     *           WHERE id = 1; weil in H2 Datenbank in die Tabelle AUTH gibst es nur einen eintrag
     *           und beim Einloggen wird automatisch auf ID 1 gespeichert
     *
     * @param sperrdate
     * @return 1
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE AUTH SET sperrdatum = :sperrdate WHERE id = 1", nativeQuery = true)
    Integer updateSperre( Long sperrdate );

}
