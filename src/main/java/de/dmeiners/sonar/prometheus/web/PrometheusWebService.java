package de.dmeiners.sonar.prometheus.web;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.common.TextFormat;
import org.sonar.api.server.ws.WebService;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class PrometheusWebService implements WebService {

    @Override
    public void define(Context context) {

        Gauge bugs = Gauge.build()
            .name("sonar_test_bugs")
            .help("bug count for project")
            .labelNames("project_key")
            .register();

        NewController controller = context.createController("api/prometheus");
        controller.setDescription("Prometheus Exporter");

        controller.createAction("metrics")
            .setHandler((request, response) -> {

                // TODO: set to actual values
                bugs.labels("my.project.key")
                    .set(12);

                OutputStream output = response.stream()
                    .setMediaType(TextFormat.CONTENT_TYPE_004)
                    .setStatus(200)
                    .output();

                try (OutputStreamWriter writer = new OutputStreamWriter(output)) {

                    TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
                }
            });

        controller.done();
    }
}
