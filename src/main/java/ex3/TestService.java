package ex3;

import ex2.TestServer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

public class TestService extends AbstractVerticle {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(TestService.class.getName());
	}

	@Override
	public void start() {
		WebClient client = WebClient.create(vertx);

		vertx.eventBus().consumer("service-test", msg -> {
			HttpRequest<Buffer> request = client.get(8081, "localhost", "/");

			// Le send ne va pas attendre la réponse mais transmettre un évènement non bloquant.
			request.send(ar -> { // async result contenant une réponse ou une error.
				if (ar.succeeded()) {
					String hello = ar.result().bodyAsJsonObject().getString("messageTest");
					msg.reply(hello);
				} else {
					msg.fail(0, "erreur");
				}
			});
		});
	}
}
