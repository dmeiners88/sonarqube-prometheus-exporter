/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package de.dmeiners.sonar.prometheus;

import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;

import java.util.List;
import java.util.stream.Collectors;

public class PrometheusExporterPlugin implements Plugin {

    @Override
    public void define(Context context) {

        List<PropertyDefinition> properties = PrometheusWebService.SUPPORTED_METRICS.stream()
            .map(metric -> PropertyDefinition.builder(PrometheusWebService.CONFIG_PREFIX + metric.getKey())
                .name(String.format("Export \"%s\" as Prometheus metric.", metric.getName()))
                .description(metric.getDescription())
                .type(PropertyType.BOOLEAN)
                .defaultValue(Boolean.TRUE.toString())
                .build())
            .collect(Collectors.toList());

        context.addExtensions(properties);
        context.addExtension(PrometheusWebService.class);
    }
}
