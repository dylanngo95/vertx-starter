package vertx.starter;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import java.sql.*;

public class MySQL {

    static final String DB_URL = "jdbc:mysql://localhost/tubee";
    static final String USER = "root";
    static final String PASS = "123456";

    public void countYoutube(Vertx vertx) {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3306)
                .setHost("127.0.0.1")
                .setDatabase("tubee")
                .setUser("root")
                .setPassword("123456");

        // Pool options
        PoolOptions poolOptions = new PoolOptions().setMaxSize(100);

        // Create the client pool
        MySQLPool client = MySQLPool.pool(vertx, connectOptions, poolOptions);

        // A simple query
        client
                .query("SELECT * FROM youtube LIMIT 10")
                .execute(ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> result = ar.result();
                        System.out.println("Got " + result.size() + " rows ");
                    } else {
                        System.out.println("Failure: " + ar.cause().getMessage());
                    }

                    // Now close the pool
                    client.close();
                });
    }

    public void getAll() {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM youtube LIMIT 10");) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.println("ID: " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
