package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Kashyap on 11/4/15.
 */
public class Autonomous extends OpMode {
    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;

    public Autonomous() {
        frontMotorLeft = hardwareMap.dcMotor.get("motor");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");
    }

    public void moveMotor(DcMotor motor, int position, double power) {
        motor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        motor.setTargetPosition(position);
        motor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
    }

    public void moveRobot(int position, double power, String direction) {
        if(direction.equals("forward")) {
            moveMotor(frontMotorLeft, position, power);
            moveMotor(frontMotorRight, position, power);
            moveMotor(backMotorLeft, position, power);
            moveMotor(backMotorRight, position, power);
        } else if(direction.equals("backward")) {
            moveMotor(frontMotorLeft, -position, power);
            moveMotor(frontMotorRight, -position, power);
            moveMotor(backMotorLeft, -position, power);
            moveMotor(backMotorRight, -position, power);
        }
    }

    public void turn(double degrees) {

    }


    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
