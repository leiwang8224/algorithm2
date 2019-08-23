package CTCI.StacksAndQueue;

import java.util.LinkedList;

// Animal shelter that always provides the oldest dogs or cats (order they came to the shelter)
public class AnimalShelter {
    public static void main(String[] args) {

    }

    /**
     * All dogs and cats need order and compare age
     */
    abstract class Animal {
        private int order;
        String name;
        Animal(String givenName) {
            name = givenName;
        }

        abstract String name();

        void setOrder(int order) {
            this.order = order;
        }

        int getOrder() {
            return this.order;
        }

        boolean isOlderThan(Animal animal) {
            return this.order < animal.order;
        }
    }

    // distinguish Cat and Dog by the name() method
    class Cat extends Animal {

        Cat(String n) {super(n);}

        @Override
        String name() {
            return "Cat " + name;
        }
    }

    class Dog extends Animal {
        Dog(String n) {super(n);}

        @Override
        String name() {
            return "Dog " + name;
        }
    }

    // maintain two linked lists
    class AnimalQueue {
        LinkedList<Dog> dogs = new LinkedList<>();
        LinkedList<Cat> cats = new LinkedList<>();
        private int order = 0;

        void enqueue(Animal animal) {
            animal.setOrder(order);
            order++;
            if (animal instanceof Dog) dogs.addLast((Dog)animal);
            else if (animal instanceof Cat) cats.addLast((Cat)animal);
        }

        // gets the oldest animal, dog or cat
        Animal dequeAny()  {
            if (dogs.size() == 0) return dequeCats();
            else if (cats.size() == 0) return dequeDogs();

            Dog dog = dogs.peek();
            Cat cat = cats.peek();

            if (dog.isOlderThan(cat)) return dogs.poll();
            else return cats.poll();
        }

        int size() {
            return dogs.size() + cats.size();
        }
        
        Dog dequeDogs() {
            return dogs.poll();
        }

        Cat dequeCats() {
            return cats.poll();
        }

        Dog peekDogs() {
            return dogs.peek();
        }

        Cat peekCats()  {
            return cats.peek();
        }
    }
    
}
