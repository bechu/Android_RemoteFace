

NEXTID = 0

class Primitive(object):
    def __init__(self):
        global NEXTID
        self.id = NEXTID
        NEXTID = NEXTID+1
        self.red = 0
        self.green = 0
        self.blue = 0
        self.alpha = 255
        self.fill = 1
        self.thickness = 1

    def setColor(self, r, g, b):
        self.red = r
        self.green = g
        self.blue = b

    def pparams(self):
        return "thickness(%d) fill(%d) color(%d,%d,%d,%d) " % (self.thickness, self.fill, self.alpha, self.red, self.green, self.blue)

class Circle(Primitive):
    def __init__(self):
        Primitive.__init__(self)
        self.x = 0
        self.y = 0
        self.radius = 10
    
    def getCommand(self):
        return "update %d type(circle) %s %s" % (self.id,  self.params(), self.pparams())
    def params(self):
        return "position(%d,%d) radius(%d)" % (self.x,self.y, self.radius)
    
class Rectangle(Primitive):
    def __init__(self):
        Primitive.__init__(self)
        self.x = 0
        self.y = 0
        self.w = 10
        self.h = 10
    
    def getCommand(self):
        return "update %d type(Rectangle) %s %s" % (self.id,  self.params(), self.pparams())
    def params(self):
        return "position(%d,%d) size(%d,%d)" % (self.x,self.y, self.x+self.w, self.y+self.h)
          

class Line(Primitive):
    def __init__(self):
        Primitive.__init__(self)
        self.x = 0
        self.y = 0
        self.x2 = 10
        self.y2 = 10
    
    def getCommand(self):
        return "update %d type(line) %s %s" % (self.id,  self.params(), self.pparams())
    def params(self):
        return "from(%d,%d) to(%d,%d)" % (self.x,self.y, self.x2, self.y2)
     
class Oval(Primitive):
    def __init__(self):
        Primitive.__init__(self)
        self.x = 0
        self.y = 0
        self.w = 10
        self.h = 10
    
    def getCommand(self):
        return "update %d type(oval) %s %s" % (self.id,  self.params(), self.pparams())
    def params(self):
        return "position(%d,%d) size(%d,%d)" % (self.x,self.y, self.x+self.w, self.y+self.h)


