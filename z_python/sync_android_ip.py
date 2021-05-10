#!/usr/bin/env python3

import os
import sys
import socket

type = sys.argv[1]

print("sys.argv",sys.argv)
print("type",type)

# 获取内网ip
def get_host_ip():
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(('8.8.8.8', 80))
        ip = s.getsockname()[0]
    finally:
        s.close()
    return ip


if type == "1":
    print("本地内网ip:" + get_host_ip())
    os.system("adb shell settings put global http_proxy " + get_host_ip() + ":8888")
    print("已经通过adb设置了android的代理ip:")
    os.system("adb shell settings get global http_proxy")
else:
    os.system("adb shell settings put global http_proxy :0")
