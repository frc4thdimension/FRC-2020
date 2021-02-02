/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Periodics.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Intake;

/**
 * Add your docs here.
 */

 //TO-DO This code is incomplete
public class PivotDownAction implements Action {

    private Intake mIntake;
    private double timerStart;

    @Override
    public void done() {
        mIntake.pivotStall();        

    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - timerStart > 0.5;
    }

    @Override
    public void start() {
        mIntake = Intake.getInstance();
        timerStart = Timer.getFPGATimestamp();
    }

    @Override
    public void update() {
        mIntake.pivotDown();
    }
}
