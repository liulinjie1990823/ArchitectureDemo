#!/usr/bin/env python3

import os
import socket

print("开始执行任务")
os.system(
    "./gradlew lib-utils:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-base-event:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-image-loader:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-image-select:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-statelayout:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-statusbar:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-swipeback:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-scrollable:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-universalAdapter:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-webview:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-jpeg-turbo-utils:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-record:publishReleasePublicationToHbhNexusRepository")

os.system(
    "./gradlew lib-net:publishReleasePublicationToHbhNexusRepository")

os.system(
    "./gradlew lib-tracker:publishReleasePublicationToHbhNexusRepository")

os.system(
    "./gradlew lib-base:publishReleasePublicationToHbhNexusRepository")

os.system(
    "./gradlew lib-base-compiler:publishMavenPublicationToHbhNexusRepository")

os.system(
    "./gradlew lib-jump-annotation:publishMavenPublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-jump-api:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-jump-compiler:publishMavenPublicationToHbhNexusRepository")

os.system(
    "./gradlew lib-mvp-annotation:publishMavenPublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-mvp-compiler:publishMavenPublicationToHbhNexusRepository")

print("----------------socialization start------------------")
os.system(
    "./gradlew lib-socialization:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-socialization-qq:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-socialization-sina:publishReleasePublicationToHbhNexusRepository")
os.system(
    "./gradlew lib-socialization-wechat:publishReleasePublicationToHbhNexusRepository")
print("----------------socialization end------------------")


