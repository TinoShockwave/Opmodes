/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Kashyap on 11/4/15.
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
    GyroSensor gyro;

    //Input: Distance in inches
    //Output: Distance in encoder pulses
    public void moveMotor(DcMotor motor, double distance, double power) {
        double encoderClicks = (distance / WHEEL_CIRCUMFERENCE) * GEAR_RATIO * ENCODER_CPR;
        while (motor.getCurrentPosition() < encoderClicks) {
            motor.setPower(power);
        }
        motor.setPower(0);
    }

    public void moveMotorFrvr(DcMotor motor, double power) {
        motor.setPower(power);
    }

    public void moveRobot(int distance, double power, String direction) {
        if (direction.equals("forward")) {
            moveMotor(frontMotorLeft, distance, power);
            moveMotor(frontMotorRight, distance, power);
            moveMotor(backMotorLeft, distance, power);
            moveMotor(backMotorRight, distance, power);
        } else if (direction.equals("backward")) {
            moveMotor(frontMotorLeft, distance, -power);
            moveMotor(frontMotorRight, distance, -power);
            moveMotor(backMotorLeft, distance, -power);
            moveMotor(backMotorRight, distance, -power);
        }
    }

    public void turn(int angle, String direction) {
        gyro.resetZAxisIntegrator();
        if (direction.equals("left")) {
            while (gyro.getHeading() <= angle) {
                moveMotorFrvr(frontMotorLeft, -0.5);
                moveMotorFrvr(frontMotorRight, 0.5);
                moveMotorFrvr(backMotorLeft, -0.5);
                moveMotorFrvr(backMotorRight, 0.5);
            }
        } else if (direction.equals("right")) {
            while (gyro.getHeading() <= angle) {
                moveMotorFrvr(frontMotorLeft, 0.5);
                moveMotorFrvr(frontMotorRight, -0.5);
                moveMotorFrvr(backMotorLeft, 0.5);
                moveMotorFrvr(backMotorRight, -0.5);
            }
        }

    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
