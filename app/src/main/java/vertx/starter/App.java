package vertx.starter;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class App {
    private HttpServer server;
    private Integer port = 8080;
    private MySQL mySQL;


    public Boolean getGreeting() {
        Vertx vertx = Vertx.vertx();
        mySQL = new MySQL();

        server = vertx.createHttpServer().requestHandler(req -> {
            mySQL.getAll();
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        });

        server.listen(this.port, res -> {
            if (res.succeeded()) {
                System.out.println("Server starting with http://localhost:" + this.port);
            } else {
                System.out.println("Start server fail");
            }
        });
        return true;
    }

    public static void main(String[] args) {
        new App().getGreeting();
    }
}
