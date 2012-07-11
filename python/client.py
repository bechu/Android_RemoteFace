import socket

PORT = 7777


def send(ip, msg):

    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except msg:
        print "error ", msg

    try:
        sock.connect((ip, PORT))
    except socket.error, msg:
        print "error connect"

    sock.send(msg)
    sock.close()


