package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;

public class Elevator extends SubsystemBase{

    private CANSparkMax elev;

    public Elevator(){
        elev = new CANSparkMax(ElevatorConstants.kElevatorCanID, MotorType.kBrushless);
    }

    @Override
    public void periodic(){

    }


    @Override public void simulationPeriodic(){
        
    }
}
