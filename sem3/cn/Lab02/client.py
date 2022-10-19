#!/bin/python3
import socket

HOST = input("IP (defaults to 127.0.0.1) = ")
PORT = input("PORT (defaults to 1234) = ")

if HOST == "": HOST = "127.0.0.1"
if PORT == "": PORT = 1234
else: PORT = int(PORT)

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    string = input("str = ")
    s.send(string.encode())
    data = s.recv(1024).decode()
    print(f"The reversed string is {data}")
