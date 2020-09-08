#!/usr/bin/env python3

import os
import socket

print("开始执行任务")
os.system(
    "./gradlew lib-utils:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-base-event:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-image-loader:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-image-select:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-statelayout:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-statusbar:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-swipeback:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-scrollable:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-universalAdapter:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-webview:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-jpeg-turbo-utils:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-record:publishReleasePublicationToLocalRepoRepository")

os.system(
    "./gradlew lib-net:publishReleasePublicationToLocalRepoRepository")

os.system(
    "./gradlew lib-tracker:publishReleasePublicationToLocalRepoRepository")

os.system(
    "./gradlew lib-base:publishReleasePublicationToLocalRepoRepository")

os.system(
    "./gradlew lib-base-compiler:publishMavenPublicationToLocalRepoRepository")

os.system(
    "./gradlew lib-jump-annotation:publishMavenPublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-jump-api:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-jump-compiler:publishMavenPublicationToLocalRepoRepository")

os.system(
    "./gradlew lib-mvp-annotation:publishMavenPublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-mvp-compiler:publishMavenPublicationToLocalRepoRepository")

print("----------------socialization start------------------")
os.system(
    "./gradlew lib-socialization:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-socialization-qq:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-socialization-sina:publishReleasePublicationToLocalRepoRepository")
os.system(
    "./gradlew lib-socialization-wechat:publishReleasePublicationToLocalRepoRepository")
print("----------------socialization end------------------")


os.system(
    "./gradlew component-service:publishReleasePublicationToLocalRepoRepository")

os.system(
    "./gradlew app-application:publishDebugPublicationToLocalRepoRepository")

os.system(
    "./gradlew app-loading:publishReleasePublicationToLocalRepoRepository")

