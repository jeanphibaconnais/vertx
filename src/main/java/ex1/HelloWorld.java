package ex1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class HelloWorld extends AbstractVerticle {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(HelloWorld.class.getName());
	}

	@Override
	public void start() {
		vertx.createHttpServer()
				.requestHandler(req -> req.response().end("Hello Vert.x !"))
				.listen(8080);
	}
}