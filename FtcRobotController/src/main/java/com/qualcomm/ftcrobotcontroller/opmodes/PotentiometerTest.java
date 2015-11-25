package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.AnalogOutput;

/**
 * Created by Kashyap on 11/25/15.
 */
public class PotentiometerTest extends OpMode {

    AnalogOutput potentiometer;

    @Override
    public void init() {
        potentiometer = hardwareMap.analogOutput.get("potentiometer");
    }

    @Override
    public void loop() {
        telemetry.addData("Connection Info", potentiometer.getConnectionInfo());
    }
}
