package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Yusuf Mostafa on 9/12/15.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class FourWheelOp extends OpMode {

    DcMotor rMotor1;
    DcMotor lMotor1;
    DcMotor rMotor2;
    DcMotor lMotor2;

    @Override
    public void init() {
        rMotor1 = hardwareMap.dcMotor.get("right_motor1");
        lMotor1 = hardwareMap.dcMotor.get("left_motor1");
        rMotor2 = hardwareMap.dcMotor.get("right_motor1");
        lMotor2 = hardwareMap.dcMotor.get("left_motor1");

        lMotor1.setDirection(DcMotor.Direction.REVERSE);
        lMotor2.setDirection(DcMotor.Direction.REVERSE);
		
		/*
		 * We might need to change the directions in the
		 * future. Our robot uses staggered motors and 
		 * therefore same-side motors may need to spin in
		 * different directions.
		*/ 
    }

    @Override
    public void loop() {
        float lspeed = -gamepad1.left_stick_y;
        float rspeed = -gamepad1.right_stick_y;

        lspeed = Range.clip(lspeed, -1, 1);
        rspeed = Range.clip(rspeed, -1, 1);

        rMotor1.setPower(rspeed);
        lMotor1.setPower(lspeed);
        rMotor2.setPower(rspeed);
        lMotor2.setPower(lspeed);

        telemetry.addData("Text", "Status:");
        telemetry.addData("r motor speed", "Right speed: " + String.format("%.2f", rspeed));
        telemetry.addData("l motor speed", "Left speed: " + String.format("%.2f", lspeed));
    }
}