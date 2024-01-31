package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

  private CANSparkMax intake;
  private CANSparkMax elbow;
  private CANSparkMax wrist;
  private SparkAbsoluteEncoder wristEncoder;
  private SparkAbsoluteEncoder elbowEncoder;
  private SparkLimitSwitch wristFwd;
  private SparkLimitSwitch wristRev;
  private SparkLimitSwitch elbowFwd;
  private SparkLimitSwitch elbowRev;

  public Intake() {
    intake = new CANSparkMax(IntakeConstants.Neo550Intake, MotorType.kBrushless);

    elbow = new CANSparkMax(IntakeConstants.NeoElbow, MotorType.kBrushless);

    wrist = new CANSparkMax(IntakeConstants.Neo550Wrist, MotorType.kBrushless);

    wristEncoder = wrist.getAbsoluteEncoder(Type.kDutyCycle);

    elbowEncoder = elbow.getAbsoluteEncoder(Type.kDutyCycle);

    wristFwd = wrist.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
    wristRev = wrist.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);

    elbowFwd = elbow.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
    elbowRev = elbow.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void stopIntake() {
    intake.stopMotor();
  }

  public void stopWrist() {
    wrist.stopMotor();
  }

  public void stopElbow() {
    elbow.stopMotor();
  }

  public void setSpeedIntake(double speed) {
    intake.set(speed);
  }

  public void setSpeedWrist(double speed) {
    wrist.set(speed);
  }

  public void setSpeedElbow(double speed) {
    elbow.set(speed);
  }

  public double getWristEncoder() {
    return wristEncoder.getPosition();
  }

  public double getElbowEncoder() {
    return elbowEncoder.getPosition();
  }

  public boolean getWristFwd() {
    return wristFwd.isPressed();
  }

  public boolean getWristRev() {
    return wristRev.isPressed();
  }

  public boolean getElbowFwd() {
    return elbowFwd.isPressed();
  }

  public boolean getElbowRev() {
    return elbowRev.isPressed();
  }
}
