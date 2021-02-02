/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.Math;

import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class ShooterPID {

    private static ShooterPID mInstance = new ShooterPID();

    public double shooterError;
    public double shooterPrevError;
    public double shooterIntegral;
    public double shooterDerivative;
    public double shooterRes;
    public double outMax = 1;
    public double outMin = -1;

    public double accError;
    public double accPrevError;
    public double accIntegral;
    public double accDerivative;
    public double accRes;

    public static ShooterPID getInstance(){
        return mInstance;
    }
    
    /**
     * PID With Integral Limititation
     */
    public double shooterPIDF(double wantedRPM, double currentRPM){
        shooterError = wantedRPM - currentRPM;
        shooterIntegral += shooterError*Constants.kShooterI;
        shooterIntegral = Constants.applyDeadband(shooterIntegral, Constants.shooterOutMin, Constants.shooterOutMax);
        shooterDerivative = shooterError - shooterPrevError;
        shooterRes = (Constants.kShooterF*wantedRPM)+ (shooterError * Constants.kShooterP) + shooterIntegral + (shooterDerivative*Constants.kShooterD);
        shooterRes = Constants.applyDeadband(shooterRes, Constants.shooterOutMin, Constants.shooterOutMax);
        return shooterRes;
    }

    public void shooterPIDReset(){
        shooterError = 0;
        shooterPrevError = 0;
        shooterIntegral = 0;
        shooterDerivative = 0;
        shooterRes = 0;
    }
    /*
    Shooter PID without Integral Limitation
    public double shooterPIDF(double wantedRPM, double currentRPM){
        shooterError = wantedRPM - currentRPM;
        shooterIntegral += shooterError;
        shooterDerivative = shooterError - shooterPrevError;
        shooterRes = (Constants.kShooterF*wantedRPM)+(shooterError * Constants.kShooterP) + (shooterIntegral*Constants.kShooterI) + (shooterDerivative*Constants.kShooterD);
        return shooterRes;
    }*/

    /**
     * PID With Integral Limititation
     */
    public double accPIDF(double wantedRPM, double currentRPM){
        accError = wantedRPM - currentRPM;
        accIntegral += accError*Constants.kAccI;
        accIntegral = Constants.applyDeadband(accIntegral, Constants.shooterOutMin, Constants.shooterOutMax);
        accDerivative = accError - accPrevError;
        accRes = (Constants.kAccF*wantedRPM)+ accIntegral + (accDerivative*Constants.kAccD);
        //Shooter Deadband might be wrong
        accRes = Constants.applyDeadband(accRes, Constants.shooterOutMin, Constants.shooterOutMax);
        return accRes;
    }

    public void accPIDReset(){
        accError = 0;
        accPrevError = 0;
        accIntegral = 0;
        accDerivative = 0;
        accRes = 0;
    }
    /*
    PID With No Integral Limitation
    public double accPIDF(double wantedRPM, double currentRPM){
        accError = wantedRPM - currentRPM;
        accIntegral += accError;
        accDerivative = accError - accPrevError;
        accRes = (Constants.kAccF*wantedRPM)+(accError * Constants.kAccP) + (accIntegral*Constants.kAccI) + (accDerivative*Constants.kAccD);
        return accRes;
    }*/
}
