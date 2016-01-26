package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ShruthiJaganathan on 1/25/16.
 */
public class AutonomousRed_v2 extends LinearOpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
    DcMotor arm;
    Servo servo1;
    Servo servo2;
    Servo servo3;
    GyroSensor gyro;

    final double MAX_POWER = 0.5;
    final double AXLE_MAX_POWER = 0.8;
    final double TURNING_POWER = 0.5;
    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1;
    final static double WHEEL_CIRCUMFERENCE = 7.85;

    double startTime;
    double currentTime;

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

        waitForStart();

        frontMotorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        startTime = this.time;

        //Start off with stop
        currentTime = this.time;
        while (this.time - currentTime <= 2) {
            frontMotorLeft.setPower(0);
            frontMotorRight.setPower(0);
            backMotorLeft.setPower(0);
            backMotorRight.setPower(0);
            axleMotorFront.setPower(0);
            axleMotorBack.setPower(0);
        }


        //Go forward
        // Input distance in inches for the distance variable.
        double distance = 72;
        double encoderClicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR;
        while (frontMotorLeft.getCurrentPosition() < encoderClicks && frontMotorRight.getCurrentPosition() < encoderClicks) {
            frontMotorLeft.setPower(MAX_POWER);
            frontMotorRight.setPower(MAX_POWER);
            backMotorLeft.setPower(MAX_POWER);
            backMotorRight.setPower(MAX_POWER);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);


        //Bring back axle down
        currentTime = this.time;
        while (this.time - currentTime <= 2) {
            axleMotorBack.setPower(AXLE_MAX_POWER);
        }
        axleMotorBack.setPower(0);


        //Turn 90 degrees
        while (gyro.getHeading() <= 50 || gyro.getHeading() >= 280) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(MAX_POWER);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);


        //Go forward
        int frontLeftClicks = frontMotorLeft.getCurrentPosition();
        int frontRightClicks = frontMotorRight.getCurrentPosition();
        while (frontMotorLeft.getCurrentPosition() - frontLeftClicks < 1000 &&
                frontMotorRight.getCurrentPosition() - frontRightClicks < 1000) {
            frontMotorLeft.setPower(MAX_POWER);
            frontMotorRight.setPower(MAX_POWER);
            backMotorLeft.setPower(MAX_POWER);
            backMotorRight.setPower(MAX_POWER);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);


        //Stop
        currentTime = this.time;
        while (this.time - currentTime <= 2) {
            frontMotorLeft.setPower(0);
            frontMotorRight.setPower(0);
            backMotorLeft.setPower(0);
            backMotorRight.setPower(0);
            axleMotorFront.setPower(0);
            axleMotorBack.setPower(0);
        }

        //Activate the servos
        servo3.setPosition(1);
    }

}