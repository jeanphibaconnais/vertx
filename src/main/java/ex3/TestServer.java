package ex3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

public class TestServer extends AbstractVerticle {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(TestServer.class.getName());
	}

	@Override
	public void start() {
		vertx.createHttpServer()
				.requestHandler(req -> {
					vertx.eventBus().<String>send("service-test", "body to send", ar -> {
						if (ar.succeeded()) {
							String message = ar.result().body();
							req.response().end(message);
						} else {
							req.response().setStatusCode(500).end();
						}
					});

				})
				.listen(8080);
	}
}