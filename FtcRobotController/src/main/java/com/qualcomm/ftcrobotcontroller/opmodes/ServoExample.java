
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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;


public class ServoExample extends OpMode {

    double servoPos;
    boolean activateServo = true;
    Servo servo1;
    public static final double MAX_POSITION = 1;
    public static final double MIN_POSITION = 0;
    Servo servo2;
    double currentTime;
    int state;

    public ServoExample() {

    }

    @Override
    public void init() {
        servo1 = hardwareMap.servo.get("servo_1");
//        servo2 = hardwareMap.servo.get("servo_2");

    }

    //Servo positions: > 0.5 is counterclockwise, 0.5 is full stop, and < 0.5 clockwise. Minimum = 0, Max = 1
    //Press A to move the servo counterclockwise. Press B to stop it. Press X to move it clockwise.
    public void loop() {

        if (activateServo) {
            servoPos = servo1.getPosition();
            servo1.setPosition(servoPos);
        }

        if (gamepad1.y) {
            //The little arm
            if (gamepad1.dpad_up) {
                servo1.setPosition(0);
            }
            else if (gamepad1.dpad_down) {
                servo1.setPosition(1);
            }
            else {
                servo1.setPosition(0.5);
            }
        }
        else {
            servo1.setPosition(0.5);
        }

        telemetry.addData("Servo Position", servo1.getPosition());

//        if (gamepad1.x) {
//            servo2.setPosition(0);
//        }
//        else if (gamepad1.b) {
//            servo2.setPosition(1);
//        }
//        else {
//            servo2.setPosition(0.5);
//        }
    }

    public void stop() {

    }
}