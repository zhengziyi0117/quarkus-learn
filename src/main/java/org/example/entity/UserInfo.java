package org.example.entity;

import com.google.common.collect.Lists;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int id;
    private String username;
    private String password;

    public static List<UserInfo> findAll(PgPool client) {
        RowSet<Row> rows = client.query("SELECT id, username, password FROM user_info ORDER BY id ASC").execute().await().indefinitely();
        List<UserInfo> list = Lists.newArrayList();
        for (Row row : rows) {
            list.add(from(row));
        }
        return list;
    }

    private static UserInfo from(Row row) {
        return new UserInfo(row.getInteger("id"), row.getString("username"), row.getString("password"));
    }

}
