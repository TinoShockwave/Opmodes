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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * TeleOp Mode
 * Enables control of the robot via the gamepad
 */

public class TeleOp6038 extends OpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
    DcMotor arm;
    TouchSensor limitSwitch1;
    Servo servo1;
    Servo servo2;
    Servo servo3;
    GyroSensor gyro;


    // Initialize SLOW and TURBO Modes
    int mode = 0;
    final int TURBO_MODE = 0;
    final int SLOW_MODE = 1;

    boolean activateServo = true;
    double position3 = 0.5;


    public TeleOp6038() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     */
    @Override
    public void init() {
      /*
       * Use the hardwareMap to get the dc motors and servos by name. Note
       * that the names of the devices must match the names used when you
       * configured your robot and created the configuration file.
       */
        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");
        arm = hardwareMap.dcMotor.get("motor_7");
        limitSwitch1 = hardwareMap.touchSensor.get("limit");
        servo1 = hardwareMap.servo.get("servo_1");
        servo2 = hardwareMap.servo.get("servo_2");
        servo3 = hardwareMap.servo.get("servo_3");
        gyro = hardwareMap.gyroSensor.get("gyro");

        frontMotorRight.setDirection(DcMotor.Direction.REVERSE);
        backMotorRight.setDirection(DcMotor.Direction.REVERSE);

        gyro.calibrate();

        /*
         * Motors axleMotorFront and axleMotorBack are set in staggered
         * position maintained on the mid-cross channel. Therefore both
         * motors will need to rotate in the same direction.
         */


    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

        if (activateServo) {
            servo3.setPosition(position3);
        }

        // Change current speed mode based on the joystick bumper
        if (gamepad1.right_bumper) {
            mode = TURBO_MODE;
        } else {
            mode = SLOW_MODE;
        }

        float leftY = -gamepad1.left_stick_y;
        float rightY = -gamepad1.right_stick_y;

        leftY = (float)scaleInput(leftY);
        rightY = (float)scaleInput(rightY);

        frontMotorLeft.setPower(leftY);
        frontMotorRight.setPower(rightY);
        backMotorLeft.setPower(leftY);
        backMotorRight.setPower(rightY);


//      For the arms
        if (gamepad1.a) {
            //The big arm
            if (gamepad1.dpad_up) {
                arm.setPower(-1);
            }
            else if (gamepad1.dpad_down) {
                arm.setPower(1);
            }
            else {
                arm.setPower(0);
            }
        }
        // servo3 is in port 1
        else if (gamepad1.y) {
            //The little arm
            if (gamepad1.dpad_up) {
                position3 -= 0.1;
                if (position3 <= 0) {
                    position3 = 0;
                }
                servo3.setPosition(position3);
            }
            else if (gamepad1.dpad_down) {
                position3 += 0.1;
                if (position3 >= 1) {
                    position3 = 1;
                }
                servo3.setPosition(position3);
            }
        }
        else {
            arm.setPower(0);
        }

//      For the side servos
        if (gamepad2.x) {
            if (gamepad2.dpad_left) {
                servo1.setPosition(0);
            }
            else if (gamepad2.dpad_right) {
                servo1.setPosition(1);
            }
        }
        else if (gamepad2.b) {
            if (gamepad2.dpad_right) {
                servo2.setPosition(0);
            }
            else if (gamepad2.dpad_left) {
                servo2.setPosition(1);
            }
        }

//        For going up the ramp.
        if (gamepad2.y) {
            //Front axle
            if(gamepad2.dpad_down){
                axleMotorFront.setPower(-1);
            }
            else if(gamepad2.dpad_up){
                if (limitSwitch1.isPressed()) {
                    axleMotorFront.setPower(0);
                }
                else {
                    axleMotorFront.setPower(1);
                }
            }
            else {
                axleMotorFront.setPower(0);
            }
        } else if (gamepad2.a) {
            //Back axle
            if (gamepad2.dpad_up) {
                axleMotorBack.setPower(1);
            } else if (gamepad2.dpad_down) {
                axleMotorBack.setPower(-1);
            } else {
                axleMotorBack.setPower(0);
            }
        } else if (gamepad2.right_bumper) {
            //Both axles
            if (gamepad2.dpad_up) {
                axleMotorFront.setPower(1);
                axleMotorBack.setPower(1);
            }
            else if (gamepad2.dpad_down) {
                axleMotorFront.setPower(-1);
                axleMotorBack.setPower(-1);
            }
            else {
                axleMotorFront.setPower(0);
                axleMotorBack.setPower(1);
            }
        } else {
            axleMotorFront.setPower(0);
            axleMotorBack.setPower(0);
        }



        //Send telemetry data back to driver station.
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", leftY));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightY));
        telemetry.addData("Front LEFT Encoders", frontMotorLeft.getCurrentPosition());
        telemetry.addData("Front RIGHT Encoders", frontMotorRight.getCurrentPosition());
        telemetry.addData("Gyro Heading", gyro.getHeading());

    }

    @Override
    public void stop() {

    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {

        double[] scaleArray = new double[17];

        /**
         * There is a turbo mode and a slow mode.
         * The turbo mode is 3 times as fast as the slow mode.
         */

        if (mode == TURBO_MODE) {
            scaleArray = new double[]{0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                    0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        } else if (mode == SLOW_MODE) {
            scaleArray = new double[]{0.0, 0.01667, 0.03, 0.0333, 0.04, 0.05, 0.06, 0.08,
                    0.10, 0.12, 0.143, 0.1667, 0.2, 0.24, 0.283, 0.333, 0.333};
        }

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}