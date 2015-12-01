package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Kashyap on 11/25/15.
 */
public class DistanceSensorTest extends OpMode {

    OpticalDistanceSensor distance;

    @Override
    public void init() {
        distance = hardwareMap.opticalDistanceSensor.get("distance");
    }

    @Override
    public void loop() {
        telemetry.addData("Light Detected", distance.getLightDetected());
        telemetry.addData("Raw Light Detected", distance.getLightDetectedRaw());
        telemetry.addData("Status", distance.status());
    }
}
