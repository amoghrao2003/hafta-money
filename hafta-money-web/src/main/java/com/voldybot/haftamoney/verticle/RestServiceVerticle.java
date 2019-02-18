package com.voldybot.haftamoney.verticle;

import com.voldybot.haftamoney.model.Article;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class RestServiceVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceVerticle.class);
    @Override
    public void start(Future<Void> future) {
        LOGGER.info("Deploying RestServiceVerticle........");
        Router router = Router.router(vertx);
        router.get("/api/testing/articles/article/:id")
                .handler(this::getArticles);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(result.cause());
                    }
                });
        LOGGER.info("Deployment Complete RestServiceVerticle");
    }

    private void getArticles(RoutingContext routingContext) {
        String articleId = routingContext.request()
                .getParam("id");
        Article article = new Article(articleId, "This is an intro to vertx", "baeldung", "01-02-2017", 1578);

        routingContext.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(Json.encodePrettily(article));
    }

}

