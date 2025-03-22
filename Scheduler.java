import java.util.*;

public class Scheduler {
    public static List<ExecutionStep> fcfs(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int time = 0;
        List<ExecutionStep> timeline = new ArrayList<>();

        for (Process p : processes) {
            if (time < p.arrivalTime) time = p.arrivalTime;
            p.startTime = time;
            time += p.burstTime;
            p.completionTime = time;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            timeline.add(new ExecutionStep(p.pid, p.startTime, p.completionTime));
        }
        return timeline;
    }

    public static List<ExecutionStep> sjf(List<Process> processes) {
        List<Process> all = new ArrayList<>(processes);
        all.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int time = 0, completed = 0, index = 0;
        int n = all.size();
        List<Process> readyQueue = new ArrayList<>();
        List<ExecutionStep> timeline = new ArrayList<>();

        while (completed < n) {
            while (index < n && all.get(index).arrivalTime <= time) {
                readyQueue.add(all.get(index));
                index++;
            }
            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(p -> p.burstTime));
                Process p = readyQueue.remove(0);
                p.startTime = time;
                time += p.burstTime;
                p.completionTime = time;
                p.turnaroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
                timeline.add(new ExecutionStep(p.pid, p.startTime, p.completionTime));
                completed++;
            } else {
                time = all.get(index).arrivalTime;
            }
        }
        return timeline;
    }

    public static void printMetrics(List<Process> processes) {
        int totalWT = 0, totalTAT = 0, totalBT = 0;
        System.out.println("\nPID\tAT\tBT\tWT\tTAT");
        for (Process p : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\n", p.pid, p.arrivalTime, p.burstTime, p.waitingTime, p.turnaroundTime);
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
            totalBT += p.burstTime;
        }
        int n = processes.size();
        System.out.printf("\nAverage Waiting Time: %.2f\n", (double) totalWT / n);
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTAT / n);
        System.out.printf("CPU Utilization: %.2f%%\n", (double) totalBT / processes.get(n - 1).completionTime * 100);
    }

    public static void displayGanttChart(List<ExecutionStep> timeline) {
        for (ExecutionStep step : timeline) {
            System.out.print("| P" + step.pid + " ");
        }
        System.out.println("|");
        for (ExecutionStep step : timeline) {
            System.out.print(step.startTime + "   ");
        }
        System.out.println(timeline.get(timeline.size() - 1).endTime);
    }

    public static void simulateFirstFit(List<MemoryBlock> memoryBlocks, int processSize) {
        for (MemoryBlock block : memoryBlocks) {
            if (block.isFree && block.size >= processSize) {
                System.out.println("Allocated process of size " + processSize + " to block starting at " + block.start);
                block.isFree = false;
                return;
            }
        }
        System.out.println("Could not allocate process of size " + processSize + " (no suitable block found)");
    }

    public static void simulateBestFit(List<MemoryBlock> memoryBlocks, int processSize) {
        MemoryBlock bestBlock = null;
        for (MemoryBlock block : memoryBlocks) {
            if (block.isFree && block.size >= processSize) {
                if (bestBlock == null || block.size < bestBlock.size) {
                    bestBlock = block;
                }
            }
        }
        if (bestBlock != null) {
            System.out.println("Allocated process of size " + processSize + " to block starting at " + bestBlock.start);
            bestBlock.isFree = false;
        } else {
            System.out.println("Could not allocate process of size " + processSize + " (no suitable block found)");
        }
    }

    public static void simulateWorstFit(List<MemoryBlock> memoryBlocks, int processSize) {
        MemoryBlock worstBlock = null;
        for (MemoryBlock block : memoryBlocks) {
            if (block.isFree && block.size >= processSize) {
                if (worstBlock == null || block.size > worstBlock.size) {
                    worstBlock = block;
                }
            }
        }
        if (worstBlock != null) {
            System.out.println("Allocated process of size " + processSize + " to block starting at " + worstBlock.start);
            worstBlock.isFree = false;
        } else {
            System.out.println("Could not allocate process of size " + processSize + " (no suitable block found)");
        }
    }
}
