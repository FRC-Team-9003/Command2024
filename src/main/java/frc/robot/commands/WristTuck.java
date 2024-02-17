package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.*;
import frc.robot.subsystems.*;

public class WristTuck extends Command {

  private Intake intake;

  public WristTuck(Intake subsystem_I) {

    intake = subsystem_I;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    intake.setSpeedWrist(IntakeConstants.defaultSpeedWrist);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return intake.getWristFwd();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
