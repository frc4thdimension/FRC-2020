/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Periodics.Auto.Action;

import frc.Math.PID;
import frc.robot.Constants;
import frc.robot.Subsystems.Drivebase;

/**
 * Add your docs here.
 */
public class TurnAction implements Action {

    public PID mPID;
    public Drivebase mDrivebase;
    private double angleToGo = 0;

    public TurnAction(double wantedAngle){
        angleToGo = wantedAngle;
    }


    @Override
    public void done() {
        mPID.turnPidReset();
    }

    @Override
    public boolean isFinished() {
        return Constants.tolerance(mDrivebase.getGyroAngle(), angleToGo, 0.5);
    }

    @Override
    public void start() {

        mDrivebase = Drivebase.getInstance();
        mPID = PID.getInstance();
    }

    @Override
    public void update() {
        mDrivebase.autoTurn(angleToGo);
    }
    
}
