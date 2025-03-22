import java.io.*;
import java.util.*;

public class ProcessSchedulingSim {

    public static List<Process> readProcesses(String filename) throws IOException {
        List<Process> processes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        br.readLine(); // Skip header
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            int pid = Integer.parseInt(parts[0]);
            int at = Integer.parseInt(parts[1]);
            int bt = Integer.parseInt(parts[2]);
            int pr = Integer.parseInt(parts[3]);
            processes.add(new Process(pid, at, bt, pr));
        }
        br.close();
        return processes;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java ProcessSchedulingSim <process_file.txt>");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        List<Process> processes = readProcesses(args[0]);

        System.out.println("Choose Scheduling Algorithm:");
        System.out.println("1. First-Come, First-Served (FCFS)");
        System.out.println("2. Shortest Job First (SJF)");
        System.out.println("3. Memory Management");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("\n--- First-Come, First-Served (FCFS) ---");
            List<Process> fcfsProcesses = new ArrayList<>();
            for (Process p : processes) fcfsProcesses.add(new Process(p.pid, p.arrivalTime, p.burstTime, p.priority));
            List<ExecutionStep> fcfsTimeline = Scheduler.fcfs(fcfsProcesses);
            Scheduler.displayGanttChart(fcfsTimeline);
            Scheduler.printMetrics(fcfsProcesses);

        } else if (choice == 2) {
            System.out.println("\n--- Shortest Job First (SJF) ---");
            List<Process> sjfProcesses = new ArrayList<>();
            for (Process p : processes) sjfProcesses.add(new Process(p.pid, p.arrivalTime, p.burstTime, p.priority));
            List<ExecutionStep> sjfTimeline = Scheduler.sjf(sjfProcesses);
            Scheduler.displayGanttChart(sjfTimeline);
            Scheduler.printMetrics(sjfProcesses);

        } else if (choice == 3) {
            System.out.println("\n--- Memory Management ---");
            List<MemoryBlock> memoryBlocks = new ArrayList<>();
            memoryBlocks.add(new MemoryBlock(0, 100));
            memoryBlocks.add(new MemoryBlock(100, 200));
            memoryBlocks.add(new MemoryBlock(300, 150));
            memoryBlocks.add(new MemoryBlock(450, 250));

            System.out.println("Choose Allocation Strategy:");
            System.out.println("1. First-Fit");
            System.out.println("2. Best-Fit");
            System.out.println("3. Worst-Fit");
            int allocChoice = scanner.nextInt();

            System.out.println("Enter process sizes (end with -1):");
            while (true) {
                int size = scanner.nextInt();
                if (size == -1) break;
                switch (allocChoice) {
                    case 1 -> Scheduler.simulateFirstFit(memoryBlocks, size);
                    case 2 -> Scheduler.simulateBestFit(memoryBlocks, size);
                    case 3 -> Scheduler.simulateWorstFit(memoryBlocks, size);
                    default -> System.out.println("Invalid strategy.");
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
