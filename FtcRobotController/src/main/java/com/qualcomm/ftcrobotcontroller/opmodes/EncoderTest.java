package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Kashyap on 11/6/15.
 */
public class EncoderTest extends OpMode {

    DcMotor motor;

    public void init() {
        motor = hardwareMap.dcMotor.get("motor");
        motor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void start() {
        motor.setTargetPosition(2);
        motor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motor.setPower(0.5);
    }

    public void loop() {
        telemetry.addData("Encoder Clicks", motor.getCurrentPosition());
    }
}




