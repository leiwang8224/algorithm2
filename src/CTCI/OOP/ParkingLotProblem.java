package CTCI.OOP;

import java.util.ArrayList;
import java.util.Random;

/**
 * Design a parking lot using oop
 * - Parking lot has multiple levels. Each level has multiple rows of spots.
 * - Parking lot can park motorcycles, cars, and buses.
 * - Parking lot has motorcycle spots, compact spots, and large spots
 * - Motorcycle can park in any spots
 * - Car can park in either a single compact spot or a single large spot.
 * - A bus can park in five large spots that are consecutive and within same row.
 */
public class ParkingLotProblem {
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot();

        Vehicle vehicle = null;
        while (vehicle == null || lot.parkVehicle(vehicle)) {
            lot.print();
            int r = Math.round(new Random().nextFloat() * 10);
            if (r < 2) vehicle = new Bus();
            else if (r < 4) vehicle = new Motorcycle();
            else vehicle = new Car();
            System.out.print("\nParking a ");
            vehicle.print();
            System.out.println();
        }
        System.out.println("Parking failed. Final state: ");
        lot.print();
    }

    /**
     * ParkingLot is a wrapper class for an array of levels. By implementing
     * this way we are able to separate out logic that deals with actually finding
     * free spots and parking cars out from the broader actions of the ParkingLot.
     * If we didn't do it this way, we would need to hold parking spots in some
     * sort of double array (or hash table which maps from a level number to
     * the list of spots). It's cleaner just separate ParkingLot from Level.
     */
    static class ParkingLot {
        private Level[] levels;
        private final int NUM_LEVELS = 5;

        ParkingLot() {
            levels = new Level[NUM_LEVELS];
            for (int index = 0; index < NUM_LEVELS; index++)
                levels[index] = new Level(index, 30);
        }

        boolean parkVehicle(Vehicle vehicle) {
            for (int index = 0; index < levels.length; index++) {
                if (levels[index].parkVehicle(vehicle)) return true;
            }
            return false;
        }

        void print() {
            for (int index = 0; index < levels.length; index++) {
                System.out.print("Level" + index + ": ");
                levels[index].print();
                System.out.println();
            }
            System.out.println();
        }
    }

    enum VehicleSize {
        Motorcycle,
        Compact,
        Large,
    }

    static class Car extends Vehicle {
        Car() {
            spotsNeeded = 1;
            size = VehicleSize.Compact;
        }

        boolean canFitInSpot(ParkingSpot spot) {
            return spot.getSize() == VehicleSize.Large ||
                   spot.getSize() == VehicleSize.Compact;
        }

        void print() {
            System.out.print("C");
        }
    }

    static class Motorcycle extends Vehicle {
        Motorcycle() {
            spotsNeeded = 1;
            size = VehicleSize.Motorcycle;
        }

        boolean canFitInSpot(ParkingSpot spot) {
            return true;
        }

        void print() {
            System.out.print("M");
        }
    }

    static class Bus extends Vehicle {
        Bus() {
            spotsNeeded = 5;
            size = VehicleSize.Large;
        }

        boolean canFitInSpot(ParkingSpot spot) {
            return spot.getSize() == VehicleSize.Large;
        }

        void print() {
            System.out.print("B");
        }
    }

    /**
     * Note that the vehicle is also tied to the parking spot
     * Information for the parking lot is embedded in the vehicle
     */
    static abstract class Vehicle {
        protected ArrayList<ParkingSpot> parkingSpots = new ArrayList<ParkingSpot>();
        protected String licensePlate;
        protected int spotsNeeded;
        protected VehicleSize size;

        int getSpotsNeeded() {
            return spotsNeeded;
        }

        VehicleSize getSize() {
            return size;
        }

        void parkInSpot(ParkingSpot spot) {
            parkingSpots.add(spot);
        }

        void clearSpots() {
            for (int index = 0; index < parkingSpots.size(); index++) {
                parkingSpots.get(index).removeVehicle();
            }
            parkingSpots.clear();
        }

        abstract boolean canFitInSpot(ParkingSpot spot);
        abstract void print();
    }

    /**
     *  Find available spots
     *  Can I park a vehicle?
     *  Park a vehicle starting at position...
     *  Open up spot at the level
     */
    static class Level {
        private int floor;
        private ParkingSpot[] spots;
        private int availableSpots = 0;
        private static final int SPOTS_PER_ROW = 10;

        Level (int floor, int numberSpots) {
            this.floor = floor;
            spots = new ParkingSpot[numberSpots];
            int largeSpots = numberSpots / 4;
            int bikeSpots = numberSpots / 4;
            int compactSpots = numberSpots - largeSpots - bikeSpots;

            for (int index = 0; index < numberSpots; index++) {
                VehicleSize sz = VehicleSize.Motorcycle;
                if (index < largeSpots) {
                    sz = VehicleSize.Large;
                } else if ( index < largeSpots + compactSpots) {
                    sz = VehicleSize.Compact;
                }

                int row = index / SPOTS_PER_ROW;
                spots[index] = new ParkingSpot(this, row, index, sz);
            }
            availableSpots = numberSpots;
        }

        int availableSpots() {
            return availableSpots;
        }

        boolean parkVehicle(Vehicle vehicle) {
            if (availableSpots() < vehicle.getSpotsNeeded()) return false;

            int spotNumber = findAvailableSpots(vehicle);
            if (spotNumber < 0) return false;
            return parkStartingAtSpot(spotNumber, vehicle);
        }

        boolean parkStartingAtSpot(int spotNumber, Vehicle vehicle) {
            vehicle.clearSpots();
            boolean success = true;
            for (int index = spotNumber; index < spotNumber + vehicle.spotsNeeded; index++)
                success &= spots[index].park(vehicle);
            availableSpots -= vehicle.spotsNeeded;
            return success;
        }

        int findAvailableSpots(Vehicle vehicle) {
            int spotsNeeded = vehicle.getSpotsNeeded();
            int lastRow = -1;
            int spotsFound = 0;

            for (int index = 0; index < spots.length; index++) {
                ParkingSpot spot = spots[index];
                if (lastRow != spot.getRow()) {
                    spotsFound = 0;
                    lastRow = spot.getRow();
                }

                if (spot.canFitVehicle(vehicle)) {
                    spotsFound++;
                } else {
                    spotsFound = 0;
                }

                if (spotsFound == spotsNeeded)
                    return index - (spotsNeeded - 1);
            }
            return -1;
        }

        void print() {
            int lastRow = -1;
            for (int index = 0; index < spots.length; index++) {
                ParkingSpot spot = spots[index];
                if (spot.getRow() != lastRow) {
                    System.out.print("  ");
                    lastRow = spot.getRow();
                }
                spot.print();
            }
        }

        void spotFreed() {
            availableSpots++;
        }


    }

    /**
     *   Is the parking spot available?
     *   Can I fit a vehicle inside?
     *   Park the vehicle
     *   Remove the vehicle
     */
    static class ParkingSpot {
        private Vehicle vehicle;
        private VehicleSize spotSize;
        private int row;
        private int spotNumber;
        private Level level;

        ParkingSpot(Level lvl, int row, int spotNumber, VehicleSize sz) {
            this.level = lvl;
            this.row = row;
            this.spotNumber = spotNumber;
            this.spotSize = sz;
        }

        boolean isAvailable() {
            return vehicle == null;
        }

        /**
         * Check if the spot is big enough for the vehicle (and is available).
         * This compare the SIZE only. It does not check if it has enough spots.
         * @param vehicle
         * @return
         */
        boolean canFitVehicle(Vehicle vehicle) {
            return isAvailable() && vehicle.canFitInSpot(this);
        }

        boolean park(Vehicle v) {
            if (!canFitVehicle(v)) return false;

            vehicle = v;
            vehicle.parkInSpot(this);
            return true;
        }

        int getRow() {
            return row;
        }

        int getSpotNumber() {
            return spotNumber;
        }

        VehicleSize getSize() {
            return spotSize;
        }

        void removeVehicle() {
            level.spotFreed();
            vehicle = null;
        }

        void print() {
            if (vehicle == null) {
                if (spotSize == VehicleSize.Compact) {
                    System.out.print("c");
                } else if ( spotSize == VehicleSize.Large) {
                    System.out.print("l");
                } else if (spotSize == VehicleSize.Motorcycle) {
                    System.out.print("m");
                }
            } else {
                vehicle.print();
            }
        }
        
    }
}
