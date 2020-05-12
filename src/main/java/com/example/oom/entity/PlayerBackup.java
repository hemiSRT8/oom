package com.example.oom.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "player_backup")
public class PlayerBackup {

    @Id
    private Long id;

    @Column(name = "nick")
    private String nick;

    @Column(name = "updated")
    private LocalDateTime updated;
}
