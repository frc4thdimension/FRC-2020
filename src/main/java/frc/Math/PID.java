/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be Feederompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.Math;

import frc.robot.Constants;

/**
 * Class For PID Calculations
 */
public class PID {
    private static PID mInstance = new PID();


    public static PID getInstance(){
        return mInstance;
    }

    private PID(){
    }

    public double turnError = 0;
    public double turnIntegral = 0;
    public double turnDerivative = 0;
    public double turnPrevError = 0;
    public double turnRes = 0;

    public double turnPID(double wantedAngle, double currentAngle){
        turnError = wantedAngle-currentAngle;
        turnIntegral += turnError*Constants.kTurnI*0.02;
        turnDerivative = turnError-turnPrevError;
        turnIntegral = Constants.applyDeadband(turnIntegral, -1, 1);

        turnRes = turnIntegral+(turnError*Constants.kTurnP)+(turnDerivative*Constants.kTurnD);

        turnRes = Constants.applyDeadband(turnRes, -1, 1);
        
        turnPrevError = turnError;

        return turnRes;

    }
    
    public void turnPidReset(){
        turnError = 0;
        turnIntegral = 0;
        turnDerivative = 0;
    }
}
