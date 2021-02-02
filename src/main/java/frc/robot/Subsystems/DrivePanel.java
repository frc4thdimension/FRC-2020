/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class for using DrivePanel buttons
 */

public class DrivePanel {
    private static DrivePanel mInstance = new DrivePanel();

    public static DrivePanel getInstance(){
        return mInstance;
    }

    Joystick panelM;
    Joystick panelA;

    public DrivePanel(){
        panelM = new Joystick(1);
        panelA = new Joystick(2);
    }

    public boolean leftRoller(){
        return panelM.getRawButton(7);
    }

    public boolean leftRollerReverse(){
        return panelM.getRawButton(8);
    }

    public boolean centerRoller(){
        return panelM.getRawButton(3);
    }

    public boolean centerRollerReverse(){
        return panelM.getRawButton(4);
    }

    public boolean rightRoller(){
        return panelM.getRawButton(2);
    }

    public boolean rightRollerReverse(){
        return panelM.getRawButton(1);
    }

    /*public boolean conveyor(){
        return panelM.getRawButton(9);
    }

    public boolean conveyorReverse(){
        return panelM.getRawButton(10);
    }*/

    public boolean pivotUp(){
        return panelM.getRawButton(5);
    }

    public boolean pivotDown(){
        return panelM.getRawButton(6);
    }

    public boolean resetGyro(){
        return panelA.getRawButton(6);
    }

    public boolean shooterSpeedUp(){
        return panelA.getRawButton(8);
    }

    public boolean feederIn(){
        return panelA.getRawButton(7);
    }

    public boolean climber_Up(){
        return panelM.getRawButton(9);
    }

    public boolean climber_Down(){
        return panelM.getRawButton(10);
    }
}
