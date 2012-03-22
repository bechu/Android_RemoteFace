import socket
import sys
import primitive
import client
import motion
import time
import random
import threading

class eye(object):
    def __init__(self):
        self.edge = primitive.Circle()
        self.pupille = primitive.Circle()
        self.edge.radius = 120
        self.pupille.radius = 50
        self.edge.setColor(0,0,0)
        self.pupille.setColor(100,0,0)
        self.eyebrow = primitive.Line()
    def setPosition(self, x, y):
        self.edge.x = x
        self.edge.y = y
        self.pupille.x = x
        self.pupille.y = y
        self.eyebrow.x = x + 90
        self.eyebrow.y = y - 30
        self.eyebrow.x2 = x + 100
        self.eyebrow.y2 = y + 100
        self.eyebrow.thickness = 20
        self.eyebrow.setColor(0,0,255)
    def update(self):
        client.send("192.168.0.11", self.edge.getCommand())
        client.send("192.168.0.11", self.pupille.getCommand())
        client.send("192.168.0.11", self.eyebrow.getCommand())

class Mouth(object):
    def __init__(self):
        self.mouth = primitive.Oval()
        self.mouth.x = 50
        self.mouth.y = 350
        self.mouth.w = 100
        self.mouth.h = 30
        self.mouth.setColor(200,150,30)
    def update(self):
        client.send("192.168.0.11", self.mouth.getCommand())

class Face(threading.Thread):
    def __init__(self):
        self.left = eye()
        self.left.setPosition(250, 250)
        self.right = eye()
        self.right.setPosition(250, 550)
        self.running = True
        threading.Thread.__init__(self)
        self.daemon = True
        self.m = motion.Sinus(self.left.pupille, "x", 0, 10, 0.05, 0)
        self.m.start()
        self.m = motion.Sinus(self.right.pupille, "red", 0, 200, 5, 0)
        self.m.start()
        self.m = motion.Sinus(self.left.pupille, "blue", 0, 200, 5, 0)
        self.m.start()
        self.m = motion.Sinus(self.left.pupille, "y", 0, 20, 1.5, 0)
        self.m.start()
        self.mouth = Mouth()
        self.m = motion.Sinus(self.mouth.mouth, "w", 0, 20, 1.5, 0)
        self.m.start()
        self.m = motion.Sinus(self.mouth.mouth, "h", 0, 30, 10, 0)
        self.m.start()
        self.m = motion.Sinus(self.left.eyebrow, "x", 0, 30, 10, 0)
        self.m.start()
        self.m = motion.Sinus(self.right.eyebrow, "x", 0, 30, 10, 0)
        self.m.start()

    def run(self):
        while self.running:
            try:
                self.left.update()
                self.right.update()
                self.mouth.update()
                time.sleep(0.1)
            except Exception:
                print "error"

def main():
    face = Face()
    face.start()
    time.sleep(100)

if __name__ == '__main__':

    main()

# add 0 type(circle) color(200, 200, 200, 200) position(100, 100) radius(10) size(10, 10)
# remove 0
# update 0 move(200, 400)
