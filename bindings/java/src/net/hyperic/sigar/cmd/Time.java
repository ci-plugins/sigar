package net.hyperic.sigar.cmd;

import net.hyperic.sigar.Sigar;
import net.hyperic.sigar.SigarException;
import net.hyperic.sigar.ThreadCpu;

public class Time extends SigarCommandBase {

    public Time(Shell shell) {
        super(shell);
    }

    public Time() {
        super();
    }

    protected boolean validateArgs(String[] args) {
        return args.length >= 1;
    }

    public String getSyntaxArgs() {
        return "[command] [...]";
    }

    public String getUsageShort() {
        return "Time command";
    }

    public void output(String[] args) throws SigarException {
        ThreadCpu cpu = new ThreadCpu();
        cpu.gather(this.sigar, 0);
        long user = cpu.getUser();
        long sys = cpu.getSys();
        long start = System.currentTimeMillis();

        this.shell.handleCommand("time " + args[0], args);

        cpu.gather(this.sigar, 0);
        println("real....." +
                format((System.currentTimeMillis() - start)));
        println("user....." + format(toMillis(cpu.getUser() - user)));
        println("sys......" + format(toMillis(cpu.getSys() - sys)));
    }

    private static long toMillis(long nano) {
        return nano / 1000000;
    }

    private static String format(long t) {
        t /= 1000;
        return t/60 + ":" + t%60;
    }

    public static void main(String[] args) throws Exception {
        new Time().processCommand(args);
    }
}
