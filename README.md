<h1 align="center">Sonarqube Prometheus Exporter</h1>
<h4 align="center">Prometheus Exporter Plugin for SonarQube.</h4>

<p align="center">
  <a href="https://travis-ci.com/bheemreddy181/sonarqube-prometheus-exporter">
    <img src="https://travis-ci.com/bheemreddy181/sonarqube-prometheus-exporter.svg?branch=develop"
         alt="Build Status">
  </a>
  <a href="https://sonarcloud.io/summary/new_code?id=CURO-Financial-Technologies-Corp_sonarqube-prometheus-exporter">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=CURO-Financial-Technologies-Corp_sonarqube-prometheus-exporter&metric=alert_status" alt="SonarCloud Analysis">
  </a>
  <a href="https://github.com/CURO-Financial-Technologies-Corp/sonarqube-prometheus-exporter/releases">
    <img src="https://img.shields.io/github/release/CURO-Financial-Technologies-Corp/sonarqube-prometheus-exporter.svg" alt="Download">
  </a>
  <a href="https://github.com/CURO-Financial-Technologies-Corp/sonarqube-prometheus-exporter/blob/develop/LICENSE">
    <img src="https://img.shields.io/github/license/CURO-Financial-Technologies-Corp/sonarqube-prometheus-exporter.svg" alt="License">
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

1. Download [latest snapshot release](https://github.com/CURO-Financial-Technologies-Corp/sonarqube-prometheus-exporter/releases/tag/v1.0.0)
2. Drop `sonar-prometheus-exporter-1.0.0.jar` into `$SONARQUBE_HOME/extensions/plugins`.
3. Restart the SonarQube server.

## Usage

1. Configure which metrics you want to export under Administration &rarr; Configuration &rarr; General Settings &rarr; Prometheus Exporter
2. Add a scrape config to your Prometheus instance similar to this:
```yaml
scrape_configs:
  - job_name: 'sonarqube'
    metrics_path: '/api/prometheus/metrics'
    static_configs:
      - targets: ['localhost:9000']
```
3. Alternatively, point your HTTP client to `http://localhost:9000/api/prometheus/metrics`

## Screenshots
<p align="center">
  <img src="config-page.png" alt="Configuration Page" width="700px">
  <img src="api-call.png" alt="API Call" width="700px">
  <img src="grafana.png" alt="Example Grafana Dashboard" width="700px">
  <img src="grafana-additional-checks.png" alt="Grafana Dashboard with Additional Checks" width="700px">
</p>
