package ex2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

public class TestServer extends AbstractVerticle {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(TestServer.class.getName());
	}

	@Override
	public void start() {
		WebClient client = WebClient.create(vertx);

		vertx.createHttpServer()
				.requestHandler(req -> {
					HttpRequest<Buffer> request = client.get(8081, "localhost", "/");

					// Le send ne va pas attendre la réponse mais transmettre un évènement non bloquant.
					request.send(ar -> {
						if (ar.succeeded()) {
							String message = ar.result().bodyAsJsonObject().getString("message");
							req.response().end(message);
						} else {
							req.response().setStatusCode(500).end();
						}
					});
				})
				.listen(8080);
	}
}