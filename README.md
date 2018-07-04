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

## Usage

> TODO: Describe usage

## Screenshots
<p align="center">
  <img src="config-page.png" alt="Configuration Page" width="700px">
  <img src="api-call.png" alt="API Call" width="700px">
  <img src="grafana.png" alt="Example Grafana Dashboard" width="700px">
</p>
