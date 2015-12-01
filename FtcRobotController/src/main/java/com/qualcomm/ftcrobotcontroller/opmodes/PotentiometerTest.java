package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DeviceManager;

/**
 * Created by Kashyap on 11/25/15.
 */
public class PotentiometerTest extends OpMode {

    AnalogInput potentiometer;

    @Override
    public void init() {
        potentiometer = hardwareMap.analogInput.get("potentiometer");
    }

    @Override
    public void loop() {
        telemetry.addData("Connection Info", potentiometer.getConnectionInfo());
        telemetry.addData("Value", potentiometer.getValue());
    }
}
