import primitive
import client
import motion
import time
import threading

IP = "192.168.1.2"

class eye(object):
    def __init__(self):
        self.edge = primitive.Circle()
        self.edge.fill = 0
        self.edge.thickness = 10
        self.pupille = primitive.Circle()
        self.edge.radius = 60
        self.pupille.radius = 25
        self.edge.setColor(0,0,0)
        self.pupille.setColor(100,0,0)
        self.eyebrow = primitive.Line()
    def setPosition(self, x, y):
        self.edge.x = x
        self.edge.y = y
        self.pupille.x = x
        self.pupille.y = y
        self.eyebrow.x = x + 45
        self.eyebrow.y = y - 15
        self.eyebrow.x2 = x + 50
        self.eyebrow.y2 = y + 50
        self.eyebrow.thickness = 20
        self.eyebrow.setColor(0,0,255)
    def update(self):
        client.send(IP, self.edge.getCommand())
        client.send(IP, self.pupille.getCommand())
        client.send(IP, self.eyebrow.getCommand())

class Mouth(object):
    def __init__(self):
        self.mouth = primitive.Oval()
        self.mouth.x = 40
        self.mouth.y = 220
        self.mouth.w = 50
        self.mouth.h = 30
        self.mouth.setColor(200,150,30)
    def update(self):
        client.send(IP, self.mouth.getCommand())

class Face(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.running = True
        self.daemon = True
        self.left = eye()
        self.left.setPosition(150, 150)
        self.right = eye()
        self.right.setPosition(150, 350)
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
            self.left.update()
            self.right.update()
            self.mouth.update()
            time.sleep(0.1)

def main():
    face = Face()
    face.start()
    time.sleep(100)

if __name__ == '__main__':

    main()

# add 0 type(circle) color(200, 200, 200, 200) position(100, 100) radius(10) size(10, 10)
# remove 0
# update 0 move(200, 400)
