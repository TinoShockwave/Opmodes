package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Kashyap on 1/6/16.
 */
public class AutonomousRed extends LinearOpMode {
    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1;
    final static double WHEEL_CIRCUMFERENCE = 7.85;

    double currentTime;

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

    @Override
    public void runOpMode() throws InterruptedException {

        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");
        arm = hardwareMap.dcMotor.get("motor_7");
        servo1 = hardwareMap.servo.get("servo_1");
        servo2 = hardwareMap.servo.get("servo_2");
        servo3 = hardwareMap.servo.get("servo_3");
        gyro = hardwareMap.gyroSensor.get("gyro");

        frontMotorRight.setDirection(DcMotor.Direction.REVERSE);
        backMotorRight.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();

        gyro.calibrate();
        while (gyro.isCalibrating()) {
            Thread.sleep(500);
        }

        waitForStart();

        moveAxleMotors(-1, 1.8);
        stopRobot();
        moveRobot(100, 0.7, "backward");
        stopRobot();
        servo3.setPosition(1);
//        moveRobot(24, 0.7, "forward");
//        turn(45, "left");
//        moveRobot(68, 0.7, "forward");
//        turn(-135, "left");
//        moveRobot(48, 0.6, "backward");
//        moveServo(servo3, 0);
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

    public void moveServo(Servo servo, double position) {
        servo.setPosition(position);
    }

    public void moveMotor(DcMotor motor, double power) {
        motor.setPower(power);
    }

    public void moveMotorTime(DcMotor motor, double power, int time) {
        updateTime();
        while(this.time - currentTime <= time) {
            motor.setPower(power);
        }
        motor.setPower(0);
    }

    public void moveAxleMotors(double power, double time) {
        updateTime();
        while (this.time - currentTime <= time) {
            axleMotorFront.setPower(power);
            axleMotorBack.setPower(power);
        }
        axleMotorBack.setPower(0);
        updateTime();
        while (this.time - currentTime <= 1) {
            axleMotorFront.setPower(power);
        }
        axleMotorFront.setPower(0);
        axleMotorBack.setPower(0);
    }

    public void updateTime() {
        currentTime = this.time;
    }

    public void moveRobot(double distance, double power, String direction) {
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
                telemetry.addData("Encoder Left", frontMotorLeft.getCurrentPosition());
                telemetry.addData("Encoder Right", frontMotorRight.getCurrentPosition());
            }
            stopRobot();
        } else if (direction.equals("backward")) {
            while (frontMotorLeft.getCurrentPosition() <= encoderClicks || frontMotorRight.getCurrentPosition() <= encoderClicks) {
                moveMotor(frontMotorLeft, -power);
                moveMotor(frontMotorRight, -power);
                moveMotor(backMotorLeft, -power);
                moveMotor(backMotorRight, -power);
                telemetry.addData("Encoder Left", frontMotorLeft.getCurrentPosition());
                telemetry.addData("Encoder Right", frontMotorRight.getCurrentPosition());
            }
            stopRobot();
        }
    }

    public void turn(int angle, String direction) {
        updateTime();
        gyro.resetZAxisIntegrator();
        if (direction.equals("left")) {
            while (gyro.getHeading() <= angle) {
                moveMotor(frontMotorLeft, -0.5);
                moveMotor(frontMotorRight, 0.5);
                moveMotor(backMotorLeft, -0.5);
                moveMotor(backMotorRight, 0.5);
            }
            stopRobot();
        } else if (direction.equals("right")) {
            while (gyro.getHeading() <= angle) {
                moveMotor(frontMotorLeft, 0.5);
                moveMotor(frontMotorRight, -0.5);
                moveMotor(backMotorLeft, 0.5);
                moveMotor(backMotorRight, -0.5);
            }
            stopRobot();
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
        axleMotorFront.setPower(0);
        axleMotorBack.setPower(0);
    }
}
