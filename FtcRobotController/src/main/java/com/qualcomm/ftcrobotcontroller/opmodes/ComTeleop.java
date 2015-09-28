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


public class ComTeleop extends OpMode {

    private double lMotorSpeed = 0.0;
    private double rMotorSpeed = 0.0;

    DcMotor rMotor;
    DcMotor lMotor;

    @Override
    public void init() {
        rMotor = hardwareMap.dcMotor.get("right_motor");
        lMotor = hardwareMap.dcMotor.get("left_motor");

        lMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {

    }
}