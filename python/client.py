import socket
import sys
PORT = 4444


def send(ip, msg):

    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except socket.error, msg:
        print "error ", msg

    try:
        sock.connect((ip, PORT))
    except socket.error, msg:
        print "error connect"

    sock.send(msg)
    sock.close()


