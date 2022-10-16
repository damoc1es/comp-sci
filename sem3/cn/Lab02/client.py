#!/bin/python3
import socket

HOST = "127.0.0.1"
PORT = 1234

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    string = input("str = ")
    s.send(string.encode())
    data = s.recv(1024).decode()
    print(f"The reversed string is {data}")
