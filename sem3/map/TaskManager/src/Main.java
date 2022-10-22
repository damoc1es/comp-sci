import utils.StrategyType;

public class Main {
    public static void main(String[] args) {
        StrategyType strat;

        if(args.length > 0) {
            switch (args[0].toUpperCase()) {
                case "LIFO" -> strat = StrategyType.LIFO;
                case "FIFO" -> strat = StrategyType.FIFO;
                default -> strat = StrategyType.LIFO;
            }
        } else {
            strat = StrategyType.LIFO;
        }

        TestRunner.run(strat);
        TestRunner.runLab(strat);
    }
}