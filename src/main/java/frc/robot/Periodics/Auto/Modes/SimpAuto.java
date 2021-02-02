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
public class SimpAuto extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new SeriesAction(Arrays.asList(
            new ResetSensorsAction(),
            new SimpShooterAction(1500),
            new ShooterStopAction(),
            new SimpDriveAction(-0.1, 0.5),
            new WaitAction()
        )));

    }
}
