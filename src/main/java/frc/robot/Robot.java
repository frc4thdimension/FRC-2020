/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Drivebase;
import frc.robot.Subsystems.Gamepad;
import frc.robot.Subsystems.*;
import frc.robot.Periodics.Teleop.*;
import frc.Math.PID;
import frc.Math.ShooterPID;
import frc.robot.Periodics.Auto.*;
import frc.robot.Periodics.Auto.Modes.SimpAuto;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public DrivebaseTP mDrivebaseTP;
  public Drivebase mDrivebase;
  public Gamepad mGamepad;
  public DrivePanel mDrivePanel;
  public Intake mIntake;
  public Shooter mShooter;
  public TeleOpPeriodic mTeleOpPeriodic;
  public AutoModeExecutor mAutoModeExecutor;
  public SimpAuto mSimpAuto;
  public Conveyor mConveyor;
  public ShooterPID mShooterPID;
  public PID mPID;
  public double timer;


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Simple Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    SmartDashboard.putNumber("Shooter Desired RPM", 0);

    mDrivebase = Drivebase.getInstance();
    mGamepad = Gamepad.getInstance();
    mDrivePanel = DrivePanel.getInstance();
    mIntake = Intake.getInstance();
    mShooter = Shooter.getInstance();
    mDrivebaseTP = DrivebaseTP.getInstance();
    mTeleOpPeriodic = TeleOpPeriodic.getInstance();
    mAutoModeExecutor = AutoModeExecutor.getInstance();
    mPID = PID.getInstance();
    mSimpAuto = new SimpAuto();
    mConveyor = Conveyor.getInstance();
    mShooterPID = ShooterPID.getInstance();

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Shooter RPM ", mShooter.getShooterRPM());
    SmartDashboard.putNumber("Accelerator RPM ", mShooter.getAccRPM());
    SmartDashboard.putBoolean("Is Ready For Shoot", mShooter.isReadyForShoot);
    SmartDashboard.putNumber("Gyro Angle", mDrivebase.getGyroAngle());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    /*mAutoModeExecutor.reset();
    if(m_autoSelected == kCustomAuto){
      mAutoModeExecutor.setAutoMode(mSimpAuto);
      mAutoModeExecutor.start();
    }
    */
    timer = Timer.getFPGATimestamp();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if(Timer.getFPGATimestamp() - timer < 3){
      mShooter.shooterSpeedUp(3200);
      mShooter.shoot();
    }
    else if(Timer.getFPGATimestamp() - timer < 5){
      mShooter.shooterStop();
      mDrivebase.robotDrive(-0.5, 0);
    }
    else{
      mDrivebase.robotDrive(0, 0);
    }
  }

  @Override
  public void teleopInit() {
    //super.teleopInit(); Don't know what this code does.
    //autoThread.interrupt();
    //mAutoModeExecutor.stop();
    mShooter.resetSensors();
    mShooterPID.accPIDReset();
    mShooterPID.shooterPIDReset();  
    mPID.turnPidReset();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    mDrivebase.robotDrive((mGamepad.getForward()-mGamepad.getReverse()), mGamepad.getSteering()*Constants.kSensitivity);
    if(mGamepad.setupRobot()){
        mDrivebase.setupRobot();
    }

    //Pivot
    if(mDrivePanel.pivotDown()){
      mIntake.pivotDown();
    }
    else if(mDrivePanel.pivotUp()){
        mIntake.pivotUp();
    }
    else{
        mIntake.pivotStall();
    }
    boolean isUsingGamepad = false;
    //Intake Codes
    if(mGamepad.getIntakeGamepad()){
        mIntake.intakeOn();
        isUsingGamepad = true;
    }
    else if(mGamepad.getReverseIntakeGamepad()){
        mIntake.intakeReverse();
        isUsingGamepad = true;
    }
    else{
        //isControllingConveyor = false;
        mIntake.stopLeftRoller();
        mIntake.stopCenterRoller();
        mIntake.stopRightRoller();
        isUsingGamepad = false;
    }

    //Manual Intake Codes
    if(mDrivePanel.leftRoller()){
        mIntake.leftRoller();
    }
    else if(mDrivePanel.leftRollerReverse()){
        mIntake.leftRollerReverse();
    }
    else if(!isUsingGamepad){
        mIntake.stopLeftRoller();
    }

    //Center Roller
    if(mDrivePanel.centerRoller()){
        mIntake.centerRoller();
    }
    else if(mDrivePanel.centerRollerReverse()){
        mIntake.centerRollerReverse();
    }
    else if(!isUsingGamepad){
        mIntake.stopCenterRoller();
    }

    //Right Roller
    if(mDrivePanel.rightRoller()){
        mIntake.rightRollerReverse();
    }
    else if(mDrivePanel.rightRollerReverse()){
        mIntake.rightRoller();
    }
    else if(!isUsingGamepad){
        mIntake.stopRightRoller();
    }

    //Conveyor
    /*if(mDrivePanel.conveyor()){
        mConveyor.conveyorStart();
    }
    else if(mDrivePanel.conveyorReverse()){
        mConveyor.conveyorReverse();
    }
    else */if(!isUsingGamepad && !mGamepad.startFeeder()){
        mConveyor.conveyorStop();
    }

    //feeder codes
    if(mGamepad.startFeeder()){
        mShooter.shoot();
    }
    else if(mDrivePanel.feederIn()){
        mShooter.feederOn();
    }
    else{
        mShooter.feederOff();
    }

    //acc wheel
    if(mDrivePanel.shooterSpeedUp()){
        mShooter.shooterSpeedUp(3000);
    }
    else if(mGamepad.getIntakeGamepad()){
        mShooter.acceleratorWheel.set(VictorSPXControlMode.PercentOutput, -0.7);
    }
    else{
        mShooter.acceleratorWheel.set(VictorSPXControlMode.PercentOutput, 0);
    }

    if(!mDrivePanel.shooterSpeedUp()){
        mShooter.shooterStop();
    }

    if(mDrivePanel.climber_Up()){
        mConveyor.climberUp();
    }
    else if(mDrivePanel.climber_Down()){
        mConveyor.climberDown();
    }
    else{
        mConveyor.climberOff();
    }

    /*if(!isControllingConveyor && mGamepad.startFeeder()){
        mConveyor.conveyorStop();
    }*/
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    
  }
}
