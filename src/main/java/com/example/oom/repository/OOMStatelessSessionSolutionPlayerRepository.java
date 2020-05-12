package com.example.oom.repository;

import com.example.oom.entity.Player;
import com.example.oom.entity.PlayerBackup;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;

@Repository
public class OOMStatelessSessionSolutionPlayerRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void backup() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        StatelessSession session = sessionFactory.openStatelessSession();
        session.beginTransaction();

        ScrollableResults players = session
                .createQuery("from Player")
                .scroll(ScrollMode.FORWARD_ONLY);

        while (players.next()) {
            Player player = (Player) players.get(0);
            session.insert(toPlayerBackup(player));
        }

        session.getTransaction().commit();
        session.close();
    }

    private PlayerBackup toPlayerBackup(Player player) {
        PlayerBackup backup = new PlayerBackup();

        backup.setId(player.getId());
        backup.setNick(player.getNick());
        backup.setUpdated(player.getUpdated());

        return backup;
    }
}
