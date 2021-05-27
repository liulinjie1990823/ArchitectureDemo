#!/usr/bin/env python3

import os
import socket

print("开始执行任务")
os.system(
    "../gradlew lib-utils:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-base-event:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-image-loader:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-image-select:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-statelayout:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-statusbar:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-swipeback:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-scrollable:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-universalAdapter:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-webview:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-jpeg-turbo-utils:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-record:publishReleasePublicationToRepoDirRepository")

os.system(
    "../gradlew lib-net:publishReleasePublicationToRepoDirRepository")

os.system(
    "../gradlew lib-tracker:publishReleasePublicationToRepoDirRepository")

os.system(
    "../gradlew lib-base:publishReleasePublicationToRepoDirRepository")

os.system(
    "../gradlew lib-base-compiler:publishMavenPublicationToRepoDirRepository")

os.system(
    "../gradlew lib-jump-annotation:publishMavenPublicationToRepoDirRepository")
os.system(
    "../gradlew lib-jump-api:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-jump-compiler:publishMavenPublicationToRepoDirRepository")

os.system(
    "../gradlew lib-mvp-annotation:publishMavenPublicationToRepoDirRepository")
os.system(
    "../gradlew lib-mvp-compiler:publishMavenPublicationToRepoDirRepository")

print("----------------socialization start------------------")
os.system(
    "../gradlew lib-socialization:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-socialization-qq:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-socialization-sina:publishReleasePublicationToRepoDirRepository")
os.system(
    "../gradlew lib-socialization-wechat:publishReleasePublicationToRepoDirRepository")
print("----------------socialization end------------------")


os.system(
    "../gradlew component-service:publishReleasePublicationToRepoDirRepository")

os.system(
    "../gradlew app-application:publishDebugPublicationToRepoDirRepository")

os.system(
    "../gradlew app-loading:publishReleasePublicationToRepoDirRepository")

