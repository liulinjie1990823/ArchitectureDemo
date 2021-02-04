#!/usr/bin/env python3

import os
import socket


# 获取内网ip
def get_host_ip():
  try:
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect(('8.8.8.8', 80))
    ip = s.getsockname()[0]
  finally:
    s.close()

  return ip


print("本地内网ip:" + get_host_ip())
os.system("adb shell settings put global http_proxy " + get_host_ip() + ":8888")
print("已经通过adb设置了android的代理ip:")
os.system("adb shell settings get global http_proxy")
