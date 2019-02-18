package com.voldybot.haftamoney.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RestServiceVerticle());
    }

    @Override
    public void start(Future<Void> future) {
        LOGGER.info("Welcome to Vertx");
    }

    @Override
    public void stop() {
        LOGGER.info("Shutting down application");
    }
}
