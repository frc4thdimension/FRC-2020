/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;


import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Intake {
    private static Intake mInstance = new Intake();

    private Conveyor mConveyor;
    private Shooter mShooter;
    public VictorSP intakeMotor;
    public VictorSP centerR;
    public VictorSP centerL;
    public VictorSP pivotMotor;
    

    public static Intake getInstance(){
        return mInstance;
    }

    private Intake(){
        mConveyor = Conveyor.getInstance();
        mShooter = Shooter.getInstance();
        intakeMotor = new VictorSP(Constants.intakeMotorPort);
        centerL = new VictorSP(Constants.centerLeftMotorPort);
        centerR = new VictorSP(Constants.centerRightMotorPort);
        pivotMotor = new VictorSP(Constants.pivotMotorPort);
    }
    //Pivot Motor Codes

    public void pivotDown(){
        pivotMotor.set(-0.2);
    }

    public void pivotUp(){
        pivotMotor.set(0.2);
    }

    public void pivotStall(){
        pivotMotor.set(0);
    }

    /*public void pivotDown(){
        if(pivotPos != PivotPosition.DOWN){
            if(firstTimeDown){
                timerStart = Timer.getFPGATimestamp();
                pivotPos = PivotPosition.TRANS;
                firstTimeDown = false;
            }
            if(Timer.getFPGATimestamp() - timerStart < 0.6){
                pivotMotor.set(-1);
            }
            else{
                pivotMotor.set(0);
                pivotPos = PivotPosition.DOWN;
                firstTimeDown = true;
            }
        }
    }*/

    /*public void intakeOn(){
        intakeMotor.set(1);
        centerL.set(-1);
        centerR.set(-1);
        mConveyor.conveyorStart();
        mShooter.feederOn();
    }*/

    public void intakeOn(){
        intakeMotor.set(-1);
        centerL.set(1);
        centerR.set(-1);
        mShooter.acceleratorWheel.set(VictorSPXControlMode.PercentOutput, -0.5);
        mConveyor.conveyorStart();
        //mShooter.feederOn();
    }

    public void intakeReverse(){
        intakeMotor.set(1);
        centerL.set(-1);
        centerR.set(1);
        mConveyor.conveyorReverse();
        //mShooter.feederReverse();
    }

    public void intakeOff(){
        intakeMotor.set(0);
        centerL.set(0);
        centerR.set(0);
        mConveyor.conveyorStop();
        Constants.stopAcc = true;
        //mShooter.feederStop();
    }

    //Manual Intake Codes

    public void centerRoller(){
        intakeMotor.set(0.2);
    }

    public void leftRoller(){
        centerL.set(1);
    }

    public void rightRoller(){
        centerR.set(-1);
    }

    //Manual Intake Reverse Codes
    public void centerRollerReverse(){
        intakeMotor.set(-1);
    }

    public void leftRollerReverse(){
        centerL.set(-1);
    }

    public void rightRollerReverse(){
        centerR.set(1);
    }

    //Manual Intake Stop
    public void stopCenterRoller(){
        intakeMotor.set(0);
    }

    public void stopLeftRoller(){
        centerL.set(0);
    }

    public void stopRightRoller(){
        centerR.set(0);
    }

}
