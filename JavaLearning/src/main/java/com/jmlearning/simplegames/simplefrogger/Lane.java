package com.jmlearning.simplegames.simplefrogger;

import java.util.ArrayList;

public class Lane {

    private int direction;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private ArrayList<Car> carList = new ArrayList <Car>();

    private double yPosition;
    private double width;
    private double speed;
    private boolean createNewCars;

    public Lane(double yPosition) {

        this.yPosition = yPosition;
        width = 0.2;
        direction = (int) Math.round(Math.random());
        speed = 0.02;
        createNewCars = true;
    }

    public void drawLane() {

        for(int i = 0; i < carList.size(); i++) {

            Car car = carList.get(i);
            car.drawCar(new StdDraw());
        }
    }

    public void stepCars() {

        if(createNewCars && Math.random() < .05) {

            double x = 0;
            double dx = 0;

            if(direction == LEFT) {

                x = 1.2;
                dx = -speed;
            }
            else {

                x = -0.2;
                dx = speed;
            }

            Car car = new Car(x, yPosition, width / 2, dx);
            carList.add(car);
        }

        for(int i = 0; i < carList.size(); i++) {

            Car car = carList.get(i);
            car.moveCar();

            if(car.getX() < -.5 || car.getX() > 1.5) {

                carList.remove(i);
                i--;
            }
        }

        if(Math.random() < 0.01) {

            direction = (direction + 1) % 2;
            createNewCars = false;
        }

        if(carList.isEmpty()) {

            createNewCars = true;
        }
    }

    public boolean didHitFrog(Frog frog) {

        for(int i = 0; i < carList.size(); i++) {

            Car car = carList.get(i);

            if(car.didHitFrog(frog)) {

                return true;
            }
        }

        return false;
    }
}
