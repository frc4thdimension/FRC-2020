/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Periodics.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Drivebase;

/**
 * Add your docs here.
 */
public class SimpDriveAction implements Action {

    private Drivebase mDrivebase;
    private double timerStart;
    private double secondsToRun;
    private double speed;

    public SimpDriveAction(double velocity, double time){
        speed = velocity;
        secondsToRun = time;
    }

    @Override
    public void done() {
        mDrivebase.stopDrive();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp()-timerStart > secondsToRun;
    }

    @Override
    public void start() {
        mDrivebase = Drivebase.getInstance();
        timerStart = Timer.getFPGATimestamp();
    }

    @Override
    public void update() {
        mDrivebase.robotDrive(speed, secondsToRun);;
    }
}
