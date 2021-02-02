/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.Math.PID;
import frc.robot.Constants;

/**
 * Add your docs here.
*/ 
public class Drivebase {
    private static Drivebase mInstance = new Drivebase();

    public static Drivebase getInstance(){
        return mInstance;
    }

    public ADXRS450_Gyro gyro;
    public VictorSP driveL; 
    public VictorSP driveR;
    public WPI_VictorSPX driveLX;
    public WPI_VictorSPX driveRX;
    
    public SpeedControllerGroup driveLeft;
    public SpeedControllerGroup driveRight;
    /*public Solenoid ptoShifter;
    public Solenoid pusherPistons;
    public Solenoid climberLock;
    public boolean onAir=false;*/
    public PID mPID;
    public DifferentialDrive differentialDrive;
    //public double driveAngle;
    public int stage = 1;


    public Drivebase(){
        driveL = new VictorSP(Constants.driveLeftMotorPort);
        driveR = new VictorSP(Constants.driveRightMotorPort);
        driveLX = new WPI_VictorSPX(Constants.driveLeftXMotorPort);
        driveRX = new WPI_VictorSPX(Constants.driveRightXMotorPort);
       
        driveLeft = new SpeedControllerGroup(driveL, driveLX);
        driveRight = new SpeedControllerGroup(driveR, driveRX);
        differentialDrive = new DifferentialDrive(driveLeft, driveRight);
        /*ptoShifter = new Solenoid(Constants.ptoShifterSolenoidPort);
        pusherPistons = new Solenoid(Constants.pusherPistonsSolenoidPort);
        climberLock = new Solenoid(Constants.climberLockSolenoidPort);*/
        gyro = new ADXRS450_Gyro();
        mPID = PID.getInstance();
        gyro.reset();

        /*climberLock.set(true);
        ptoShifter.set(false);
        pusherPistons.set(false);*/
    }


    public double getGyroAngle(){
        return gyro.getAngle();
    }

    
    public void gyroReset(){
        gyro.reset();
    }

    public void setupRobot(){
        differentialDrive.curvatureDrive(0, mPID.turnPID(Constants.kShootingAngle, getGyroAngle()), true);
        
    }

    public void autoTurn(double wantedAngle){
        if(Constants.tolerance(getGyroAngle(), wantedAngle, 0.5));
        else
        differentialDrive.curvatureDrive(0,mPID.turnPID(wantedAngle, getGyroAngle()),false);
    }

    //Robot Driving
    public void robotDrive(double speed, double rotation_speed){
        differentialDrive.arcadeDrive(speed, rotation_speed);
    }

    //Robot lift up for pto
    /*public void pushUp_pto(Boolean state){
        onAir = state;
        ptoShifter.set(state);
        pusherPistons.set(state);
    }
    

    public void release_climber(){
        climberLock.set(false);
    }*/

    
    public void autoDrive(double driveAngle){
        differentialDrive.curvatureDrive(0.2, mPID.turnPID(driveAngle, getGyroAngle()), false);
    }

    public void stopDrive(){
        differentialDrive.tankDrive(0, 0);
    }
}
