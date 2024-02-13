package frc.robot.commands;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;
import java.util.*;

public class ShuffleboardSetup extends Command {

  Elevator elev;
  Intake intake;

  // Network table setup
  NetworkTableInstance inst;
  NetworkTable table;
  DoublePublisher angle;

  public ShuffleboardSetup(Elevator subsystem_E, Intake subsystem_I) {

    elev = subsystem_E;
    intake = subsystem_I;

    ShuffleboardTab encoderTab = Shuffleboard.getTab("Absolute Encoder");
    ShuffleboardTab commandsTab = Shuffleboard.getTab("Commands");

    ShuffleboardLayout elevSensors =
        encoderTab
            .getLayout("Elevator", BuiltInLayouts.kList)
            .withSize(2, 2)
            .withProperties(Map.of("Label Position", "HIDDEN"));

    ShuffleboardLayout intakeSensors =
        encoderTab
            .getLayout("Elevator", BuiltInLayouts.kList)
            .withSize(2, 2)
            .withProperties(Map.of("Label Position", "HIDDEN"));

    elevSensors.addDouble("Elevator Encoder", elev::getElevEncoder);

    intakeSensors.addDouble("Wrist Encoder", intake::getWristEncoder);
    intakeSensors.addDouble("Elbow Encoder", intake::getElbowEncoder);

    commandsTab.add(new Fold(elev, intake));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("datatable");
    angle = table.getDoubleTopic("gyro").publish();
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
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
