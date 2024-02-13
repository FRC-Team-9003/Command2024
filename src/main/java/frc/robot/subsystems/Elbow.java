package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Elbow extends SubsystemBase {

  private CANSparkMax elbow;
  private SparkAbsoluteEncoder elbowEncoder;
  private SparkLimitSwitch elbowFwd;
  private SparkLimitSwitch elbowRev;

  public Elbow() {

    elbow = new CANSparkMax(IntakeConstants.NeoElbow, MotorType.kBrushless);

    elbowEncoder = elbow.getAbsoluteEncoder(Type.kDutyCycle);

    elbowFwd = elbow.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
    elbowRev = elbow.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
  }

  @Override
  public void periodic() {}

  @Override
  public void simulationPeriodic() {}

  public void stopElbow() {
    elbow.stopMotor();
  }

  public void setSpeedElbow(double speed) {
    elbow.set(speed);
  }

  public double getElbowEncoder() {
    return elbowEncoder.getPosition();
  }

  public boolean getElbowFwd() {
    return elbowFwd.isPressed();
  }

  public boolean getElbowRev() {
    return elbowRev.isPressed();
  }
}
