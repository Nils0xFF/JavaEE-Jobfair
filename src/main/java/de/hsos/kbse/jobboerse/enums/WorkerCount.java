package de.hsos.kbse.jobboerse.enums;

/**
 *
 * @author lennartwoltering
 */
public enum WorkerCount {
    minimal("1-10"),
    low("10-50"),
    middle("50-100"),
    high("100-500"),
    maximal("500+");
    
    private final String count;
    
    private WorkerCount(String count){
        this.count = count;
    }
    
    public String getCount(){
        return count;
    }
    
    
}
