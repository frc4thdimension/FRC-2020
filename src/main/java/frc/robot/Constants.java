/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Constants {
    //TO-DO correct the ports
    //Motor Ports
    public static final int driveLeftMotorPort = 0; //2
    public static final int driveRightMotorPort = 1; //2
    public static final int driveLeftXMotorPort = 0; //1
    public static final int driveRightXMotorPort = 4; //1
    //6
    public static final int climberMotorPort = 7;
   
    public static final int conveyorMotorPort = 7; //1 corrected
    
    //public static final int controlPanelMotorPort = 3; //1
    
    public static final int pivotMotorPort = 3; //1 corrected
    public static final int centerRightMotorPort = 4; //1 corrected
    public static final int centerLeftMotorPort = 5; //1corrected
    public static final int intakeMotorPort = 6; //1 corrected
   
    public static final int feederMotorPort = 2; //1 corrected
    public static final int acceleratorWheelMotorPort = 2; //1
    public static final int shooterWheelMotorPort = 1; //2

    //Solenoid Ports
    /*public static final int ptoShifterSolenoidPort = 0;
    public static final int pusherPistonsSolenoidPort = 1;
    public static final int climberLockSolenoidPort = 2; 
    public static final int controlPanelSolenoidPort = 3;*/

    //Encoder Ports
    public static final int shooterEncPort1 = 10;//corrected
    public static final int shooterEncPort2 = 11; //corrected
    
    public static final int accEncPort1 = 12; //corrected
    public static final int accEncPort2 = 13; //corrected

    //Encoder Directions
    public static final boolean shooterEncDirection = false;
    public static final boolean accEncDirection = false;

    //Distance per pulse values
    public static final double shooterEncDistancePerPulse = 0.002;
    public static final double accEncDistancePerPulse = 0.002;

    //PID Constants
    public static final double kShooterP = 0.01;
    public static final double kShooterI = 0.0001;
    public static final double kShooterD = 0.002;
    public static final double kShooterF = 12/3670;
    public static final double shooterOutMax = 1;
    public static final double shooterOutMin = -1;

    public static final double kAccP = 0.1;
    public static final double kAccI = 0.135;
    public static final double kAccD = 0.135;
    public static final double kAccF = 12/6000;

    public static final double kTurnP = 0.1;
    public static final double kTurnI = 0.135;
    public static final double kTurnD = 0.135;
    public static final double turnOutMax = 1;
    public static final double turnOutMin = -1;


    //Constant Values
    public static final double kSensitivity = 0.75;
    public static final double kShooterMaxPower = 0;
    public static final double kFeederMaxPower = 0;
    public static final double kAcceleratorMaxPower = 0;

    public static final double kShootingAngle = 0;

    //Boolean Value
    public static boolean isShooterUsingFeeder = false;
    public static boolean isShooterUsing = false;
    public static boolean isConveyorUsing = false;
    public static boolean isUsingFeeder = false;
    public static boolean stopAcc= false;

    /**
     * Return deadband value
     * @param value
     * @param minValue
     * @param maxValue
     * @return Value
     */
    public static double applyDeadband(double value, double minValue, double maxValue){
        if(value > maxValue)
            return maxValue;
        else if(value<minValue){
            return minValue;
        }
        else{
            return value;
        }
    }
    /**
     * Tolerance
     * @param value
     * @param control_value
     * @param tolerance
     * @return value = +- control_value
     */
    public static boolean tolerance(double value,double control_value, double tolerance){
        if(value >= control_value - tolerance && value <= control_value + tolerance)
            return true;
        else
            return false;
    }
}
