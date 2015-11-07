package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Kashyap on 11/6/15.
 */
public class GyroExample extends OpMode {
    GyroSensor gyro;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
    }

    @Override
    public void loop() {
        turn();
    }

    public void turn(){
        telemetry.addData("Rotation", gyro.getRotation());
    }

}
