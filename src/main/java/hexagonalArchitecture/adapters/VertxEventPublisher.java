package hexagonalArchitecture.adapters;

import hexagonalArchitecture.ports.EventPublisher;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class VertxEventPublisher implements EventPublisher {
    private final EventBus bus;
    public VertxEventPublisher(Vertx vertx) { this.bus = vertx.eventBus(); }

    @Override
    public void publish(String topic, JsonObject event) {
        bus.publish(topic, event);
    }
}
