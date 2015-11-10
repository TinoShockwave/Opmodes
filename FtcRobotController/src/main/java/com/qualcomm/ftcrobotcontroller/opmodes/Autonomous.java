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

    }

    //Input: Distance in inches
    //Output: Distance in encoder pulses
    public void moveMotor(DcMotor motor, double distance, double power) {
        double encoderClicks = (distance / 7.85) * 1 * 1120;
        int position = (int) (int) Math.round(encoderClicks);
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
