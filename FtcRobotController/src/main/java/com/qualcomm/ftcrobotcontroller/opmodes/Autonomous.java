
/*
 * Copyright (c) 2015 - 2016 Tino Shockwave
 *
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of Tino Shockwave nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 *
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Kashyap on 11/4/15.
 * @author Kashyap
 */
public class Autonomous extends LinearOpMode {
    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1;
    final static double WHEEL_CIRCUMFERENCE = 7.85;

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
//    OpticalDistanceSensor distance;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

    }

    public void initialize() {
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
//        checkForDebris();
    }

    public void moveMotor(DcMotor motor, double power) {
        motor.setPower(power);
//        checkForDebris();
    }

    public void moveServo(Servo servo, double position) {
        servo.setPosition(position);
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
                telemetry.addData("Encoder Left", frontMotorLeft.getCurrentPosition());
                telemetry.addData("Encoder Right", frontMotorRight.getCurrentPosition());
            }
        } else if (direction.equals("backward")) {
            while (frontMotorLeft.getCurrentPosition() <= encoderClicks || frontMotorRight.getCurrentPosition() <= encoderClicks) {
                moveMotor(frontMotorLeft, -power);
                moveMotor(frontMotorRight, -power);
                moveMotor(backMotorLeft, -power);
                moveMotor(backMotorRight, -power);
                telemetry.addData("Encoder Left", frontMotorLeft.getCurrentPosition());
                telemetry.addData("Encoder Right", frontMotorRight.getCurrentPosition());
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
