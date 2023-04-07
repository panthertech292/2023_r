// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class DriveTeleop extends CommandBase {
  private final DriveSubsystem DriveSub;
  private final LEDSubsystem LEDSub;
  private double speedMod;
  private double rampTime;
  private double previousRampTime;
  /** Creates a new DriveTeleop. */
  public DriveTeleop(DriveSubsystem s_DriveSubsystem, LEDSubsystem s_LEDSubsystem) {
    DriveSub = s_DriveSubsystem;
    LEDSub = s_LEDSubsystem;
    speedMod = 1;
    rampTime = 0;
    previousRampTime = 0;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.setLimeLightDriverCam();
    DriveSub.setRampRate(0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    speedMod = 1;
    previousRampTime = rampTime;
    if (RobotContainer.getDriveRightTrigger() > 0.50){
      speedMod = 0.55;
    }
    if (RobotContainer.getDriveLeftTrigger() > 0.50){
      speedMod = 0.80;
    }

    DriveSub.arcadeDrive(RobotContainer.getDriverLeftSpeedX()*0.81*speedMod, (RobotContainer.getDriverRightSpeedY()*speedMod));
    
    /* OLD CODE. KEEPING HERE JUST IN CASE
    if(Math.abs(RobotContainer.getDriverLeftSpeedX()) > 0.15){
      DriveSub.setRampRate(0);
    } else if (DriveSub.getRightMotorEncoderVelocity() > 0 && RobotContainer.getDriverRightSpeedY() > 0){
      DriveSub.setRampRate(0.65);
    }else if(DriveSub.getRightMotorEncoderVelocity() < 0 && RobotContainer.getDriverRightSpeedY() < 0){
      DriveSub.setRampRate(0.65);
    }else{
      DriveSub.setRampRate(0);
    }*/

    if(Math.abs(RobotContainer.getDriverLeftSpeedX()) > 0.15){
      rampTime = 0;
    } else if (DriveSub.getRightMotorEncoderVelocity() > 0 && RobotContainer.getDriverRightSpeedY() > 0){
      rampTime = 0.75;
    }else if(DriveSub.getRightMotorEncoderVelocity() < 0 && RobotContainer.getDriverRightSpeedY() < 0){
      rampTime = 0.75;
    }else{
      rampTime = 0;
    }
    //This should only change the ramp rate if a new one is given. Hopefully this helps with some loop overruns.
    if (rampTime != previousRampTime){
      DriveSub.setRampRate(rampTime);
      //System.out.println(rampTime);
    }
   

    if (DriverStation.getAlliance() == Alliance.Red){
      LEDSub.setColorChase(0, 120, (int) Math.round(Math.abs(RobotContainer.getDriverRightSpeedY()*2)));
    }else{
      LEDSub.setColorChase(120, 0, (int) Math.round(Math.abs(RobotContainer.getDriverRightSpeedY()*2)));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSub.tankDrive(0, 0);
    DriveSub.setRampRate(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
