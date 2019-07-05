package com.voldybot.haftamoney.verticle;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.voldybot.haftamoney.model.Article;
import com.voldybot.haftamoney.utils.Common;
import com.voldybot.haftamoney.utils.EnvData;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

public class RestServiceVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceVerticle.class);
    WebClient client;
    EnvData envData;
    @Override
    public void start(Future<Void> future) {
        LOGGER.info("Deploying RestServiceVerticle........");
        Router router = Router.router(vertx);
        initWebClient();
        // define routes
        router.get(Common.RestEndPoint.Exposed.TEST_GET).handler(this::getArticles);
        router.get(Common.RestEndPoint.Exposed.UPCOMING_IPOS).handler(this::getUpcomingIpos);

        HttpServerOptions options = new HttpServerOptions().setCompressionSupported( true );
        vertx.createHttpServer(options).requestHandler(router::accept).listen(config().getInteger("http.port", 8080),
                result -> {
                    if (result.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(result.cause());
                    }
                });
        LOGGER.info("Deployment Complete RestServiceVerticle");
        envData = new EnvData(Boolean.FALSE);
    }

    private void getArticles(RoutingContext routingContext) {
        String articleId = routingContext.request().getParam("id");
        Article article = new Article(articleId, "This is an intro to vertx", "Credits-baeldung", "01-02-2017", 1578);

        routingContext.response().putHeader("content-type", "application/json").setStatusCode(200)
                .end(Json.encodePrettily(article));
    }
    
    public void initWebClient() {
        WebClientOptions options = new WebClientOptions().setUserAgent("Hafta-Money/1.2.3");
        options.setKeepAlive(false);
        client = WebClient.create(vertx, options);
    }
    
    public void getUpcomingIpos(RoutingContext routingContext) {
        client
        .getAbs(envData.getBaseURL()+Common.RestEndPoint.IDEXClient.VERSION
                +Common.RestEndPoint.IDEXClient.UPCOMING_IPOS+Common.RestEndPoint.Q+envData.getPublicKey())
        .send(ar -> {
          if (ar.succeeded()) {
            HttpResponse<Buffer> response = ar.result();
            if (response.statusCode() == 200) {
                ReadContext ctx = JsonPath.parse(response.bodyAsString());
                String viewDatas = ctx.read("$.viewData").toString();
                sendSuccessResponse(response,routingContext,viewDatas);
            } else {
                LOGGER.error("response.statusCode() != 200:" + response.statusCode());
            }

          } else {
              LOGGER.error("Error:" + ar.cause().getMessage());
          }
        });
    }
    /**
     * Send success response to the client using routingContext
     * @param response
     * @param routingContext
     */
    private void sendSuccessResponse(HttpResponse<Buffer> response, RoutingContext routingContext, String body) {
        LOGGER.debug("Received response with status code " + response.statusCode() + " with body " + response.bodyAsString());
        LOGGER.debug("content-type: "+response.getHeader("content-type"));
        LOGGER.debug("content-encoding: "+response.getHeader("content-encoding"));
        routingContext.response().putHeader(Common.CONTENT_TYPE, Common.APPLICATION_JSON).setStatusCode(200)
        .end(body);
    }
}
