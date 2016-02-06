
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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;                                  //imports files and other packages
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class TwoWheelOp extends OpMode {

    DcMotor motorRight;                                                             //initialize right Dc motor
    DcMotor motorLeft;                                                          //initialize left Dc motor
    double maxSpeed;
    @Override
    public void init() {

        motorRight = hardwareMap.dcMotor.get("motor_2");              //initializes each motor of its direction
        motorLeft = hardwareMap.dcMotor.get("motor_1");

        motorRight.setDirection(DcMotor.Direction.REVERSE);                               //Sets direction of one motor
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        maxSpeed = 0.75;
    }

    @Override
    public void loop() {

        float throttle = -gamepad1.left_stick_y;                           //Changes negative input to positive for clarity
        float direction = gamepad1.left_stick_x;
        double right = throttle - direction;        //Makes direction opposite of default direction returned by the throttle
        double left = throttle + direction;


        right = Range.clip(right, -maxSpeed, maxSpeed);                                            //Initializes speed ranges
        left = Range.clip(left, -maxSpeed, maxSpeed);

        right = (float)scaleInput(right);                                            //Initializes scale of input
        left =  (float)scaleInput(left);

        motorRight.setPower(right);                                                    //Set Power of the right motor
        motorLeft.setPower(left);                                                  //Set Power of the left motor


        telemetry.addData("Text", "*** Robot Data***");                                     //Adds data from the phones
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));

        if (gamepad1.a) {
            if (!(maxSpeed == 0.95)) {
                maxSpeed = maxSpeed + 0.05;
                telemetry.addData("maxSpeed", "maxSpeed:" + String.format("%.2f", maxSpeed));
            }
        }

        if (gamepad1.b) {
            if (!(maxSpeed == 0.0)) {
                maxSpeed = maxSpeed - 0.05;
            }
        }
    }


    @Override
    public void stop() {

    }

    double scaleInput(double dVal)  {                                               //Scales input, and makes it more accurate
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {                                                             //sets scale of input
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

}