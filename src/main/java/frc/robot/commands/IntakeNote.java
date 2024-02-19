package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake;

public class IntakeNote extends Command {

  private Intake intake;

  public IntakeNote(Intake subsystem_I) {
    intake = subsystem_I;

    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intake.setSpeedIntake(IntakeConstants.defaultSpeedIntake);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stopIntake();
  }

  @Override
  public boolean isFinished() {
    return intake.isNote();
  }

  @Override
  public boolean runsWhenDisabled() {
      return false;
  }
}
