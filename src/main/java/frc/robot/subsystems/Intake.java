package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkLimitSwitch;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

  private CANSparkMax intake;
  private CANSparkMax wrist;
  private SparkPIDController wristController;
  private AbsoluteEncoder wristEncoder;
  private RelativeEncoder internalWrist;
  private SparkLimitSwitch wristFwd;
  private SparkLimitSwitch wristRev;

  private DigitalInput intakeSwitch;

  private SimpleMotorFeedforward ffWrist;

  public Intake() {
    intake = new CANSparkMax(IntakeConstants.Neo550Intake, MotorType.kBrushless);
    intakeSwitch = new DigitalInput(1);

    ffWrist = new SimpleMotorFeedforward(0.34174, 0.00096567, 0.00017337);

    wrist = new CANSparkMax(IntakeConstants.Neo550Wrist, MotorType.kBrushless);
    internalWrist = wrist.getEncoder();
    wristEncoder = wrist.getAbsoluteEncoder(Type.kDutyCycle);
    // wristEncoder.setZeroOffset(0.0);

    wristController = wrist.getPIDController();
    wristController.setFeedbackDevice(internalWrist);
    wristController.setOutputRange(-0.2, 0.2);
    wristController.setP(6.3078e-6);
    wristController.setD(0.55842);
    wristController.setFF(ffWrist.calculate(0.0));

    wristFwd = wrist.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
    wristRev = wrist.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
  }

  @Override
  public void periodic() {
    if (internalWrist.getVelocity() > 0) {
      wristController.setFF(ffWrist.calculate(internalWrist.getVelocity()));
    }
  }

  @Override
  public void simulationPeriodic() {}

  public void stopIntake() {
    intake.stopMotor();
  }

  public void stopWrist() {
    wrist.stopMotor();
  }

  public void setSpeedIntake(double speed) {
    intake.set(speed);
  }

  public void setSpeedWrist(double speed) {
    wrist.set(speed);
  }

  public double getWristEncoder() {
    return wristEncoder.getPosition();
  }

  public boolean getWristFwd() {
    return wristFwd.isPressed();
  }

  public boolean getWristRev() {
    return wristRev.isPressed();
  }

  public boolean isNote() {
    return intakeSwitch.get();
  }
}
