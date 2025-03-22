class MemoryBlock {
    int start, size;
    boolean isFree;

    MemoryBlock(int start, int size) {
        this.start = start;
        this.size = size;
        this.isFree = true;
    }
}
