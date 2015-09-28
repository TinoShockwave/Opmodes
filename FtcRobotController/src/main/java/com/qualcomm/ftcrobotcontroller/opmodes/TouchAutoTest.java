package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class TouchAutoTest extends OpMode {

    TouchSensor touch;
    @Override
    public void init() {
        touch = hardwareMap.touchSensor.get("touch_1");
    }

    @Override
    public void loop() {
        checkPressed();
    }

    public void checkPressed() {
        if(touch.isPressed()==true) {
            telemetry.addData("TRUE","The button has been pressed.");
        } else {
            telemetry.addData("FALSE","The button has not been pressed");
        }
    }

    public void checkStrength() {
        telemetry.addData("Touch Strength: ",touch.getValue());
    }

    @Override
    public void stop() {

    }
}