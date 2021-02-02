/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Periodics.Auto.Modes;

import java.util.Arrays;

import frc.robot.Periodics.Auto.AutoModeEndedException;
import frc.robot.Periodics.Auto.Action.*;

/**
 * Add your docs here.
 */
public class MainAuto extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new SeriesAction(Arrays.asList(
        new ResetSensorsAction(),
        new ShooterAction(2000), 
        new ShooterStopAction(),
        new TurnAction(90), 
        new DriveForwardAction(90,2),
        new TurnAction(180),
        new ParallelAction(Arrays.asList(new DriveForwardAction(180, 2), new IntakeAction())),
        new TurnAction(315),
        new ShooterAction(3000),
        new ShooterStopAction(),
        new WaitAction())));
    }
}
