// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.ClawOpen;
import frc.robot.commands.DualArmControl;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PickupSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoTraverseBalance extends SequentialCommandGroup {
  /** Creates a new AutoTraverseBalance. */
  public AutoTraverseBalance(DriveSubsystem s_DriveSubsystem,ArmSubsystem s_ArmSubsystem, PickupSubsystem s_PickupSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 11, 0).withTimeout(2),
      new ClawOpen(s_PickupSubsystem).withTimeout(0.4),
      new DriveManual(s_DriveSubsystem, -0.20, -0.20).withTimeout(0.3),
      new DualArmControl(s_ArmSubsystem, false, ArmConstants.kStowedSpot, 9, 0).withTimeout(1.7),
      new DriveUntilPitch(s_DriveSubsystem, -0.3, 10),
      new DriveUntilZeroPitch(s_DriveSubsystem, -0.3),
      new DriveUntilPitch(s_DriveSubsystem, 0.27, 10),
      new DriveToPosition(s_DriveSubsystem, 0.15, (36), 0.03)
    );
  }
}
