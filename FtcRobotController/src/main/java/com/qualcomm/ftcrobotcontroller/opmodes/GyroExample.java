package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kashyap on 11/6/15.
 */
public class GyroExample extends OpMode {
    GyroSensor gyro;
    Servo servo1;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        servo1 = hardwareMap.servo.get("servo");
    }


    @Override
    public void loop() {
        if (gamepad1.y) {
            servo1.setPosition(1);
        }
        else if (gamepad1.a) {
            servo1.setPosition(0);
        }
        else if (gamepad1.b) {
            servo1.setPosition(0.5);
        }

        telemetry.addData("Rotational Degrees", gyro.getHeading());
        telemetry.addData("Rotation", gyro.getRotation());
    }
}
