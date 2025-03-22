public class Process {
    int pid, arrivalTime, burstTime, priority;
    int startTime = -1, completionTime, waitingTime, turnaroundTime;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
