package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Kashyap on 11/25/15.
 */

/**
 * This is a simple test that detects if the limit switch is pressed. It returns the answer to the phone.
 */
public class LimitSwitchTest extends OpMode {

    TouchSensor limitSwitch;

    @Override
    public void init() {
        limitSwitch = hardwareMap.touchSensor.get("switch");
    }

    @Override
    public void loop() {
        telemetry.addData("Pressed", limitSwitch.isPressed());
    }
}
