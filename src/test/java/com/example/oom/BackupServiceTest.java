package com.example.oom;

import com.example.oom.service.BackupService;
import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BackupServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BackupService backupService;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> jdbcTemplate.getDataSource().getConnection());

    @Test
    @DataSet(provider = PlayersProvider.class, executeStatementsBefore = "TRUNCATE TABLE player RESTART IDENTITY")
    public void shouldInsertPlayers() {
        backupService.backup();
    }
}
