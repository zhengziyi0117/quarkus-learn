package org.example.entity;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int id;
    private String username;
    private String password;

    public static List<UserInfo> findAll(PgPool client) {
        RowSet<Row> rows = client.query("SELECT id, username, password FROM user_info ORDER BY id ASC").execute().await().indefinitely();
        List<UserInfo> list = new ArrayList<>();
        for (Row row : rows) {
            list.add(from(row));
        }
        return list;
    }

    public static Multi<UserInfo> findAllMulti(PgPool client) {
        return client.query("SELECT id, username, password FROM user_info ORDER BY id ASC").execute()
                .onItem().transformToMulti(RowSet::toMulti)
                .onItem().transform(UserInfo::from);
    }

    public static Uni<List<UserInfo>> findAllUni(PgPool client) {
        return client.query("SELECT id, username, password FROM user_info ORDER BY id ASC").execute()
                .onItem().transform(rows -> {
                    List<UserInfo> list = new ArrayList<>();
                    for (Row row : rows) {
                        list.add(from(row));
                    }
                    return list;
                });
    }


    private static UserInfo from(Row row) {
        return new UserInfo(row.getInteger("id"), row.getString("username"), row.getString("password"));
    }

}
