/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Periodics.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Shooter;

/**
 * Add your docs here.
 */
public class SimpShooterAction implements Action{
    public Shooter mShooter;
    private double wantedRPM;
    public double timerStart;

    public SimpShooterAction(double rpm){
        wantedRPM = rpm;
    }

    @Override
    public void done() {
        mShooter.shooterStop();
        mShooter.feederStop();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp()-timerStart > 3;
    }

    @Override
    public void start() {
        mShooter = Shooter.getInstance();
        timerStart = Timer.getFPGATimestamp();
    }

    @Override
    public void update() {
        mShooter.autoShoot(wantedRPM);
    }
}
