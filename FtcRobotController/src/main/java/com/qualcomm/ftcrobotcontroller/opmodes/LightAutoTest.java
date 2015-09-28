package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.LightSensor;

public class LightAutoTest extends OpMode {

    LightSensor light;

    @Override
    public void init() {
        light = hardwareMap.lightSensor.get("light_1");
    }

    @Override
    public void loop() {
        checkLightStatus();
        displayLight();
    }

    public void checkLightStatus() {
        telemetry.addData("Status of the Light: ",light.status());
    }

    public void displayLight() {
        telemetry.addData("Light Strength: ",light.getLightDetected());
    }

    @Override
    public void stop() {

    }
}