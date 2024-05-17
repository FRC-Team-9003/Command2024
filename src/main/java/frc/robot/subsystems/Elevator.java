package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkLimitSwitch;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;

public class Elevator extends SubsystemBase {

  private CANSparkMax elev;
  private SparkAbsoluteEncoder elevEncoder;
  private SparkLimitSwitch elevFwd;
  private SparkLimitSwitch elevRev;

  private SparkPIDController controller;

  private final double top = 0.863;
  private final double bottom = 0.463;
  private final double travel_dist = top - bottom;

  private ElevatorConstants.ElevState state;

  private double target;

  public Elevator() {
    elev = new CANSparkMax(ElevatorConstants.kElevatorCanID, MotorType.kBrushless);
    elevEncoder = elev.getAbsoluteEncoder(Type.kDutyCycle);
    elevFwd = elev.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);
    elevRev = elev.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyClosed);

    controller = elev.getPIDController();
    controller.setOutputRange(ElevatorConstants.kMinOutput, ElevatorConstants.kMaxOutput);
    controller.setFeedbackDevice(elevEncoder);
    controller.setP(ElevatorConstants.kP);
    controller.setFF(ElevatorConstants.kFF);

    state = ElevatorConstants.ElevState.Down;
  }

  @Override
  public void periodic() {
    if (state.equals(ElevatorConstants.ElevState.Up) && !this.getElevRev()){
      double newFF = controller.getFF()+(top-this.getElevEncoder()/travel_dist);
      controller.setFF(newFF);
      controller.setReference(top, ControlType.kPosition);
    }
  }

  @Override
  public void simulationPeriodic() {}

  public void stop() {
    // update state is done when commands finish
    elev.stopMotor();
  }

  public void setSpeed(double speed) {
    state = ElevatorConstants.ElevState.Transition;
    elev.set(speed);
  }

  public double getElevEncoder() {
    return elevEncoder.getPosition();
  }

  public boolean getElevFwd() {
    return elevFwd.isPressed();
  }

  public boolean getElevRev() {
    return elevRev.isPressed();
  }

  public void setState(ElevatorConstants.ElevState update) {
    state = update;
  }
}
