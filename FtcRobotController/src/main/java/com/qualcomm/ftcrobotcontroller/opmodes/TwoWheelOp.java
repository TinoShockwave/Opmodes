package com.qualcomm.ftcrobotcontroller.opmodes;

// Created by Yusuf on 9/4/2015

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
        maxSpeed = 0.5;
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