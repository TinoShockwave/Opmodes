package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous;
/**
 * Created by Kashyap Panda on 9/28/15.
 *

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
*/
public class AutonomousTest extends Autonomous {
    Autonomous myAuto;

    @Override
    public void init() {
        myAuto = new Autonomous();
    }

    @Override
    public void loop() {
        myAuto.moveMotor(myAuto.frontMotorLeft, 200, 0.5);
    }
}

