<h1 align="center">Sonarqube Prometheus Exporter</h1>
<h4 align="center">Prometheus Exporter Plugin for SonarQube.</h4>

<p align="center">
  <a href="https://travis-ci.com/dmeiners88/sonarqube-prometheus-exporter">
    <img src="https://travis-ci.com/dmeiners88/sonarqube-prometheus-exporter.svg?branch=develop"
         alt="Build Status">
  </a>
  <a href="https://sonarcloud.io/dashboard?id=de.dmeiners%3Asonar-prometheus-exporter">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=de.dmeiners%3Asonar-prometheus-exporter&metric=alert_status" alt="SonarCloud Analysis">
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Download-1.0.0-blue.svg" alt="Download">
  </a>
  <a href="https://github.com/dmeiners88/sonarqube-prometheus-exporter/blob/develop/LICENSE">
    <img src="https://img.shields.io/github/license/dmeiners88/sonarqube-prometheus-exporter.svg" alt="License">
  </a>
  <a href="https://semver.org/spec/v2.0.0.html">
    <img src="https://img.shields.io/badge/semver-2.0.0-brightgreen.svg" alt="Semantic Versioning">
  </a>
</p>

<p align="center">
  <a href="#features">Features</a> •
  <a href="#requirements">Requirements</a> •
  <a href="#installation">Installation</a> •
  <a href="#usage">Usage</a> •
  <a href="#screenshots">Screenshots</a>
</p>

## Features
* Configure which metrics to export

## Requirements
* SonarQube 7.x

## Installation

1. Drop `sonar-prometheus-exporter-<VERSION>.jar` into `$SONARQUBE_HOME/extensions/plugins`.
2. Restart the SonarQube server.

> TODO: Automatically create and link to current GitHub release.

```
GET /api/prometheus/metrics HTTP/1.1

# HELP sonarqube_sqale_index Total effort (in days) to fix all the issues on the component and therefore to comply to all the requirements.
# TYPE sonarqube_sqale_index gauge
sonarqube_sqale_index{key="de.dmeiners.mapping:mapping-api",name="Mapping Library - API",} 1061.0
sonarqube_sqale_index{key="de.dmeiners.mapping:mapping-impl-java",name="Mapping Library - Java Implementation",} 448.0
# HELP sonarqube_code_smells Code Smells
# TYPE sonarqube_code_smells gauge
sonarqube_code_smells{key="de.dmeiners.mapping:mapping-api",name="Mapping Library - API",} 248.0
sonarqube_code_smells{key="de.dmeiners.mapping:mapping-impl-java",name="Mapping Library - Java Implementation",} 128.0
# HELP sonarqube_vulnerabilities Vulnerabilities
# TYPE sonarqube_vulnerabilities gauge
sonarqube_vulnerabilities{key="de.dmeiners.mapping:mapping-api",name="Mapping Library - API",} 0.0
sonarqube_vulnerabilities{key="de.dmeiners.mapping:mapping-impl-java",name="Mapping Library - Java Implementation",} 3.0
# HELP sonarqube_coverage Coverage by tests
# TYPE sonarqube_coverage gauge
sonarqube_coverage{key="de.dmeiners.mapping:mapping-api",name="Mapping Library - API",} 44.4
sonarqube_coverage{key="de.dmeiners.mapping:mapping-impl-java",name="Mapping Library - Java Implementation",} 76.8
# HELP sonarqube_bugs Bugs
# TYPE sonarqube_bugs gauge
sonarqube_bugs{key="de.dmeiners.mapping:mapping-api",name="Mapping Library - API",} 0.0
sonarqube_bugs{key="de.dmeiners.mapping:mapping-impl-java",name="Mapping Library - Java Implementation",} 0.0
```

## Usage

## Screenshots
![Configuration Page](config-page.png "Configuration Page")

![API Call](api-call.png "API Call")

![Example Grafana Dashboard](grafana.png "Example Grafana Dashboard")
