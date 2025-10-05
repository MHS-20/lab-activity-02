package hexagonalArchitecture.ports;

import io.vertx.core.json.JsonObject;

public interface EventPublisher {
    void publish(String topic, JsonObject event);
}
