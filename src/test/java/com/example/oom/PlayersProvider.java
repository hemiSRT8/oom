package com.example.oom;

import com.github.database.rider.core.api.dataset.DataSetProvider;
import com.github.database.rider.core.dataset.builder.ColumnBuilder;
import com.github.database.rider.core.dataset.builder.DataSetBuilder;
import org.dbunit.dataset.IDataSet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PlayersProvider implements DataSetProvider {

    @Override
    public IDataSet provide() {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        LocalDateTime dateTimePoint = LocalDateTime
                .now()
                .withMonth(1)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);

        ColumnBuilder builder = new DataSetBuilder()
                .table("player")
                .columns("nick", "updated");

        for (int i = 0; i < 10; i++) {
            LocalDateTime updated = dateTimePoint
                    .plusDays(random.nextInt(27))
                    .plusHours(random.nextInt(24))
                    .plusMinutes(random.nextInt(60))
                    .plusSeconds(random.nextInt(60));

            builder.values("toma", updated.format(formatter));
        }

        return builder.build();
    }
}
