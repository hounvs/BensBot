package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.AnalogChannel;

public class Robot extends IterativeRobot {
    
    public Joystick DriveStick;
    public Joystick ShooterStick;  
    
    public Victor LeftMotor;
    public Victor RightMotor;
    public Victor SteerMotor;
    public Victor AimMotor;
    
    private final double kPOTAngleMax = 4.90; //TODO: check all POT values
    private final double kPOTAngleMin = 2.5;
    private final double kPOTDeadzone = 0.1;
    private final double kTurnSpeed   = 0.2;
    private final double kSteeringMotorDeadzone = 0.15;
    
    double x, y, l, r;
    
    private AnalogChannel SteeringAnglePOT;
    
    public void robotInit() {
        DriveStick = new Joystick(1);
        ShooterStick = new Joystick(2);
        
        LeftMotor = new Victor(2);
        RightMotor = new Victor(1);
        SteerMotor = new Victor(3);
        AimMotor = new Victor(4);
        
        SteeringAnglePOT = new AnalogChannel(1);
        
        System.out.println("T470 Is ALIVE!");
    }
    
    public void teleopPeriodic() {
        double driveStickY  = DriveStick.getY();
        double driveStickX  = DriveStick.getX();
        double currentPOT = ((SteeringAnglePOT.getAverageVoltage() - kPOTAngleMin)/(kPOTAngleMax-kPOTAngleMin) -.5 )*2;
        
        if(currentPOT - kPOTDeadzone >= driveStickX && currentPOT >= kPOTAngleMin) { //adjust left until limit
            SteerMotor.set(-kTurnSpeed);
        } else if(currentPOT + kPOTDeadzone <= driveStickX && currentPOT <= kPOTAngleMax) { //adjust right until limit
            SteerMotor.set(kTurnSpeed);
        } else {                                     //do not adjust
            SteerMotor.set(0.0);
        }
        
        LeftMotor.set(driveStickY);
        RightMotor.set(driveStickY);
        
        System.out.println(currentPOT);
    }
}