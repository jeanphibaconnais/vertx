package ex2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Server extends AbstractVerticle {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(Server.class.getName());
	}

	@Override
	public void start() {
		vertx.createHttpServer()
				.requestHandler(req -> req.response().end(new JsonObject()
						.put("message", "Hello Back end ")
						.encode()))
				.listen(8081);
	}
}