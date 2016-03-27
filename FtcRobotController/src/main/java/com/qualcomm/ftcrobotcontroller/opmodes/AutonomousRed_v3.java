/*
 * Copyright (c) 2015-2016
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kashyap on 3/19/16.
 * @author Kashyap Panda
 */
public class AutonomousRed_v3 extends LinearOpMode {

     DcMotor frontMotorLeft;
     DcMotor frontMotorRight;
     DcMotor backMotorLeft;
     DcMotor backMotorRight;
     GyroSensor gyro;
     Servo servo3;

     final double MAX_POWER = 0.8;
     final double TURN_POWER = 1.0;
     final static int ENCODER_CPR = 1120;
     final static double GEAR_RATIO = 1;
     final static double WHEEL_CIRCUMFERENCE = 7.85;

    double currentTime;

    @Override
    public void runOpMode() throws InterruptedException {
        hardwareMap.logDevices();

        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        servo3 = hardwareMap.servo.get("servo_3");
        gyro = hardwareMap.gyroSensor.get("gyro");

        frontMotorRight.setDirection(DcMotor.Direction.REVERSE);
        backMotorRight.setDirection(DcMotor.Direction.REVERSE);

        frontMotorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        //move forward 2 feet
        moveRobot(24);
        //turn towards the beacon
        currentTime = this.time;
        while (this.time - currentTime < 1.5) {
            frontMotorLeft.setPower(-1);
            frontMotorRight.setPower(1);
            backMotorLeft.setPower(-1);
            backMotorRight.setPower(1);
        }
        stopRobot();
        //move forward 34 inches
        moveRobot(34);
        //activate arm
        currentTime = this.time;
        while (this.time - currentTime < 1) {
            servo3.setPosition(1);
        }
        servo3.setPosition(0.5);
    }

     public void moveRobot(int distance) {
        stopRobot();
        double clicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR * -1;
        frontMotorLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontMotorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        while (frontMotorLeft.getCurrentPosition() > clicks || frontMotorRight.getCurrentPosition() > clicks) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(-MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(-MAX_POWER);
            telemetry.addData("RCLicks", frontMotorRight.getCurrentPosition());
            telemetry.addData("LCLicks", frontMotorLeft.getCurrentPosition());
        }
        stopRobot();
    }

     public void stopRobot() {
        currentTime = this.time;
        while (this.time - currentTime < 1) {
            frontMotorLeft.setPower(0);
            frontMotorRight.setPower(0);
            backMotorLeft.setPower(0);
            backMotorRight.setPower(0);
        }
    }
}
