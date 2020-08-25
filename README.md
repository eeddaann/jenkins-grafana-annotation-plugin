# grafana-annotation

[![Build Status](https://ci.jenkins.io/job/Plugins/job/grafana-annotation-plugin/job/master/badge/icon)](https://ci.jenkins.io/job/Plugins/job/grafana-annotation-plugin/job/master/)
[![Contributors](https://img.shields.io/github/contributors/jenkinsci/grafana-annotation-plugin.svg)](https://github.com/jenkinsci/grafana-annotation-plugin/graphs/contributors)
[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/grafana-annotation.svg)](https://plugins.jenkins.io/grafana-annotation)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/grafana-annotation-plugin.svg?label=changelog)](https://github.com/jenkinsci/grafana-annotation-plugin/releases/latest)
[![Jenkins Plugin Installs](https://img.shields.io/jenkins/plugin/i/grafana-annotation.svg?color=blue)](https://plugins.jenkins.io/grafana-annotation)

## Introduction

This plugin sends annotations to Grafana in order to improve the observability of Jenkins.
Combination of this plugin and the CPU/memory of the Jenkins instance may reveal which jobs consumes most of the resources, explain peaks in Jenkins' cpu usage, etc. 

## Getting started

To configure this plugin, navigate in jenkins to "configure system" and look under "Grafana Annotaions Plugin" section.
Fill the following fields:
- **Grafana url** - for example: *http://127.0.0.1:3000*
- **Grafana Authentication** 
    - if you plan to authenticate with basic authentication, enter: *user:pass*, for example: *admin:1234*
    - if you plan to authenticate with API key, just paste the key, for example: *eyJrIjoiT0tTcG1pUlY2RnVKZTFVaDFsNFZXdE9ZWmNrMkZYbk*

## Issues

Report issues and enhancements in the [Jenkins issue tracker](https://issues.jenkins-ci.org/).

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE.md)

