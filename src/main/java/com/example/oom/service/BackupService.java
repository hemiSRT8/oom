package com.example.oom.service;

import com.example.oom.entity.Player;
import com.example.oom.entity.PlayerBackup;
import com.example.oom.repository.PlayerBackupRepository;
import com.example.oom.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BackupService {

    @Autowired
    private PlayerRepository repository;

    @Autowired
    private PlayerBackupRepository backupRepository;

    private static final int PAGE_SIZE = 10000;

    @Transactional
    public void backup() {
        Pageable request = PageRequest.of(0, PAGE_SIZE);
        Page<Player> page = repository.findAll(request);

        //todo remove
        int count = 0;

        while (!page.isEmpty()) {
            save(page.get());
            request = request.next();
            page = repository.findAll(request);

            //todo remove
            count++;
            log.info(String.format("!!!count %s", count));
        }
    }

    private void save(Stream<Player> players) {
        backupRepository.saveAll(toPlayerBackup(players));
    }

    private List<PlayerBackup> toPlayerBackup(Stream<Player> players) {
        return players
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
