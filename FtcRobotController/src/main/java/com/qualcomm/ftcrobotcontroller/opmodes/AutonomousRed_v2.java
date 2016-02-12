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

/**
 * Created by Kashyap Panda on 1/25/16.
 * @author Kashyap
 */
public class AutonomousRed_v2 extends LinearOpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
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
        servo3 = hardwareMap.servo.get("servo_3");
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
        double distance = 50;
        double encoderClicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR;
        while (frontMotorLeft.getCurrentPosition() < encoderClicks && frontMotorRight.getCurrentPosition() < encoderClicks) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(-MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(-MAX_POWER);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        //Bring front axle down
        currentTime = this.time;
        while (this.time - currentTime <= 2.5) {
            axleMotorFront.setPower(-AXLE_MAX_POWER);
        }
        axleMotorFront.setPower(0);

        //Go forward
        // Input distance in inches for the distance variable.
        distance = -12;
        encoderClicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR;
        while (frontMotorLeft.getCurrentPosition() < encoderClicks && frontMotorRight.getCurrentPosition() < encoderClicks) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(-MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(-MAX_POWER);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        //Turn 90 degrees
        gyro.resetZAxisIntegrator();
        while (gyro.getHeading() < 270) {
            frontMotorLeft.setPower(TURNING_POWER);
            frontMotorRight.setPower(-TURNING_POWER);
            backMotorLeft.setPower(TURNING_POWER);
            backMotorRight.setPower(-TURNING_POWER);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        //Go backward
        distance = -72;
        encoderClicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR;
        int frontLeftClicks = frontMotorLeft.getCurrentPosition();
        int frontRightClicks = frontMotorRight.getCurrentPosition();
        while (frontMotorLeft.getCurrentPosition() - frontLeftClicks < encoderClicks &&
                frontMotorRight.getCurrentPosition() - frontRightClicks < encoderClicks) {
            frontMotorLeft.setPower(-MAX_POWER);
            frontMotorRight.setPower(-MAX_POWER);
            backMotorLeft.setPower(-MAX_POWER);
            backMotorRight.setPower(-MAX_POWER);
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

        //Activate the arm
        while (servo3.getPosition() != 0) {
            servo3.setPosition(0);
        }
    }
}