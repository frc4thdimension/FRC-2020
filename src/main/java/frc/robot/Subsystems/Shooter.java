/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import frc.Math.ShooterPID;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter {
    private static Shooter mInstance = new Shooter();

    public static Shooter getInstance(){
        return mInstance;
    }

    public Conveyor mConveyor;
    public VictorSPX shooterWheel;
    public VictorSPX acceleratorWheel;
    public VictorSP feederWheel;
    //public Solenoid hoodPiston; 
    public Encoder accEnc;
    public Encoder shooterEnc;

    public double shooterRPM;
    public double accRPM;

    public double timerStart;
    
    //public boolean firstTime = true;
    public boolean isReadyForSpeedUp = false;
    public boolean isReadyForShoot = false;
    public int stage = 0;

    public ShooterPID mPID;

    public Shooter(){
        mConveyor = Conveyor.getInstance();
        shooterWheel = new VictorSPX(Constants.shooterWheelMotorPort);
        acceleratorWheel = new VictorSPX(Constants.acceleratorWheelMotorPort);
        feederWheel = new VictorSP(Constants.feederMotorPort);
        shooterEnc = new Encoder(Constants.shooterEncPort1, Constants.shooterEncPort2, Constants.shooterEncDirection);
        accEnc = new Encoder(Constants.accEncPort1, Constants.accEncPort2, Constants.accEncDirection);
        shooterEnc.setDistancePerPulse(Constants.shooterEncDistancePerPulse);
        accEnc.setDistancePerPulse(Constants.accEncDistancePerPulse);
        acceleratorWheel.setInverted(true);
        feederWheel.setInverted(true);
        mPID = ShooterPID.getInstance();
    }

    public void resetSensors(){
        shooterEnc.reset();
        accEnc.reset();
    }

    
    public double getShooterRPM(){
        shooterRPM = shooterEnc.getRate()*60;
        return shooterRPM;
    }

    public double getAccRPM(){
        accRPM = accEnc.getRate()*60;
        return accRPM;
    }

    //Feeder Wheel Codes
    public void feederOn(){
        feederWheel.set(0.3);
    }

    public void feederOff(){
        feederWheel.set(0);
    }

    public void feederReverse(){
        feederWheel.set(-0.7);   
    }

    /*public void shooterSpeedUp(double wantedRPM){
        if(!isReadyForSpeedUp){
            if(firstTime){
                mPID.shooterPIDReset();
                mPID.accPIDReset();
                timerStart = Timer.getFPGATimestamp();
                firstTime = false;
                Constants.isShooterUsing = true;
            }
            if(Timer.getFPGATimestamp() - timerStart < 0.5){
                Constants.isShooterUsing = true;
                System.out.println("im in");
                mConveyor.conveyorReverse();
                feederReverse();
                acceleratorWheel.set(VictorSPXControlMode.PercentOutput, -1);
                //TO-DO intake motor reset
            }else if(Timer.getFPGATimestamp() - timerStart < 0.7){
                acceleratorWheel.set(VictorSPXControlMode.PercentOutput, 0);
            }
            
            else{
                feederWheel.set(0);
                mConveyor.conveyorStop();
                //acceleratorWheel.set(VictorSPXControlMode.PercentOutput, 0);
                System.out.println("4");
                isReadyForSpeedUp = true;
            }
        }
        else{
            //don't forget to reset firstTime and isReadyForSpeedUp Values
            shooterWheel.set(VictorSPXControlMode.PercentOutput, mPID.shooterPIDF(wantedRPM, getShooterRPM()));
            acceleratorWheel.set(VictorSPXControlMode.PercentOutput, mPID.accPIDF(wantedRPM, getAccRPM()));
            //Correct the tolerance
            if(Constants.tolerance(getShooterRPM(), wantedRPM, 200) && Constants.tolerance(getAccRPM(), wantedRPM, 200)){
                isReadyForShoot = true;
            }
            else{
                isReadyForShoot = false;
            }
        }
    }*/
    public void shooterSpeedUp(double wantedRPM){
        //don't forget to reset firstTime and isReadyForSpeedUp Values
        shooterWheel.set(VictorSPXControlMode.PercentOutput, mPID.shooterPIDF(wantedRPM, getShooterRPM()));
        acceleratorWheel.set(VictorSPXControlMode.PercentOutput, mPID.accPIDF(wantedRPM, getAccRPM()));
        //Correct the tolerance
        if(Constants.tolerance(getShooterRPM(), wantedRPM, 200)){ //&& Constants.tolerance(getAccRPM(), wantedRPM, 200)){
            isReadyForShoot = true;
        }
        else{
            isReadyForShoot = false;
        }
    }



    public void shooterStop(){
        shooterWheel.set(ControlMode.PercentOutput, 0);
        //acceleratorWheel.set(ControlMode.PercentOutput, 0);
        mPID.accPIDReset();
        mPID.shooterPIDReset();
    }

    public void feederStop(){
        feederWheel.set(0);
        mConveyor.conveyorStop();
    }

    //don't forget to reset feederWheel motor value 
    public void shoot(){
        if(isReadyForShoot){
            feederWheel.set(1);
            mConveyor.conveyorStart();
        }
        else{
            feederWheel.set(0);
            mConveyor.conveyorStop();
        }
    }

    public void autoShoot(double wantedRPM){
        shooterSpeedUp(wantedRPM);

        if(Constants.tolerance(getShooterRPM(), wantedRPM, 200)){
            isReadyForShoot = true;
            Constants.isShooterUsing = true;
        }
        else{
            isReadyForShoot = false;
            Constants.isShooterUsing = false;
        }
        
        if(Constants.isShooterUsing){
            if(isReadyForShoot){
                System.out.println("3");
                Constants.isShooterUsing = true;
                feederWheel.set(1);
                mConveyor.conveyorStart();
            }
            else{
                mConveyor.conveyorStop();
            }
        }
        
    }
}
