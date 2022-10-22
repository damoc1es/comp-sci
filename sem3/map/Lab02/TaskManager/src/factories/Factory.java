package factories;

import containers.Container;
import utils.StrategyType;

public interface Factory {
    Container createContainer(StrategyType strategy);
}
