package args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        try{
            Constructor<?> firstConstructor = optionsClass.getDeclaredConstructors()[0];
            List<String> argList = Arrays.asList(args);
            Object[] values = Arrays.stream(firstConstructor.getParameters())
                    .map(it -> parseOption(argList, it))
                    .toArray();
            return (T) firstConstructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> argList, Parameter OptionParameter) {
        Object value = null;
        String flagName = OptionParameter.getAnnotation(Option.class).value();
        if (OptionParameter.getType() == boolean.class) {
            value = argList.contains("-" + flagName);
        }
        if (OptionParameter.getType() == int.class) {
            int indexOfFlag = argList.indexOf("-" + flagName);
            value = Integer.valueOf(argList.get(indexOfFlag + 1));
        }
        if (OptionParameter.getType() == String.class) {
            int indexOfFlag = argList.indexOf("-" + flagName);
            value = argList.get(indexOfFlag + 1);
        }
        return value;
    }
}
