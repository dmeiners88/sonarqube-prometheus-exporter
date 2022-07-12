package de.dmeiners.sonar.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.common.TextFormat;
import org.sonar.api.config.Configuration;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.server.ws.WebService;
import org.sonarqube.ws.Components;
import org.sonarqube.ws.Measures;
import org.sonarqube.ws.client.WsClient;
import org.sonarqube.ws.client.WsClientFactories;
import org.sonarqube.ws.client.components.SearchRequest;
import org.sonarqube.ws.client.measures.ComponentRequest;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class PrometheusWebService implements WebService {

    static final Set<Metric<?>> SUPPORTED_METRICS = new HashSet<>();
    static final String CONFIG_PREFIX = "prometheus.export.";
    private static final String METRIC_PREFIX = "sonarqube_";

    private final Configuration configuration;
    private final Map<String, Gauge> gauges = new HashMap<>();
    private final Set<Metric<?>> enabledMetrics = new HashSet<>();

    static {

        SUPPORTED_METRICS.add(CoreMetrics.BUGS);
        SUPPORTED_METRICS.add(CoreMetrics.VULNERABILITIES);
        SUPPORTED_METRICS.add(CoreMetrics.CODE_SMELLS);
        SUPPORTED_METRICS.add(CoreMetrics.COVERAGE);
        SUPPORTED_METRICS.add(CoreMetrics.TECHNICAL_DEBT);
        SUPPORTED_METRICS.add(CoreMetrics.COMPLEXITY);
        SUPPORTED_METRICS.add(CoreMetrics.BLOCKER_VIOLATIONS);
        SUPPORTED_METRICS.add(CoreMetrics.CRITICAL_VIOLATIONS);
        SUPPORTED_METRICS.add(CoreMetrics.MAJOR_VIOLATIONS);
        SUPPORTED_METRICS.add(CoreMetrics.MINOR_VIOLATIONS);
        SUPPORTED_METRICS.add(CoreMetrics.INFO_VIOLATIONS);
        SUPPORTED_METRICS.add(CoreMetrics.DUPLICATED_LINES_DENSITY);
        SUPPORTED_METRICS.add(CoreMetrics.DUPLICATED_BLOCKS);
        SUPPORTED_METRICS.add(CoreMetrics.DUPLICATED_LINES);
        SUPPORTED_METRICS.add(CoreMetrics.DUPLICATED_FILES);
        SUPPORTED_METRICS.add(CoreMetrics.OPEN_ISSUES);
        SUPPORTED_METRICS.add(CoreMetrics.NCLOC);
        SUPPORTED_METRICS.add(CoreMetrics.COGNITIVE_COMPLEXITY);

    }

    public PrometheusWebService(Configuration configuration) {

        this.configuration = configuration;
    }

    @Override
    public void define(Context context) {

        updateEnabledMetrics();
        updateEnabledGauges();

        NewController controller = context.createController("api/prometheus");
        controller.setDescription("Prometheus Exporter");

        controller.createAction("metrics")
                .setHandler((request, response) -> {

                    updateEnabledMetrics();
                    updateEnabledGauges();

                    if (!this.enabledMetrics.isEmpty()) {

                        WsClient wsClient = WsClientFactories.getLocal().newClient(request.localConnector());

                        List<Components.Component> projects = getProjects(wsClient);
                        projects.forEach(project -> {

                            Measures.ComponentWsResponse wsResponse = getMeasures(wsClient, project);

                            wsResponse.getComponent().getMeasuresList().forEach(measure -> {

                                if (this.gauges.containsKey(measure.getMetric())) {
                                    this.gauges.get(measure.getMetric()).labels(project.getKey(), project.getName()).set(Double.valueOf(measure.getValue()));
                                }
                            });
                        });
                    }

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

    private void updateEnabledMetrics() {

        Map<Boolean, List<Metric<?>>> byEnabledState = SUPPORTED_METRICS.stream()
                .collect(Collectors.groupingBy(metric -> this.configuration.getBoolean(CONFIG_PREFIX + metric.getKey()).orElse(false)));

        this.enabledMetrics.clear();

        if (nonNull(byEnabledState.get(true))) {
            this.enabledMetrics.addAll(byEnabledState.get(true));
        }
    }

    private void updateEnabledGauges() {

        CollectorRegistry.defaultRegistry.clear();

        this.enabledMetrics.forEach(metric -> gauges.put(metric.getKey(), Gauge.build()
                .name(METRIC_PREFIX + metric.getKey())
                .help(metric.getDescription())
                .labelNames("key", "name")
                .register()));
    }

    private Measures.ComponentWsResponse getMeasures(WsClient wsClient, Components.Component project) {

        List<String> metricKeys = this.enabledMetrics.stream()
                .map(Metric::getKey)
                .collect(Collectors.toList());

        return wsClient.measures().component(new ComponentRequest()
                .setComponent(project.getKey())
                .setMetricKeys(metricKeys));
    }

    private List<Components.Component> getProjects(WsClient wsClient) {

        return wsClient.components().search(new SearchRequest()
                .setQualifiers(Collections.singletonList(Qualifiers.PROJECT))
                .setPs("500"))
                .getComponentsList();
    }
}
