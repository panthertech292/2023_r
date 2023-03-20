// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class VisionSetPipeline extends CommandBase {
  private final DriveSubsystem DriveSub;
  private int v_pipeline;
  /** Creates a new VisionSetPipeline. */
  public VisionSetPipeline(DriveSubsystem s_DriveSubsystem, int pipeline) {
    DriveSub = s_DriveSubsystem;
    v_pipeline = pipeline;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveSub.setLimeLightPipeline(v_pipeline);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (DriveSub.getLimeLightPipeline() == v_pipeline);
  }
}
