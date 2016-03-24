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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * TeleOp Mode
 * Enables control of the robot via the gamepad
 * @author Kashyap
 */

public class TeleOp6038 extends OpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
    DcMotor arm;
    DcMotor claw;
    TouchSensor limitSwitch1;
    Servo servo1;
    Servo servo2;
    Servo servo3;
    GyroSensor gyro;


    // Initialize SLOW and TURBO Modes
    int mode = 0;
    final int TURBO_MODE = 0;
    final int SLOW_MODE = 1;

    private boolean activateServo = true;
    double position3;


    public TeleOp6038() {

    }

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
        claw = hardwareMap.dcMotor.get("motor_8");
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
     */
    @Override
    public void loop() {

        if (activateServo) {
            position3 = servo3.getPosition();
            servo3.setPosition(position3);
            activateServo = false;
        }

        // Change current speed mode based on the joystick bumper
        if (gamepad1.right_bumper) {
            mode = TURBO_MODE;
        } else {
            mode = SLOW_MODE;
        }

        float leftY = -gamepad1.left_stick_y;
        float rightY = -gamepad1.right_stick_y;

        float leftY2 = gamepad2.left_stick_y;
        float rightY2 = gamepad2.right_stick_y;

        leftY = (float)scaleInput(leftY);
        rightY = (float)scaleInput(rightY);

        frontMotorLeft.setPower(leftY);
        frontMotorRight.setPower(rightY);
        backMotorLeft.setPower(leftY);
        backMotorRight.setPower(rightY);

        axleMotorFront.setPower(leftY2);
        axleMotorBack.setPower(rightY2);



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
            if (gamepad2.dpad_right) {
                servo1.setPosition(0);
            }
            else if (gamepad2.dpad_left) {
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

        //For the claw
        if (gamepad1.x) {
            if (gamepad1.dpad_up) {
                claw.setPower(1);
            }
            else if (gamepad1.dpad_down) {
                claw.setPower(-1);
            }
            else {
                claw.setPower(0);
            }
        }
        else {
            claw.setPower(0);
        }



        //Send telemetry data back to driver station.
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", leftY));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightY));
        telemetry.addData("RCLicks", frontMotorRight.getCurrentPosition());
        telemetry.addData("LCLicks", frontMotorLeft.getCurrentPosition());
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
            scaleArray = new double[]{0.0, 0.10, 0.18, 0.20, 0.24, 0.30, 0.36, 0.48,
                    0.60, 0.72, 0.86, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00};
        } else if (mode == SLOW_MODE) {
            scaleArray = new double[]{0.0, 0.02222666667, 0.04, 0.0444, 0.053333, 0.06667, 0.08, 0.106667,
                    0.13333, 0.16, 0.1906667, 0.2227, 0.266667, 0.32, 0.377, 0.444, 0.444};
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
