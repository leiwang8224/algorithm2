package CTCI.OOP;

import java.util.ArrayList;
import java.util.List;

/**
 * Imagine you have a call center with three levels of employees, respondent,
 * manager, and director. An incoming phone call must be first allocated to a respondent
 * who is free. If the respondent can't handle the call, the or she must escalate the call
 * to a manager. If the manager is not free or not able to handle it, then the call should be escalated
 * to a director. Design the classes and data structures for this problem. Implement a method
 * dispatchCall() which assigns a call to the first available employee.
 */
public class CallCenter {
    public static void main(String[] args) {
        CallHandler ch = new CallHandler();

    }

    static class CallHandler {
        // init with 3 levels: respondents, managers, directors
        private final int LEVELS = 3;

        private final int NUM_RESPONDENTS = 10;
        private final int NUM_MANAGERS = 4;
        private final int NUM_DIRECTORS = 2;

        /**
         * List of employees, by level.
         * employeeLevels[0] = respondents
         * employeeLevels[1] = managers
         * employeeLevels[2] = directors
         */
        List<List<Employee>> employeeLevels;

        // queues for each call's rank
        List<List<Call>> callQueues;

        CallHandler() {
            employeeLevels = new ArrayList<>(LEVELS);
            callQueues = new ArrayList<>(LEVELS);

            // create respondents
            ArrayList<Employee> respondents = new ArrayList<>(NUM_RESPONDENTS);
            for (int k = 0; k < NUM_RESPONDENTS - 1; k++) {
                respondents.add(new Respondent(this));
            }
            employeeLevels.add(respondents);

            // create managers
            ArrayList<Employee> managers = new ArrayList<>(NUM_MANAGERS);
            managers.add(new Manager(this));
            employeeLevels.add(managers);

            // create directors
            ArrayList<Employee> directors = new ArrayList<>(NUM_DIRECTORS);
            directors.add(new Director(this));
            employeeLevels.add(directors);
        }

        /**
         * Gets the first available employee who can handle the call
         * @param call
         * @return
         */
        Employee getHandlerForCall(Call call) {
            for (int level = call.getRank().getValue(); level < LEVELS-1; level++) {
                List<Employee> employeeLevel = employeeLevels.get(level);
                for (Employee emp : employeeLevel) {
                    if (emp.isFree()) return emp;
                }
            }
            return null;
        }

        /**
         * Routes the call to an available employee, or saves in a queue if no employee available
         * @param caller
         */
        void dispatchCall(Caller caller) {
            Call call = new Call(caller);
            dispatchCall(call);
        }

        void dispatchCall(Call call) {
            Employee emp = getHandlerForCall(call);
            if (emp != null) {
                emp.receiveCall(call);
                call.setHandler(emp);
            } else {
                call.reply("Please wait for free employee to reply");
                callQueues.get(call.getRank().getValue()).add(call);
            }
        }

        /**
         * An employee got free. Look for a waiting call that he / she can serve.
         * Return true if we were able to assign a call, false otherwise.
         * @param emp
         * @return
         */
        boolean assignCall(Employee emp) {
            for (int rank = emp.getRank().getValue(); rank >= 0; rank--) {
                List<Call> que = callQueues.get(rank);

                if (que.size() > 0) {
                    Call call = que.remove(0);
                    if(call != null) {
                        emp.receiveCall(call);
                        return true;
                    }
                }
            }
            return false;
        }

    }

    static class Respondent extends Employee {
        Respondent(CallHandler callHandler) {
            super(callHandler);

        }
    }

    static class Director extends Employee {
        Director(CallHandler callHandler) {
            super(callHandler);
            rank = Rank.Director;
        }
    }

    static class Manager extends Employee {
        Manager(CallHandler callHandler) {
            super(callHandler);
            rank = Rank.Manager;
        }
    }



    static class Call {
        // minimal rank of employee who can handle this call
        private Rank minRank;

        // person who is calling
        private Caller caller;

        // employee who is handling the call
        private Employee handler;

        Call(Caller c) {
            minRank = Rank.Responder;
            caller = c;
        }

        void setHandler(Employee e) {
            handler = e;
        }

        void reply(String message) {
            System.out.println(message);
        }

        Rank getRank() {
            return minRank;
        }

        void setRank(Rank r) {
            minRank = r;
        }

        Rank incrementRank() {
            if (minRank == Rank.Responder) {
                minRank = Rank.Manager;
            } else if (minRank == Rank.Manager) {
                minRank = Rank.Director;
            }
            return minRank;
        }

        void disconnect() {
            reply("Thank you for calling");
        }
    }

    static class Employee {
        Call currentCall = null;
        protected Rank rank;
        CallHandler callHandler;

        Employee(CallHandler handler) {
            callHandler = handler;
        }
        
        void receiveCall(Call call) {
            currentCall = call;
        }
        
        void callCompleted() {
            if (currentCall != null) {
                currentCall.disconnect();
                currentCall = null;
            }
            
            assignNewCall();
        }

        private boolean assignNewCall() {
            if(!isFree()) return false;
            return callHandler.assignCall(this);
        }

        boolean isFree() {
            return currentCall == null;
        }

        Rank getRank() {
            return rank;
        }

    }

    static class Caller {
        private String name;
        private int userId;
        Caller(int id, String nm) {
            name = nm;
            userId = id;
        }

    }

    static enum Rank {
        Responder(0),
        Manager(1),
        Director(2);

        private int value;
        private Rank (int v) {
            value = v;
        }

        int getValue() {
            return value;
        }

        
    }
    

    
}
