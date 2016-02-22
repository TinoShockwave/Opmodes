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

/**
 * Created by Kashyap on 2/20/16.
 */

/**
 * This is a program made to test all motors to make sure that they work correctly.
 */
public class MotorTester extends LinearOpMode{

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
    DcMotor arm;

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

        waitForStart();

        //Test wheels
        currentTime = this.time;
        while (this.time - currentTime < 3) {
            frontMotorLeft.setPower(1);
            frontMotorRight.setPower(1);
            backMotorLeft.setPower(1);
            backMotorRight.setPower(1);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        currentTime = this.time;
        while (this.time - currentTime < 3) {
            frontMotorLeft.setPower(0.5);
            frontMotorRight.setPower(0.5);
            backMotorLeft.setPower(0.5);
            backMotorRight.setPower(0.5);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        while (this.time - currentTime < 3) {
            frontMotorLeft.setPower(0);
            frontMotorRight.setPower(0);
            backMotorLeft.setPower(0);
            backMotorRight.setPower(0);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        currentTime = this.time;
        while (this.time - currentTime < 3) {
            frontMotorLeft.setPower(-0.5);
            frontMotorRight.setPower(-0.5);
            backMotorLeft.setPower(-0.5);
            backMotorRight.setPower(-0.5);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        currentTime = this.time;
        while (this.time - currentTime < 3) {
            frontMotorLeft.setPower(-1);
            frontMotorRight.setPower(-1);
            backMotorLeft.setPower(-1);
            backMotorRight.setPower(-1);
        }
        frontMotorLeft.setPower(0);
        frontMotorRight.setPower(0);
        backMotorLeft.setPower(0);
        backMotorRight.setPower(0);

        wait(100);

        //Test arm
        currentTime = this.time;
        while (this.time - currentTime < 2) {
            arm.setPower(-1);
        }
        arm.setPower(0);

        currentTime = this.time;
        while (this.time - currentTime < 2) {
            arm.setPower(1);
        }
        arm.setPower(0);
    }
}
