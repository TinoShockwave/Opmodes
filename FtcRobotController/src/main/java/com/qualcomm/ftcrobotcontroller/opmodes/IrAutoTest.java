package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/**
 * Created by Yusuf on 9/4/15.
 */
public class IrAutoTest extends OpMode {

    //DcMotor motorRight;
    //DcMotor motorLeft;
    IrSeekerSensor irSensor;

    public IrAutoTest() {

    }

    public void init() {


      /*
       * Use the hardwareMap to get the dc motors and servos by name. Note
       * that the names of the devices must match the names used when you
       * configured your robot and created the configuration file.
       */

      /*
       * For the demo Tetrix K9 bot we assume the following,
       *   There are two motors "motor_1" and "motor_2"
       *   "motor_1" is on the right side of the bot.
       *   "motor_2" is on the left side of the bot and reversed.
       *
       * We also assume that there is one IR Seeker Sensor "ir_1"
       *    "ir_1" is the sensor to help see which way the robot should face
       */
        //motorRight = hardwareMap.dcMotor.get("motor_2");
        //motorLeft = hardwareMap.dcMotor.get("motor_1");
        //motorLeft.setDirection(DcMotor.Direction.REVERSE);
        //motorRight.setDirection(DcMotor.Direction.REVERSE);

        irSensor = hardwareMap.irSeekerSensor.get("ir_seeker");

    }
    public void loop() {

        double degree = irSensor.getAngle();
        double strength = irSensor.getStrength();
        double power; //this variable stores the power of the motor as a double

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
        // Setting the direction based on the angle
        //float direction = 1; // Moves continuous in this direction in a cicle
        //float throttle = 0; // only need to face the right direction

       // float right = throttle - direction;
        //float left = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1

        //robot is stationary until it detects the IrSensor...
        //motorRight.setPower(0);
        //motorLeft.setPower(0);

        // write the values to the motors

        /*if (degree > 0) {
            //if the irsensor is to the right, turn the robot right
            motorLeft.setPower(0.25);
            motorRight.setPower(-0.15);
        } else if (degree < 0) {
            //if the irsensor is to the left, turn the robot left
            motorLeft.setPower(-0.15);
            motorRight.setPower(0.25);
        } else {
            if (degree == 0) {
                if (strength == 1.0) { //if the strength is maximum, that means that the robot has approached the ir sensor with success
                    motorLeft.setPower(0);
                    motorRight.setPower(0);
                } else { //if the strength is less than 1.0, then the robot can inch forward until the robot has reached the ir sensor
                    while (strength < 1.0) { //keep inching forward while the strength is less
                        motorLeft.setPower(0.15);
                        motorRight.setPower(0.15);
                        if (strength > 0.95) { //but at 95% strength, break the loop
                            break;
                        }
                    }
                }
            }
        }*/



      /*
       * Send telemetry data back to driver station. Note that if we are using
       * a legacy NXT-compatible motor controller, then the getPower() method
       * will return a null value. The legacy NXT-compatible motor controllers
       * are currently write only.
       */
        telemetry.addData("Angle: ", irSensor.getAngle());
       // telemetry.addData("Power Left: ", motorLeft.getPower());
        //telemetry.addData("Power Right: ", motorRight.getPower());
        telemetry.addData("Strength: ", strength);
        telemetry.addData("Angle: ", degree);
        telemetry.addData("Detected: ", irSensor.signalDetected());
    }

    public void stop() {


    }

    double scaleInput(double dVal) {                                               //Scales input, and makes it more accurate
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

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