
import threading
import time
import math

class Motion(threading.Thread):
    def __init__(self, motor, attr, refresh_freq=50):
    	"""
    	Abstract class to create a motion for a motor

	@param refresh_freq : frequency of refresh
   	"""
        threading.Thread.__init__(self)

	
	self.motor = motor
	self.attr = attr
        self.daemon = True
        self.suspended = threading.Lock()
        self.running = True
        self.refresh_freq = refresh_freq      
        self.attached_variables = []
        self.value = 0
        self.should_stop = False
	self.start_value = getattr(motor, attr)
        self.attach(motor, attr)

    def compute(self, t, start_value):
	"""
	call each refresh_freq time
	@param t : total time ellapsed since the start time thread
	"""
        raise NotImplemented
    
    def run(self):
	"""
	the thread
	"""
        self.zero = time.time()
        while self.running:
            t = time.time() - self.zero

		
            for motor,attr in self.attached_variables:
                self.value = int(self.compute( t ))
                setattr(motor, attr, self.value)

            if self.should_stop:
                self.stop()
            time.sleep( 1.0/self.refresh_freq )
            self.suspended.acquire()
            self.suspended.release()         

    def attach(self, motor, attr):
	"""
	attach a motor to a motion

	@param motor motor to apply the motion
	"""
        self.attached_variables.append( (motor, attr) )

    def detach(self, motor, attr):
	"""
	detach a motor from this motion

	@param motor to unapply the motion
	"""
        self.attached_variables.remove( (motor, attr) )

    def freeze(self):
	"""
	freeze the current motion
	"""
        self.zero = time.time()
        self.suspended.acquire()

    def unfreeze(self):
	"""
	unfreeze the current motion
	"""
        self.suspended.release()
        
    def stop(self):
	"""
	stop the thread
	"""
        self.running = False


class Goto(Motion):
    def __init__(self, motor, attr, reach_value, delta_time, refresh_freq=50):
        Motion.__init__(self, motor, attr, refresh_freq)
        self.reach_value = reach_value
        self.delta_time = delta_time
        self.offset = (self.reach_value - self.start_value) / self.delta_time

    def compute(self, t):
        if (t > self.delta_time):
            self.should_stop = True
            return self.reach_value
        return self.start_value + self.offset * t


class Sinus(Motion):
    def __init__(self, motors, attr, shift, amplitude, period, phase=0, refresh_freq=50):
	"""
	apply a sinus to a motor
	
	@param shift : the initial shift value
	@param amplitude : amplitude of the sinus
	@param period : period of the sinus
	@param phase : phase of the sinus
	@param refresh_freq : refresh frequency
	"""
        Motion.__init__(self, motors, attr, refresh_freq)
        self.shift = float(shift)
        self.amplitude = float(amplitude)
        self.omega = float(2 * math.pi / period)
        self.phase = float(phase)

    def compute(self, t):
        return float(float(self.start_value) + (self.shift + self.amplitude * math.sin(self.omega * t + self.phase)))

