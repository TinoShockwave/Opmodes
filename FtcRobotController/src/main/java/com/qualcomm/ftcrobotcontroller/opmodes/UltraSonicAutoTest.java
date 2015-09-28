package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public class UltraSonicAutoTest extends OpMode {

    UltrasonicSensor ultra;

    @Override
    public void init() {
        ultra = hardwareMap.ultrasonicSensor.get("ultra_1");
    }

    @Override
    public void loop() {
        displayUltraSonicStatus();
    }

    public void displayUltraSonicStatus() {
        telemetry.addData("Status of UltraSonic: ", ultra.status());
    }

    public void displayLevel() {
        telemetry.addData("Level detected: ", ultra.getUltrasonicLevel());
    }

    @Override
    public void stop() {

    }
}