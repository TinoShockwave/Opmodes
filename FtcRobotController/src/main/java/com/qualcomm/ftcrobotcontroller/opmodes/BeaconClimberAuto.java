package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public class BeaconClimberAuto extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    UltrasonicSensor ultra;

    double rightMaxSpeed = 0.75;
    double leftMaxSpeed = -0.75;

    @Override
    public void init() {

        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        ultra = hardwareMap.ultrasonicSensor.get("ultra_1");

        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (ultra.status().equals("0.0")) {
            forward();
        }
    }

    @Override
    public void stop() {

    }

    public void forward() {
        motorRight.setPower(rightMaxSpeed);
        motorLeft.setPower(leftMaxSpeed);
    }

}