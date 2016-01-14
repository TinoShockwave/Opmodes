package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Kashyap on 1/6/16.
 */
public class AutonomousTest extends LinearOpMode {
    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1;
    final static double WHEEL_CIRCUMFERENCE = 7.85;

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
    GyroSensor gyro;
//    OpticalDistanceSensor distance;

    @Override
    public void runOpMode() throws InterruptedException {

        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");
        gyro = hardwareMap.gyroSensor.get("gyro");

        frontMotorRight.setDirection(DcMotor.Direction.REVERSE);
        backMotorRight.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();

        gyro.calibrate();
        while (gyro.isCalibrating()) {
            Thread.sleep(500);
        }

        waitForStart();

        moveRobot(72, 0.5, "forward");
    }

    //Input: Distance in inches
    //Output: Distance in encoder pulses
    public void moveMotorEnc(DcMotor motor, double distance, double power) {
        motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        double encoderClicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR;
        motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        while (motor.getCurrentPosition() < encoderClicks) {
            motor.setPower(power);
        }
        motor.setPower(0);
    }

    public void moveMotor(DcMotor motor, double power) {
        motor.setPower(power);
    }

    public void moveRobot(int distance, double power, String direction) {
        resetEncoders();
        double encoderClicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR;
        frontMotorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        if (direction.equals("forward")) {
            while (frontMotorLeft.getCurrentPosition() <= encoderClicks || frontMotorRight.getCurrentPosition() <= encoderClicks) {
                moveMotor(frontMotorLeft, power);
                moveMotor(frontMotorRight, power);
                moveMotor(backMotorLeft, power);
                moveMotor(backMotorRight, power);
            }
        } else if (direction.equals("backward")) {
            while (frontMotorLeft.getCurrentPosition() <= encoderClicks || frontMotorRight.getCurrentPosition() <= encoderClicks) {
                moveMotor(frontMotorLeft, -power);
                moveMotor(frontMotorRight, -power);
                moveMotor(backMotorLeft, -power);
                moveMotor(backMotorRight, -power);
            }
        }
    }

    public void turn(int angle, String direction) {
        gyro.resetZAxisIntegrator();
        if (direction.equals("left")) {
            while (gyro.getHeading() <= angle) {
                moveMotor(frontMotorLeft, -0.5);
                moveMotor(frontMotorRight, 0.5);
                moveMotor(backMotorLeft, -0.5);
                moveMotor(backMotorRight, 0.5);
            }
        } else if (direction.equals("right")) {
            while (gyro.getHeading() <= angle) {
                moveMotor(frontMotorLeft, 0.5);
                moveMotor(frontMotorRight, -0.5);
                moveMotor(backMotorLeft, 0.5);
                moveMotor(backMotorRight, -0.5);
            }
        }

    }

//    public void checkForDebris() {
//        if (distance.getLightDetectedRaw() > 200 && distance.getLightDetectedRaw() < 640) {
//            stopRobot();
//        }
//    }

    public void resetEncoders() {
        frontMotorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void stopRobot() {
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);
    }
}
