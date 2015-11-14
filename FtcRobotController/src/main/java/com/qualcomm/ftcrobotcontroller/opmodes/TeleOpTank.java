package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * Enables control of the robot via the gamepad
 */
public class TeleOpTank extends OpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;
//    Servo servo1;
//    Servo servo2;
//    Servo servo3;
    //int numOfMotors;


    // Initialize SLOW and TURBO Modes
    int mode = 0;
    final int TURBO_MODE = 0;
    final int SLOW_MODE = 1;
    /**
     * Constructor
     */
    public TeleOpTank() {

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
        frontMotorLeft = hardwareMap.dcMotor.get("motor_1");
        frontMotorRight = hardwareMap.dcMotor.get("motor_2");
        backMotorLeft = hardwareMap.dcMotor.get("motor_3");
        backMotorRight = hardwareMap.dcMotor.get("motor_4");
        axleMotorFront = hardwareMap.dcMotor.get("motor_5");
        axleMotorBack = hardwareMap.dcMotor.get("motor_6");
//        servo1 = hardwareMap.servo.get("manipulator");
//        servo2 = hardwareMap.servo.get("unknown1");
//        servo3 = hardwareMap.servo.get("unknown2");
        frontMotorRight.setDirection(DcMotor.Direction.REVERSE);
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

//        Manipulator code. Commented out until the manipulator is attached.
//        while (gamepad2.a) {
//
//            if(gamepad2.dpad_up){
//              servo1.setPosition(1);
//              numOfMotors += 1;
//            }
//            else if(gamepad2.dpad_down){
//                servo1.setPosition(0);
//                numOfMotors += 1;
//            }
//            else{
//                servo1.setPosition(0.5);
//            }
//
//        }

//        For going up the ramp.
        if (gamepad1.x) {
            if(gamepad1.dpad_up){
                axleMotorFront.setPower(1);
                //numOfMotors++;
            }
            else if(gamepad1.dpad_down){
                axleMotorFront.setPower(-1);
                //numOfMotors++;
            }
            else {
                axleMotorFront.setPower(0);
            }
        }else if (gamepad1.b) {
            if (gamepad1.dpad_up){
                axleMotorBack.setPower(1);
                //numOfMotors++;
            }
            else if (gamepad1.dpad_down){
                axleMotorBack.setPower(-1);
                //numOfMotors++;
            }
            else{
                axleMotorBack.setPower(0);
            }
        }else {
            axleMotorFront.setPower(0);
            axleMotorBack.setPower(0);
        }


//        if (numOfMotors > 6){
//            frontMotorLeft.setPower(0);
//            backMotorLeft.setPower(0);
//            frontMotorRight.setPower(0);
//            backMotorRight.setPower(0);
//            axleMotorBack.setPower(0);
//            axleMotorFront.setPower(0);
//            servo1.setPosition(0.5);
//        }



        // both axleMotorFront and axleMotorBack require the same set values



      /*
       * Send telemetry data back to driver station. Note that if we are using
       * a legacy NXT-compatible motor controller, then the getPower() method
       * will return a null value. The legacy NXT-compatible motor controllers
       * are currently write only.
       */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", leftY));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightY));

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

        // Change the scaleArray based on the current mode
        // The slow mode is 1/3 the speed of the fast mode
        if (mode == TURBO_MODE) {
            scaleArray = new double[]{0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                    0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        } else if (mode == SLOW_MODE) {
            scaleArray = new double[]{0.0, 0.0167, 0.03, 0.0333, 0.04, 0.05, 0.06, 0.08,
                    0.10, 0.12, 0.143, 0.167, 0.2, 0.24, 0.283, 0.33, 0.33};
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