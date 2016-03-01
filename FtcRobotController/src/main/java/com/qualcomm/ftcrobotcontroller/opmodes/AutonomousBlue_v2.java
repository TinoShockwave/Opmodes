/*
 * Copyright (c) 2015 - 2016 Tino Shockwave
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
 * Created by Kashyap Panda on 1/25/16.
 * @author Kashyap
 */
public class AutonomousBlue_v2 extends LinearOpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
    DcMotor arm;
    Servo servo3;
//    GyroSensor gyro;

    final double MAX_POWER = 0.6;
    final double AXLE_MAX_POWER = 0.5;
    final double TURNING_POWER = 0.3;
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
        arm = hardwareMap.dcMotor.get("motor_7");
        servo3 = hardwareMap.servo.get("servo_3");
//        gyro = hardwareMap.gyroSensor.get("gyro");

        frontMotorRight.setDirection(DcMotor.Direction.REVERSE);
        backMotorRight.setDirection(DcMotor.Direction.REVERSE);

//        gyro.calibrate();

        waitForStart();

//        while (gyro.isCalibrating()) {
//            Thread.sleep(50);
//        }

        frontMotorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontMotorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        startTime = this.time;

        //Start off with a quick stop
        stopRobot();
        stopRobot();
        stopRobot();
        stopRobot();

        //go forward a bit
        currentTime = this.time;
        while (this.time - currentTime < 0.4) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(-MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(-MAX_POWER);
        }
        stopRobot();

        //Turn towards the beacon and lift the arm
        currentTime = this.time;
        while (this.time - currentTime < 1.5) {
            frontMotorLeft.setPower(-0.4);
            frontMotorRight.setPower(0);
            backMotorLeft.setPower(-0.4);
            backMotorRight.setPower(0);
            arm.setPower(-0.8);
        }
        stopRobot();

        //Bring front axle down
        axleMotorFront.setPower(0);
        currentTime = this.time;
        while (this.time - currentTime < 2.5) {
            axleMotorFront.setPower(-AXLE_MAX_POWER);
        }
        axleMotorFront.setPower(0);
        stopRobot();

        // move back axle down a bit
        axleMotorBack.setPower(0);
        currentTime = this.time;
        while (this.time - currentTime < 0.8) {
            axleMotorBack.setPower(-AXLE_MAX_POWER);
        }
        axleMotorBack.setPower(0);
        stopRobot();

        //go forward
        currentTime = this.time;
        while (this.time - currentTime < 4.5) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(-MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(-MAX_POWER);
        }
        stopRobot();

        //turn a little bit more
        currentTime = this.time;
        while (this.time - currentTime < 1.2) {
            frontMotorLeft.setPower(-1);
            frontMotorRight.setPower(1);
            backMotorLeft.setPower(-1);
            backMotorRight.setPower(1);
        }
        stopRobot();

        //move forward
        currentTime = this.time;
        while (this.time - currentTime < 3.5) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(-MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(-MAX_POWER);
        }
        stopRobot();

        // Move the servos
        servo3.setPosition(1);

        stopRobot();

        servo3.setPosition(0);
    }

    public void stopRobot() {
        currentTime = this.time;
        while (this.time - currentTime <= 1) {
            frontMotorLeft.setPower(0);
            frontMotorRight.setPower(0);
            backMotorLeft.setPower(0);
            backMotorRight.setPower(0);
            axleMotorFront.setPower(0);
            axleMotorBack.setPower(0);
            arm.setPower(0);
        }
    }
}