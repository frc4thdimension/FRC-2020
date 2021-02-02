/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Periodics.Auto.Action;

import frc.Math.PID;
import frc.Math.ShooterPID;
import frc.robot.Subsystems.Drivebase;
import frc.robot.Subsystems.Shooter;

/**
 * Add your docs here.
 */
public class ResetSensorsAction implements Action {

    private boolean isReset = false;
    private Drivebase mDrive;
    private Shooter mShooter;
    private PID mPID;
    private ShooterPID mShooterPID;

    @Override
    public void done() {
    }

    @Override
    public boolean isFinished() {
        return isReset;
    }

    @Override
    public void start() {
        mDrive = Drivebase.getInstance();
        mShooter = Shooter.getInstance();
        mShooterPID = ShooterPID.getInstance();
        mPID = PID.getInstance();
        mDrive.gyroReset();
        mShooter.resetSensors();
        mPID.turnPidReset();
        mShooterPID.accPIDReset();
        mShooterPID.shooterPIDReset();
        isReset = true;
    }

    @Override
    public void update() {
    }
}
