package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class SixWheelDrive extends OpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
    Servo servo1;

    /**
     * Constructor
     */
    public SixWheelDrive() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {


      /*
       * Use the hardwareMap to get the dc motors and servos by name. Note
       * that the names of the devices must match the names used when you
       * configured your robot and created the configuration file.
       */

      /*
       * We also assume that there is one servo "servo_1."
       *    "servo_1" controls the manipulator.
       */
        frontMotorLeft = hardwareMap.dcMotor.get("motor_2");
        frontMotorRight = hardwareMap.dcMotor.get("motor_1");
        backMotorLeft = hardwareMap.dcMotor.get("motor_2");
        backMotorRight = hardwareMap.dcMotor.get("motor_1");
        axleMotorFront = hardwareMap.dcMotor.get("motor_1");
        axleMotorBack = hardwareMap.dcMotor.get("motor_1");
        servo1 = hardwareMap.servo.get("manipulator");
        frontMotorLeft.setDirection(DcMotor.Direction.REVERSE);
        backMotorLeft.setDirection(DcMotor.Direction.REVERSE);

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

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;
        float right = throttle - direction;
        float left = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        // write the values to the motors
        frontMotorLeft.setPower(left);
        frontMotorRight.setPower(right);
        backMotorLeft.setPower(left);
        backMotorRight.setPower(right);

        //Manipulator
        if (gamepad1.a) {
            servo1.setPosition(0);
        }
        else {
            servo1.setPosition(0.5);
        }

        if (gamepad1.y) {
            servo1.setPosition(1);
        }
        else {
            servo1.setPosition(0.5);
        }


        //For going up the ramp. 20 is a placeholder for the actual speed.
        if(gamepad1.x) {
            axleMotorFront.setPower(20);
            axleMotorBack.setPower(20);
        }
        else {
            axleMotorBack.setPower(0);
            axleMotorFront.setPower(0);
        }



        // both axleMotorFront and axleMotorBack require the same set values



      /*
       * Send telemetry data back to driver station. Note that if we are using
       * a legacy NXT-compatible motor controller, then the getPower() method
       * will return a null value. The legacy NXT-compatible motor controllers
       * are currently write only.
       */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));

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
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

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