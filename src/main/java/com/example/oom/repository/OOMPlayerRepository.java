package com.example.oom.repository;

import com.example.oom.entity.Player;
import com.example.oom.entity.PlayerBackup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class OOMPlayerRepository {

    @Value("${backup.batch.size}")
    private int backupBatchSize;

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public void backup() {
        Session session = em.unwrap(Session.class);

        session.beginTransaction();

        Long size = (Long) session.createQuery("select count(*) from Player").getSingleResult();

        if (size == 0) {
            log.info("Nothing to backup");
            return;
        }

        int limit = Math.min(size.intValue(), backupBatchSize);
        int offset = 0;

        log.info("Starting backup. [size={}], [batchSize={}]", size, limit);

        for (int i = 0; i < size; i += limit) {
            List<Player> players = (List<Player>) session
                    .createQuery("from Player")
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .list();

            List<PlayerBackup> backup = toPlayerBackup(players);
            backup.forEach(session::persist);

            offset += limit;
        }

        session.getTransaction().commit();
        session.close();

        log.info("Backup done. [size={}]", size);
    }

    private List<PlayerBackup> toPlayerBackup(List<Player> players) {
        return players
                .stream()
                .map(this::toPlayerBackup)
                .collect(Collectors.toList());
    }

    private PlayerBackup toPlayerBackup(Player player) {
        PlayerBackup backup = new PlayerBackup();

        backup.setId(player.getId());
        backup.setNick(player.getNick());
        backup.setUpdated(player.getUpdated());

        return backup;
    }
}
