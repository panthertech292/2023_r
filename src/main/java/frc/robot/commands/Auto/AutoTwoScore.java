// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.ClawClose;
import frc.robot.commands.ClawOpen;
import frc.robot.commands.DualArmControl;
import frc.robot.commands.Vision.VisionScoreCube;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.PickupSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoTwoScore extends SequentialCommandGroup {
  /** Creates a new AutoTwoScore. */
  public AutoTwoScore(DriveSubsystem s_DriveSubsystem,ArmSubsystem s_ArmSubsystem, PickupSubsystem s_PickupSubsystem, LEDSubsystem s_LEDSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DualArmControl(s_ArmSubsystem, true, ArmConstants.kScoreSpot, 11, 0).withTimeout(4),
      new ClawOpen(s_PickupSubsystem).withTimeout(0.5),
      new DriveToPositionEnd(s_DriveSubsystem, 0.10, -150, 0.003),
      new GyroTurn(s_DriveSubsystem, 180, 0.003, 0.10).withTimeout(2),
      new DriveToPositionEnd(s_DriveSubsystem, 0.10, 15, 0.002),
      new DualArmControl(s_ArmSubsystem, true, ArmConstants.kFloorSpot, 11, 0).withTimeout(4),
      new DriveToPositionEnd(s_DriveSubsystem, 0.10, 2, 0.002),
      new ClawClose(s_PickupSubsystem).withTimeout(0.3),

      //new DriveManual(s_DriveSubsystem, 0, 0).withTimeout(0.2),

      new DualArmControl(s_ArmSubsystem, true, .3, 8 ,0.0),
      new DualArmControl(s_ArmSubsystem, false, ArmConstants.kStowedSpot, 4, 0.0),

      new GyroTurn(s_DriveSubsystem, 180, 0.003, 0.10).withTimeout(2),
      new DriveToPositionEnd(s_DriveSubsystem, 0.10, 140, 0.002),
      new VisionScoreCube(s_DriveSubsystem, s_ArmSubsystem, s_PickupSubsystem, s_LEDSubsystem)
    );
  }
}
