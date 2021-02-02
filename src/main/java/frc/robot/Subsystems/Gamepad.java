/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Add your docs here.
 */
public class Gamepad {
    private static Gamepad mInstance = new Gamepad();
    
    public static Gamepad getInstance(){
        return mInstance;
    }

    public XboxController gamepad;

    public Gamepad(){
        gamepad = new XboxController(0);
    }


    public double getForward(){
        return gamepad.getRawAxis(3);
    }

    public double getReverse(){
        return gamepad.getRawAxis(2);
    }

    public double getSteering(){
        return gamepad.getRawAxis(0);
    }

    public boolean getIntakeGamepad(){
        return gamepad.getRawButton(1);
    }

    public boolean getReverseIntakeGamepad(){
        return gamepad.getRawButton(2);
    }

    public boolean pushUp_pto(){
        return gamepad.getRawButton(3);
    }

    public boolean removeClimberLock(){
        return gamepad.getRawButton(4);
    }

    public boolean setupRobot(){
        return gamepad.getRawButton(5);
    }
    
    public boolean startFeeder(){
        return gamepad.getRawButton(6);
    }

    /*public boolean flashlightSwitch(){
        if (dpad == 0){
            return true;
        }
        else{
            return false;
        }
    }*/
}
