package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Kashyap on 11/6/15.
 */
public class EncoderTest extends OpMode {

    DcMotor frontMotorLeft;
    DcMotor frontMotorRight;
    DcMotor backMotorLeft;
    DcMotor backMotorRight;
    DcMotor axleMotorFront;
    DcMotor axleMotorBack;

    public void init() {
        frontMotorLeft = hardwareMap.dcMotor.get("motor");
        frontMotorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void start() {
        frontMotorLeft.setTargetPosition(2);
        frontMotorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        frontMotorLeft.setPower(0.1);
    }

    public void loop() {
        telemetry.addData("Encoder Clicks", frontMotorLeft.getCurrentPosition());
    }
}




